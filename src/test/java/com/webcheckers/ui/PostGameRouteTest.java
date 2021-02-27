package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * Unit test file for PostGameRoute.
 * TODO: Add more info here
 * @author Elizabeth Sherrock
 */
@Tag("UI-tier")
public class PostGameRouteTest {
	//mocks
	private Request request;
	private Response response;
	private Player player2;
	private HashMap<String, Player> playerHM = new HashMap<String, Player>();

	private PostGameRoute CuT;

	/**
	 * Initializes mock objects and creates CuT
	 */
	@BeforeEach
	public void setup() {
		request = mock(Request.class);
		Session session = mock(Session.class);
		when(request.session()).thenReturn(session);
		response = mock(Response.class);
		Player player1 = new Player("p1");
		player2 = new Player("p2");
		player2.setColor(Constants.Color.WHITE);
		PlayerLobby playerLobby = mock(PlayerLobby.class);
		UserSession userSession = mock(UserSession.class);
		GameCenter gameCenter = new GameCenter();
		when(request.session().attribute("UserSession")).thenReturn(userSession);

		when(userSession.getPlayer()).thenReturn(player1);
		when(request.queryParams("user")).thenReturn("p2");
		playerHM.put(player2.getUsername(), player2);
		when(playerLobby.getAllUsers()).thenReturn(playerHM);

		CuT = new PostGameRoute(playerLobby, gameCenter);
	}

	/**
	 * Tests handling when Player 2 is already in a game.
	 */
	@Test
	public void whenPlayer2InGameRedirect() {
		player2.setPlaying(true);
		CuT.handle(request, response);
		verify(response).redirect("/?error=true");
	}

	/**
	 * Tests handling when Player 2 is available for a game.
	 */
	@Test
	public void gameStartsCorrectly() {
		player2.setPlaying(false);
		CuT.handle(request, response);
		verify(response).redirect("/game");
	}
}
