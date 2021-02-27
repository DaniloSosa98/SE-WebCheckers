package com.webcheckers.models;


import com.webcheckers.global.Constants;

import java.util.logging.Logger;

/**
 * Represents a move as made in the WebCheckers application
 *
 * @author Donald Craig
 * @author Joseph Saltalamacchia
 */
public class Move {

	private static final Logger LOG = Logger.getLogger(Move.class.getName());

	private Board board;
	private Position start;
	private Position end;

	private boolean isJumpRow;
	private boolean isJumpCell;
	private boolean isJumpRowPos;
	private boolean isJumpCellPos;
	private boolean correctRows;
	private boolean correctCell;
	private boolean correctCellPos;
	private boolean correctRowsPos;

	private int startRowIndex;
	private int endRowIndex;
	private int startCell;
	private int endCell;
	private int targetRow;
	private int targetCell;


	/**
	 * Constructor to represent Move of a checker piece
	 * @param start
	 *   beginning position
	 * @param end
	 *   end position
	 */
	public Move(Board board, Position start, Position end) {
		this.board = board;
		this.start = start;
		this.end = end;
	}

	/**
	 * Method to return the target position of a jump move
	 * @return Position representing midpoint of jump
	 */
	public Position getJumpMidpoint(Position start, Position end) {
		int jumpedRow = ((start.getRow() + end.getRow()) / 2);
		int jumpedCel = ((start.getCell() + end.getCell()) / 2);

		return new Position(jumpedRow, jumpedCel);
	}

	/**
	 * returns the starting position of a piece (the space it is moving from)
	 * @return a Position object indicating the pieces location before the attempted move
	 */
	public Position getStart() {
		return start;
	}

	/**
	 * returns the ending position of a piece (the space it is moving to)
	 * @return a Position object indicating the Location the piece is attempting to move to
	 */
	public Position getEnd() {
		return end;
	}

	/**
	 * Returns the space associated with the position that a piece is attempting to move to
	 * @param endRow the row that this move is attempting to end in
	 * @return the Space that this move is attempting to end in
	 */
	private Space getEndSpace(Row endRow) {
		return endRow.getSpace(this.endCell);
	}

	/**
	 * returns the Board that this move is taking place on
	 * @return the Board
	 */
	public Board getBoard() {
		return this.board;
	}

	// public Piece getStartPiece() {
	// 	return
	// }


	/**
	 * a helper method to help ensure that a given move is a valid one for the red player
	 */
	private void redValidationSetup() {
		System.out.println("in red validation");

		startRowIndex = this.start.getRow();
		endRowIndex = this.end.getRow();
		startCell =  this.start.getCell();
		endCell = this.end.getCell();
		targetRow = (startRowIndex + endRowIndex) / 2;
		targetCell = (startCell + endCell) / 2;

		isJumpRow = (startRowIndex - 2) == endRowIndex;
		isJumpCell = (startCell - 2 == endCell);
		isJumpRowPos = (startRowIndex + 2) == endRowIndex;
		isJumpCellPos = (startCell + 2 == endCell);

		correctRows = (startRowIndex - 1 == endRowIndex);
		correctCell = (startCell-1 == endCell);
		correctCellPos = (startCell+1 == endCell);
		correctRowsPos = (startRowIndex+1 == endRowIndex);
	}

	/**
	 * A helper method to help ensure that a given move is a valid one for the white player
	 */
	private void whiteValidationSetup() {

		System.out.println("in white validation");
		startRowIndex = this.start.getRow();
		endRowIndex = this.end.getRow();
		startCell =  this.start.getCell();
		endCell = this.end.getCell();
		targetRow = (startRowIndex + endRowIndex) / 2;
		targetCell = (startCell + endCell) / 2;

		isJumpRow = (startRowIndex - 2) == endRowIndex;
		isJumpCell = (startCell - 2 == endCell);
		isJumpRowPos = (startRowIndex + 2) == endRowIndex;
		isJumpCellPos = (startCell + 2 == endCell);

		correctRows = (startRowIndex - 1 == endRowIndex);
		correctCell = (startCell-1 == endCell);
		correctCellPos = (startCell+1 == endCell);
		correctRowsPos = (startRowIndex+1 == endRowIndex);
	}

