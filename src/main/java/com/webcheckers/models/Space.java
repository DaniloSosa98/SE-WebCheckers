package com.webcheckers.models;

import com.webcheckers.global.Constants;

import java.util.Objects;


/**
 * An individual space on the Checkers board
 *
 * @author Donald Craig
 */
public class Space {

	private Piece piece;
	// Space validity is if there is no piece there; i.e., space is empty.
	// (Should be refactored soon!)
	private boolean isValid;
	private int cellIdx;
	private SpaceColor spaceColor;

	public enum SpaceColor{BLACK, WHITE}

	/**
	 * creates a new black space
	 * @param isValid whether the space is valid on a checkers board
	 * @param cellIdx The index that the space is located at in it's row
	 */
	public Space(boolean isValid, int cellIdx){
		this.piece = null;
		this.cellIdx = cellIdx;
		this.isValid = isValid;
		this.spaceColor = SpaceColor.BLACK;
	}

	/**
	 * creates a duplicate of a space
	 * @param other the space that is being duplicated
	 */
	public Space(Space other) {
		this.piece = other.piece;
		this.cellIdx = other.cellIdx;
		this.isValid = other.isValid;
		this.spaceColor = other.spaceColor;
	}

	/**
	 * returns a string representation of the space
	 * @return "R" if the space has a red piece, "W" for white, and "-" for blank
	 */
	public String toString() {
		if (this.piece != null)
			return this.piece.toString();
		else
			return "–";
	}

	/**
	 * Returns the Piece that is on the Space
	 * @return the Piece on the Space. If the Space is blank, returns null
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * returns the index of this space
	 * @return the index of this space in it's row
	 */
	public int getCellIdx() {
		return this.cellIdx;
	}

	/**
	 * Place a white piece on the Space. Space is occupied and no longer a valid position to move
	 */
	public void putWhitePiece() {
		if(this.isValid) {
			this.piece = new Piece(Constants.Color.WHITE, cellIdx);
		}
		this.isValid = false;
	}

	/**
	 * Place a red piece on the Space. Space is occupied and no longer a valid position to move
	 */
	public void putRedPiece() {
		if(this.isValid) {
			this.piece = new Piece(Constants.Color.RED, cellIdx);
		}
		this.isValid = false;
	}

	/**
	 * Make the Space color WHITE
	 */
	public void makeSpaceWhite(){
		this.spaceColor = SpaceColor.WHITE;
	}

	/**
	 * Fetch the color of this space
	 * @return color
	 *      The color {@link Constants.Color} of the space ( WHITE or BLACK )
	 */
	public SpaceColor getColor(){
		return this.spaceColor;
	}

	/**
	 * Puts Piece object in this space
	 * @param piece Piece object to put
	 */
	public Piece putPiece(Piece piece){
		if(piece != null){
			this.piece = piece;
			this.isValid = false;
			return piece;
		}
		else return null;
	}

	/**
	 * remove Piece object in this space
	 * @return the piece in this space if there is one, null otherwise
	 */
	public Piece removePiece(){
		if(this.piece == null){
			return null;
		}
		Piece tempPiece = this.piece;
		this.piece = null;
		this.isValid = true;
		return tempPiece;
	}

	/**
	 * Returns a boolean of whether the space is valid or not
	 * @return True if the space is valid and does not currently contain a piece
	 */
	public boolean isValid(){
		return this.isValid && (this.piece == null);
	}

	/**
	 * Compares an object with this space to see if they are equal
	 * @param object the object being compared to this space
	 * @return true is the objects is a space and that space has the same index, color, validity and occupancy
	 * (if is has a piece, and if so what color that piece is) as this space, false otherwise
	 */
	@Override
	public boolean equals(Object object){
		if(object instanceof Space){
			Space space = (Space) object;
			if(piece == null){
				return space.getPiece() == null && (cellIdx == space.getCellIdx())
					&& (isValid() == space.isValid()) && spaceColor == space.getColor();
			}
			return piece.equals(space.getPiece()) && (cellIdx == space.getCellIdx())
				&& (isValid() == space.isValid()) && spaceColor == space.getColor();
		}
		return false;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(cellIdx);
	}
}
