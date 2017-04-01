var appList = {
	curCategory:'',
	curType:'puppet',
	curPagesize:6,
	init : function() {
		appList.initCategory();
		appList.requestAppList("puppet");
	},
	initCategory : function(){
		var html = painter.paintCategory(appMain.cachedCate);
		$("#category-panel-root").html(html);
		$("#type-list").change(function(){
			if(appList.curCategory===''){
				var type = $(this).val();
				appList.requestAppList(type);
			}else{
				appList.loadList(appList.curCategory,appList.curPagesize,1);
			}
		});
	},
	
	requestAppList : function(type) {
		ajaxGetJsonAuthc(dURIs.appURI+"/6/1?type="+type, null, appList.requestAppListCallback,
				null);
	},

	requestAppListCallback : function(data) {
		var arts = data;
		var pageNum = arts.number;
		var pageSize = arts.size;
		var totalpage = arts.totalPages;
		var apps = arts.content;
		if (!(apps instanceof Array)) {
			return;
		}
		var html = '';
		if (apps.length > 0) {
			for ( var i in apps) {
				var app = apps[i];
				html += '<li class="clearfix"><div class="col"><h3><a href="#" onclick=appList.loadDetail('
						+app.id+')>'
						+app.author+"/"+app.name
						+'</a></h3><div class="tags"><a href="#">'
						+app.tags.replace(/\//g,",")
						+'</a></div><p class="summary">'
						+app.addition
						+'</p><div class="module-footer clearfix">'
						+'<div class="release-info"><small>	<span>Version '
						+app.version
						+' • '+app.lastDate+' • '+app.download
						+'downloads</span></small></div><div class="rank-info"><div class="counts total-download">'
						 +app.alldown+' total download</div></div></div></li>';
			}
			
		} else {
			html = DHtml.emptyRow(6);
		}
		appList.loadPaging(pageNum,totalpage,pageSize);
		$("#art-list").html(html);
	},
	loadList : function(category,pagesize,page){
		appList.curCategory = category;
		appList.curPagesize = pagesize;
		appList.curType=$("#type-list").val();
		var url = dURIs.categoryURI+'/'+pagesize+'/'+page+'?cate='+category+'&type='+appList.curType;
		ajaxGetJsonAuthc(url, null, appList.requestAppListCallback,
				null);
	},
	loadPageList : function(page){
		var url = dURIs.categoryURI+'/'+appList.curPagesize+'/'+page+'?cate='+appList.curCategory+'&type='+appList.curType;
		ajaxGetJsonAuthc(url, null, appList.requestAppListCallback,
				null);
	},
	loadDetail : function(id){
		var url = dURIs.appURI+'/'+id;
		ajaxGetJsonAuthc(url, null, appList.requestDetailCallback,
				null);
	},
	requestDetailCallback : function(data) {
		var arts = data;
	},
	loadPaging: function(cur,total,size){
		var showPage=cur+1;
		var page = '';
		var end = showPage+3;
		if(end > total){
			end = total;
		}
		if(showPage == 1){
			page+='<li class="disabled"><a href="#" >&laquo;</a></li>';
		}else{
			page+='<li><a href="#" onclick=appList.loadPageList('+(showPage-1)+')>&laquo;</a></li>';
		}
		page+='<li class="active"><a href="#">'+showPage+'</a></li>';
		showPage++;
		for(;showPage<end;showPage++){
			page+='<li><a href="#" onclick=appList.loadPageList('+showPage+')>'+showPage+'</a></li>';
		}
		if(showPage <= total){
			page+='<li class="disabled"><a href="#">...</a></li>';
			page+='<li><a href="#" onclick=appList.loadPageList('+total+')>'+total+'</a></li>';
		}
		if((cur+1) == total){
			page+='<li class="disabled"><a href="#">&raquo;</a></li>';
		}else{
			page+='<li><a href="#"  onclick=appList.loadPageList('+(cur+2)+')>&raquo;</a></li>';
		}
		$("#list-paging").html(page);
	}
};


var painter = {
	paintCategory : function(cateJson) {
		var html = '';
		for ( var i in cateJson.sub) {
			var cate = cateJson.sub[i];
			var cateName = cate.name;
			var catePath = cate.path;
			var headingId = 'heading-' + cateName, bodyId = "body-panel-"+cateName;
			var hasChild = false;
			var children = '';
			if(typeof cate.sub != 'undefined'){
				hasChild = true;
				children = cate.sub.length;
			}
			html += '<div class="panel panel-default">'
					+ '<div class="panel-heading" role="tab" id="' 
					+ headingId
					+ '">'
					+ '<h4 class="panel-title">'
					+ '<a data-toggle="collapse" data-parent="#category-panel-'
					+cateJson.name+'" href="#'
					+ bodyId
					+ '" aria-expanded="true" aria-controls="'
					+ bodyId
					+ '" onclick="appList.loadList(\''
					+cate.path
					+'\',6,1)">'
					+ cateName
					+ ' <span class="badge">'
					+ children
					+'</span></a>'
					+ '</h4></div>'
					+ '<div id="'
					+ bodyId
					+ '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="'
					+ headingId + '">';
					if(hasChild){
						html +='<ul id="category-panel-'
							+cateName
							+'" class="list-group sub-group">'+
							painter.paintCategory(cate)+'</ul>';
					}
					
					html+= '</div></div>';
		}
		return html;
	},

	/**
	 * 绘制组件列表
	 * 
	 * @param components
	 */
	paintComponentList : function(components) {
		for ( var i in components) {
			var component = components[i];
			var listId = component.type.name.replace(/::/g, "_") + "-list";
			var html = '<li class="list-group-item">'
					+ component.displayName
					+ '<i class="fa fa-plus pull-right" onclick="javascript:appPanel.addNode('
					+ component.id + ')"></i></li>';
			var group = $("#panel-group-operations #" + listId);
			group.find(".list-group").append(html);
			var badge = group.prev().find(".badge");
			badge.html(String(parseInt(badge.html()) + 1));
		}
	},
};
$(document).ready(function() {
	appList.init();
});