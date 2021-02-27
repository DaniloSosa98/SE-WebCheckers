package com.webcheckers.ui;

import com.webcheckers.global.UserSession;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Sign In page.
 *
 * @author Peter Kos
 */
public class GetSignInRoute implements Route {

	// Attributes
	private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
	private final TemplateEngine templateEngine;

	/**
	* Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	*
	* @param templateEngine
	*   the HTML template rendering engine
	*/
	public GetSignInRoute(final TemplateEngine templateEngine) {

		this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
		LOG.config("GetSignInRoute is initialized.");

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

		UserSession userSession = request.session().attribute("UserSession");

		// Check if userSession is null, in which case
		// a new user has attempted to navigate directly to this page,
		// and we should redirect them to the homepage, which is the
		// sole creator of a UserSession object.
		if (userSession == null) {
			LOG.fine("userSession null in GetSignInRoute -- redirecting to Home");
			response.redirect("/");
			return null;
		}

		// No view model as the provided data on the sign in page is static,
		// and the user is _providing_ information that will be handled
		// on POST /signin.
		Map<String, Object> viewModel = new HashMap<>();

		// (However, we will still log!)
		LOG.finer("GetSignInRoute is invoked.");

		// Render the sign in view
		return templateEngine.render(new ModelAndView(viewModel, "signin.ftl"));
	}
}
