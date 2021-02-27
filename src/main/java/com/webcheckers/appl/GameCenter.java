package com.webcheckers.appl;

import com.webcheckers.models.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Application level component to handle maintain all games currently being played
 * @author Peter Kos
 */
public class GameCenter {

	private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

	/* HashMap of all games */
	private Map<String, Game> games;

	/**
	 * constructor, creates a new Game center
	 */
	public GameCenter() {
		games = new HashMap<String, Game>();
	}

	/**
	 * Adds a new game to the game center
	 *
	 * @param id The String representing the ID of the Game being added
	 * @param game The the Game that is being added to the HashMap
	 * @return the Game added if it was added successfully, null otherwise
	 */
	public Game addGame(String id, Game game) {
		return games.put(id, game);
	}

	/**
	 * Return the Game of a given ID
	 * @param id a String representing the ID of a Game
	 * @return The Game associated with the ID if it exists, null otherwise
	 */
	public Game getGame(String id) {
		return games.get(id);
	}

	/**
	 * Return a game in the game center that contains a given user
	 * @param username The username of the user who's game is being searched for
	 * @return The game that the user is in, null otherwise
	 */
	public Game getGameForPlayer(String username) {
		for(String key : games.keySet()) {
			if(key.contains(username)) {
				return(games.get(key));
			}
		}

		return null;
	}


	public void replaceGame(Game game, String id) {
		games.replace(id, game);
	}

	/**
	 * Checks to see if there are any games currently in the Game Center
	 * @return True if there are any games in the Game Center, false otherwise
	 */
	public boolean hasGames() {
		return !games.isEmpty();
	}
}
