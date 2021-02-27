
package com.webcheckers.global;

import com.webcheckers.models.Move;
import com.webcheckers.models.Player;
import com.webcheckers.ui.BoardView;

import java.util.ArrayList;

/**
 * This class serves as a wrapper around the Session object
 * to store data relevant to each user's session.
 * @author Joseph Saltalamacchia
 */
public class UserSession {

	private Player player;
	private BoardView boardView;
	// A list of the moves the player has made in a given turn
	private ArrayList<Move> turn;

	/**
	 * Create a new UserSession with an empty list of moves.
	 */
	public UserSession() {
		turn = new ArrayList<Move>();
	}

	public Player getPlayer() { return player; }
	public BoardView getBoardView() { return boardView; }

	/**
	 * returns the full list of moves made this turn
	 * @return an arraylist of moves made this turn
	 */
	public ArrayList<Move> getTurn() {
		return(this.turn);
	}

	/**
	 * removes the last move made this turn from the list
	 * @return the turn removed, null if no move was made this turn
	 */
	public Move removeLastMove()
	{
		if(this.turn.isEmpty()){
			return (null);
		}
		return(this.turn.remove(this.turn.size()-1));

	}

	/**
	 * removes the last move made this turn from the list
	 * @return the turn removed, null if no move was made this turn
	 */
	public Move removeFirstMove() {
		if (this.turn.isEmpty()) {
			return null;
		}

		return this.turn.remove(0);

	}

	/**
	 * appends a new move to the end of the list of moves made
	 * @param move the move to be added
	 * @return true if the move was successfully added, false otherwise
	 */
	public boolean addMove(Move move) {
		if (move != null) {
			return this.turn.add(move);
		}

		return false;
	}

	/**
	 * Sets and returns the new Player.
	 *
	 * @param player:  The Player that this user session is meant to be tied to
	 *
	 * @return The player this user session is tied to
	 */
	public Player setPlayer(Player player) {
		this.player = player;
		return player;
	}

	/**
	 * sets and returns a new BoardView
	 *
	 * @param boardView  The BoardView this user's session is tied to
	 * @return The BoardVIew that this user's session is tied to
	 */
	public BoardView setBoardView(BoardView boardView) {
		this.boardView = boardView;
		return boardView;
	}

}
