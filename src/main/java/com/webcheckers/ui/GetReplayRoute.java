package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Player;
import com.webcheckers.models.Replay;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UI controller to GET the replay route.
 *
 * @author Peter Kos
 */
public class GetReplayRoute implements Route {

	//attributes
	private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
	private final TemplateEngine templateEngine;
	private final GameCenter gameCenter;
	private final ReplayList replayList;
	private final Gson gson;

	enum Mode {
		REPLAY
	}

	/**
	 * Creates a new Game Route
	 * @param templateEngine the HTML template rendering engine
	 * @param gameCenter The Game center that this Game route is going to connect to
	 */
	public GetReplayRoute(final Gson gson, final TemplateEngine templateEngine, GameCenter gameCenter,
						  ReplayList replayList) {
		this.gson           = gson;
		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		this.gameCenter     = Objects.requireNonNull(gameCenter);
		this.replayList = Objects.requireNonNull(replayList);
		LOG.config("GetReplayRoute is initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(Request request, Response response) {

		LOG.fine("=============================== CALLED REPLAYROUTE =================================");

		// Create the view model
		Map<String, Object> vm = new HashMap<String, Object>();
		vm.put("viewMode", Mode.REPLAY); // Set at PLAY for all MVP user stories
		vm.put("title", "Checkers Game");

		UserSession userSession = request.session().attribute("UserSession");

		// Check if userSession is null, in which case
		// a new user has attempted to navigate directly to this page,
		// and we should redirect them to the homepage, which is the
		// sole creator of a UserSession object.
		if (userSession == null) {
			LOG.fine("userSession null in GetReplayRoute -- redirecting to Home");
			response.redirect("/");
			return null;
		}

		// Sanity check
		if (!gameCenter.hasGames()) {
			LOG.log(Level.SEVERE, "No games exist when GetReplayRoute was called.");
			return null;
		}

		// Using our parameter gameID, get the game we are trying to replay.
		String gameID = request.queryParams("gameID");
		Game game = gameCenter.getGame(gameID);
		LOG.fine("gameID in the GetReplayRoute: " + gameID);

		// USE OLD GAME DATA to copy SOME parameters in the new game
		Player redPlayer = game.getRedPlayer();
		Player whitePlayer = game.getWhitePlayer();

		vm.put("redPlayer", redPlayer);
		vm.put("whitePlayer", whitePlayer);

		// Now, we need to set the replay mode.
		Map<String, Object> modeOptionsAsJSON = new HashMap<>(2);

		Replay currentReplay = replayList.getReplayWithID(gameID);
		LOG.fine("current replay is" + currentReplay);

		if (currentReplay != null) {
			modeOptionsAsJSON.put(Constants.hasNextKey, currentReplay.hasNextMove());
			modeOptionsAsJSON.put(Constants.hasPreviousKey, currentReplay.hasPreviousMove());
		}

		// Put our modeOptions data into the viewModel
		LOG.fine(gson.toJson(modeOptionsAsJSON));
		vm.put(Constants.modeOptionsAsJSONKey, gson.toJson(modeOptionsAsJSON));


		// Reset the game board ON FIRST LAUNCH ONLY!
		// (Or really, with this implementation, every time the user is viewing the first move.)
		if (currentReplay.isInitialState()) {
			LOG.fine("RESETTING BOARD");
			Game newGame = new Game(redPlayer, whitePlayer, redPlayer.getUsername() + whitePlayer.getUsername());
			gameCenter.replaceGame(newGame, gameID);

			// Re-save our copy of it for the initial turn
			game = gameCenter.getGame(gameID);
		} else {
			LOG.fine("NOT RESETTING BOARD");
		}

		// Because the model needs it...
		vm.put("currentUser", userSession.getPlayer());

		// Now, setup the BoardView
		// @FIXME: ALWAYS FROM RED PLAYER PERSPECTIVE.... unless this code is changed!
		BoardView boardView = new BoardView(game.getBoard());

		// Put the board in
		vm.put("board", boardView);
		vm.put("activeColor", game.getActiveColor());

		// Set the player replay to true
		userSession.getPlayer().setIsReplaying(true);

		// Save to session
		userSession.setBoardView(boardView);

		// Finally, render the game view out to the client, in replay mode
		return templateEngine.render(new ModelAndView(vm, "game.ftl"));
	}
}
