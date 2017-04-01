var appAnalyze = {
	init : function() {
		$("#deploy-state").hide();
		$("#tar-upload").click(function() {
			appMain.cleanState();
			$("#art-analyze-btn").addClass("active");
			appAnalyze.showShade();
			setTimeout("loadPage(dURIs.viewsURI.artAnalyze, null);",3000);
			
		});
	},
	showShade : function(){
		var maskHtml = '<div id="deploy-state" style="display: none;">'
			+ '<div style="z-index: 1050; position: fixed; top: 45%; left: 30%; width: 500px;">'
			+ '<div style="color: white; margin-bottom: 5px;">正在检测</div>'
			+ '<div class="progress">'
			+ '<div class="progress-bar progress-bar-striped active" '
			+ 'role="progressbar" aria-valuenow="100" aria-valuemin="0"'
			+ 'aria-valuemax="100" style="width: 100%;"></div></div></div>'
			+ '<div class="modal-backdrop fade in"></div></div>';
	$("body").append(maskHtml);
	$("#deploy-state").show();
	}
};
$(document).ready(function() {
	appAnalyze.init();
});