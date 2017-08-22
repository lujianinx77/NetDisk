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
	<script src=<%=request.getContextPath()+"/resources/js/um/register.js" %>></script>
	<title>NetDisk-Register</title>
</head>
<body >
	<div class="container">
		<form class="form-signin"  onsubmit="return false;">
			<h2 class="form-singin-heading">Register</h2>
			<label for="inputUsername" class="sr-only">Username</label>
			<input type="text" id="inputUsername" class="form-control" placeholder="Username" autofocus="autofocus">
			<label for="inputEmail" class="sr-only">Email Address</label>
			<input type="text" id="inputEmail" class="form-control" placeholder="Email Address" >
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" class="form-control" placeholder="Password" >
			<label for="inputConfirmPassword" class="sr-only">Password</label>
			<input type="password" id="inputConfirmPassword" class="form-control" placeholder="Confirm Password" >
			<button class="btn btn-lg btn-primary btn-block"  onclick="register();" type="submit" style="pointer-events: auto;margin-top:11px;">Register</button>
			<div class="alert alert-success hide" id="registerSuccessDiv">注册成功，1S后跳转到<a href="login">主页</a></div>
		</form>
	</div>
</body>
</html>
