var appAnalyze = {
	lintReportData:null,
	checkReportData:null,
	init : function() {
		$("#deploy-state").hide();
		$('#upload-btn').click(function() {
			appMain.cleanState();
			appAnalyze.showShade();
			$("#art-analyze-btn").addClass("active");
			
			$('#file-upload').uploadify('upload');
		});
		appAnalyze.initUploadify(appAnalyze.uploadedFile);
	},
	initUploadify : function(uploadCallBack) {
		var uploadifyOptions = {
			'auto' : false,
			'multi' : false,
			'uploadLimit' : 1,
			'buttonText' : '请选择制品',
			'swf' : dURIs.swfs + '/uploadify.swf',
			'uploader' : dURIs.filesURI+'analyze/puppet',
			'fileObjName' : 'file',
			'fileTypeExts' :'*.zip',
			 'queueID'  : 'some_file_queue',
			'formData' : {
			},
			'onUploadSuccess' : function(file, data, response) {
				data = $.parseJSON(data);
				 var swfu = $('#file-upload').data('uploadify');
			        var stats = swfu.getStats();
			        stats.successful_uploads=0;
			        swfu.setStats(stats);
			        $("input[name='name']").val('');
				if (verifyParam(uploadCallBack)) {
					uploadCallBack(data);
				}
			},
			'onSelect' : function(file) {
//				if ($("input[name='name']").attr("disabled") == "disabled") {
//					return;
//				}
				$("input[name='name']").val(file.name);
				$('#upload-btn').removeClass("disabled");
			}
		};
		$('#file-upload').uploadify(uploadifyOptions);
	},
	
	uploadedFile:function(data){
		$("#deploy-state").hide();
		$('#upload-btn').addClass("disabled")
		$("#analyze-result").removeClass('hidden');
		var fileshtml = '';
		appAnalyze.lintReportData = data.lint;
		appAnalyze.checkReportData = data.check;
		alert(data.lint)
		alert(data.check)
		for(var p in appAnalyze.checkReportData){
			fileshtml+='<li><a href="#" onclick="appAnalyze.loadReport(this,\''+p+'\')">'+p+'</a>';
		}
		$('#file-list').html(fileshtml);
		var first = $('#file-list a').first();
//		first.addClass('active');
//		alert(first.html());
		appAnalyze.loadReport(first,first.text());
		
	},
	loadReport: function(node,key){
		$('#file-list a').each(function(){
			$(this).removeClass('active');
		});
		$(node).addClass('active');
		var lreport='';
		var llist = appAnalyze.lintReportData[key];
		for(var p in llist){
			lreport+='<li>'+llist[p]+'</li>';
		}
		$("#puppet-lint ul").html(lreport);
		var report='';
		var list = appAnalyze.checkReportData[key];
		for(var p in list){
			report+='<li>'+compose(list[p])+'</li>';
		}
		$("#extend-check ul").html(report);
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
function compose(smell){
	var re = smell['level']+': In '+smell['syntax']+' of '+smell['position']+' '+smell['msg']+' with attribute '+smell['attribute']+' at line '+smell['line']; 
	return re;
}
$(document).ready(function() {
	appAnalyze.init();
});