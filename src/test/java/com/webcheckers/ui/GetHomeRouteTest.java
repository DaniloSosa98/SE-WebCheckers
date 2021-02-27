package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the {@link GetHomeRoute} component.
 *
 * @author <a href='mailto:dwc9402@rit.edu'>Donald Craig</a>
 */
@Tag("UI-tier")
public class GetHomeRouteTest {

	/**
	 * The component-under-test (CuT).
	 *
	 */
	private GetHomeRoute CuT;
	private HashMap<String, Player> playerHM = new HashMap<String, Player>();

	// friendly objects
	private GameCenter gameCenter;
	private Game game;

	// mock objects
	private Request request;
	private UserSession userSession;

	private TemplateEngine engine;
	private Response response;

	/**
	 * Set up new mock objects for each test.
	 */
	@BeforeEach
	public void setup() {
		request = mock(Request.class);
		Session session = mock(Session.class);
		when(request.session()).thenReturn(session);
		userSession = mock(UserSession.class);
		when(request.session().attribute("UserSession")).thenReturn(userSession);
		response = mock(Response.class);
		engine = mock(TemplateEngine.class);
		gameCenter = mock(GameCenter.class);
		PlayerLobby playerLobby = mock(PlayerLobby.class);
		game = mock(Game.class);

		//create a unique Component under test for each test
		//CuT = new GetHomeRoute(engine, playerLobby);
	}


	/**
	 * Test that the Component under test will render home screen when the player
	 * is not in game yet (new session)
	 */
	@Test
	public void newSessionNoPlayersInGame() {

		final TemplateEngineTester testHelper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

		CuT.handle(request,response);
		testHelper.assertViewModelExists();
		testHelper.assertViewModelIsaMap();
		testHelper.assertViewName("home.ftl");

	}

	/**
	 * Test that the Component under test will redirect to the game screen if there is an existing session in progress
	 */
	@Test
	public void existingSessionTwoPlayersInGame() {

		Player player1 = new Player("p1");
		Player player2 = new Player("p2");
		playerHM.put(player1.getUsername(), player1);
		playerHM.put(player2.getUsername(), player2);
		player1.setPlaying(true);
		player2.setPlaying(true);

		final TemplateEngineTester testHelper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
		when(userSession.getPlayer()).thenReturn(player1);

		// Invoke the test
		CuT.handle(request,response);

		verify(response).redirect("/game");
	}
}
