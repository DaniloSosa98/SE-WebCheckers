package com.webcheckers.appl;

import com.webcheckers.models.Game;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test of the GameCenter class.
 * @author Peter Kos
 */


public class GameCenterTest {


	// Component under test
	private GameCenter gameCenter;

	@BeforeEach
	public void setup() {

		gameCenter = new GameCenter();

	}

	@Test
	public void testGameIsAddedToGamesList() {

		String gameId = "game";
		Game game = new Game(new Player("p1"), new Player("p2"), gameId);

		gameCenter.addGame(gameId, game);

		assertEquals(gameCenter.getGame(gameId), game);
	}

	@Test
	public void testGamesListIsEmptyWhenNoGamesArePresent() {
		assertFalse(gameCenter.hasGames());
	}

	@Test
	public void testGamesListIsNotEmptyWhenGamesArePresent() {

		String gameId = "game";
		Game game = new Game(new Player("p1"), new Player("p2"), gameId);

		gameCenter.addGame(gameId, game);

		assertTrue(gameCenter.hasGames());
	}
}
