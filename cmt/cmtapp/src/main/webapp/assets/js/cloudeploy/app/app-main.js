var appMain = {
	cachedCate : null,
	init : function() {
		appMain.loadCategory();
		appMain.initControlEvent();
		$("#art-list-btn").click();
		calendar();
	},
	loadCategory: function(){
		ajaxGetJsonAuthc(dURIs.categoryURI,null, appMain.initCacheCate,null);
	},
	initCacheCate : function(data){
		appMain.cachedCate = data;
		/*for(var aa in cachedCate.sub){
			alert(cachedCate.sub[aa].name);
		}*/
	},
	initControlEvent : function() {
		$("#art-list-btn").click(function() {
			appMain.cleanState();
			$(this).addClass("active");
			loadPage(dURIs.viewsURI.artList, null);
		});
		$("#art-search-btn").click(function() {
			appMain.cleanState();
			$(this).addClass("active");
			loadPage(dURIs.viewsURI.artSearch, null);
		});
		$("#art-analyze-btn").click(function(){
			appMain.cleanState();
			$(this).addClass("active");
			loadPage(dURIs.viewsURI.artUpload, null);
		});
	},
	cleanState: function(){
		$("#sidebar-list li").each(function(){
			$(this).removeClass("active");
		});
	}
};
function calendar() {
    var date = new Date();
    var monthStr = new Array("Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sep", "Oct", "Nov", "Dec");
    $("#avartar-top").html(monthStr[date.getMonth()]);
    $("#avartar-bottom").html(date.getDate());
}
$(document).ready(function() {
	appMain.init();
});