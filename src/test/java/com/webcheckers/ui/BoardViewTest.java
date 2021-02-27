package com.webcheckers.ui;

import com.webcheckers.global.Constants;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for BoardView.
 * TODO: Add more info
 * @author Elizabeth Sherrock
 */
@Tag("UI-tier")
public class BoardViewTest {
	private Player player;

	/**
	 * Initializes mock objects
	 */
	@BeforeEach
	public void setup() {
		player = mock(Player.class);
	}
	/**
	 * Tests generating a board for red player
	 */
	@Test
	public void testNewRedBoardView() {
		when(player.getColor()).thenReturn(Constants.Color.RED);
		// check to see if placeRedPieces was called?
		fail("Test not implemented.");
	}

	/**
	 * Tests generating a board for white player
	 */
	@Test
	public void testNewWhiteBoardView() {
		when(player.getColor()).thenReturn(Constants.Color.WHITE);
		// check to see if placeWhitePieces was called?
		fail("Test not implemented.");
	}

}
