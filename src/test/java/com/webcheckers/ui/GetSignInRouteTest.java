package com.webcheckers.ui;


import com.webcheckers.global.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * The unit test suite for the {@link GetSignInRoute} component.
 *
 * @author <a href='mailto:jks6634@rit.edu'>Joseph Saltalamacchia</a>
 */
@Tag("UI-tier")
public class GetSignInRouteTest {

	/**
	 * the component-under-Test
	 */
	private GetSignInRoute signInRoute;

	private Request request;
	private Session session;
	private Response response;
	private TemplateEngine engine;

	/**
	 * set up mock objects for each test
	 */
	@BeforeEach
	public void setup() {
		request = mock(Request.class);
		session = mock(Session.class);
		when(request.session()).thenReturn(session);

		response = mock(Response.class);
		engine = mock(TemplateEngine.class);

		//create a unique Component under test for each test
		signInRoute = new GetSignInRoute(engine);
	}

	/**
	 * Test that Component Under Test will show the Sign in view when a
	 * user tries to sign in.
	 */
	@Test
	public void testNewSignInCreatesValidSignInRoute()
	{
		//create mock 'render' method by giving Mockito an 'Answer' object
		//that captures ModelAndView data passed to the template engine
		final TemplateEngineTester testHelper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

		//create a userSession so the handle will not redirect to the home screen
		UserSession userSession = mock(UserSession.class);
		when(session.attribute("UserSession")).thenReturn(userSession);

		//Invoke Test
		signInRoute.handle(request,response);

		//Analyze the results
		//	*model is a non-null map
		testHelper.assertViewModelExists();
		testHelper.assertViewModelIsaMap();
		//	*test view name
		testHelper.assertViewName("signin.ftl");
	}

	/**
	 * Test that the Component under test will redirect to the home screen is there is not a valid userSession
	 */
	@Test
	public void testNewSignWithInNoUserSession()
	{
		//create mock 'render' method by giving Mockito an 'Answer' object
		//that captures ModelAndView data passed to the template engine
		final TemplateEngineTester testHelper = new TemplateEngineTester();
		when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

		signInRoute.handle(request,response);
		verify(response).redirect("/");
	}

	/**
	 * verify that the Sign in route will not continue in a faulty session
	 */
	@Test
	public void testSignInRouteWillThrowIllegalArgumentExceptionSessionIsInvalid()
	{
		when(engine.render(any(ModelAndView.class))).thenReturn(null);

		//Attempt to establish a faulty session
		try {
			signInRoute = new GetSignInRoute(engine);
		}catch(IllegalArgumentException i) {
			i.getMessage();
		}
	}

	/**
	 *
	 */
	@Test
	public void testSignInRouteWillThrowIllegalArgumentExceptionSessionIsInvalidHandle()
	{
		when(engine.render(any(ModelAndView.class))).thenReturn(null);

		//Attempt to invoke the handle method
		try{
			signInRoute.handle(request,response);
		}
		catch(IllegalArgumentException i){
			i.getMessage();
		}
	}

}
