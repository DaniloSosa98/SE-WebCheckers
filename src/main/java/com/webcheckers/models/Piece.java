package com.webcheckers.models;

import com.webcheckers.global.Constants;
import com.webcheckers.ui.BoardView;

import java.util.Objects;

/**
 * Represents a Checkers piece on the Board
 *
 * @author Donald Craig
 */

public class Piece {

	public enum Type {SINGLE, KING}

	private Constants.Color color;
	private int index;
	private Type type;

	/**
	 * Create a Checkers piece
	 * @param color
	 *      The Color {@link Constants.Color} of the piece, either RED or WHITE
	 * @param idx
	 *      The index associated with the placement on the board
	 */
	public Piece(Constants.Color color, int idx){
		this.color = color;
		this.index = idx;
		this.type = Type.SINGLE;
	}

	/**
	 * Gets the type of the Piece
	 * @return
	 *      the Type {@link Type} of the Piece, SINGLE in this case
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gets the color of the Piece
	 * @return
	 *      the Color {@link Constants.Color} of the Piece, RED or WHITE
	 */
	public Constants.Color getColor() {
		return color;
	}

	/**
	 * Gets the index of the Piece
	 * @return the integer representation of the index of the piece, the place on the board
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Updates Type to reflect King piece instead of single
	 */
	public void makeKing() {
		this.type = Type.KING;
	}

	/**
	 * Method to check if a single piece has reached the far row so it can be promoted
	 * from type Single to King. Depending on the color being tested, the furthest row
	 * for that color is iterated through to see if a single piece of that color is present
	 * and if so, it will be promoted.
	 *
	 * DEPENDENT on piece location. Color matters for determining orientation.
	 * Needs board orientation to be 0 at bottom, 7 on top,
	 * relative to the red player's location.
	 *
	 * @param targetColor color of the piece on the position being checked
	 * @param boardView current board as it exists
	 */
	public static void promoteToKingCheck(Constants.Color targetColor, Board board) {

		// We are looking along the TOP of the board for the king location.
		for (int i = 0; i < 8; i++) {

			Position targetPos;

			// Look on top for white, bottom for red.
			// (We are realtive to red board!)
			if (targetColor == Constants.Color.WHITE) {
				targetPos = new Position(7, i);
			} else {
				targetPos = new Position(0, i);
			}

			// Go to next position if it is empty.
			if (board.getPiece(targetPos) == null) {
				continue;
			}

			Piece targetPiece = board.getPiece(targetPos);

			// Check if the piece is eligible to inherit the throne
			if (targetPiece.getColor() == targetColor &&
				targetPiece.getType() == Type.SINGLE) {
				// The piece is now a king! Behold! Behold!
				targetPiece.makeKing();
			}
		}
	}

	/**
	 * String representation of a Piece
	 *
	 * @return "R" if Piece is red, "W" if Piece is white
	 */
	@Override
	public String toString() {
		if (this.color == Constants.Color.RED)
			return "R";
		else
			return "W";
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(index);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		return this.hashCode() == other.hashCode();
	}
}
