package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * The UI component to undo a validated move, via POST.
 *
 * @author Elizabeth Sherrock
 * @author Joseph Saltalamacchia
 */
public class PostBackupMoveRoute implements Route {
	private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
	private final Gson gson;

	/**
	 * Creates new PostBackupMoveRoute
	 *
	 * @param gson The Google JSON parser object used to render Ajax responses.
	 */
	public PostBackupMoveRoute(Gson gson) {
		this.gson = gson;
		LOG.config("PostBackupMoveRoute is initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(Request request, Response response) {
		Session session = request.session();
		UserSession userSession = session.attribute(Constants.USER_SESSION_ATTRIBUTE);

		if(userSession.removeLastMove() != null) {
			return gson.toJson(Message.info(Constants.MOVE_REMOVED));
		}
		return gson.toJson(Message.error(Constants.NOT_REMOVED));
	}
}
