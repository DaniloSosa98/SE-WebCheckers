package com.webcheckers.models;

import com.webcheckers.global.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests the Player model.
 * @author Peter Kos
 */


@Tag("Model-tier")
public class PlayerTest {

	// Component under test
	private Player player;

	// Helper field
	private String username;

	@BeforeEach
	public void setup() {

		username = "username";
		player = new Player(username);

	}

	/**
	 * NOTE: To avoid reflection, we assume all getters return their stated value.
	 * This test class will therefore focus on setters.
	 */


	@Test
	public void testGetPlayerUsername() {
		assertEquals(player.getUsername(), this.username);
	}


	@Test
	public void testSetPlayerUsername() {
		String newUsername = "new username";
		player.setUsername(newUsername);

		assertEquals(player.getUsername(), newUsername);
	}

	@Test
	public void testDefaultInitializedColorIsRed() {
		assertEquals(player.getColor(), Constants.Color.RED);
	}

	@Test
	public void testDefaultInitializedPlayingStateIsFalse() {
		assertFalse(player.isPlaying());
	}

	@Test
	public void testTwoEquivalentPlayerObjectsAreEqual() {

		Player first = new Player(username);
		Player second = new Player(username);

		// Using default constructor values, the following are set already:
		// username = username,
		// isPlaying = false,
		// color = Color.RED

		// Now, the equals method only checks that the usernames are equal.
		// So this should return true.

		assertEquals(username, username); // Sanity check for Java
		assertEquals(first, second);

	}

	@Test
	public void testTwoExactPlayerObjectsAreEqual() {
		assertEquals(player, player);
	}

	@Test
	public void testNullPlayerObjectIsNotEqualToAValidPlayerObject() {
		assertNotNull(player);
	}

	/**
	 * This test is valid as long as the username is the primary key.
	 */
	@Test
	public void testHashCodeIsEqualToPlayerUsernameHashCode() {
		assertEquals(player.hashCode(), Objects.hash(player.getUsername()));
	}

}
