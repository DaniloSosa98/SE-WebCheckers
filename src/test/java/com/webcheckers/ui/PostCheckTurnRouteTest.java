package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostCheckTurnRoute.
 *
 * @author Elizabeth Sherrock
 */
@Tag("UI-tier")
public class PostCheckTurnRouteTest {
	private Request request;
	private Response response;
	private UserSession userSession;
	private Player user;
	private Game game;
	private Gson gson;
	private PostCheckTurnRoute CuT; //TODO: Change name

	@BeforeEach
	public void setup() {
		request = mock(Request.class);
		Session session = mock(Session.class);
		when(request.session()).thenReturn(session);
		response = mock(Response.class);
		userSession = mock(UserSession.class);
		when(request.session().attribute("UserSession")).thenReturn(userSession);
		user = new Player("user");
		when(userSession.getPlayer()).thenReturn(user);
		game = mock(Game.class);
		gson = new Gson();
		GameCenter gameCenter = mock(GameCenter.class);
		when(gameCenter.getGameForPlayer(user.getUsername())).thenReturn(game);

		CuT = new PostCheckTurnRoute(gson, gameCenter);
	}

	/**
	 * Tests handling when the active color is red and the user's color is red.
	 */
	@Test
	public void whenCurrentUserRedActive() {
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);
		user.setColor(Constants.Color.RED);
		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg INFO 'true'}", message.toString());
	}

	/**
	 * Tests handling when the active color is white and the user's color is white.
	 */
	@Test
	public void whenCurrentUserWhiteActive() {
		when(game.getActiveColor()).thenReturn(Constants.Color.WHITE);
		user.setColor(Constants.Color.WHITE);
		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg INFO 'true'}", message.toString());
	}

	/**
	 * Tests handling when the active color is white and the user's color is red.
	 */
	@Test
	public void whenCurrentUserRedNotActive() {
		when(game.getActiveColor()).thenReturn(Constants.Color.WHITE);
		user.setColor(Constants.Color.RED);
		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg INFO 'false'}", message.toString());
	}

	/**
	 * Tests handling when the active color is red and the user's color is white.
	 */
	@Test
	public void whenCurrentUserWhiteNotActive() {
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);
		user.setColor(Constants.Color.WHITE);
		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg INFO 'false'}", message.toString());
	}
}
