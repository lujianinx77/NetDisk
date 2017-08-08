<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/bootstrap/css/bootstrap.css" %> type="text/css"/>
	<script src=<%=request.getContextPath()+"/resources/js/lib/jquery-3.2.1.min.js" %>></script>
	<script>
		$(document).ready(function(){
			$(window).resize(function(){
					$("#iframebody").height($(window).height()-72-$("#headerdiv").height()-$("#navigationdiv").height());
				});
			});
	</script>
<title>NetDisk</title>
</head>
<body>
	<input type="hidden" id="baseurl" value=<%=request.getContextPath()%>/>
	<div class="container-fluid">
		<div id="headerdiv" class="page-header">
			<h1>NetDisk,<%= request.getSession().getAttribute("username") %></h1>
		</div>
		<div id="navigationdiv" style="margin-top: -5px;" >
			<div id="top">
				<ul class="nav nav-tabs">
					<li class="active"><a>全部文件</a></li>
					<li><a>图片</a></li>
					<li><a>视频</a></li>
					<li><a>其他</a></li>
				</ul>
			</div>
		</div>
		<div>
			<iframe id="iframebody" style="height:450px; width:100%; border-left:1px solid #ddd;border-right:1px solid #ddd;border-top:0px;" scrolling="no" src="fileInfo?type=all"></iframe>
		</div>
	</div>
</body>
</html>