var appSearch = {
	curQuery:'',
	curType:'puppet',
	curPagesize:6,
	init : function() {
		appSearch.initEvent();
		appList.requestAppList();
	},
	initEvent : function(){
		$("#btn-search").click(function() {
			var query = $("#input-search").val();
			if(query === ''){
				alert("请输入查询，例如 mysql");
				return false;
			}else{
				var os = $("#os-filter").val();
				if(os!=''){
					query+=" "+os;
				}
				appSearch.requestQueryList(query);
			}
		});
	},
	
	requestQueryList : function(query) {
		appSearch.curQuery = query;
		appSearch.curType=$("#cmt-filter").val();
		query = query.replace(/\+\+/g, "pp");
		query = query.replace(/#/g, "_sharp");
		ajaxGetJsonAuthc(dURIs.searchURI+"/6/1?query="+query+"&type="+appSearch.curType, null, appSearch.requestQueryListCallback,
				null);
	},

	requestQueryListCallback : function(data) {
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
				html += '<li class="clearfix"><div class="col"><h3><a href="#" onclick=appSearch.loadDetail('
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
		appSearch.loadPaging(pageNum,totalpage,pageSize);
		$("#art-list").html(html);
	},
	loadList : function(query,pagesize,page){
		appSearch.curQuery = query;
		appSearch.curPagesize = pagesize;
		appSearch.curType = $("#cmt-filter").val();
		var url = dURIs.categoryURI+'/'+pagesize+'/'+page+'?cate='+appSearch.category+'&type='+appSearch.curType;
		ajaxGetJsonAuthc(url, null, appSearch.requestQueryListCallback,
				null);
	},
	loadPageList : function(page){
		var url = dURIs.searchURI+'/'+appSearch.curPagesize+'/'+page+'?query='+appSearch.curQuery+'&type='+appList.curType;
		ajaxGetJsonAuthc(url, null, appSearch.requestQueryListCallback,
				null);
	},
	loadDetail : function(id){
		var url = dURIs.appURI+'/'+id;
		ajaxGetJsonAuthc(url, null, appSearch.requestDetailCallback,
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
			page+='<li><a href="#" onclick=appSearch.loadPageList('+(showPage-1)+')>&laquo;</a></li>';
		}
		page+='<li class="active"><a href="#">'+showPage+'</a></li>';
		showPage++;
		for(;showPage<end;showPage++){
			page+='<li><a href="#" onclick=appSearch.loadPageList('+showPage+')>'+showPage+'</a></li>';
		}
		if(showPage <= total){
			page+='<li class="disabled"><a href="#">...</a></li>';
			page+='<li><a href="#" onclick=appSearch.loadPageList('+total+')>'+total+'</a></li>';
		}
		if((cur+1) == total){
			page+='<li class="disabled"><a href="#">&raquo;</a></li>';
		}else{
			page+='<li><a href="#"  onclick=appSearch.loadPageList('+(cur+2)+')>&raquo;</a></li>';
		}
		$("#list-paging").html(page);
	}
};
$(document).ready(function() {
	appSearch.init();
});