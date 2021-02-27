package com.webcheckers.appl;


import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author <a href='mailto:jks6634@rit.edu'>Joseph Saltalamacchia</a>
 */
class PlayerLobbyTest {

	private final String VALID_NAME = "ThisNameIsValid";
	private final String VALID_NAME_WITH_NUMBER = "ThisNameIsValid2";
	private final String VALID_NAME_WITH_SPACE = "This is Also a Valid Name";
	private final String VALID_NAME_ALL_NUM = "1337";
	private final String EMPTY_NAME = "";
	private final String INVALID_NAME = "!#$%^&*()";

	private Player playerValidName, playerValidNumber, playerValidSpace, playerValidAllNum, invalidPlayer;

	//component under test
	private PlayerLobby playerLobby;

	/**
	 * setup before each test
	 */
	@BeforeEach
	void setup()
	{
		playerLobby = new PlayerLobby();
		playerValidName = new Player(VALID_NAME);
		playerValidNumber = new Player(VALID_NAME_WITH_NUMBER);
		playerValidSpace = new Player(VALID_NAME_WITH_SPACE);
		playerValidAllNum = new Player(VALID_NAME_ALL_NUM);
		invalidPlayer = new Player(INVALID_NAME);
	}

	/**
	 * Test the ability to create a new PlayerLobby
	 */
	@Test
	void testMakeNewPlayerLobby()
	{
		final PlayerLobby playerLobby = new PlayerLobby();
		//Invoke test
		assertNotNull(playerLobby);
	}

	/**
	 * Tests that the isValidUsername will pass valid names and fail invalid ones
	 */
	//If anyone has advice: this apparently doesn't quite exercise all of the possible
	//cases, but I can't think of what I'm missing
	@Test
	void testIsValidUsernameAlphabet() {
		assertTrue(playerLobby.isValidUsername(VALID_NAME));
	}

	@Test
	void testIsValidUsernameWithNumbers() {
		assertTrue(playerLobby.isValidUsername(VALID_NAME_WITH_NUMBER));
	}

	@Test
	void testIsValidUsernameWithSpace() {
		assertTrue(playerLobby.isValidUsername(VALID_NAME_WITH_SPACE));
	}

	@Test
	void testIsValidUsernameAllNumbers() {
		assertTrue(playerLobby.isValidUsername(VALID_NAME_ALL_NUM));
	}

	//not thrilled with this test overall, but the only way to test if it would fail
	//a username already in the list was to add that username to the list
	@Test
	void testIsDuplicateUsernameInvalid() {
		playerLobby.addUser(VALID_NAME, playerValidName);
		assertFalse(playerLobby.isValidUsername(VALID_NAME));
	}

	@Test
	void testIsNullUsernameInvalid() {
		assertFalse(playerLobby.isValidUsername(null));
	}

	@Test
	void testIsSymbolsUsernameInvalid() {
		assertFalse(playerLobby.isValidUsername(INVALID_NAME));
	}

	@Test
	void testIsEmptyUsernameInvalid() {
		assertFalse(playerLobby.isValidUsername(EMPTY_NAME));
	}

	/**
	 * tests that Player's usernames are added to the lobby correctly
	 */
	@Test
	void testAddPlayer() {
		assertEquals(playerValidName, playerLobby.addUser(VALID_NAME, playerValidName));
		assertEquals(playerValidNumber, playerLobby.addUser(VALID_NAME_WITH_NUMBER, playerValidNumber));
		assertEquals(playerValidSpace, playerLobby.addUser(VALID_NAME_WITH_SPACE,playerValidSpace));
		assertEquals(playerValidAllNum, playerLobby.addUser(VALID_NAME_ALL_NUM,playerValidAllNum));

		assertNull(playerLobby.addUser(null, invalidPlayer));
		assertNull(playerLobby.addUser(VALID_NAME,playerValidName));
		assertNull(playerLobby.addUser(INVALID_NAME, invalidPlayer));
		assertNull(playerLobby.addUser(EMPTY_NAME, invalidPlayer));
	}

	/**
	 * tests if getAllUsers returns the correct values
	 */
	@Test
	void testGetAllUsers()
	{
		HashMap<String, Player> testHash = new HashMap<>();
		testHash.put(VALID_NAME, playerValidName);
		testHash.put(VALID_NAME_WITH_NUMBER, playerValidNumber);
		testHash.put(VALID_NAME_WITH_SPACE, playerValidSpace);
		testHash.put(VALID_NAME_ALL_NUM, playerValidAllNum);

		playerLobby.addUser(VALID_NAME, playerValidName);
		playerLobby.addUser(VALID_NAME_WITH_NUMBER, playerValidNumber);
		playerLobby.addUser(VALID_NAME_WITH_SPACE, playerValidSpace);
		playerLobby.addUser(VALID_NAME_ALL_NUM, playerValidAllNum);

		assertEquals(testHash, playerLobby.getAllUsers());
	}

	/**
	 * tests if getAllUsersWithout returns the correct values
	 */
	@Test
	void testGetAllUsersWithout()
	{
		HashMap<String, Player> testHash = new HashMap<>();
		testHash.put(VALID_NAME_WITH_NUMBER, playerValidNumber);
		testHash.put(VALID_NAME_WITH_SPACE, playerValidSpace);
		testHash.put(VALID_NAME_ALL_NUM, playerValidAllNum);

		playerLobby.addUser(VALID_NAME, playerValidName);
		playerLobby.addUser(VALID_NAME_WITH_NUMBER, playerValidNumber);
		playerLobby.addUser(VALID_NAME_WITH_SPACE, playerValidSpace);
		playerLobby.addUser(VALID_NAME_ALL_NUM, playerValidAllNum);

		assertEquals(testHash, playerLobby.getAllUsersWithout(playerValidName));
	}

	/**
	 * tests that Player's usernames are removed from the lobby correctly
	 */
	@Test
	void testRemovePlayer() {
		playerLobby.addUser(VALID_NAME, playerValidName);
		assertEquals(playerValidName, playerLobby.removeUser(VALID_NAME));
	}

	/**
	 * tests that the getCurrentNumberOfPlayers method returns the correct values
	 */
	@Test
	void testCurrentNumberOfPlayers()
	{
		assertEquals(0,playerLobby.getCurrentNumberOfPlayers());

		playerLobby.addUser(VALID_NAME, playerValidName);
		assertEquals(1, playerLobby.getCurrentNumberOfPlayers());

		playerLobby.addUser(VALID_NAME_WITH_NUMBER, playerValidNumber);
		playerLobby.addUser(VALID_NAME_WITH_SPACE, playerValidSpace);
		playerLobby.addUser(VALID_NAME_ALL_NUM, playerValidAllNum);
		assertEquals(4,playerLobby.getCurrentNumberOfPlayers());

	}
}
