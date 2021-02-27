package com.webcheckers.ui;


import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.global.Constants;
import com.webcheckers.global.UserSession;
import com.webcheckers.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Test of the PostSignInRoute controller.
 * @author Peter Kos
 */


@Tag("UI-tier")
public class PostSignInRouteTest {


	// Component under test
	private PostSignInRoute postSignInRoute;

	private UserSession userSession;
	private Request request;
	private Response response;
	private TemplateEngineTester testHelper;

	@BeforeEach
	public void setup() {

		// Instantiate friendlies
		// Friendly objects
		PlayerLobby playerLobby = new PlayerLobby();

		// Setup other classes
		// Not so friendly objects
		Session session = mock(Session.class);
		userSession    = mock(UserSession.class);
		request        = mock(Request.class);
		response       = mock(Response.class);
		TemplateEngine templateEngine = mock(TemplateEngine.class);

		// Setup and register our fancy special overbuilt tester object
		testHelper = new TemplateEngineTester();
	    Mockito.when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

		// Return OUR UserSession object
		Mockito.when(request.session()).thenReturn(session);
		Mockito.when(session.attribute("UserSession")).thenReturn(userSession);

		// Finally, instantiate a brand new PostSignInRoute for each test
		postSignInRoute = new PostSignInRoute(templateEngine, playerLobby);

	}

	@Test
	public void testWhenUsernameHasNoAlphanumericCharactersShowInvalidMessage() {

		// One possible condition of an invalid username
		// Note: depends on PlayerLobby.isValidUsername() to work!
		String invalidUsername = "";

		// Setup path to return our invalid username from request params to UserSession
		Mockito.when(request.queryParams("username")).thenReturn(invalidUsername);

		postSignInRoute.handle(request, response);

		testHelper.assertViewModelAttribute("message", Constants.invalidUsernameMessage);

	}


	@Test
	public void testWhenUsernameHasNonAlphanumericCharactersShowInvalidMessage() {

		// One possible condition of an invalid username
		// Note: depends on PlayerLobby.isValidUsername() to work!
		// Tested using non-alphanumeric ASCII codes: https://www.ascii-code.com/
		String invalidUsername = ":;<=>?@[\\]^_`{|}~\"";

		// Setup path to return our invalid username from request params to UserSession
		Mockito.when(request.queryParams("username")).thenReturn(invalidUsername);

		postSignInRoute.handle(request, response);

		testHelper.assertViewModelAttribute("message", Constants.invalidUsernameMessage);

	}

	@Test
	public void testValidUserIsAddedToUserSession() {

		// Invalid test as we are attempting to test a mock class.
		// UserSession needs to be tested for this test to be valid!
	}

	@Test
	public void testValidUserIsRedirectedToHome() {

		String username = "James Gosling";
		Player player = new Player(username);

		// Setup path to return our invalid username from request params to UserSession
		Mockito.when(request.queryParams("username")).thenReturn(username);
		Mockito.when(userSession.getPlayer()).thenReturn(player);

		postSignInRoute.handle(request, response);

		verify(response).redirect("/");

	}

}