	/**
	 * Method to check if a potential move is valid or not
	 * @param movingPiece The piece being moved
	 * @return True if move is valid, False otherwise
	 */
	public boolean isValid(Piece movingPiece) {

		LOG.fine("movingPiece: " + movingPiece);

		Constants.Color pieceColor = movingPiece.getColor();

		// @FIXME: Orientation should already be flipped by caller??
		if (pieceColor == Constants.Color.WHITE) {
			whiteValidationSetup();
			this.flipOrientation();
		}
		else {
			redValidationSetup();
		}

		// @TODO: Refactor this boolean if possible.
		boolean valid = false;

		// Capture the locations of the end space, and the target space for future use
		Space endSpace = getEndSpace(board.getRow(this.endRowIndex));
		Space targetSpace = board.getRow(targetRow).getSpace(targetCell);

		LOG.fine("The piece who's move is being validated "+movingPiece);

		if (jumpAvailable(pieceColor)) {
			LOG.fine("There is a " + pieceColor + " jump available");
		}
		else {
			LOG.fine("There is NOT a jump available");
		}
		if (simpleMoveAvailable(pieceColor)) {
			LOG.fine("There is a " + pieceColor + " move available");
		}
		else {
			LOG.fine("There is NOT a simple move available");
		}

		//===================================================

		// @TODO: Refactor this!! Please!

		if(movingPiece.getType() == Piece.Type.SINGLE){
			LOG.fine("Step 1");
			LOG.fine((startCell+1) + " * " + (startCell-1) + " * " + endCell + " * " + endRowIndex);
			if((correctCellPos || correctCell) && correctRows){
				LOG.fine("Step 2");
				if(endSpace.isValid()){ // in this context, valid means no piece present on that space
					LOG.fine("Step 3");
					if (!jumpAvailable(pieceColor)) //if jump is available, must do that instead of simple move
						valid = true;
				}
			}
			else if (isJumpRow && (isJumpCell || isJumpCellPos)){
				if(endSpace.isValid() && !targetSpace.isValid()){
					if(targetSpace.getPiece().getColor() != movingPiece.getColor()){
						valid = true;
					}
				}
			}
		}
		// Not a single, so this section for the King
		else {
			if (((correctRows && (correctCellPos || correctCell)) ||
				(correctRowsPos && (correctCellPos || correctCell)))) {
				if(endSpace.isValid()){ // in this context, valid means no piece present on that space
					if (!jumpAvailable(pieceColor)) //if jump is available, must do that instead of simple move
					valid = true;
				}
			}
			else if((isJumpRow && (isJumpCell || isJumpCellPos)) ||
				(isJumpRowPos && (isJumpCell || isJumpCellPos))) {
				if(endSpace.isValid() && !targetSpace.isValid()){
					if(targetSpace.getPiece().getColor() != movingPiece.getColor()) {
						valid = true;
					}
				}
			}
		}
		System.out.println(valid);
		return valid;
	}

	/**
	 * Method to check if a player has the option to jump (and capture) an opponents piece.
	 * By rule, if such option is available it MUST be taken instead of performing a
	 * simple move.
	 * NOTE!!! Board orientation is Row 7 at top of board, Row 0 at bottom of board. (Cell 0 left, cell 7 right)
	 * @return if a jump move is available or not
	 */
	public boolean jumpAvailable(Constants.Color pieceColor) {

		for (int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {

				// For each position on the board...
				Position testPos = new Position(i, j);

				// (Skipping over empty spaces)
				if (board.getPiece(testPos) == null) {
					continue;
				}

				// Get the piece at that position
				Piece target = board.getPiece(testPos);

				// And if that piece is ours
				if (target.getColor() == pieceColor) {

					// Figure out if that piece has a possible simple move
					// in any direction.
					if (canJumpFwdLeft(target, testPos) ||
						canJumpFwdRight(target, testPos) ||
						canJumpBackLeft(target, testPos) ||
						canJumpBackRight(target, testPos)) {
						return true;
					}
				}
			}
		}

		// If no move is found, return false.
		return false;
	}

