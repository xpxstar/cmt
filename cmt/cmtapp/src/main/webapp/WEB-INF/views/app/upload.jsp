<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>制品检测</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div id="d-main-content">
		<div class="intro">
				<h1>制品检测</h1>
			
		</div>
		<div id="analyze-content">
			<div class="analyze-title">
				<div class="analyze-name col-sm-4">
					<div class="analyze-img">
						<img id="project-avatar" src="<c:url value='/img/logo.png'/>">
						
					</div>
					<h1>Puppet Analyzer</h1>
					<ul id="current-module" class="module-info hide">
						<li>MODULE: </li>
						<li ><h5 class="target-module red"><b id="checked-module">single-hadoop</b></h5></li>
					</ul>
				</div>
				<div class="module-name col-sm-6" role="form">
					<div class="form-group">
						<div class="col-sm-12" style="margin-top: 20px;">
							<a href="#" id="file-upload" class="btn btn-primary">选择制品</a>
						</div>
					</div>
					<div class="form-group">
						<label for="fileName" class="col-sm-12 control-label">file name</label>
						<div class="col-sm-12">
							<div class="col-sm-6" style="padding-left:0px;">
								<input class="form-control" type="text" name="name"
									id="fileName" disabled />
							</div>
							<div class="col-sm-6">
								<a id="upload-btn" class="btn btn-default disabled">上传</a>
								<div style="display:none;" id="some_file_queue"></div>
							</div>
						</div>
					</div>
					<!-- <ul class="tar-form">
					    <li>
					      <label for="tarball" class="tar-labeld">Upload a tarball</label>
					      <input id="tarball" accept="tar" name="file" type="file" required="">
					      <input id="tarname" name="name" type=hidden >
					    </li>
					
					    <li>
					      <input type="submit" value="Upload" id="tar-upload">
					    </li>
				  	</ul> -->
				</div>
			</div>
			<div id="analyze-result" class="analyze-result hidden" >
				<div class="file-list col-sm-2">
					<ul id ="file-list">
					</ul>
				</div>
				<div class="analyze-report col-sm-8">
					<h2>Report</h2>
					<!-- <div id="syntax-check" class="report-content">
						<h4>Syntax Check：</h4>
						<ul>
						</ul>
					</div> -->
					<div id="puppet-lint" class="report-content">
						<h4>Puppet-lint:</h4>
						<ul>
						</ul>
					</div>
					<div id="extend-check" class="report-content">
						<h4>Extend Check:</h4>
						<ul >
						</ul>
					</div>
				</div>
		</div>
		<!-- operation end -->
		<!-- node details end -->
		<script src="<c:url value='/js/cloudeploy/app/app-analyze.js' />"></script>
		
	</div>
	<div id="deploy-state" style="display: none;">
			 <div style="z-index: 1050; position: fixed; top: 45%; left: 30%; width: 500px;">
			 <div style="color: white; margin-bottom: 5px;">action is under-going, be patient...</div>
			 <div class="progress">
			 <div class="progress-bar progress-bar-striped active" 
			 role="progressbar" aria-valuenow="100" aria-valuemin="0"
			 aria-valuemax="100" style="width: 100%;"></div></div></div>
			 <div class="modal-backdrop fade in"></div></div>
</body>
</html>