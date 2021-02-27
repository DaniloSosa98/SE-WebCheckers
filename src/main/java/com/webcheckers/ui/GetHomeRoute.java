package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.global.UserSession;
import com.webcheckers.global.Constants;
import com.webcheckers.models.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

	//attributes
	private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

	private final TemplateEngine templateEngine;
	private PlayerLobby playerLobby;
	private ReplayList replayList;


	/**
	* Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	*
	* @param templateEngine the HTML template rendering engine
	*/
	public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, ReplayList replayList) {
		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby must not be null");
		this.replayList = Objects.requireNonNull(replayList, "The replayList must not be null");

		LOG.config("GetHomeRoute is initialized.");
	}

	/**
	* Render the WebCheckers Home page.
	*
	* @param request the HTTP request
	* @param response the HTTP response
	*
	* @return the rendered HTML for the Home page
	*/
	@Override
	public Object handle(Request request, Response response) {

		LOG.finer("GetHomeRoute is invoked.");

		// First, we need to configure the Session.
		// If this is not set (i.e., new visitor), set it.
		Session session = request.session();
		if (session.attribute("UserSession") == null) {
			session.attribute("UserSession", new UserSession());
			LOG.fine("New user, set new user session.");
		}

		// Otherwise, we have a returning visitor! (Just continue.)

		// Give ourselves a nice UserSession to work with
		UserSession userSession = request.session().attribute("UserSession");
		Player currentPlayer = userSession.getPlayer();

		// Build the view model
		Map<String, Object> vm = new HashMap<>();

		vm.put("title", "Welcome!");



		vm.put("NumberOfUsers", Constants.numPlayersString(playerLobby.getCurrentNumberOfPlayers()));

		LOG.fine("Current player is not null in get home route: " + String.valueOf(currentPlayer != null));

		// If a player is signed in, send a custom message
		if (currentPlayer != null) {

			// Redirect them to the game screen if they are playing a game
			if (userSession.getPlayer().isPlaying()) {
				response.redirect("/game");
			}

			LOG.fine("PLAYER UPDATING " + currentPlayer);
			vm.put("message", Constants.welcomeMessageWithUsername(currentPlayer.getUsername()));

			vm.put("users", playerLobby.getAllUsersWithout(currentPlayer));
			LOG.fine("users in home: " + playerLobby.getAllUsersWithout(currentPlayer));

			vm.put("currentUser", currentPlayer);

			vm.put("replays", replayList.getAllReplays());
			LOG.fine("all Replays in sever" + replayList.getAllReplays());

			//return the template engine with the player's username
			return templateEngine.render(new ModelAndView(vm , "home.ftl"));
		}

		// Show an error message if the user attempted to select a user
		// from the list (when signed in) that is already in a game.

		String errorAttribute = request.queryParams("error");

		if (errorAttribute != null && errorAttribute.equals("true")) {
			vm.put("message", Constants.userAlreadyInGameMessage);
		} else {
			vm.put("message", Constants.welcomeMessage);
		}
		// Render the Home view
		return templateEngine.render(new ModelAndView(vm , "home.ftl"));
	}
}
