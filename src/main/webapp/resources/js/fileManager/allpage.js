function disablecheckbox(){
	$("input[type='checkbox']").map(function(){
		$(this).attr("disabled","true");
	});
}
function enablecheckbox(){
	$("input[type='checkbox']").map(function(){
		$(this).removeAttr("disabled");
	});
}
function disableoperationbutton(rename,remove,down,isdelete){
	if(rename)
		$("#renamebutton").attr("disabled","true");
	if(remove)
		$("#removebutton").attr("disabled","true");
	if(down)
		$("#downloadbutton").attr("disabled","true");
	if(isdelete)
		$("#deletebutton").attr("disabled","true");
}
function enableoperationbutton(rename,remove,down,isdelete){
	if(rename)
		$("#renamebutton").removeAttr("disabled");
	if(remove)
		$("#removebutton").removeAttr("disabled");
	if(down)
		$("#downloadbutton").removeAttr("disabled");
	if(isdelete)
		$("#deletebutton").removeAttr("disabled","true");
}
function getcurrentpath(){
	return (typeof($("#navigationpath").val()) == "undefined"?"":$("#navigationpath").val());
}
function refreshcurrentpath(){
	var val = getcurrentpath();
	$.ajax({
		type:"post",
		url:"getFileListAction",
		data:{"path":val},
		dataType:"text",
		success:function(result){
			var jsondata=JSON.parse(result);
			createTableFromJSON(jsondata,"filelist");
			disableoperationbutton(true,true,true,true);
		}
	});
}
function createTableFromJSON(jsondata,tableid){
	$("#"+tableid).children().map(function(){
		if("THEAD" != this.tagName)
			$(this).remove();
	});
	var ret="";
	var baseurl=$("#baseurl").val();
	var tr="<tbody>";
	for(var item in jsondata){
		tr+="<tr id=\"fileitem\" ext-path=\""+jsondata[item].ext+"-"+item+"\">";
		tr+="<td style=\"width:60%;\"><div><input id=\"filecheck\" type=\"checkbox\" ext-path=\""+jsondata[item].ext+"-"+item+"\"/>";
		if("path" == jsondata[item].ext)
			tr+="<img src=\""+baseurl+"/resources/pictures/filewidget/Folder_24.png\">";
		else
			tr+="<img src=\""+baseurl+"/resources/pictures/filewidget/Video_24.png\">";
		tr+="<label>"+item+"</label></div></td>";
		if("path" == jsondata[item].ext)
			tr+="<td>-</td>";
		else
			tr+="<td>"+jsondata[item].size+"</td>";
		tr+="<td>"+jsondata[item].createtime+"</td>";
		tr+="</tr></tbody>";
		$("#"+tableid).append(tr);
		if("移动到" != $("#removebutton").val())
			disablecheckbox();
		tr="";
	}
}
//用于检测是否重名
function checksamename(name){
	var ishave=false;
	$("input[type='checkbox']").map(function(){
		var ext = $(this).attr("ext-path").split("-")[0];
		var itemname = $(this).attr("ext-path").split("-")[1];
		if(itemname == name){
			ishave=true;
		}
	});
	return ishave;
}
$(document).ready(function(){
	/*
	 * 当页面加载完成时，显示根目录的文件
	 * */
	$(window).resize(function(){
		$("#fileinfotablediv").height($(document.body).height()-25-$("#operationdivbody").height()-$("#navigationdivbody").height());
	});
	disableoperationbutton(true,true,true,true);
	$.ajax({
		type:"post",
		url:"getFileListAction",
		data:{"path":""},
		dataType:"text",
		success:function(result){
			var jsondata = JSON.parse(result);
			createTableFromJSON(jsondata,"filelist");
			var val = getcurrentpath();
		}
	});
	/*
	 * 双击文件夹时加载到对应文件夹并添加导航
	 * */
	$("body").delegate("#fileitem","dblclick",function(){
		//当进行重命名时不允许更换页面
		if($("#renamediv").length>0)
			return;
		//title的格式为 ext-path
		var ext = $(this).attr("ext-path").split("-")[0];
		var path = $(this).attr("ext-path").split("-")[1];
		//当类型为非文件夹时推出
		if("path" != ext)
			return;
		//显示返回上一层
		//$("#navigationlastdiv").show();
		//val为一个hidden 用于存储当前文件路径
		var val = getcurrentpath();
		//当进行移动文件时
		if("移动到" != $("#removebutton").val()){
			var oldpath=$("#removebutton").val().split(" To ")[0];
			var split=oldpath.split("/");
			var filename=split[split.length-1];
			if(filename == path)
				return;
			var split = oldpath.split("/");
			var oname="";
			if(split[split.length-1] == "")
				oname=split[split.length-2]+"/";	//文件夹
			else
				oname=split[split.length-1];	//文件
			$("#removebutton").val(oldpath + " To 全部文件/"+val+path+"/"+oname);
		}
		$.ajax({
			type:"post",
			url:"getFileListAction",
			data:{"path":val+path+"/"},
			dataType:"text",
			success:function(result){
				var jsondata = JSON.parse(result);
				createTableFromJSON(jsondata,"filelist");
				//修改当前路径
				var val = getcurrentpath();
				val+=path+"/";
				$("#navigationpath").val(val);
				//修改导航
				var lastp = $("#currentpath").text();
				$("#currentpath").remove();
				$("#navigationdiv").append("<li><a id=\"navigationitem\">"+lastp+"</a></li>");
				$("#navigationdiv").append("<li class=\"active\" id=\"currentpath\">"+path+"</li>");
			}
		});
	});
	$("body").delegate("#navigationitem","click",function(){
		//当进行重命名时不允许更换页面
		if($("#renamediv").length>0)
			return;
		var path="";
		var val="";
		if("全部文件" != $(this).text()){
			//$("#navigationlastdiv").show();
			path=$(this).text();
			var isadd=true;	//用于标记是否超过了点击的路径
			//遍历所有导航标签
			$("#navigationdiv").children().map(function(){
				//将点击的标签替换为label
				if(path == $(this).text()){
					$(this).remove();
					$("#navigationdiv").append("<li class=\"active\" id=\"currentpath\">"+path+"</li>");
					isadd=false;
				}
				else{
					//删除所有点击标签之后的标签
					if("全部文件" != $(this).text() && isadd)
						val+=$(this).text()+"/";
					if(!isadd){
						$(this).remove();
					}
				}
			});
			path = val+path+"/";
		}
		else{
			//$("#navigationlastdiv").hide();
			$("#navigationdiv").empty();
			$("#navigationdiv").append("<li class=\"active\" id=\"currentpath\">全部文件</li>");
			path="";
		}
		if("移动到" != $("#removebutton").val()){
			var oldpath=$("#removebutton").val().split(" To ")[0];
			var split = oldpath.split("/");
			var oname="";
			if(split[split.length-1] == "")
				oname=split[split.length-2]+"/";	//文件夹
			else
				oname=split[split.length-1];	//文件
			$("#removebutton").val(oldpath + " To 全部文件/"+path+oname);
		}
		//修改当前路径
		$("#navigationpath").val(path);
		$.ajax({
			type:"post",
			url:"getFileListAction",
			data:{"path":path},
			dataType:"text",
			success:function(result){
				var jsondata = JSON.parse(result);
				createTableFromJSON(jsondata,"filelist");	
			}
		});
	});
	//返回上一层单击事件
	$("#navigationlast").click(function(){
		//当进行重命名时不允许更换页面
		if($("#renamediv").length>0)
			return;
		var val=getcurrentpath();
		var split = val.split("/");
		if(split.length <= 2)
			val="全部文件";
		else{
			val = split[split.length-3];
		}
		$("div#navigationdiv").children().map(function(){
			if($(this).text() == val)
				$(this).click();
		});
	});
	//复选框单击
	$("body").delegate("#filecheck","click",function(){
		var checkcount=0;
		$("input[type='checkbox']").map(function(){
			if($(this).prop("checked"))
				checkcount++;
		});
		if(checkcount <= 0)
			disableoperationbutton(true,true,true,true);
		else{
			if(checkcount > 1){
				disableoperationbutton(true,true,false,false);
			}
			else{
				enableoperationbutton(true,true,true,true);
			}
		}
	});
	$("#renamebutton").click(function(){
		$("input[type='checkbox']").map(function(){
			if($(this).prop("checked")){
				var ext = $(this).attr("ext-path").split("-")[0];
				var oldname = $(this).attr("ext-path").split("-")[1];
				$(this).siblings().map(function(){
					if("LABEL" == this.tagName){
						disablecheckbox();	//禁用复选框
						var addon="";
						if("path" != ext && "" != ext)
							addon="<span class=\"input-group-addon\">."+ext+"</span>";
						$(this).parent().append("<div id=\"renamediv\" style=\"display:inline; width:40%; position:absolute;\" class=\"input-group input-group-sm\">" 
								+"<input style=\"width:60%;\" class=\"form-control\"  type=\"text\" id=\"newnametext\"/>"
								+addon
								+"<div class=\"input-group-btn\">" 
								+"<button class=\"btn btn-default\" id=\"renameconfirmbutton\">"
								+"<span class=\"glyphicon glyphicon-ok\"></span></button>" 
								+"<button class=\"btn btn-default\" id=\"renamecancelbutton\">" 
								+"<span class=\"glyphicon glyphicon-remove\"></span></button></div></div>");
						$parent=$(this).parent();
						$(this).remove();
						disableoperationbutton(true,true,true,true);
						$("#newnametext").focus();
						$("#renamecancelbutton").click(function(){
							$("#renamediv").remove();
							$parent.append("<label>"+oldname+"</label>");
							enableoperationbutton(true,true,true,true);
							enablecheckbox();	//恢复复选框
						});
						$("#renameconfirmbutton").click(function(){
							if(checksamename(oldname)){
								$("#newnametext").addClass("has-error");
								return;
							}
							var val = getcurrentpath();
							var newpath=val+$("#newnametext").val()+ ("path"==ext?"/":""==ext?"":"."+ext);
							var oldpath = val+oldname+("path" == ext?"/":"");
							$("#renamediv").remove();
							$parent.append("<label>"+oldname+"</label>");
							enableoperationbutton(true,true,true,true);
							enablecheckbox();	//恢复复选框
							//TODO:向服务器请求并修改页面上的值
							$.ajax({
								type:"post",
								url:"renameFileAction",
								data:{"oldName":oldpath,"newName":newpath},
								dataType:"text",
								success:function(result){
									if("success" == result){
										refreshcurrentpath();
									}
								}
							});
						});
					}
				});
			}
		});
	});
	//移动按钮事件
	$("#removebutton").click(function(){
		if("移动到" == $(this).val()){
			var oldname="";
			$("input[type='checkbox']").map(function(){
				if($(this).prop("checked")){
					var ext = $(this).attr("ext-path").split("-")[0];
					oldname = $(this).attr("ext-path").split("-")[1]+("path" == ext?"/":"");
				}
			});
			disableoperationbutton(true,false,true,true);
			disablecheckbox();
			var oldpath="全部文件/"+getcurrentpath()+oldname;
			$(this).val(oldpath+" To "+oldpath);
		}
		else{
			enableoperationbutton(true,true,true,true);
			enablecheckbox();
			var oldpath =$(this).val().split(" To ")[0].replace("全部文件/","");
			var newpath = $(this).val().split(" To ")[1].replace("全部文件/","");
			$(this).val("移动到");
			if(oldpath == newpath)
				return;
			//TODO 如果移动到的文件夹存在相同的文件（文件夹）
			var split=oldpath.split("/");
			var ext="";
			var filename="";
			if("" == split[split.length-1]){
				filename=split[split.length-2]+"/";
			}
			else{
				filename=split[split.length-1];
			}
			var replacename=filename;
			var ishave=false;
			//处理文件（夹）名重复 -- 在后面添加(n)
			var i=1;
			var patt=/\([0-9]+\)/i;
			do{
				ishave=false;
				$("input[type='checkbox']").map(function(){
					var ext = $(this).attr("ext-path").split("-")[0];
					var itemname = $(this).attr("ext-path").split("-")[1]+("path" == ext?"/":"");
					if(itemname == filename){
						ishave=true;
						if(patt.test(filename))
							filename = filename.replace(patt,"("+i+")");
						else{
							if("path" == ext){	
								var split = filename.split("/");
								filename=split[split.length-2]+"("+i+")/";
							}
							else{
								var split = filename.split(".");
								if(split.length > 1)	//防止无扩展名的文件出现错误
									filename=filename.replace("."+ext,"")+"("+i+")."+ext;
								else
									filename=filename+"("+i+")";
							}
						}
					}
				});
				i++;
			}while(ishave);
			newpath=newpath.replace(replacename,filename);
			$.ajax({
				type:"post",
				url:"moveFileAction",
				data:{"oldName":oldpath,"newName":newpath},
				dataType:"text",
				success:function(result){
					if("success" == result){
						refreshcurrentpath();
					}
				}
			});
		}
	});
	//删除按钮单击事件
	$("#deletebutton").click(function(){
		var deletepaths=new Array();
		var val=getcurrentpath();
		//获取选中的文件名
		$("input[type='checkbox']").map(function(){
			if($(this).prop("checked")){
				var ext = $(this).attr("ext-path").split("-")[0];
				oldname = val + $(this).attr("ext-path").split("-")[1]+("path" == ext?"/":"");
				deletepaths.push(oldname);
			}
		});
		$.ajax({
			type:"post",
			url:"deleteFilesAction",
			data:{names:deletepaths},
			dataType:"text",
			success:function(result){
				if("success" == result){
					refreshcurrentpath();
				}
			}
		});
	});
	$("#createfolderbutton").click(function(){
		$("#createfolderdiv").removeClass("hide");
		$("#foldername").focus();
	});
	$("#foldername").focus(function(){
		$(this).removeClass("has-error");
	});
	$("#createfolderconfirmbutton").click(function(){
		var foldername = $("#foldername").val()
		//文件夹名已存在
		if(checksamename(foldername)){
			$("#foldername").addClass("has-error");
			return;
		}
		$("#createfolderdiv").addClass("hide");
		//TODO 判断文件名是否符合规则
		var val=getcurrentpath()+foldername+"/";
		$("#foldername").val("");
		//TODO 请求创建文件夹
		$.ajax({
			type:"post",
			url:"createFolderAction",
			data:{"folderName":val},
			dataType:"text",
			success:function(result){
				if("success" == result){
					refreshcurrentpath();
				}
			}
		});
	});
	$("#createfoldercancelbutton").click(function(){
		$("#createfolderdiv").addClass("hide");
		$("#foldername").val("");
	});
	$("#upload").on("change",function(){
		if("" == this.value)
			return;
		$("#uploaddiv").addClass("hide");
		$("#uploadprogressdiv").removeClass("hide");
		var file= $("#upload").get(0).files[0];
		//console.log(file);
		//处理重名
		var filename = file["name"];
		var ishave=false;
		//处理文件（夹）名重复 -- 在后面添加(n)
		var i=1;
		do{
			ishave=false;
			$("input[type='checkbox']").map(function(){
				var ext = $(this).attr("ext-path").split("-")[0];
				var itemname = $(this).attr("ext-path").split("-")[1]+("path" == ext?"/":"");
				if("path" == ext)
					return;
				//console.log(itemname+"----"+filename);
				if(itemname == filename){
				ishave=true;
				//先进行正则查找(n)
				var patt=/\([0-9]+\)/i;
				if(patt.test(filename)){	//匹配成功
					filename = filename.replace(patt,"("+i+")");
				}
				else{
					//如果不存在则在字符串结尾添加
					var split = filename.split(".");
					if(split.length > 1)	//防止无扩展名的文件出现错误
						filename=filename.replace("."+ext,"")+"("+i+")."+ext;
					else
						filename=filename+"("+i+")";
					}
				}
			});
			i++;
		}while(ishave);
		var formData = new FormData();
		formData.append("fileName",filename);
		formData.append("path",getcurrentpath());
		formData.append("file",file);
		$.ajax({
			type:"post",
			url:"fileUploadAction",
			data:formData,
			processData:false,
			contentType:false,
			xhr:function(){
				var xhr = $.ajaxSettings.xhr();
				if(onprogress && xhr.upload){
					//console.log("添加上传监听函数 onprogress");
					xhr.upload.addEventListener("progress",onprogress,false);
				}
				return xhr;
			},
			success:function(result){
				if("success"==result)
					{
						refreshcurrentpath();
						$("#uploaddiv").removeClass("hide");
						$("#uploadprogressdiv").addClass("hide");
						var upload=$("#upload").val("");
					}
			}
		});
	});
	function onprogress(evt){
		var loaded = evt.loaded;
		var tol = evt.total;
		var per = Math.floor(100*loaded/tol);
		var totalwidth = $("#uploadprogress").parent().width();
		$("#uploadprogress").width((totalwidth/100)*per);
		//console.log("以上传"+per+"%");
	}
});