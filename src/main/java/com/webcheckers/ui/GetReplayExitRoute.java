package com.webcheckers.ui;

import com.webcheckers.global.UserSession;
import com.webcheckers.models.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * The UI Controller to GET the replay exit behavior.
 */
public class GetReplayExitRoute implements Route {

	//attributes
	private static final Logger LOG = Logger.getLogger(GetReplayExitRoute.class.getName());


	/**
	* Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	*
	*/
	public GetReplayExitRoute() {

		LOG.config("GetReplayExitRoute is initialized.");
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

		LOG.finer("GetReplayExitRoute is invoked.");

		// Give ourselves a nice UserSession to work with
		UserSession userSession = request.session().attribute("UserSession");
		Player currentPlayer = userSession.getPlayer();

		currentPlayer.setIsReplaying(false);

		// Redirect home
		response.redirect("/");

		return null;
	}
}
