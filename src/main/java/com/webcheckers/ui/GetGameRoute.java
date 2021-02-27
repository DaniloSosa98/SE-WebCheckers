package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UI controller to GET the game route.
 *
 * @author Elizabeth Sherrock
 * @author Joseph Saltalamacchia
 */
public class GetGameRoute implements Route {

	//attributes
	private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
	private final TemplateEngine templateEngine;
	private final GameCenter gameCenter;
	private final Gson gson;
	private ReplayList replayList;

	enum Mode {
		PLAY
	}

	/**
	 * Creates a new Game Route
	 * @param templateEngine the HTML template rendering engine
	 * @param gameCenter The Game center that this Game route is going to connect to
	 */
	public GetGameRoute(final Gson gson, final TemplateEngine templateEngine, GameCenter gameCenter, ReplayList replayList) {
		this.gson = gson;
		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		this.gameCenter = Objects.requireNonNull(gameCenter);
		this.replayList = replayList;
		LOG.config("GetGameRoute is initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(Request request, Response response) {

		LOG.fine("=============================== CALLED GETGAME =================================");

		// Create the view model
		Map<String, Object> vm = new HashMap<String, Object>();
		vm.put("viewMode", Mode.PLAY); // Set at PLAY for all MVP user stories
		vm.put("title", "Checkers Game");

		UserSession userSession = request.session().attribute("UserSession");

		// Check if userSession is null, in which case
		// a new user has attempted to navigate directly to this page,
		// and we should redirect them to the homepage, which is the
		// sole creator of a UserSession object.
		if (userSession == null) {
			LOG.fine("userSession null in GetGameRoute -- redirecting to Home");
			response.redirect("/");
			return null;
		}

		// Sanity check
		if (!gameCenter.hasGames()) {
			LOG.log(Level.SEVERE, "No games exist when GetGameRoute was called.");
			return null;
		}

		Player currentUser = userSession.getPlayer();
		vm.put("currentUser", currentUser);

		//create some helper variables to make things readable
		String p1Username = currentUser.getUsername();

		Game game = gameCenter.getGameForPlayer(p1Username);

		vm.put("redPlayer", game.getRedPlayer());
		vm.put("whitePlayer", game.getWhitePlayer());

		// Check game over state, and set the modeOptionsAsJSON accordingly
		// if the game is indeed over.
		if (game.isGameOver()) {

			LOG.fine("GAME IS OVER");

			Map<String, Object> modeOptionsAsJSON = new HashMap<>(2);

			modeOptionsAsJSON.put(Constants.gameOverBooleanKey, true);

			// This is the NON-Active player
			// as the turn is switched after the move is made.
			Player winner;
			if (game.getActiveColor() == Constants.Color.RED) {
				winner = game.getWhitePlayer();
			} else {
				winner = game.getRedPlayer();
			}
			modeOptionsAsJSON.put(Constants.gameOverMessageKey,
				                  Constants.endGameMessage(winner, game.getGameOverReason()));

			// Set players to not playing anymore
			game.getRedPlayer().setPlaying(false);
			game.getWhitePlayer().setPlaying(false);

			// Put our data into the viewModel and render out our page
			LOG.fine(gson.toJson(modeOptionsAsJSON));
			vm.put(Constants.modeOptionsAsJSONKey, gson.toJson(modeOptionsAsJSON));

			// We only want to add the winner
			//
			// This is the strange case where the first player has resigned,
			// and they are immediately taken to the home screen.
			// That player sets gameOver to be true, but they never
			// trigger this route.
			// As a result, the SECOND player will be the only player
			// to enter the gameOver state, as they *do* trigger this route.
			// It is this case that we are generating the replay list for.
			//
			// Side effect: first player will not see a list of replays until
			// the second player returns to the home screen.
			if (currentUser.equals(winner)) {
				LOG.fine("adding the replay");
				this.replayList.addReplay(game.generateReplay());
			}
		}

		// Now, setup the BoardView

		BoardView boardView;
		if(currentUser.getColor().equals(Constants.Color.RED)) {
			boardView = new BoardView(game.getBoard());
		} else {
			boardView = new BoardView(game.getBoard().flipBoard());
		}

		vm.put("board", boardView); // This odd naming is intentional, I blame the JS API -P
		vm.put("activeColor", game.getActiveColor());

		// Save to session
		userSession.setBoardView(boardView);

		// Finally, render the game view out to the client
		return templateEngine.render(new ModelAndView(vm, "game.ftl"));
	}
}
