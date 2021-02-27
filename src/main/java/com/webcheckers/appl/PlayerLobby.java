package com.webcheckers.appl;

import com.webcheckers.models.Player;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Application level component to handle sign-in actions
 *
 * @author Joseph Saltalamacchia
 * @author Peter Kos
 */
public class PlayerLobby {

	private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

	private HashMap<String, Player> players;

	/**
	 * Initializes the lobby
	 */
	public PlayerLobby() {
		players = new HashMap<>();
	}

	/**
	 * Checks if a given username is valid
	 * ie: Has at least one alphanumeric character, has no non-alphanumeric
	 * or non-space characters, and is not already in use
	 * @param username The username to be vetted
	 * @return true if the username is valid, false if it's not
	 */
	public synchronized boolean isValidUsername(String username) {
		return (username != null && username.matches("^[ A-Za-z0-9]+$") && !(players.containsKey(username)) && !username.trim().isEmpty());
	}

	/**
	 * Adds a username to the list of valid usernames
	 * @param username the username to be added to the list
	 * @param p the Player object to be added to the list
	 * @return the Player that was added, null otherwise
	 */
	public Player addUser(String username, Player p) {
		if (isValidUsername(username)) {
			players.put(username, p);
			return(players.get(username));
		}
		return null;
	}

	/**
	 * Removes the given username from the lobby
	 * @param username the username to be removed
	 * @return the Player that was removed, null otherwise
	 */
	public Player removeUser(String username) {
		return players.remove(username);
	}

	/**
	 * Get all users in the server.
	 *
	 * @return     A HashMap of all users.
	 */
	public HashMap<String, Player> getAllUsers() {
		return players;
	}

	/**
	 * Gets all players, minus.the specified player.
	 * This is used mainly for printing all users except the current user.
	 * Since the callee has access to the UserSession, the choice of which
	 * player to remove is delegated to the callee.
	 *
	 * @param player  The player to exclude from the returned list
	 *
	 * @return A collection of players minus the player provided in param.
	 */
	public HashMap<String, Player> getAllUsersWithout(Player player) {
		HashMap<String, Player> playersWithoutPlayer = (HashMap) players.clone();
		playersWithoutPlayer.remove(player.getUsername());
		return playersWithoutPlayer;
	}

	/**
	 * Returns the current number of users registered to the server
	 * @return the size of usernames
	 */
	public int getCurrentNumberOfPlayers() {
		return players.size();
	}
}
