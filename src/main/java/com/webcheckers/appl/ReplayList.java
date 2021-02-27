package com.webcheckers.appl;

import com.webcheckers.models.Replay;

import java.util.TreeMap;

/**
 * Application tier component that contains all of the replays in the server
 * @author Joseph Saltalamacchia
 */
public class ReplayList {

	private TreeMap<String, Replay> allReplays;

	/**
	 * Creates a new Replay List
	 * ***There should only ever be one of these made***
	 */
	public ReplayList() {
		allReplays = new TreeMap<String, Replay>();
	}

	/**
	 * Gets all replays.
	 *
	 * @return     All replays.
	 */
	public TreeMap<String, Replay> getAllReplays() {
		return this.allReplays;
	}

	/**
	 * Adds a replay to the list of replays.
	 *
	 * @param      newReplay  The new replay
	 *
	 * @return     Returns the added replay.
	 */
	public Replay addReplay(Replay newReplay) {
		return this.allReplays.put(newReplay.getGameID(), newReplay);
	}

	/**
	 * Returns null if no replay is found for the given player
	 * @param id The game ID
	 * @return The replay, null if not found
	 */
	public Replay getReplayWithID(String id) {
		return allReplays.get(id);
	}
}
