<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/bootstrap/css/bootstrap.css" %> type="text/css"/>
	<script src=<%=request.getContextPath()+"/resources/js/lib/jquery-3.2.1.min.js" %>></script>
	<script>
		$(document).ready(function(){
			$("#iframebody").height($(window).height()-72-$("#headerdiv").height());
			$(window).resize(function(){
					$("#iframebody").height($(window).height()-72-$("#headerdiv").height());
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
		<div>
			<iframe id="iframebody" style="margin-top:-5px;;width:100%; border:0px;" scrolling="no" src="fileInfo?type=all"></iframe>
		</div>
	</div>
</body>
</html>