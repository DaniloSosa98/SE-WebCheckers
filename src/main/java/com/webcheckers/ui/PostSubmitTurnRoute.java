package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Board;
import com.webcheckers.models.Game;
import com.webcheckers.models.Move;
import com.webcheckers.models.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The UI component to submit a turn, via POST.
 *
 * @author Elizabeth Sherrock
 * @author Joseph Saltalamacchia
 */
public class PostSubmitTurnRoute implements Route {
	private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
	private final Gson gson;
	private GameCenter gameCenter;

	/**
	 * Creates new PostSubmitTurnRoute
	 *
	 * @param gson The Google JSON parser object used to render Ajax responses.
	 */
	public PostSubmitTurnRoute(Gson gson, GameCenter gameCenter) {
		this.gson = gson;
		this.gameCenter = gameCenter;
		LOG.config("PostSubmitTurnRoute is initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object handle(Request request, Response response) {

		Session session = request.session();
		UserSession userSession = session.attribute(Constants.USER_SESSION_ATTRIBUTE);

		Player player = userSession.getPlayer();
		String p1Username = player.getUsername();

		// The gameID is the concatenation of both player usernames,
		// so we can use either username to retrieve the game.
		Game game = gameCenter.getGameForPlayer(p1Username);
		Board board = game.getBoard();
		// If this is null, we have a BIG problem.

		if (game == null) {
			LOG.fine("ERROR: Game containing player " + p1Username + " does not exist!");
			return null;
		}

		Move lastMove = null;
		Move firstMove = null;
		boolean lastMoveWasJump = false;

		if(!userSession.getTurn().isEmpty()) {
			LOG.fine("assigning the lastMove");
			lastMove = userSession.getTurn().get(userSession.getTurn().size()-1);
			firstMove = userSession.getTurn().get(0);
			lastMoveWasJump = Math.abs(lastMove.getEnd().getRow() - lastMove.getStart().getRow()) == 2;
		}

		if (!(lastMove == null)) {
			LOG.fine("the last move is not null");
			System.out.println(lastMove.doesSpaceHaveJumpMove(lastMove.getEnd(), board.getPiece(firstMove.getStart())) );
			System.out.println(lastMoveWasJump);


			if(lastMove.doesSpaceHaveJumpMove(lastMove.getEnd(), board.getPiece(firstMove.getStart())) && lastMoveWasJump) {
				LOG.fine("There is still a jump move left (1)");
				return gson.toJson(Message.error(Constants.JMP_AVAILABLE));
			}

			if (!lastMove.doesSpaceHaveJumpMove(lastMove.getEnd(), board.getPiece(firstMove.getStart())) || !lastMoveWasJump) {

				LOG.fine("There are no more jump moves: " + !lastMove.doesSpaceHaveJumpMove(lastMove.getEnd(), board.getPiece(firstMove.getStart())));
				// Loop while there are still turns to process
				while (!userSession.getTurn().isEmpty()) {
					Move nextMove = userSession.removeFirstMove();
					game.makeMove(nextMove, false);

				}
				// Game over logic is checked in GetGameRoute,
				// as we need access to the view model
				// in order to render the game over state.

				game.changeTurn();
				return gson.toJson(Message.info(Constants.SUBMITTED));
			} else {
				LOG.fine("There is still a jump move left (2)");
				return gson.toJson(Message.error(Constants.JMP_AVAILABLE));
			}
		} else {
			LOG.fine("There is still a jump move left (3)");
			return gson.toJson(Message.error(Constants.JMP_AVAILABLE));
		}

	}
}
