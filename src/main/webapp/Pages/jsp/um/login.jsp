<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,height=device-height, initial-scale=1">
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/bootstrap/css/bootstrap.css" %> type="text/css" />
	<link rel="stylesheet" href=<%=request.getContextPath()+"/resources/styles/login.css" %> type="text/css" />
	<script src=<%=request.getContextPath()+"/resources/js/lib/jquery-3.2.1.min.js" %>></script>
	<script src=<%=request.getContextPath()+"/resources/bootstrap/js/bootstrap.min.js" %>></script>
	<script src=<%=request.getContextPath()+"/resources/js/um/login.js" %>></script>
	<title>NetDisk-Login</title>
</head>
<body >
	<div class="container">
		<form class="form-signin"  onsubmit="return false;">
			<h2 class="form-singin-heading">Please log in</h2>
			<label for="inputEmail" class="sr-only">Email address</label>
			<input type="text" id="inputUsername" class="form-control" placeholder="Username" autofocus="autofocus">
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" class="form-control" placeholder="Password" >
			<button class="btn btn-lg btn-primary btn-block"  onclick="login();" type="submit" style="pointer-events: auto;margin-top:11px;">Sign in</button>
			<p style="margin-top:15px;">没有账号?<a href="register">创建账号</a></p>
			<p>忘记密码?<a href="forgetpwd">找回密码</a></p>
		</form>
	</div>
</body>
</html>
