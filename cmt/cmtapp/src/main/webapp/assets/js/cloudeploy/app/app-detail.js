var appDetail = {
	arts:'',
	init : function(data) {
		arts = data;
		loadPage(dURIs.viewsURI.artDetail, appDetail.loadDetail);
	},
	loadDetail : function(){
		$("#detail-namespace").html(arts.author+"/"+arts.name);
		$("#detail-type").html(arts.type);
		var html = "<a href='/'>项目链接</a>" ;
		$("#detail-git").html(html);//git 链接
		$("#detail-addtion").html(arts.addition);
		
		var strs= new Array();  
		html = '';
		strs=arts.category.split("/"); 
		for (i=1;i<strs.length ;i++){ 
			//document.write(strs[i]+"<br/>"); 
			html += "<li><a href='javascript:void(0)' onclick=\"appDetail.loadCat('"+strs[i]+"')\""+
			">"+strs[i]+" </a></li>";
		} 
		$("#detail-cat").html(html);
		
		html = '';
		strs=arts.tags.split("/"); 
		for (i=0;i<strs.length ;i++){ 
			//document.write(strs[i]+"<br/>"); 
			html += "<li><a href='/'>"+strs[i]+" </a></li>";
		} 
		$("#detail-tags").html(html);
		
		html = " <i class='fa fa-download'></i>" + arts.alldown +"<br>";
		html += "<small>最新版本下载量: "+arts.download +"</small>"
		$("#detail-download-num").html(html);
		
		html = "<small><a href='/'>download latest tar.gz</a></small>"
		$("#detail-download-file").html(html);
		
		html = "<div id='module-release-title'>版本： <strong>";
		html += arts.version +"</strong> 上传日期： <strong>" + arts.lastDate 
				+ "</strong></div>评分: <strong>"+ arts.score+"</strong>";
		$("#detail-info").html(html);
		
		
	},
	
	loadCat : function(data){
		loadPage(dURIs.viewsURI.artList, appList.loadList("/"+name+"/",6,1));
	},
};
