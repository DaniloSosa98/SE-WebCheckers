package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.models.Game;
import com.webcheckers.models.Move;
import com.webcheckers.models.Replay;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the Replay next turn route.
 *
 * @author Peter Kos
 */
public class PostReplayNextTurnRoute implements Route {

	private static final Logger LOG = Logger.getLogger(PostReplayNextTurnRoute.class.getName());

	private Gson gson;
	private ReplayList replayList;
	private GameCenter gameCenter;

	/**
	* Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
	*s
	*/
	public PostReplayNextTurnRoute(Gson gson, ReplayList replayList, GameCenter gameCenter) {
		this.replayList = Objects.requireNonNull(replayList, "replaysList is required");
		this.gson = Objects.requireNonNull(gson);
		this.gameCenter = Objects.requireNonNull(gameCenter);
		LOG.config("PostReplayNextTurnRoute is initialized.");
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

		LOG.finer("PostReplayNextTurnRoute is invoked.");

		// Get game using its ID
		String gameID = request.queryParams("gameID");
		LOG.fine("gameID: " + gameID);


		Replay currentReplay = replayList.getReplayWithID(gameID);
		Move nextMove = currentReplay.getNextMove();

		Game game = gameCenter.getGame(gameID);

		// Make the move!
		// (Not backwards, so passing in false.)
		LOG.fine("PostReplayNextTurnRoute: making move " + nextMove);
		game.makeMove(nextMove, false);

		// We don't talk about changing turns. You saw nothing.

		return gson.toJson(Message.info("next"));
	}

}
