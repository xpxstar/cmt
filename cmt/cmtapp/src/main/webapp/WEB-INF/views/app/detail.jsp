<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>制品详情</title>
</head>
<body>
	<div id="d-main-content">
		
		<div class="intro">
			<h1>制品详情</h1>
		</div> 
		<div class="main-content">
	       <div class="module-info-header">
	         <h1 id="detail-namespace">puppetlabs/puppetdb</h1>
	        </div>
         	<div class="module-info-type">by: <a href="/puppet" id="detail-type">Puppet</a></div>
         	<div class="module-info-git" id="detail-git"><a href="/puppet">项目链接</a></div>
         	<div class="module-info-edit" id="detail-edit">
	         	<i class="fa fa-edit"></i> 
	         	<a href="/">编辑</a>
         	</div>
         	<br>
	        <div class="basic-content-left">
		       <div class="module-info-cat">
		       	分类：<ul class="comma-separated" id="detail-cat">
						<li>database</li>
						<li>puppetdb</li>
					</ul>
				</div>
		
		         <div class="module-info-addition" >
		          简介：<span id="detail-addtion"> Installs PostgreSQL and PuppetDB, sets up the connection to Puppet master.</span>
		         </div>
		
		<!--  
		         <div class="compatibility">
		           <strong>Latest version is compatible with:</strong>
		           <ul class="bullets">
		
		               <li>Puppet Enterprise </li>
		               <li>Puppet 2.7.x</li>
		
		               <li>
		                 <ul class="release-os-compat tags">
		                     <li><span data-releases="5, 6">RedHat</span>,</li>
		                     <li><span data-releases="10.04, 12.04">Ubuntu</span>,</li>
		                     <li><span data-releases="6, 7">Debian</span>,</li>
		                     <li><span data-releases="5, 6">CentOS</span>,</li>
		                     <li><span data-releases="18">Fedora</span>,</li>
		                     <li><span data-releases="5.0">OpenBSD</span></li>
		                 </ul>
		               </li>
		           </ul>
		         </div>
		-->
		      <div class="module-info-tags">
		            <span>标签 :</span>
		            <ul class="comma-separated tags" id="detail-tags">
		                <li><a href="/tags/puppet" title="Tagged &quot;puppet&quot;">puppet</a></li>
		                <li><a href="/tags/puppetdb" title="Tagged &quot;puppetdb&quot;">puppetdb</a></li>
		                <li><a href="/tags/storeconfig" title="Tagged &quot;storeconfig&quot;">storeconfig</a></li>
		            </ul>
	          	</div>
			</div> 
	
	   	
			<div class="download-info clearfix">
		
		          <div class="download-counts" id="detail-download-num">
		            <i class="fa fa-download"></i> 4,653<br>
		            <small>Latest version: 4,568</small>
		          </div>
		          <div class="install-help" id="detail-download-file">
					<small>
						<a href="/v3/files/puppetlabs-puppetdb-5.1.2.tar.gz">download latest tar.gz</a>
					</small>
				</div>
		      </div>
		</div>	
		<div class="module-release-info" id="detail-info">
			
				<div id="module-release-title">Version 
					<strong>5.1.2</strong>
					released
					<strong>Mar 15th 2016</strong>
				</div>
				<p>Quality Score: 5.0</p>
				
			
		</div>
		
		<!--  
			<div id="cate-group" class="col-sm-2">
				<div id="category-panel-root" class="panel-group" role="tablist"
						aria-multiselectable="true">
						</div>
			</div>
		
		<div id="list-group" class="col-sm-8">
		hkjhkjhkh
				<ul  id="art-list" class="list modules">
					
					</ul>
				<div class="pageing">
				
				<ul  id="list-paging" class="pagination">
					
				</ul>
				</div>
				svsfvsdf
			</div>
			-->
		<script src="<c:url value='/js/cloudeploy/app/app-detail.js' />"></script>
		<script src="<c:url value='/js/cloudeploy/app/app-edit.js' />"></script>
	</div>
</body>
</html>