package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PostResignGameRouteTest {

	private Request request;
	private UserSession userSession;
	private Session session;
	private Response response;
	private GameCenter gameCenter;
	private Gson gson;
	private Game game;
	private Player player;
	private Player player2;
	private PostResignGameRoute CuT;

	@BeforeEach
	public void setup(){
		request = mock(Request.class);
		session = mock(Session.class);
		when(request.session()).thenReturn(session);

		userSession = mock(UserSession.class);
		when(request.session().attribute("UserSession")).thenReturn(userSession);

		response = mock(Response.class);
		gameCenter = mock(GameCenter.class);
		gson = new Gson();

		game = mock(Game.class);

		player = new Player("p1");
		player2 = new Player("p2");

		game = new Game(player, player2, "test");

		gameCenter.addGame("test", game);

		CuT = new PostResignGameRoute(gson, gameCenter);
	}

	@Test
	public void resign() {
		when(userSession.getPlayer()).thenReturn(player);
		when(gameCenter.getGameForPlayer(player.getUsername())).thenReturn(game);
		assertNotNull(CuT);
		assertNotNull(CuT.handle(request, response));
		assertTrue(game.isGameOver());
		assertFalse(player.isPlaying());
	}
}
