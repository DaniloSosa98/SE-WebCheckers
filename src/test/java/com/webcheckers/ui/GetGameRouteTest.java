package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayList;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test of the GetGameRoute controller.
 * @author Danilo Sosa
 */

@Tag("UI-tier")
public class GetGameRouteTest {
	// Component under test
	private GetGameRoute CuT;

	//Necessary object for testing
	private TemplateEngine engine;
	private UserSession userSession;
	private Request request;
	private Response response;
	private GameCenter gameCenter;
	private PlayerLobby playerLobby;
	private Game game;
	private Player player1, player2;
	private ReplayList replayList;

	//Current players HashMap
	private HashMap<String, Player> playerHM = new HashMap<String, Player>();

	@BeforeEach
	public void setup() {
		//Set up classes
		engine = mock(TemplateEngine.class);
		request = mock(Request.class);

		Session session = mock(Session.class);
		when(request.session()).thenReturn(session);

		userSession = mock(UserSession.class);
		when(request.session().attribute("UserSession")).thenReturn(userSession);

		response = mock(Response.class);
		gameCenter = mock(GameCenter.class);
		playerLobby = mock(PlayerLobby.class);
		game = mock(Game.class);
		Gson gson = new Gson();
		replayList = mock(ReplayList.class);

		//Instantiate Players used for testing
		player1 = new Player("p1");
		player2 = new Player("p2");

		//Instantiate GetGameRoute for testing
		CuT = new GetGameRoute(gson, engine, gameCenter, replayList);
	}

	@Test
	public void testThereIsACurrentGame() {
		//Put red player1 into players HashMap
		playerHM.put(player1.getUsername(), player1);
		//Set player2 to White and put him into players HashMap
		player2.setColor(Constants.Color.WHITE);
		playerHM.put(player2.getUsername(), player2);

		final TemplateEngineTester helper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(helper.makeAnswer());

		//Current user will be player1
		when(userSession.getPlayer()).thenReturn(player1);

		//OtherUser will be player2
		when(game.getOtherPlayer(player1.getUsername())).thenReturn(player2);
		when(playerLobby.getAllUsers()).thenReturn(playerHM);

		//GameCenter know has a Game
		when(gameCenter.hasGames()).thenReturn(true);

		when(game.getRedPlayer()).thenReturn(player1);
		when(game.getWhitePlayer()).thenReturn(player2);

		//Active color will be Red
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);

		//Return tempGame when asked for it
		when(gameCenter.getGameForPlayer(player1.getUsername())).thenReturn(game);

		//GetGameRoute handle testing
		CuT.handle(request, response);

		//Test View and Map
		helper.assertViewModelExists();
		helper.assertViewModelIsaMap();

		//Test we are in Play mode and title is correct
		helper.assertViewModelAttribute("viewMode", GetGameRoute.Mode.PLAY);
		helper.assertViewModelAttribute("title", "Checkers Game");

		//Test if currentUser from the view is player1
		helper.assertViewModelAttribute("currentUser", player1);
		//Test if redPlayer from view is player1
		helper.assertViewModelAttribute("redPlayer", player1);
		//Test if whitePlayer from view is player2
		helper.assertViewModelAttribute("whitePlayer", player2);
		//Test if activeColor from view is Red
		helper.assertViewModelAttribute("activeColor", Constants.Color.RED);
	}
}
