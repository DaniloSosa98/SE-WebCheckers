package com.webcheckers.models;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A model tier component to represent a row of spaces on a checkers board
 * @author Donald Craig
 */
public class Row implements Iterable<Space>{

	private final int ROW_LENGTH = 8;
	private Space[] row = new Space[ROW_LENGTH];
	private int index;

	/**
	 * Makes a Row of alternating black and white Spaces
	 * @param index The index of the Row
	 */
	public Row(int index){
		this.index = index;

		if( index % 2 != 0){
			for( int i = 0; i < ROW_LENGTH; i++){
				if( i % 2 == 1){
					row[i] = new Space(false,  i);
					row[i].makeSpaceWhite();
				}
				else{
					row[i] = new Space(true,  i);
				}
			}
		}
		else{
			for( int i = 0; i < ROW_LENGTH; i++){
				if( i % 2 == 0){
					row[i] = new Space(false, i);
					row[i].makeSpaceWhite();
				}
				else{
					row[i] = new Space(true,  i);
				}
			}
		}
	}

	/**
	 * create a Row that is a duplicate of another row
	 *
	 * @param other the Row that is being duplicated
	 */
	public Row (Row other) {
		this.index = other.index;
		this.row = new Space[ROW_LENGTH];
		for (int i = 0; i < ROW_LENGTH; i++) {
			this.row[i] = new Space(other.getSpace(i));
		}
	}

	/**
	 * Checks to see if two rows are equal to each other
	 * (Two rows are equal if all of the spaces that make up that row are equal)
	 * @param object The object that this Row is being compared to
	 * @return True if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object object){
		if(object instanceof Row){
			Row row = (Row) object;
			if(index != row.getIndex()){
				return false;
			}
			Space[] rows = row.getRow();
			for (int i = 0; i < ROW_LENGTH; i++) {
				if(!rows[i].equals(this.row[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns the index of this row
	 * @return An integer representing the index of this row
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns a given space in the row
	 *
	 * @param index the index of the space in the row
	 * @return The Space at the given index in the row
	 */
	public Space getSpace(int index) {
		return this.row[index];
	}

	/**
	 * returns the entire row of spaces
	 * @return an Array of Spaces representing the row
	 */
	public Space[] getRow() {
		return row;
	}

	/**
	 * returns a string representation of the row (as a list if spaces)
	 * @return The string representation of the row
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < ROW_LENGTH; i++) {
			stringBuilder.append(row[i].toString());
		}
		return stringBuilder.toString();
	}

	/**
	 * establishes the RowIterator as the iterator for a row
	 * @return a RowIterator
	 */
	@Override
	public Iterator<Space> iterator() {
		return new RowIterator();
	}


	/**
	 * A class to return the game piece at a given location, if there is one
	 * @param cell the cell being queried
	 * @return the piece at the given position if there is one, null otherwise
	 */
	public Piece getPiece(int cell) {
		return this.getSpace(cell).getPiece();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(index);
	}

	public void setRow(Space[] spaces)
	{
		if(spaces != null && spaces.length == ROW_LENGTH){
			this.row = spaces;
		}
	}

	/**
	 * Returns a copy of this Row, but flipped.
	 *
	 * @return     Returns a copy of Row, but flipped
	 */
	public Row flip() {
		Row flipped = new Row(this);

		for (int x = 0; x < ROW_LENGTH; x++)
			flipped.getRow()[x] = this.row[ROW_LENGTH - 1 - x];

		return flipped;
	}


	/**
	 * a helper class to iterate over a row
	 */
	private class RowIterator implements Iterator<Space> {

		//attributes
		private int cursor;

		/**
		 * Initializes the cursor to 0
		 */
		public RowIterator() {
			this.cursor = 0;
		}

		/**
		 * Checks if there are still Spaces to iterate through in the row
		 * @return
		 *      True if the cursor is within the ROW_LENGTH, 8
		 *      False otherwise
		 */
		public boolean hasNext() {
			return this.cursor < ROW_LENGTH;
		}

		/**
		 * moves the cursor to the next space
		 * @return The next space in the order
		 */
		public Space next() {
			if(this.hasNext()) {
				int current = cursor;
				cursor ++;
				return row[current];
			}
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
