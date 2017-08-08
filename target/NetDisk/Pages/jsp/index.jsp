<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" href="resources/styles/index.css" type="text/css" />
	<script src="resources/js/jquery-3.2.1.min.js"></script>
	<script src="resources/js/index.js"></script>
	<title>NetDisk-Login</title>
</head>
<body class="cb">
<div>
	<div class="background">
		<div class="backgroundImage" style="background-image:url(/NetDisk/resources/pictures/mainpagebackground.jpg);"></div>
		<div class="background-overlay"></div>
	</div>
	<div class="outer">
		<div class="middle">
			<div class="inner">
				<div><label>NetDisk</label></div>
				<div class="row text-title">登录</div>
				<div class="errorhide" id="errorinfo"></div>
				<form action="loginAction" method="post">
					<div class="row form-group col-md-24">
						<input type="text" id="username" class="form-control ltr_override" name="username" placeholder="用户名">
					</div>
					<div class="row form-group col-md-24">
					   	<input type="password" id="password" class="form-control ltr_override margin_top" name="password" placeholder="密码">
					</div>
					<div class="row form-group col-md-24">
					   	<input type="button" id="submit" class="btn btn-block btn-primary" value="登陆"/>
					</div>
			    </form>
				<div class="text-13 form-group">没有账号?<a href="newUser" class="display-inline-block" >创建一个！</a></div>
				<div class="text-13 form-group">忘记密码?<a href="getPassword" class="display-inline-block" >找回密码！</a></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
