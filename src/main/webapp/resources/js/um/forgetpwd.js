const J_USERNAME = "#inputUsername";
const J_EMAIL = "#inputEmail";
const J_PASSWORD="#inputPassword";
const J_CONFIRM_PASSWORD ="#inputConfirmPassword";
const J_REGISTER_SUCCESS_TIP = "#registerSuccessDiv"
function register(){
	var username = $(J_USERNAME).val();
	var password = $(J_PASSWORD).val();
	var email = $(J_EMAIL).val();
	var confirmpassword = $(J_CONFIRM_PASSWORD).val();
		console.log("register");
		if(username.length < 8){
			 $(J_USERNAME).tooltip({trigger:"manual",title:"用户名不能小于八位!",placement:"bottom"});
			 $(J_USERNAME).tooltip("show");
			 $(J_USERNAME).focus();
			return;
		}
		var regex= /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
		if(!regex.test(email)){
			$(J_EMAIL).tooltip({trigger:"manual",title:"邮箱格式错误!",placement:"bottom"});
			 $(J_EMAIL).tooltip("show");
			 $(J_EMAIL).focus();
			return;
		}
		if(password.length < 8){
			 $(J_PASSWORD).tooltip({trigger:"manual",title:"密码不能小于八位!",placement:"bottom"});
			 $(J_PASSWORD).tooltip("show");
			 $(J_PASSWORD).focus();
			return;
		}
		if(confirmpassword != password){
			$(J_CONFIRM_PASSWORD).tooltip({trigger:"manual",title:"确认密码与密码不同!",placement:"bottom"});
			 $(J_CONFIRM_PASSWORD).tooltip("show");
			 $(J_CONFIRM_PASSWORD).focus();
			return;
		}
		$.ajax({
			type:"post",
			url:"getPasswordAction",
			data:{'username':username,'password':password,'email':email},
			dataType:'text',
			success:function(result){
				if("success" == result){
					$(J_REGISTER_SUCCESS_TIP).removeClass("hide");
					setTimeout(function(){
						window.location="login";
					},1000);
				}
			}
		});
}
$(document).ready(function(){
	$(J_USERNAME).blur(function(){
		var username = $(J_USERNAME).val();
		$.ajax({
			type:"post",
			url:"reInspectionAction",
			data:{'username':username},
			dataType:'text',
			success:function(result){
				if("fail" != result){
					$(J_USERNAME).tooltip({trigger:"manual",title:"用户名已不存在!",placement:"bottom"});
					 $(J_USERNAME).tooltip("show");
					 $(J_USERNAME).focus();
					return;
				}
			}
		});
	});
	$(J_USERNAME).on("input",function(){
		$(J_USERNAME).tooltip("hide");
	});
	$(J_EMAIL).on("input",function(){
		$(J_EMAIL).tooltip("hide");
	});
	$(J_PASSWORD).on("input",function(){
		$(J_PASSWORD).tooltip("hide");
	});
	$(J_CONFIRM_PASSWORD).on("input",function(){
		$(J_CONFIRM_PASSWORD).tooltip("hide");
	});
});