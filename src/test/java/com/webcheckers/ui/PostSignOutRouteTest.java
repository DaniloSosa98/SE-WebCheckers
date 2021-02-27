package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;


/**
 * Unit test for PostSignOutRoute.
 *
 * @author Elizabeth Sherrock
 */
@Tag("UI-tier")
public class PostSignOutRouteTest {
	//Component under test
	private PostSignOutRoute postSignOutRoute;

	//mocked classes
	private Request request;
	private Response response;

	//Friendly classes
	private PlayerLobby playerLobby;
	private Player player;

	@BeforeEach
	public void setup() {
		//mock the classes
		request = mock(Request.class);
		Session session = mock(Session.class);
		when(request.session()).thenReturn(session);
		response = mock(Response.class);
		UserSession userSession = mock(UserSession.class);
		when(request.session().attribute("UserSession")).thenReturn(userSession);

		Gson gson = new Gson();
		playerLobby = new PlayerLobby();
		player = new Player("user");
		when(userSession.getPlayer()).thenReturn(player);

		//instantiate the component under test
		postSignOutRoute = new PostSignOutRoute(playerLobby);
	}

	@Test
	public void testSignOutFromHomePage() {
		playerLobby.addUser(player.getUsername(), player);

		postSignOutRoute.handle(request, response);

		assertFalse(playerLobby.getAllUsers().containsKey(player.getUsername()));
		verify(response).redirect("/");
	}
}
