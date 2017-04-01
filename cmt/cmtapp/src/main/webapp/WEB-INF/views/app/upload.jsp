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
				<div class="analyze-name">
					<div class="analyze-img">
						<img id="project-avatar" src="<c:url value='/img/logo.png'/>">
						
					</div>
					<h1>Puppet Analyzer</h1>
					
				</div>
				<div class="module-name">
					<ul class="tar-form">
					    <li>
					      <label for="tarball" class="tar-labeld">Upload a tarball</label>
					      <input id="tarball" name="tarball" type="file" required="">
					    </li>
					
					    <li>
					      <input type="submit" value="Upload" id="tar-upload">
					    </li>
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