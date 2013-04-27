<%@ page import="fr.irit.wanda.entities.*"%>
<%@ page import="fr.irit.wanda.servlet.ClientConfiguration"%>
<%@ page import="fr.irit.wanda.dao.*"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.io.*"%>

<div id="create_SiteForm" class="">
	<h2>Administration site section</h2>
	<div style="margin-bottom: 5px" class="description">
		<p>
			This section allows you to admin site. <br> You can create a new
			site thanks to this following from. Else, if you have the rights on a
			site, you can admin (cf bottom section).<br> It's reserved for
			administration and and should not be misused.
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="New_entities" id="add_site"
		name="site">
		<input type="hidden" name="hidden_field" value="site"> <label
			for="name_site"><span>Name</span></label> <input type="text"
			name="name_site" placeholder="Name" autofocus="" required="" /> 
		<p class="validate">
			<input class="validate_button" name="validate" type="submit"
				size="40" value="Validate" />
		</p>
	</form>
</div>