
package com.webcheckers.models;


import com.webcheckers.global.Constants;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * This class describes the Player entity.
 * @author Donald Craig
 */
public class Player {

	// Logging
	private static final Logger LOG = Logger.getLogger(Player.class.getName());

	// Attributes
	private String username;
	private boolean isPlaying;
	private boolean isReplaying;
	private Constants.Color color;

	private int pieceCount;

	/**
	 * creates a new Player
	 * @param username The (unique) String identifying the player (comprised of alphanumeric characters and spaces)
	 */
	public Player(String username) {
		this.username = username;
		this.isPlaying = false;
		this.isReplaying = false;
		this.color = Constants.Color.RED;
		LOG.fine("Instantiated user " + username + ".");
		pieceCount=0;
	}

	/**
	 * Method to set current piece count for player
	 * @param num the new piece count
	 */
	public void setPieceCount(int num) {
		pieceCount=num;
	}

	/**
	 * Method to get current piece count for player
	 * @return piece count
	 */
	public int getPieceCount() {
		return this.pieceCount;
	}

	/**
	 * method to decrease player piece count by 1
	 */
	public void decrementPieceCount() {
		this.pieceCount--;
	}

	public void incrementPieceCount() {
		this.pieceCount++;
	}

	// May remove these getters/setters

	/**
	 * returns the username of the player
	 * @return a String containing alphanumeric characters and spaces
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the new username after it is set.
	 *
	 * @param username The username
	 * @return The new username
	 */
	public String setUsername(String username) {
		this.username = username;
		return this.username;
	}

	/**
	 * Returns the player's play status.
	 *
	 * @return Play status of the player.
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * Sets the player's play status.
	 *
	 * @param playing Player's new play status.
	 */
	public void setPlaying(boolean playing) {
		isPlaying = playing;
	}

	/**
	 * Get the Player's color
	 * @return
	 *      The color of the Player, either Color.RED or .WHITE
	 */
	public Constants.Color getColor() {
		return color;
	}

	/**
	 * Sets Player's color
	 *
	 * @param color The color of the player's pieces, either Color.RED or .WHITE
	 */
	public void setColor(Constants.Color color) {
		this.color = color;
	}

	public boolean getIsReplaying() {
		return this.isReplaying;
	}

	public void setIsReplaying(boolean replaying) {
		this.isReplaying = replaying;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return Objects.equals(username, player.username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}
}
