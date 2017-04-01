<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div id="d-main-content">
		<div class="intro">
			<h1>检索管理</h1>
			<div class="query-form">
			<input id="input-search" type="search" name="query" required placeholder="please input query...">
			<input id="btn-search" type="button" value="检索" class="search-btn">
			</div>
		</div>
		<div class="clearfix col-sm-2">
			<div id="search-filters">
				<div class="clearfix">
	          		<div class="filter-title">Filters</div>
	          		<a href="#" class="clear-filters" onclick="appSearch.clearFilters()">clear</a>
	          		<div class="filter-section">
	          			<h5>选择工具类型</h5>
						<select id="cmt-filter" name="cmt-fileter">
							<option value="puppet">Puppet</option>
							<option value="ansible">Ansible</option>
						</select>
					</div>
	          		<div class="filter-section">
	          			<h5>Operating System</h5>
	          			<select id="os-filter" name="filter-os">
					           <option value="">- Any -</option>
					           <option value="redhat">RedHat</option>
					           <option value="windows">Windows</option>
					           <option value="debian">Debian</option>
					           <option value="ubuntu">Ubuntu</option>
					           <option value="darwin">OS X</option>
					           <option value="solaris">Solaris</option>
					           <option value="sles">SLES</option>
					           <option value="aix">AIX</option>
					           <option value="freebsd">FreeBSD</option>
					           <option value="openbsd">OpenBSD</option>
					           <option value="netbsd">NetBSD</option>
					    </select>
					</div>
					<div class="filter-section">
						<h5>Category</h5>
						<select name="filter-puppet">
						  <option value="">--all--</option>
						  <option value="database">database</option>
						  <option value="development ">development </option>
						  <option value="distributed">distributed</option>
						  <option value="logging">logging</option>
						  <option value="packaging">packaging</option>
						  <option value="runtime">runtime</option>
						  <option value="server">server </option>
						  <option value="system">system</option>
						  <option value="virtualization">virtualization</option>
						</select>
					</div>
					<div class="filter-section">
						<h5>Version</h5>
						<select name="filter-puppet">
						  <option value="">- Any -</option>
						  <option value="4.x">4.x</option>
						  <option value="3.7.x">3.7.x</option>
						  <option value="3.6.x">3.6.x</option>
						  <option value="3.5.x">3.5.x</option>
						  <option value="3.4.x">3.4.x</option>
						  <option value="3.3.x">3.3.x</option>
						  <option value="3.2.x">3.2.x</option>
						  <option value="3.1.x">3.1.x</option>
						  <option value="3.x">3.x</option>
						</select>
					</div>
        		</div>
        	</div>
        </div>
		<div id="list-group" class="col-sm-8">
				
				<ul  id="art-list" class="list modules">
					
					</ul>
				<div class="pageing">
				
				<ul  id="list-paging" class="pagination">
					
				</ul>
				</div>
			</div>
		<!-- operation end -->
		<!-- node details end -->
		<script
			src="<c:url value='/js/cloudeploy/app/app-search.js' />"></script>
			<script
			src="<c:url value='/js/cloudeploy/app/app-list.js' />"></script>
	</div>
</body>
</html>