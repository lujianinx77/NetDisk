<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/bootstrap/css/bootstrap.css" %> type="text/css"/>
	<script src=<%=request.getContextPath()+"/resources/js/lib/jquery-3.2.1.min.js" %>></script>
	<script src=<%=request.getContextPath()+"/resources/js/fileManager/allpage.js" %>></script>
	<script src=<%=request.getContextPath()+"/resources/js/userinfoManager/userinfoconst.js" %>></script>
	
<title>NetDisk</title>
</head>
<body>
	<input type="hidden" id="baseurl" value=<%=request.getContextPath()%>/>
	<input type="hidden" id="navigationpath" value=""/>
	<div style="padding-left: 0px;padding-right:0px;" class="container-fluid">
			<div style="user-select:none;">
				<div id="operationdivbody"  class="btn-group" style="width:100%;">
					<div id="uploaddiv" class="btn-group">
						<input type="file" class="hide" id="upload" multiple="multiple"/>
						<label class="btn btn-primary" for="upload"><span class="glyphicon glyphicon-upload"></span> 上传</label>
					</div>
					<div id="uploadprogressdiv" class="btn-group hide" style="width:20%;border-radius:4px;">
						<div style="height:34px;margin-bottom:1px;border-bottom:1px solid #ddd" class="progress">
								<div id="uploadprogress" class="progress-bar" style="width:0%;height:34px;"></div>
						</div>
					</div>
					<div class="btn-group">
						<input type="button" id="createfolderbutton" value="创建文件夹" class="btn btn-default"/>
					</div>
					<div id="createfolderdiv" style="width: 40%;" class="input-group btn-group hide">
						<input type="text" id="foldername" class="form-control"/>
						<div class="input-group-btn">
							<button id="createfolderconfirmbutton" class="btn btn-default"><span class="glyphicon glyphicon-ok"></span></button>
							<button id="createfoldercancelbutton" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span></button>
						</div>
					</div>	
					<div id="fileoperation" class="btn-group">
						<input type="button" id="renamebutton" class="btn btn-default" value="重命名"/>
						<input type="button" id="removebutton" class="btn btn-default" value="移动到"/>
						<input type="button" id="downloadbutton" class="btn btn-default" value="下载"/>
						<input type="button" id="deletebutton" class="btn btn-default" value="删除"/>
					</div>
				</div>
				<div id="navigationdivbody">
					<div id="navigationlastdiv" class="hide"><a class="btn btn-default" id="navigationlast">返回上一层</a></div>
					<div>
						<ol class="breadcrumb" id="navigationdiv"><li class="active" id="currentpath">全部文件</li></ol>
					</div>
				</div>
				<div id="fileinfotablediv" style="overflow: auto;">
					<table class="table table-hover" id="filelist">
						<thead>
							<tr>
								<td> 文件名</td>
								<td>文件大小</td>
								<td>上传时间</td>
							</tr>
						</thead>
					</table>
				</div>
			</div>
	</div>
</body>
</html>