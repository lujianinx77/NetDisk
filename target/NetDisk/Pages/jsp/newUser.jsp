<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="resources/styles/newuser.css" type="text/css" />
	<script src="resources/js/jquery-3.2.1.min.js"></script>
	<script src="resources/js/newuser.js"></script>
<title>创建账户</title>
</head>
<body>
	<div id="maincontent">
		<div>
			<div>NetDisk</div>
			<div class="head text-subheader">创建账户</div>
		</div>
		<div id="pageControlHost">
			<div style="opacity: 1;">
				<form action="newUserAction" method="post">
					<div id="Credentials">
						<div class="container">
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">用户名</label>
									<input type="text" id="username" class="form-control"/>
									<div id="usernameTip" class="alert hide">用户名必须至少包含 8 个字符.</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">密码</label>
									<input type="password" id="password" class="form-control"/>
									<div id="passwordTip" class="alert hide">密码必须至少包含 8 个字符.</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">确认密码</label>
									<input type="password" id="repassword" class="form-control"/>
									<div id="repasswordTip" class="alert alert-error hide">与密码不同.</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">邮箱</label>
									<input type="text" id="email" class="form-control"/>
									<div id="emailTip" class="alert alert-error hide">邮箱格式错误.</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<input type="button" id="submit" value="创建账户" class="btn btn-primary btn-block"/>
								</div>
							</div>
							<div id="registerSuccessTip" class="hide"><label class="text-base">注册成功！1S后跳转到主页.</label></div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>