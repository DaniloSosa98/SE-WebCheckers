package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.net.URLDecoder;
import java.util.logging.Logger;

/**
 * The UI component to check if opponent has taken turn, via POST.
 *
 * @author Elizabeth Sherrock
 * @author Joseph Saltalamacchia
 */
public class PostCheckTurnRoute implements Route {
	private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
	private final Gson gson;
	private GameCenter gameCenter;

	/**
	 * Creates new PostCheckTurnRoute
	 *
	 * @param gson The Google JSON parser object used to render Ajax responses.
	 */
	public PostCheckTurnRoute(Gson gson, GameCenter gameCenter) {
		this.gson = gson;
		this.gameCenter = gameCenter;
		LOG.config("PostCheckTurnRoute is initialized.");
	}

	@Override
	public Object handle(Request request, Response response) {
		Session session = request.session();
		UserSession userSession = session.attribute(Constants.USER_SESSION_ATTRIBUTE);

		Player currentUser = userSession.getPlayer();

		Game game = gameCenter.getGameForPlayer(currentUser.getUsername());

		Constants.Color userColor = userSession.getPlayer().getColor();
		if(game.getActiveColor().equals(userColor)) {
			return gson.toJson(Message.info("true"));
		}
		return gson.toJson(Message.info("false"));
	}
}
