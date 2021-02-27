<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<meta http-equiv="refresh" content="10">
	<title>Web Checkers | ${title}</title>
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

	<h1>Web Checkers | ${title}</h1>

	<!-- Provide a navigation bar -->
	<#include "nav-bar.ftl" />

	<div class="body">

		<!-- Provide a message to the user, if supplied. -->
		<#include "message.ftl" />

	<#if users??>
		<form action="./startgame" method="POST">
		<#list users as username, player>
			<!-- TODO: change the CSS so the buttons don't look terrible -->
			<input type="radio" id="user" name = "user" value="${username}">
			<label for="username">${username}</label>
		</#list>
		</br></br>
		<!-- TODO: disable the button if no users are being displayed -->
		<button type="submit">Start Game</button>
		</form>
	<#else>
		<p>${NumberOfUsers}</p>
	</#if>
		<#if replays??>
			<form action = "./replay/game" method = "GET">
				<#list replays as gameID, replay>
					<input type="radio" id="gameID" name="gameID" value="${gameID}">
					<label for="gameID">${gameID}</label>
				</#list>
				</br></br>
				<button type="submit">Replay</button>
			</form>
		</#if>
	</assign>
	</div>

</div>
</body>

</html>
