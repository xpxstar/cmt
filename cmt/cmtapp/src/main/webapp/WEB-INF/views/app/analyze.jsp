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
				<ul class="module-info">
						<li>MODULE: </li>
						<li ><h5 class="target-module red"><b id="checked-module">single-hadoop</b></h5></li>
					</ul>
				</div>
			</div>
			<div class="analyze-result">
				<div class="file-list col-sm-2">
					<ul>
						<li><a href="#" class="active">config</a>
						<li><a href="#" onclick="">init.pp</a>
						<li><a href="#" onclick="">install.pp</a>
						<li><a href="#" onclick="">rsync.pp</a>
						<li><a href="#" onclick="">ssh.pp</a>
						<li><a href="#" onclick="">startup.pp</a>
					</ul>
					
				</div>
				<div class="analyze-report col-sm-8">
					<h2>Report</h2>
					<div id="syntax-check" class="report-content">
						<h4>Syntax Check：</h4>
						<ul>
							<li>ERROR：Could not parse for environment production: Syntax error at 'handoop' at init.pp:2</li>
						</ul>
					</div>
					<div id="puppet-lint" class="report-content">
						<h4>Puppet-lint:</h4>
						<ul>
							<li>ERROR: two-space soft tabs not used on line 14</li>
							<li>FIXED: unquoted resource title on line 15</li>
							<li>WARNING: line has more than 80 characters on line 25</li>
						</ul>
					</div>
					<div id="extend-check" class="report-content">
						<h4>Extend Check:</h4>
						<ul>
							<li>ERROR: In {resource} of {file}-naming {db.conf} with attribute {mode} format error: line 19 </li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- operation end -->
		<!-- node details end -->
		<script src="<c:url value='/js/cloudeploy/app/app-analyze.js' />"></script>
	</div>
</body>
</html>