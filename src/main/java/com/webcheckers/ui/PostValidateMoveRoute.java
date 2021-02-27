package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Board;
import com.webcheckers.models.Move;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Position;
import com.webcheckers.util.Message;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * The UI component to check if a move is valid, via POST.
 *
 * @author Elizabeth Sherrock
 * @author Joseph Saltalamacchia
 */
public class PostValidateMoveRoute implements Route {
	private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
	private final Gson gson;

	/**
	 * Creates new PostValidateMoveRoute
	 *
	 * @param gson The Google JSON parser object used to render Ajax responses.
	 */
	public PostValidateMoveRoute(final Gson gson) {
		this.gson = gson;
		LOG.config("PostValidateMoveRoute is initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(Request request, Response response) {
		QueryParamsMap queryParams = request.queryMap();

		LOG.info(queryParams.value("actionData"));

		//{"start":{"row":#,"cell":#},"end":{"row":#,"cell":#}}
		String queryVal = queryParams.value("actionData");

		// Making Position objects from the query
		// @FIXME: Refactor this to use gson parsing
		Position start = new Position(Integer.parseInt(queryVal.substring(16, 17)),
			Integer.parseInt(queryVal.substring(25, 26)));
		Position end = new Position(Integer.parseInt(queryVal.substring(41, 42)),
			Integer.parseInt(queryVal.substring(50, 51)));

		UserSession userSession = request.session().attribute(Constants.USER_SESSION_ATTRIBUTE);
		Constants.Color userColor = userSession.getPlayer().getColor();

		// Create the move
		// We need the board object to send back to the model.
		// This coupling is intentional!
		// @TODO: Refactor board to usersession? getBoardView() only used here!
		Move move;
		if (userColor == Constants.Color.RED) {
			move = new Move(userSession.getBoardView().getBoard(), start, end);
			LOG.fine("MOVE MADE RED: " + move);
		} else {
			// Board is already flipped!
			move = new Move(userSession.getBoardView().getBoard(), start, end);
			LOG.fine("MOVE MADE WHITE: " + move);
			// If this is the white player then re-orient the move to make sense on the white board
			move.flipOrientation();
		}

		// If no moves have been made...
		if (userSession.getTurn().isEmpty()) {

			// If the move is valid, cache it in the user session
			if (move.isValid(move.getBoard().getPiece(start))) {
				LOG.fine("True");
				userSession.addMove(move);
				return gson.toJson(Message.info(Constants.VALID_MOVE));
			} else {
				if (move.jumpAvailable(userColor)) {
					return gson.toJson(Message.error(Constants.JMP_AVAILABLE));
				} else {
					return gson.toJson(Message.error(Constants.INVALID_MOVE));
				}
			}
			//make sure, if this is not the first move, that the move is a jump move
		} else if(Math.abs(move.getStart().getRow() - move.getEnd().getRow())==2 &&
			Math.abs(userSession.getTurn().get(0).getStart().getRow() - userSession.getTurn().get(0).getEnd().getRow()) != 1) {

			LOG.fine("There is a second jump move to be made by this piece");
			Position original = userSession.getTurn().get(0).getStart();
			LOG.fine("start position "+ original.getRow() + " * " + original.getCell());

			Piece originalPiece;

			//orient the board so that it's the perspective of the red player
			if(userColor == Constants.Color.RED){
				originalPiece = move.getBoard().getPiece(original);
			}
			else {
				originalPiece = move.getBoard().getPiece(original.flip());
			}


			if (move.isValid(originalPiece)) {
				LOG.fine("True");
				userSession.addMove(move);
				return gson.toJson(Message.info(Constants.VALID_MOVE));
			} else {
				if (move.jumpAvailable(userColor)) {
					LOG.fine("False");
					return gson.toJson(Message.error(Constants.JMP_AVAILABLE));
				} else {
					LOG.fine("False");
					return gson.toJson(Message.error(Constants.INVALID_MOVE));
				}
			}
		} else {
			LOG.fine("What? How did you even get here? Are you trying to cheat?");
			return gson.toJson(Message.error(Constants.INVALID_MOVE));
		}
	}
}
