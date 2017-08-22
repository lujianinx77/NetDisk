const J_USERNAME = "#inputUsername";
const J_PASSWORD = "#inputPassword"
function login(){
	var username = $(J_USERNAME).val();
	var password = $(J_PASSWORD).val();
	if(username.length < 8){
		 $(J_USERNAME).tooltip({trigger:"manual",title:"用户名不能小于八位!",placement:"bottom"});
		 $(J_USERNAME).tooltip("show");
		 $(J_USERNAME).focus();
		return;
	}
	if(password.length < 8){
		 $(J_PASSWORD).tooltip({trigger:"manual",title:"密码不能小于八位!",placement:"bottom"});
		 $(J_PASSWORD).tooltip("show");
		 $(J_PASSWORD).focus();
		return;
	}
	$.ajax({
		type:"post",
		url:"loginAction",
		data:{"username":username,"password":password},
		dataType:"text",
		success:function(result){
			console.log(result);
			if("fail" == result){
				$(J_USERNAME).val("");
				$(J_PASSWORD).val("");
				$(J_USERNAME).tooltip({trigger:"manual",title:"用户名或密码错误,请重新输入!",placement:"bottom"});
				 $(J_USERNAME).tooltip("show");
				 $(J_USERNAME).focus();
			}
			else{
				window.location="mainPage";
			}
		}
	});
}
$(document).ready(function(){
	$(J_USERNAME).on("input",function(){
		$(J_USERNAME).tooltip("hide");
	});
	$(J_PASSWORD).on("input",function(){
		$(J_PASSWORD).tooltip("hide");
	});
});

