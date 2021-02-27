package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI component to sign out from the game, via POST.
 *
 * @author Danilo Sosa
 */
public class PostSignOutRoute implements Route {

	private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
	private PlayerLobby playerLobby;

	public PostSignOutRoute(PlayerLobby playerLobby) {
		LOG.config("PostSignOutRoute is initialized.");
		Objects.requireNonNull(playerLobby, "playerLobby can't be null");
		this.playerLobby = playerLobby;
	}

	@Override
	public Object handle(Request request, Response response) {

		UserSession userSession = request.session().attribute("UserSession");

		Player player = userSession.getPlayer();

		//Player can only sign out if he is not in a game
		if(!player.isPlaying()){
			playerLobby.removeUser(player.getUsername());
			request.session().removeAttribute("UserSession");
		}

		response.redirect("/");

		return null;
	}
}
