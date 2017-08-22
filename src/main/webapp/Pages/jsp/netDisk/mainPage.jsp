<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/bootstrap/css/bootstrap.css" %> type="text/css"/>
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/styles/mainpage.css" %> type="text/css"/>
	<script src=<%=request.getContextPath()+"/resources/js/lib/jquery-3.2.1.min.js" %>></script>
	<script src=<%=request.getContextPath()+"/resources/bootstrap/js/bootstrap.min.js" %>></script>
	<script src=<%=request.getContextPath()+"/resources/js/fileManager/allpage.js" %>></script>
<title>NetDisk</title>
</head>
<body>
	<input type="hidden" id="baseurl" value=<%=request.getContextPath()%>/>
	<input type="hidden" id="navigationpath" value=""/>
	<div class="container-fluid">
			<div style="user-select:none;">
				<div id="navigationdivbody" class="navbar navbar-fixed-top"  style="background-color: #f5f5f5;margin-left:15px;margin-right:15px; margin-top:15px;">
					<div id="uploaddiv" class="btn-group" >
							<label class="btn btn-primary" for="upload"><span class="glyphicon glyphicon-upload"></span> 上传</label>
							<input type="file" class="hide" id="upload" multiple="multiple"/>
							<input type="button" id="createfolderbutton" value="创建文件夹" class="btn btn-default" />
							<input type="button" id="renamebutton" class="btn btn-default" value="重命名" />
							<input type="button" id="removebutton" class="btn btn-default" value="移动到"/>
							<input type="button" id="downloadbutton" class="btn btn-default" value="下载"/>
							<input type="button" id="deletebutton" class="btn btn-default" value="删除"/>
					</div>
					<div style="height:3px;margin-bottom:0px;" class="progress hide"  id="uploadprogressdiv">
							<div class="progress-bar"  role="progressbar"  id="uploadprogress" style="width: 60%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					<div> 
						<ol style="margin-bottom:0px;" class="breadcrumb" id="navigationdiv"><li class="active" id="currentpath">全部文件</li></ol>
				</div>
				</div>
				<div id="fileinfotablediv" style="overflow: auto; margin-top:80px;">
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
	<!-- 模态框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="createfoldermodal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">创建文件夹</h4>
      </div>
      <div class="modal-body">
        <input type="text" class="form-control" id="foldername">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="createfolderconfirmbutton">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>