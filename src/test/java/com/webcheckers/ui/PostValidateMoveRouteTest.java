package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Game;
import com.webcheckers.models.Piece;
import com.webcheckers.models.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for PostValidateMoveRoute
 *
 * @author Elizabeth Sherrock
 */
@Tag("UI-tier")
class PostValidateMoveRouteTest {

	//Friendly
	private Player user;
	private Gson gson;

	//Mocks
	private Request request;
	private Response response;
	private QueryParamsMap queryParams;
	private Game game;

	private PostValidateMoveRoute CuT;

	@BeforeEach
	void setup() {
		//Instantiate friendly objects
		user = new Player("username");
		gson = new Gson();
		UserSession userSession = new UserSession();
		BoardView boardView = new BoardView(user);

		//Mock it up
		request = mock(Request.class);
		response = mock(Response.class);
		Session session = mock(Session.class);
		queryParams = mock(QueryParamsMap.class);
		game = mock(Game.class);

		when(request.session()).thenReturn(session);
		when(request.session().attribute(Constants.USER_SESSION_ATTRIBUTE)).thenReturn(userSession);
		when(request.queryMap()).thenReturn(queryParams);
		userSession.setPlayer(user);
		userSession.setBoardView(boardView);

		CuT = new PostValidateMoveRoute(gson);
	}

	@Test
	void redMakesValidMove() {
		user.setColor(Constants.Color.RED);
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);
		when(queryParams.value("actionData")).thenReturn(
			"{\"start\":{\"row\":5,\"cell\":2},\"end\":{\"row\":4,\"cell\":3}}");

		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg INFO '" + Constants.VALID_MOVE + "'}", message.toString());
	}

	@Test
	void redMakesInvalidMove() {
		user.setColor(Constants.Color.RED);
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);
		when(queryParams.value("actionData")).thenReturn(
			"{\"start\":{\"row\":5,\"cell\":2},\"end\":{\"row\":5,\"cell\":3}}");

		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg ERROR '" + Constants.INVALID_MOVE + "'}", message.toString());
	}

	@Test
	void whiteMakesValidMove() {
		user.setColor(Constants.Color.WHITE);
		when(game.getActiveColor()).thenReturn(Constants.Color.WHITE);
		when(queryParams.value("actionData")).thenReturn(
			"{\"start\":{\"row\":2,\"cell\":3},\"end\":{\"row\":3,\"cell\":4}}");

		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg INFO '" + Constants.VALID_MOVE + "'}", message.toString());
	}

	@Test
	void whiteMakesInvalidMove() {
		user.setColor(Constants.Color.WHITE);
		when(game.getActiveColor()).thenReturn(Constants.Color.WHITE);
		when(queryParams.value("actionData")).thenReturn(
			"{\"start\":{\"row\":2,\"cell\":3},\"end\":{\"row\":3,\"cell\":3}}");

		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		assertEquals("{Msg ERROR '" + Constants.INVALID_MOVE + "'}", message.toString());
	}

	@Test
	void redMakesSingleMoveWhenJumpAvailable() {
		user.setColor(Constants.Color.RED);
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);
		//set up a board state in here that allows for a red piece to jump over a white

		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		fail("Test not yet implemented.");
	}

	@Test
	void whiteMakesSingleMoveWhenJumpAvailable() {
		user.setColor(Constants.Color.WHITE);
		when(game.getActiveColor()).thenReturn(Constants.Color.RED);
		//set up a board state in here that allows for a white piece to jump over a red

		String json = (String) CuT.handle(request, response);
		Message message = gson.fromJson(json, Message.class);
		fail("Test not yet implemented.");
	}
}
