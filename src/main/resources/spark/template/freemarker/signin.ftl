<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title>Web Checkers | Sign In</title>
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>

	<div class="page">

		<h1>Web Checkers | Sign In</h1>

		<!-- Provide a navigation bar -->
		<#include "nav-bar.ftl" />

		<#if message??>
			<#include "message.ftl" />
		</#if>

		<div class="body">

			<form action="./signin" method="POST">
				<p>Enter your name to sign in:</p>
				<#-- @TODO: Inform user of username requirements? -->
				<input name="username" placeholder="CoolUser42" />
				<br/><br/>
				<button type="submit">Sign In</button>
			</form>

		</div>

	</div>
</body>

</html>
