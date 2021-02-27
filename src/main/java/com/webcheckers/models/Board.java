package com.webcheckers.models;


import com.webcheckers.global.Constants;

/**
 * A class that creates and maintains a checkers board for a player
 *
 * @author Donald Craig
 * @author Joeseph Saltalamacchia
 */
public class Board {

	private Row[] gameBoard;
	private final int BOARD_LENGTH = 8;
	private Player player;
	public Board(Player player)
	{
		this.gameBoard = new Row[BOARD_LENGTH];
		for(int i = 0; i < BOARD_LENGTH; i++) {
			this.gameBoard[i] = new Row(i);
		}

		// This actually places all pieces...
		placeRedPieces();


		this.player = player;
	}

	/**
	 * Initializes all pieces on the board and places them according to the colors of the Spaces.
	 */
	public void placeRedPieces(){
		for(int i = 0; i < BOARD_LENGTH; i++ ){
			if( i < 3){
				for (Space space: gameBoard[i]) {
					if (space.getColor() == Space.SpaceColor.BLACK) {
						space.putWhitePiece();
					}
				}
			}
			if( i > 4){
				for (Space space: gameBoard[i]) {
					if (space.getColor() == Space.SpaceColor.BLACK) {
						space.putRedPiece();
					}
				}
			}
		}
	}

	/**
	 * A method to move the game pieces on the board of each player when a move is made
	 * @param move the move being made
	 * @return true if the move succeeded, false otherwise
	 */
	public Piece makeMove(Move move, boolean isBackwards) {

		int startRow   = move.getStart().getRow();
		int startIndex = move.getStart().getCell();
		int endRow     = move.getEnd().getRow();
		int endIndex   = move.getEnd().getCell();

		// Move is a jump, need to find piece that was jumped over to capture
		if (Math.abs(startRow - endRow) > 1) {

			Position target;

			if (isBackwards) {
				target = move.getJumpMidpoint(move.getEnd(), move.getStart());
			} else {
				target = move.getJumpMidpoint(move.getStart(), move.getEnd());
			}

			Space targetSpace = this.gameBoard[target.getRow()].getSpace(target.getCell());
			Piece targetPiece;

			// Add the piece if we're undoing a jump,
			// remove the piece if we're making a jump.
			if (isBackwards) {
				if (player.getColor() == Constants.Color.WHITE) {
					targetPiece = targetSpace.putPiece(new Piece(Constants.Color.RED, targetSpace.getCellIdx()));
				} else {
					targetPiece = targetSpace.putPiece(new Piece(Constants.Color.WHITE, targetSpace.getCellIdx()));
				}
			} else {
				targetPiece = targetSpace.removePiece();
			}

			// Both players call this, so we only decrement (or increment) the piece count
			// of the player whose piece is removed.
			if (targetPiece.getColor() == player.getColor()) {

				if (isBackwards) {
					this.player.incrementPieceCount();
				} else {
					this.player.decrementPieceCount();
				}
			}

		}

		// Making simple move
		// If backwards, do it backwards.
		// Otherwise do it forwards!

		if (isBackwards) {
			// Remove piece at end
			Piece currentPiece = this.getRow(endRow).getSpace(endIndex).removePiece();
			// Put piece at start, and return it
			return this.getRow(startRow).getSpace(startIndex).putPiece(currentPiece);
		} else {
			// Remove piece at start
			Piece currentPiece = this.getRow(startRow).getSpace(startIndex).removePiece();
			// Put piece at end, and return it
			return this.getRow(endRow).getSpace(endIndex).putPiece(currentPiece);
		}

	}

	/**
	 * creates a checkers board from pre-defined rows
	 *
	 * @param row1 The first row of the checkers board
	 * @param row2 The second row of the checkers board
	 * @param row3 The third row of the checkers board
	 * @param row4 The fourth row of the checkers board
	 * @param row5 The fifth row of the checkers board
	 * @param row6 The sixth row of the checkers board
	 * @param row7 The seventh row of the checkers board
	 * @param row8 The eighth row of the checkers board
	 */
	public Board(Row row1, Row row2, Row row3, Row row4, Row row5, Row row6, Row row7, Row row8) {
		this.gameBoard = new Row[BOARD_LENGTH];
		this.gameBoard[0] = new Row(row1);
		this.gameBoard[1] = new Row(row2);
		this.gameBoard[2] = new Row(row3);
		this.gameBoard[3] = new Row(row4);
		this.gameBoard[4] = new Row(row5);
		this.gameBoard[5] = new Row(row6);
		this.gameBoard[6] = new Row(row7);
		this.gameBoard[7] = new Row(row8);
	}

	/**
	 * returns a given row of this checkers board
	 * @param index the number associated with a given row in the checkers board
	 * @return the row of the checker's board at the given index if the index if in bounds, null otherwise
	 */
	public Row getRow(int index) {

		if(index < this.BOARD_LENGTH) {
			return this.gameBoard[index];
		}
		return null;
	}

	public int numberOfRows()
	{
		return(gameBoard.length);
	}

	/**
	 * creates a string representation of the Board
	 * @return the string representation of the Board
	 */
	@Override
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < BOARD_LENGTH; i++) {
			stringBuilder.append(gameBoard[i].toString());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Check the equality of two objects and see if the provided object
	 * is equal to the Board
	 * @param object
	 *      Object (hopefully a Board) to compare if it is equal to the Board
	 * @return
	 *      True if the Board matches the provided Object
	 *      False otherwise
	 */
	@Override
	public boolean equals(Object object){
		if(object instanceof Board){
			Board boardView = (Board) object;
			for (int i = 0; i < BOARD_LENGTH; i++) {
				if(!boardView.getRow(i).equals(gameBoard[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * A class to return the game piece at a given location, if there is one
	 * @param position the position being queried
	 * @return the piece at the given position if there is one, null otherwise
	 */
	public Piece getPiece(Position position){
		int row = position.getRow();
		int cell = position.getCell();
		return (this.getRow(row).getPiece(cell));
	}

	/**
	 * Returns a board that is a mirror image of this board
	 * @return the flipped board
	 */
	public Board flipBoard() {
		return new Board(getRow(7).flip(), getRow(6).flip(), getRow(5).flip(), getRow(4).flip(),
						 getRow(3).flip(), getRow(2).flip(), getRow(1).flip(), getRow(0).flip());
	}
}