	/**
	 * Helper method to check if a jump move is possible diagonally forward and left
	 * @param piece The piece being tested for jumping possibility
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canJumpFwdLeft(Piece piece, Position startPos) {

		LOG.fine("checking jmp Fwd left from " + startPos.getRow() + "*" + startPos.getCell());

		// Only consider valid forward left jump locations
		if (!(startPos.getRow() >= 2 && startPos.getCell() >= 2)) {
			return false;
		}

		// Our jump victim and destination position
		Position victimPos = new Position(startPos.getRow() - 1 , startPos.getCell() - 1);
		Position destinationPos = new Position(startPos.getRow() - 2 , startPos.getCell() - 2);

		// Has to be a piece here to jump over
		if (board.getPiece(victimPos) == null ) {
			return false;
		}

		 // Can't jump over piece of same color
		if (piece.getColor() == board.getPiece(victimPos).getColor()) {
			return false;
		}

		// Destination position needs to be free!
		if (board.getPiece(destinationPos) != null) {
			return false;
		}

		LOG.fine("jmp Fwd left from " + startPos.getRow() + "*" + startPos.getCell());
		return true;
	}

	/**
	 * Helper method to check if a jump move is possible diagonally forward and right
	 * @param piece The piece being tested for jumping possibility
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canJumpFwdRight(Piece piece, Position startPos) {

		LOG.fine("checking jmp Fwd right from " + startPos.getRow() + "*" + startPos.getCell());

		// Only consider valid forward right jump locations
		if (!(startPos.getRow() >= 2 && startPos.getCell() <= 5)) {
			return false;
		}

		// Our jump victim and destination position
		Position victimPos = new Position(startPos.getRow() - 1, startPos.getCell() + 1);
		Position destinationPos = new Position(startPos.getRow() - 2, startPos.getCell() + 2);

		// Has to be a piece here to jump over
		if (board.getPiece(victimPos) == null ) {
			return false;
		}

		 // Can't jump over piece of same color
		if (piece.getColor() == board.getPiece(victimPos).getColor()) {
			return false;
		}

		// Destination position needs to be free!
		if (board.getPiece(destinationPos) != null) {
			return false;
		}

		LOG.fine("jmp Fwd right from " + startPos.getRow() + "*" + startPos.getCell());
		return true;
	}

	/**
	 * Helper method to check if a jump move is possible diagonally backward and left
	 * @param piece The piece being tested for jumping possibility
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canJumpBackLeft(Piece piece, Position startPos) {

		LOG.fine("checking jmp back left from " + startPos.getRow() + "*" + startPos.getCell());

		// Only consider king pieces
		if (piece.getType() != Piece.Type.KING) {
			return false;
		}

		// Only consider valid forward right jump locations
		if (!(startPos.getRow() <= 5 && startPos.getCell() >= 2)) {
			return false;
		}

		// Our jump victim and destination position
		Position victimPos = new Position(startPos.getRow() + 1, startPos.getCell() - 1);
		Position destinationPos = new Position(startPos.getRow() + 2, startPos.getCell() - 2);

		// Has to be a piece here to jump over
		if (board.getPiece(victimPos) == null ) {
			return false;
		}

		 // Can't jump over piece of same color
		if (piece.getColor() == board.getPiece(victimPos).getColor()) {
			return false;
		}

		// Destination position needs to be free!
		if (board.getPiece(destinationPos) != null) {
			return false;
		}

		LOG.fine("jmp back left from " + startPos.getRow() + "*" + startPos.getCell());
		return true;

	}

	/**
	 * Helper method to check if a jump move is possible diagonally backward and right
	 * @param piece The piece being tested for jumping possibility
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canJumpBackRight(Piece piece, Position startPos) {

		LOG.fine("checking jmp back right from " + startPos.getRow() + "*" + startPos.getCell());

		// Only consider king pieces
		if (piece.getType() != Piece.Type.KING) {
			return false;
		}

		// Only consider valid forward right jump locations
		if (!(startPos.getRow() <= 5 && startPos.getCell() <= 5)) {
			return false;
		}

		// Our jump victim and destination position
		Position victimPos = new Position(startPos.getRow() + 1 , startPos.getCell() + 1);
		Position destinationPos = new Position(startPos.getRow() + 2, startPos.getCell() + 2);

		// Has to be a piece here to jump over
		if (board.getPiece(victimPos) == null ) {
			return false;
		}

		 // Can't jump over piece of same color
		if (piece.getColor() == board.getPiece(victimPos).getColor()) {
			return false;
		}

		// Destination position needs to be free!
		if (board.getPiece(destinationPos) != null) {
			return false;
		}

		LOG.fine("jmp back right from " + startPos.getRow() + "*" + startPos.getCell());
		return true;
	}

	public boolean doesSpaceHaveJumpMove(Position position, Piece piece) {
		Position actualPosition = position;
		if(piece.getColor() == Constants.Color.WHITE) {
			actualPosition = position.flip();
		}

		if(piece == null) {
			LOG.fine("\"DoesSpaceHaveMove\": Piece is null");
			return false;
		}
		if(piece.getType() == Piece.Type.KING) {
			LOG.fine("king piece has a back right jump: " + this.canJumpBackRight(piece,actualPosition));
			LOG.fine("king piece has a back left jump: " + this.canJumpBackLeft(piece,actualPosition));
			return (this.canJumpBackRight(piece,actualPosition) || this.canJumpBackLeft(piece,actualPosition));
		}
		else {
			LOG.fine("piece has a forward right jump: " + this.canJumpFwdRight(piece,actualPosition));
			LOG.fine("piece has a forward left jump: " + this.canJumpFwdLeft(piece,actualPosition));
			return (this.canJumpFwdRight(piece,actualPosition) || this.canJumpFwdLeft(piece,actualPosition));
		}
	}

	/**
	 * Takes the white player's piece location with respect to white view and finds the location with with respect
	 * to red view.
	 *
	 */
	public void flipOrientation() {
		//the absolute value of 7 - the current value will return the mirrored orientation
		int newStartRow = Math.abs(7-start.getRow());
		int newStartCell = Math.abs(7-start.getCell());

		int newEndRow = Math.abs(7-end.getRow());
		int newEndCell = Math.abs(7-end.getCell());

		start.setRow(newStartRow);
		start.setCell(newStartCell);

		end.setRow(newEndRow);
		end.setCell(newEndCell);
	}

