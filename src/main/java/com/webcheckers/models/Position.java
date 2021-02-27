package com.webcheckers.models;

/**
 * This class represents the Position on a checkers board
 * @author Donald Craig
 */
public class Position {

	private int row;
	private int cell;

	/**
	 * Creates a new position
	 * @param row an integer representing the row of the checkers board that this position is in
	 * @param cell an integer representing the cell (space in a row) that the position is in
	 */
	public Position(int row, int cell) {
		this.row = row;
		this.cell = cell;
	}

	/**
	 * Returns row for given position
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Returns cell for given position
	 * @return cell
	 */
	public int getCell() {
		return this.cell;
	}

	/**
	 * sets the row to a new row
	 * (used exclusively by Move)
	 * @param row the new row value
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * sets the cell to a new cell
	 * (used exclusively by Move)
	 * @param cell the new cell value
	 */
	public void setCell(int cell) {
		this.cell = cell;
	}

	public Position flip() {
		int newX = Math.abs(7-this.row);
		int newY = Math.abs(7-this.cell);
		return (new Position(newX, newY));
	}
}
