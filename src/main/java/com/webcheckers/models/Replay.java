package com.webcheckers.models;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

/**
 * a model tier component that represents a replay of a game
 * @author Joseph Saltalamacchia
 */
public class Replay {

	private static final Logger LOG = Logger.getLogger(Replay.class.getName());

	private List<Move> moves;
	private ListIterator<Move> moveIterator;
	private String gameID;

	// To check if we are in the initial state of the replay or not
	private int index;

	/**
	 * constructor for the replay
	 */
	public Replay(List<Move> moves, String gameID) {
		this.moveIterator = moves.listIterator();
		this.gameID = gameID;

		// We need to do a DEEP COPY of the moves.
		this.moves = new ArrayList<>();
		for (Move move : moves) {
			// Move board flip is implicit
			this.moves.add(new Move(move.getBoard(), move.getStart(), move.getEnd()));
		}

		LOG.fine("MOVES:");
		LOG.fine(moves.toString());
	}

	/**
	 * returns the ID for this replay
	 * @return A string representing this game ID
	 */
	public String getGameID() {
		return this.gameID;
	}

	public boolean hasNextMove() {
		return moveIterator.hasNext();
	}

	public boolean hasPreviousMove() {
		return moveIterator.hasPrevious();
	}

	/**
	 * We need to return the next move that needs to be made,
	 * so the Board used by the rest of the server is updated,
	 * not just our copy of it.
	 *
	 * @return     The next move to occur.
	 */
	public Move getNextMove() {
		LOG.fine("next move");
		LOG.fine(moves.toString());
		index++;
		return moveIterator.next();
	}

	/**
	 * We need to return the previous move that needs to be made,
	 * so the Board used by the rest of the server is updated,
	 * not just our copy of it.
	 *
	 * @return     The previous move to occur.
	 */
	public Move getPreviousMove() {
		LOG.fine("previous move");
		LOG.fine(moves.toString());
		index--;
		return moveIterator.previous();
	}

	public boolean isInitialState() {
		return index == 0;
	}

}
