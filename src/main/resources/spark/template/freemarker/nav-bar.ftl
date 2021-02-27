 <div class="navigation">
	<#if currentUser??>
		<a href="/">Home Page</a> |
		<form id="signout" action="/signout" method="post">
			<a href="#" onclick="event.preventDefault(); signout.submit();">Sign Out [${currentUser.username}]</a>
		</form>
	<#else>
		<a href="/">Home Page</a>
		<a href="/signin">Sign In</a>
	</#if>
 </div>