	/**
	 * Returns a string representation of a move
	 * @return a String containing the starting position and ending position of a move
	 */
	public String toString() {
		return "start: (" + start.getRow() + ", " + start.getCell() + ")" +
			" end: (" + end.getRow() + ", " + end.getCell() + ")";
	}

	/**
	 * Method to check if a player has the option to make a simple move.
	 * Target square must be diagonally away from current square and NOT occupied.
	 * NOTE!!! Board orientation is Row 7 at top of board, Row 0 at bottom of board. (Cell 0 left, cell 7 right)
	 * @return if a jump move is available or not
	 */
	boolean simpleMoveAvailable(Constants.Color pieceColor) {

		for (int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {

				// For each position on the board...
				Position testPos = new Position(i, j);

				// (Skipping over empty spaces)
				if (board.getPiece(testPos) == null) {
					continue;
				}

				// Get the piece at that position
				Piece target = board.getPiece(testPos);

				// And if that piece is ours
				if (target.getColor() == pieceColor) {

					// Figure out if that piece has a possible simple move
					// in any direction.
					if (canMovFwdLeft(testPos) ||
						canMovFwdRight(testPos) ||
						canMovBackLeft(target, testPos) ||
						canMovBackRight(target, testPos)) {
						return true;
					}
				}
			}
		}

		// If no move is found, return false.
		return false;
	}

	/**
	 * Helper method to check if a simple move is possible diagonally forward and left
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canMovFwdLeft(Position startPos) {

		// Ignore invalid possible positions top make a forward left move from
		if (!(startPos.getRow() >= 1 && startPos.getCell() >= 1)) {
			return false;
		}

		Position testPos = new Position(startPos.getRow() - 1 , startPos.getCell() - 1);

		// If the position is empty, we can move there!
		if (board.getPiece(testPos) == null) {
			LOG.fine("simple forward left from " + startPos.getRow() + "*" + startPos.getCell());
			return true;

		}

		return false;
	}

	/**
	 * Helper method to check if a simple move is possible diagonally forward and right
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canMovFwdRight(Position startPos) {

		// Ignore invalid possible positions top make a forward right move from
		if (!(startPos.getRow() >= 1 && startPos.getCell() <= 6)) {
			return false;
		}

		Position testPos = new Position(startPos.getRow() - 1, startPos.getCell() + 1);

		// Check if empty space available
		if (board.getPiece(testPos) == null) {
			LOG.fine("simple forward right from " + startPos.getRow() + "*" + startPos.getCell());
			return true;
		}

		// Otherwise, no dice.
		return false;
	}

	/**
	 * Helper method to check if a simple move is possible diagonally backward and left
	 * @param piece The piece being tested for jumping possibility
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canMovBackLeft(Piece piece, Position startPos) {

		// Only a King can move backwards
		if (piece.getType() != Piece.Type.KING) {
			return false;
		}

		// Ignore invalid possible positions top make a back left move from
		if (!(startPos.getRow() <= 6 && startPos.getCell() >= 1)) {
			return false;
		}

		Position testPos = new Position(startPos.getRow() + 1, startPos.getCell() - 1);

		// Check if empty space available
		if (board.getPiece(testPos) == null) {
			LOG.fine("simple back left from " + startPos.getRow() + "*" + startPos.getCell());
			return true;
		}

		// Otherwise, no dice.
		return false;
	}

	/**
	 * Helper method to check if a simple move is possible diagonally backward and right
	 * @param piece The piece being tested for jumping possibility
	 * @param startPos The startPos the piece being tested is currently occupying
	 * @return True if possible, false otherwise
	 */
	private boolean canMovBackRight(Piece piece, Position startPos) {

		// Only a King can move backwards
		if (piece.getType() != Piece.Type.KING) {
			return false;
		}

		// Ignore invalid possible positions top make a back right move from
		if (!(startPos.getRow() <= 6 && startPos.getCell() <= 6)) {
			return false;
		}

		Position testPos = new Position(startPos.getRow() + 1, startPos.getCell() + 1);

		// Check if empty space available
		if (board.getPiece(testPos) == null) {
			LOG.fine("simple back right from " + startPos.getRow() + "*" + startPos.getCell());
			return true;
		}

		// Otherwise, no dice.
		return false;
	}
}
