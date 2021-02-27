package com.webcheckers.models;

import com.webcheckers.global.Constants;
import com.webcheckers.global.Constants.GameOverReason;
import com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This class represents the WebCheckers Game entity.
 *
 * @author Donald Craig
 * @author Joseph Saltalamacchia
 */
public class Game {

	private Player redPlayer;
	private Player whitePlayer;
	private Constants.Color activeColor;
	private Board board;
	private String id;
	// Used so controller can quickly check if game is over
	// We, in our Game model, can use our special permissions to check
	// if the game is over on each move.
	private boolean gameIsOver;
	private GameOverReason gameOverReason;

	// keep track of moves made
	private List<Move> moves = new ArrayList<Move>();

	/**
	 * Create a Game with the red and white players, where the active color is
	 * red and the winner is null.
	 * @param redPlayer
	 *      Player {@link Player} representing the red Player
	 * @param whitePlayer
	 *      Player {@link Player} representing the white Player
	 */
	public Game(Player redPlayer, Player whitePlayer, String id) {
		Objects.requireNonNull(redPlayer, "redPlayer must not be null");
		Objects.requireNonNull(whitePlayer, "whitePlayer must not be null");

		this.redPlayer = redPlayer;
		this.whitePlayer = whitePlayer;

		this.redPlayer.setColor(Constants.Color.RED);
		this.whitePlayer.setColor(Constants.Color.WHITE);
		this.activeColor = Constants.Color.RED;

		// This is set when the reason becomes known
		this.gameOverReason = GameOverReason.Unknown;

		// All board operations are done with respect to the red player's perspective!!
		this.board = new Board(redPlayer);
		this.id = id;
	}

	/**
	 * A method to move the game pieces on the board of each player when a move is made
	 * @param move the move being made
	 */
	public void makeMove(Move move, boolean isBackwards) {

		// Make the move
		board.makeMove(move, isBackwards);

		// Store our move for replayability
		moves.add(move);

		// Game Over cases:

		// Case where there are no pieces left
		if (redPlayer.getPieceCount() <= 0 || whitePlayer.getPieceCount() <= 0) {
			gameIsOver = true;
			gameOverReason = GameOverReason.OutOfPieces;
		} else {

			// Move class takes care of board orientation
			if (!move.simpleMoveAvailable(this.activeColor) &&
		        !move.jumpAvailable(this.activeColor)) {

				// Case where there are no more moves left
				gameIsOver = true;
				gameOverReason = GameOverReason.OutOfMoves;
			}
		}

		// Case where player resigns is set by the playerResign method in this class.

	}

	/**
	 * Sets the current active player's color.
	 * Note that king promotion is checked here!
	 * @return The new active color
	 */
	public Constants.Color changeTurn() {

		Piece.promoteToKingCheck(activeColor, board);

		if (this.activeColor.equals(Constants.Color.RED)) {
			this.activeColor = Constants.Color.WHITE;
		} else {
			this.activeColor = Constants.Color.RED;
		}

		return this.activeColor;
	}

	/**
	 * Sets gameIsOver to true when a player resigns
	 */
	public void playerResign() {
		this.gameIsOver = true;
		this.gameOverReason = GameOverReason.Resigned;
		changeTurn();
	}

	/**
	 * returns the color (red or white) of the turn player
	 * @return the color of the turn player
	 */
	public Constants.Color getActiveColor() {
		return activeColor;
	}

	/**
	 * This is mainly used by controllers to facilitate the creation
	 * of a BoardView object for the view, using this board.
	 *
	 * @return The game board oriented for the red player
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @return Player object assigned to red color
	 */
	public Player getRedPlayer() {
		return redPlayer;
	}

	/**
	 * @return Player object assigned to white color
	 */
	public Player getWhitePlayer() {
		return whitePlayer;
	}

	/**
	 * Returns the other player in this game
	 * @param username the primary player in this game
	 * @return The user that isn't the one that was entered
	 */
	public Player getOtherPlayer(String username) {

		if (this.getRedPlayer().getUsername().equals(username)) {
			return this.getWhitePlayer();
		}
		else {
			return this.getRedPlayer();
		}
	}

	/**
	 * returns the id of this game
	 * @return String representation of the id of this game
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return true if game is over, false otherwise
	 */
	public boolean isGameOver() {
		return gameIsOver;
	}

	/**
	 * Gets the game over reason.
	 *
	 * @return     The game over reason.
	 */
	public GameOverReason getGameOverReason() {
		return gameOverReason;
	}

	/**
	 * Create a replay using the given data from this Game.
	 *
	 * @return     A new Replay object with the data from this Game.
	 */
	public Replay generateReplay() {
		return new Replay(moves, id);
	}

}
