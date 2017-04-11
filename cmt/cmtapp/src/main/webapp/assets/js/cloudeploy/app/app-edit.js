var appEdit = {
	arts:'',
	cat:'',
	init : function(data) {
		arts = data;
		cat = appMain.cachedCate;
		//alert(arts.name);
		loadPage(dURIs.viewsURI.artEdit, appEdit.loadEdit);
	},
	loadEdit : function(){
		var html = arts.author+"/<input type='text' name='name' value="+arts.name+">";
		$("#edit-namespace").html(html);
		
		var strs= new Array();  
		html = '';
		strs=arts.category.split("/"); 
		var category = cat;
		var child = '';
		for (i=1;i<strs.length-1 ;i++){ 
			//document.write(strs[i]+"<br/>"); 
//			
//			html += "<li><a href='javascript:void(0)' onclick=\"appDetail.loadCat('"+strs[i]+"')\""+
//			">"+strs[i]+" </a></li>";
			//alert(strs[i]+child.sub[0]);
			html += "<select onchange='appEdit.changeCatList(this.options.selectedIndex,this.id);' id='edit-list"+
					i+"'>";
			var length = category.sub.length;
			
			for(j=0;j<length;j++){
				html += "<option>"+category.sub[j].name+"</option>";
				//alert(strs[i]+category.sub[j].name);
				if(category.sub[j].name == strs[i]){
					child = category.sub[j];
				}
			}
			category = child;
			html += "</select>";
		} 
		$("#edit-cat").html(html);
		
	},
	changeCatList : function(data,id){
		var html = $("#edit-cat").html();
		
		var integer = id.split("edit-list")[1];
		var category = cat;
		var length = '';
		for(i=1;i<=integer;i++){
			var name = $("#edit-list"+i).val();
			length = category.sub.length;
			for(j=0;j<length;j++){
				//alert(category.sub[j].name);
				if(name == category.sub[j].name){
					category = category.sub[j];
					break;
				}
			}
		}
		integer = Number(integer) + 1;//变化该select后面select里面的内容
		length = category.sub.length;
		var tempHtml = "";
		if(length>0){
			tempHtml = "<select onchange='appEdit.changeCatList(this.options[this.options.selectedIndex].value,this.id);' id='edit-list"+
			integer+"'>";
			for(i = 0;i<length;i++){
				tempHtml += "<option>"+category.sub[i].name+"</option>";
			}
			tempHtml += "</select>";
		}
		
		var str = "<select onchange=\"appEdit.changeCatList(this.options[this.options.selectedIndex].value,this.id);\" id=\"edit-list"+
		integer+"\">";
		html = html.split(str)[0];
		html += tempHtml;
		alert("aaa");
		$("#edit-cat").html(html);
		
		//由于HTML未变 需更新刚改变的select值
//		alert(data);
//		$("#"+id).get(0).selectedindex = data;
	}
};
