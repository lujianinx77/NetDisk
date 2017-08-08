<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="resources/styles/newuser.css" type="text/css" />
<title>找回密码</title>
</head>
<body>
	<div id="maincontent">
		<div>
			<div>NetDisk</div>
			<div class="head text-subheader">找回密码</div>
		</div>
		<div id="pageControlHost">
			<div style="opacity: 1;">
				<form action="getPassWordAction" method="post">
					<div id="Credentials">
						<div class="container">
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">用户名</label>
									<input type="text" id="username" class="form-control"/>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">邮箱</label>
									<input type="text" id="email" class="form-control"/>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">新密码</label>
									<input type="text" id="password" class="form-control"/>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<label class="text-base">确认密码</label>
									<input type="text" id="repassword" class="form-control"/>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-xs-18">
									<input type="submit" value="更改密码" class="btn btn-primary btn-block"/>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>