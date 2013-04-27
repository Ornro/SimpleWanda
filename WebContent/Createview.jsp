<div id="create_VideoForm">
	<h2>Add Video</h2>
	<div style="margin-bottom: 5px" class="description">
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="New_entities" id="add_video"
		name="Video" enctype="multipart/form-data">
		<br> 
		<input type="hidden" name="hidden_field" value="video"/>
		<label for="name_video"><span>Name</span></label> 
		<input name="name_video" type="text" size="40" /><br> 
		<input type="file" name="videofile_name" />
		
		<input name="view_video" value="<%= request.getParameter("id") %>" type="hidden" />
		<input name="workflow_video" value="1" type="hidden" />
		<p class="validate">
			<br> <input class="validate_button" name="validate"
				type="submit" size="40" value="Validate" />
		</p>
	</form>
</div>