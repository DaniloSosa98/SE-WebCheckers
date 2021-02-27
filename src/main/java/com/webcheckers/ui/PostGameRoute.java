package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI controller to POST the game route.
 *
 * @author Elizabeth Sherrock
 * @author Joseph Saltalamacchia
 */
public class PostGameRoute implements Route {

	//attributes
	private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
	private final PlayerLobby playerLobby;
	GameCenter gameCenter;

	/**
	 * Creates a new Post Start Game Route
	 *
	 * @param playerLobby The lobby from which this game is being started
	 */
	public PostGameRoute(PlayerLobby playerLobby, GameCenter gameCenter) {
		this.playerLobby = Objects.requireNonNull(playerLobby);
		this.gameCenter = gameCenter;
		LOG.config("PostGameRoute is initialized.");
	}

	/**
	 * Handle an incoming POST request on the Game route
	 * (See WebServer for route definition)
	 *
	 * @param      request   The request
	 * @param      response  The response
	 *
	 */
	@Override
	public Object handle(Request request, Response response) {

		Session session = request.session();

		UserSession userSession = session.attribute("UserSession");

		// Create the view model
		Map<String, Object> vm = new HashMap<String, Object>();
		vm.put("viewMode", GetGameRoute.Mode.PLAY); // Set at PLAY for all MVP user stories
		vm.put("title", "Checkers Game");

		Player currentUser = userSession.getPlayer();
		vm.put("currentUser", currentUser);

		// Check if userSession is null, in which case
		// a new user has attempted to navigate directly to this page,
		// and we should redirect them to the homepage, which is the
		// sole creator of a UserSession object.
		if (userSession == null) {
			LOG.fine("userSession null in PostGameRoute -- redirecting to Home");
			response.redirect("/");
			return null;
		}

		Player p1 = userSession.getPlayer();
		Player p2 = playerLobby.getAllUsers().get(request.queryParams("user"));

		if (p2 == null) {
			LOG.fine("Player 2 is null");
			response.redirect("/");
			return null;
		}

		if (p2.isPlaying() || p2.getIsReplaying()) {
			LOG.fine("Player 2 is playing, user needs to select a different player.");
			response.redirect("/?error=true");
			return null;
		}

		// Configure player
		p1.setColor(Constants.Color.RED);
		p2.setColor(Constants.Color.WHITE);
		p1.setPlaying(true);
		p2.setPlaying(true);

		p1.setPieceCount(12);
		p2.setPieceCount(12);

		String redPlayerName;
		String whitePlayerName;

		String p1Username = currentUser.getUsername();
		String p2Username = p2.getUsername();

		if (currentUser.getColor().equals(Constants.Color.RED)) {
			vm.put("redPlayer", currentUser);
			vm.put("whitePlayer", p2);
			redPlayerName = p1Username;
			whitePlayerName = p2Username;
		}
		else {
			vm.put("whitePlayer", currentUser);
			vm.put("redPlayer", p2);

			whitePlayerName = p1Username;
			redPlayerName = p2Username;

			System.out.println(currentUser + "***" + p2 + " This is the one" );
		}

		Game game = new Game((Player) vm.get("redPlayer"), (Player) vm.get("whitePlayer"), redPlayerName + whitePlayerName);
		System.out.println(game+ "AFTER GAME CREATION");
		gameCenter.addGame(game.getID(), game);



		// Redirect back to game
		response.redirect("/game");
		return null;
	}
}
