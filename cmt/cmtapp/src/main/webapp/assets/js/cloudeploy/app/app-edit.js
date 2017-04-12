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
		var count = 1;//事先准备4（最多四个类别）个select，不用的暂时隐藏
		for(count;count<=4;count++){
			html += "<select style='display: none' onchange='appEdit.changeCatList(this.options.selectedIndex,this.id);' id='edit-list"+
			count+"'></select>";
		}
		$("#edit-cat").html(html);
		for (i=1;i<strs.length-1 ;i++){ 
			var tempHtml = "";
			if(category.sub!="undefined"){
				var length = category.sub.length;
				var hasSame = false;//如果类别太细分类中没有则不显示
				for(j=0;j<length;j++){
					tempHtml += "<option>"+category.sub[j].name+"</option>";
					if(category.sub[j].name == strs[i]){
						hasSame = true;
						child = category.sub[j];
						count = Number(count) + 1; 
					}
				}
				category = child;
			}
			if(hasSame){
				$("#edit-list"+i).html(tempHtml);
				$("#edit-list"+i).val(strs[i]);
				$("#edit-list"+i).css('display', 'inline'); 
			}else{
				break;
			}
		} 
		
		$("#edit-addtion").html(arts.addition);
		var tags = "";
		strs=arts.tags.split("/"); 
		for (i=0;i<strs.length ;i++){ 
			tags += strs[i]+",";
		} 
		tags += ",";
		tags = tags.split(",,")[0];
		$("#edit-tags").html("<input type='text' name='tags' style='width:400px;' value="+tags+">");
		
		
	},
	changeCatList : function(data,id){
		var integer = id.split("edit-list")[1];
		var category = cat;
		var length = '';
		for(i=1;i<=integer;i++){//找到当前分类object
			var name = $("#edit-list"+i).val();
			length = category.sub.length;
			for(j=0;j<length;j++){
				if(name == category.sub[j].name){
					category = category.sub[j];
					break;
				}
			}
		}
		integer = Number(integer) + 1;//变化该select后面select里面的内容
		for(i = integer;i<4;i++){
			$("#edit-list"+i).css('display', 'none'); 
		}
		length = category.sub.length;
		
		var tempHtml = "";
		if(length>0){
			for(i = 0;i<length;i++){
				tempHtml += "<option>"+category.sub[i].name+"</option>";
			}
			$("#edit-list"+integer).css('display', 'inline'); 
			$("#edit-list"+integer).html(tempHtml);
		}
	},
	
	saveEdit : function(){
		var name = $("input[name='name']").val();
		var cat = "/";
		for(i = 1;i<=4;i++){
			if($("#edit-list"+i).css('display')!="none"){
				cat += $("#edit-list"+i).val() + "/";
			}
		}
		var addition = $("#edit-addtion").val();
		var tags = "";
		var strs = $("input[name='tags']").val().split(","); 
		for (i=0;i<strs.length ;i++){ 
			tags += strs[i]+"/";
		} 
		tags += "/"; //去掉末尾/
		tags = tags.split("//")[0];
		//alert(name+" "+cat+" "+addition+" "+tags);
	}
};
