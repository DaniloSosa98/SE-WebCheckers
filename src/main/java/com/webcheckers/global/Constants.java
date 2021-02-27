
package com.webcheckers.global;


import com.webcheckers.models.Player;
import com.webcheckers.util.Message;

/**
 * A utility file for storing constants used across the project.
 * @author Peter Kos
 */
public class Constants {

	// MARK: Enums

	public enum GameOverReason {
		OutOfMoves, OutOfPieces, Resigned, Unknown
	}

	public enum Color {
		RED, WHITE
	}

	// MARK: String attributes to the UI tier

	public static final String USER_SESSION_ATTRIBUTE = "UserSession";
	public static final String VALID_MOVE             = "You have made a valid move, you can either hit \"submit turn\" " +
		"to finalize " + "your move or \"Backup\" to return your piece to it's original position";
	public static final String INVALID_MOVE = "An invalid move has been made";
	public static final String JMP_AVAILABLE = "A jump move is available";
	public static final String MOVE_REMOVED = "Move successfully removed";
	public static final String NOT_REMOVED = "No moves left to remove";
	public static final String SUBMITTED = "You dun did good kid";

	public static String numPlayersString(int playerCount) {
		return "There are currently " + playerCount + " players signed in to the Lobby.";
	}

	// MARK: Keys

	// GameOver
	public static final String gameOverBooleanKey = "isGameOver";
	public static final String gameOverMessageKey = "gameOverMessage";
	public static final String modeOptionsAsJSONKey = "modeOptionsAsJSON";
	// Replay
	public static final String hasNextKey = "hasNext";
	public static final String hasPreviousKey = "hasPrevious";

	// MARK: Messages

	public static final Message invalidUsernameMessage   = Message.error("Username is invalid or already taken. Please try a different username.");
	public static final Message userAlreadyInGameMessage = Message.error("That user is in a game, please choose another.");
	public static final Message welcomeMessage = Message.info("Welcome to the world of online Checkers.");

	public static Message welcomeMessageWithUsername(String username) {
		return Message.info("Welcome to the world of online Checkers, " + username + "!");
	}

	public static String endGameMessage(Player player, GameOverReason reason) {
		switch (reason) {
			case OutOfMoves: return "The game is over! " + player.getUsername() + " blocked all possible moves!";
			case OutOfPieces: return "The game is over! " + player.getUsername() + " captured all the pieces!";
			case Resigned: return "The game is over! " + player.getUsername() + " wins as the other player has resigned.";
			default: return "Unknown reason why game is over.";
		}

	}

}
