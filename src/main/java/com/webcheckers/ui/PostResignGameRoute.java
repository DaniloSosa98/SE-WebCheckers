package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.URLDecoder;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI component to resign the game, via POST.
 *
 * @author Elizabeth Sherrock
 * @author Danilo Sosa
 */
public class PostResignGameRoute implements Route {
	private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
	private final Gson gson;
	private GameCenter gameCenter;

	/**
	 * Creates new PostResignGameRoute
	 *
	 * @param gson The Google JSON parser object used to render Ajax responses.
	 */
	public PostResignGameRoute(Gson gson, GameCenter gameCenter) {
		this.gson = gson;
		LOG.config("PostResignGameRoute is initialized.");
		Objects.requireNonNull(gameCenter, "gameCenter can't be null");
		this.gameCenter = gameCenter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(Request request, Response response) {
		UserSession userSession = request.session().attribute("UserSession");

		// Current player resigning
		Player player = userSession.getPlayer();
		String p1Username = userSession.getPlayer().getUsername();

		Game game = gameCenter.getGameForPlayer(p1Username);

		// Calls method to make game.gameIsOver true
		game.playerResign();

		// Now the resigning player is eligible to play other games
		player.setPlaying(false);

		return gson.toJson(Message.info("player resigned"));
	}
}

