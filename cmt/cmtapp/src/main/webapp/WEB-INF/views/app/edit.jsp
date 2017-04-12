<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>制品编辑</title>
</head>
<body>
	<div id="d-main-content">
		
		<div class="intro">
			<h1>制品编辑</h1>
		</div> 
		<div class="main-content">
	       <div class="module-info-header">
	         <h1 id="edit-namespace">puppetlabs/puppetdb</h1>
	        </div>
         	<div class="module-info-type">by: <a href="/puppet" id="edit-type">Puppet</a></div>
         	
         	
         	<br>
	        <div class="basic-content-left">
		       <div class="module-info-cat">
		       	分类：<div class="comma-separated" id="edit-cat"></div>
				</div>
		
		         <div class="module-info-addition" >
		          简介：<br><br>
		          <textarea rows='3' cols='40' name='addition' id="edit-addtion" style="width: 800px; height: 100px;"></textarea>
		         </div>
		
		
		      <div class="module-info-tags">
		            <span>标签 :</span>
		            <span class="comma-separated tags" id="edit-tags"></span>
	          	</div>
	          	(注：标签用逗号分隔)
	          	<br><br><br>
	          	<input type="button" value="保存" class="module-info-save" onclick="appEdit.saveEdit()"/>
			</div> 
	
	   	
		</div>	
		
		
	
		<script src="<c:url value='/js/cloudeploy/app/app-edit.js' />"></script>
	</div>
</body>
</html>