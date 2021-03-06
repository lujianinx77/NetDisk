$(document).ready(function(){
	$("#username").blur(function(){
		//验证用户名是否重复
		var username = $("#username").val();
		if(username.length<8){
			$("#username").addClass("has-error");
			$("#usernameTip").addClass("alert-error");
			return;
		}
		else{
			//用户名输入正确（大于8为）则隐藏提示
			$("#usernameTip").addClass("hide");
		}
		//验证用户名是否重复
		$.ajax({
			type:'post',
			url:'reInspectionAction',
			data:{"username":username},
			dataType:'text',
			success:function(result){
				if(result == "fail")
					$("#username").addClass("has-error");
			}
		});
		
	});
	$("#username").focus(function(){
		if($("#username").hasClass("has-error")){
			$("#username").removeClass("has-error");
		}
		$("#usernameTip").removeClass("alert-error");
		$("#usernameTip").removeClass("hide");
	});
	$("#password").blur(function(){
		var password = $("#password").val();
		if(password.length<8){
			$("#password").addClass("has-error");
			$("#passwordTip").addClass("alert-error");
			return;
		}
		else{
			//用户名输入正确（大于8为）则隐藏提示
			$("#passwordTip").addClass("hide");
		}
	});
	$("#password").focus(function(){
		if($("#password").hasClass("has-error")){
			$("#password").removeClass("has-error");
		}
		$("#passwordTip").removeClass("alert-error");
		$("#passwordTip").removeClass("hide");
	});
	$("#repassword").blur(function(){
		//验证两个密码是否一致
		if($("#password").val() != $("#repassword").val()){
			$("#repassword").addClass("has-error");
			$("#repasswordTip").removeClass("hide");
		}
	});
	$("#repassword").focus(function(){
		if($("#repassword").hasClass("has-error")){
			$("#repassword").removeClass("has-error");
		}
		$("#repasswordTip").addClass("hide");
		
	});
	$("#email").blur(function(){
		//验证邮箱是否符合格式
		var reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.com)+/;
		var email=$("#email").val();
		if(!reg.test(email)){
			$("#email").addClass("has-error");
			$("#emailTip").removeClass("hide");
		}
	})
	$("#email").focus(function(){
		if($("#email").hasClass("has-error")){
			$("#email").removeClass("has-error")
		}
		$("#emailTip").addClass("hide");
	});
	$("#submit").click(function(){
		//如果存在输入格式错误
		if($("#username").hasClass("has-error")
				||$("password").hasClass("has-error")
				||$("#repassword").hasClass("has-error")
				||$("#email").hasClass("has-error"))
			return;
		$.ajax({
			type:'post',
			url:'registerAction',
			data:{'username':$("#username").val(),'password':$("#password").val(),'email':$("#email").val()},
			dataType:'text',
			success:function(result){
				if(result = "success"){
					$("#registerSuccessTip").removeClass("hide");
				}
			}
		});
	});
});