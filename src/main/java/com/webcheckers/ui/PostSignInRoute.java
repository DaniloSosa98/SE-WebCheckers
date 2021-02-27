package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the Sign In page, and sign a user in.
 *
 * @author Peter Kos
 * @author Joseph Saltalamacchia
 */
public class PostSignInRoute implements Route {

	//
	private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
	private final TemplateEngine templateEngine;
	private PlayerLobby playerLobby;

	/**
	* Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	*
	* @param templateEngine the HTML template rendering engine
	*/
	public PostSignInRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {

		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		this.playerLobby = Objects.requireNonNull(playerLobby);
		LOG.config("PostSignInRoute is initialized.");

	}

	/**
	* Render the WebCheckers Sign In page.
	*
	* @param request the HTTP request
	* @param response the HTTP response
	*
	* @return the rendered HTML for the Sign In page
	*/
	@Override
	public Object handle(Request request, Response response) {

		// Note: User session is instantiated in the home route,
		// but checked in the GetSignInRoute instead of here,
		// as it would be strange to redirect the user to the homepage AFTER
		// they entered their information.

		// View model, and a quick log
		Map<String, Object> viewModel = new HashMap<>();
		LOG.finer("PostSignInRoute is invoked.");

		// Grab the user session
		UserSession userSession = request.session().attribute("UserSession");

		// Check if userSession is null, in which case
		// a new user has attempted to navigate directly to this page,
		// and we should redirect them to the homepage, which is the
		// sole creator of a UserSession object.
		if (userSession == null) {
			LOG.fine("userSession null in PostSignInRoute -- redirecting to Home");
			response.redirect("/");
			return null;
		}

		// Get the username of the user
		String username = request.queryParams("username");

		// Check if the username is valid: that is,
		// a username that contains at least one alphanumeric character,
		// and is NOT already in use.
		// If not valid, show an error to the user.
		if (!playerLobby.isValidUsername(username)) {
			LOG.warning("Invalid username \"" + username + "\" entered. Returning to sign in...");
			viewModel.put("message", Constants.invalidUsernameMessage);
			return templateEngine.render(new ModelAndView(viewModel, "signin.ftl"));
		}

		// At this point, the username is valid.
		// So, we create a Player and add them to the session.
		Player newPlayer = new Player(username);
		userSession.setPlayer(newPlayer);

		// Add the user to the lobby
		playerLobby.addUser(newPlayer.getUsername(), newPlayer);
		LOG.fine("Added user " + newPlayer.getUsername() + " to lobby from signin");

		// Redirect to the homepage.
		// The player model is saved in the UserSession, so the
		// homepage controller can access the data it needs from there.
		// #globalstate
		response.redirect("/");

		return null;
	}

}
