<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>制品详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
	<div id="d-main-content">
		<div class="intro">
			<h1>制品详情</h1>
		</div>
		
   		<div class="content-board">
	   		<div id="cate-group" class="col-sm-2">
				<div id="category-panel-root" class="panel-group" role="tablist"
						aria-multiselectable="true">
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
		</div>
		<script src="<c:url value='/js/cloudeploy/app/app-list.js' />"></script>
	</div>
</body>
</html>