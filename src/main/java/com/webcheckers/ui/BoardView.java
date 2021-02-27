package com.webcheckers.ui;

import com.webcheckers.global.Constants;
import com.webcheckers.models.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that interprets a checkers board for the view
 *
 * @author Donald Craig
 * @author Joeseph Saltalamacchia
 * @author Peter Kos
 */



public class BoardView implements Iterable<Row> {

	private Board board;

	/**
	 *  Creates a new Board with 8 Rows then places the Pieces in the correct positions.
	 *
	 * @param player the player for who this board is being generated (the red or white player)
	 */
	public BoardView(Player player) {

		// Depending on the player color, present them with a different board.
		if (player.getColor() == Constants.Color.RED) {
			board = new Board(player);
		} else if (player.getColor() == Constants.Color.WHITE) {
			board = new Board(player).flipBoard();
		}

	}
	/**
	 *  Creates a new BoardView of an existing board
	 *
	 * @param board The board for this BoardView
	 */
	public BoardView(Board board) {
		this.board = board;
	}

	/**
	 * creates a string representation of the BoardView
	 * @return the string representation of the BoardView
	 */
	@Override
	public String toString() {
		return(this.board.toString());
	}

	public Board getBoard() {
		return this.board;
	}

	/**
	 * Creates a new BoardIterator, providing a way to iterate through the game board
	 * @return
	 *      BoardIterator {@link BoardIterator} a private Iterator class with hasNext and next() methods to
	 *      iterate through the Rows of the board.
	 */
	@Override
	public Iterator<Row> iterator() {
		return new BoardIterator();
	}

	/**
	 * Check the equality of two objects and see if the provided object
	 * is equal to the BoardView
	 * @param object
	 *      Object (hopefully a BoardView) to compare if it is equal to the BoardView
	 * @return
	 *      True if the BoardView matches the provided Object
	 *      False otherwise
	 */
	@Override
	public boolean equals(Object object) {
		// @FIXME: Implement this equals method properly!! This does not currently work.
		// Or: do we need this?
		return(this.board.equals(object));
	}

	/**
	 * Provides a way to Iterate through each Row of the Board
	 */
	private class BoardIterator implements Iterator<Row> {

		int curr = 0;

		/**
		 * Checks if the current value is still valid within the confines of the Board
		 * @return
		 *      True if curr < the length of the board
		 *      False otherwise
		 */
		@Override
		public boolean hasNext() {
			return (curr < board.numberOfRows());
		}

		/**
		 * Gets the next Row of the board if one exists. If not, it throws a NoSuchElementException
		 * @return
		 *      The next Row in the Board
		 */
		public Row next() {
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			return board.getRow(curr++);
		}
	}
}
