package com.webcheckers.models;

import com.webcheckers.global.Constants;
import com.webcheckers.ui.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the Game model component.
 *
 * @author Elizabeth Sherrock
 */
@Tag("Model-tier")
class GameTest {
	//helper attributes
	private Player redPlayer, whitePlayer;
	private BoardView boardView;
	//component under test
	private Game game;
	@BeforeEach
	void setup() {
		redPlayer = new Player("red");
		whitePlayer = new Player("white");
		whitePlayer.setColor(Constants.Color.WHITE);
		game = new Game(redPlayer, whitePlayer, "id");
		boardView = new BoardView(redPlayer); // mock????
	}

	/*@Test
	void testGameConstructor() {
		fail("Test not implemented.");
	}*/

	/*@Test
	void testMakeSingleMove() {
		fail("Test not implemented.");
	}*/

	//TODO: Add test for jump move and multi-jump when story is implemented.

	@Test
	void testChangeTurnRedToWhite() {
		assertEquals(Constants.Color.WHITE, game.changeTurn());
	}

	@Test
	void testChangeTurnWhiteToRed() {
		game.changeTurn();
		assertEquals(Constants.Color.RED, game.changeTurn());
	}

	@Test
	void testPlayerResign() {
		game.playerResign();
		assertEquals(Constants.GameOverReason.Resigned, game.getGameOverReason());
	}
}
