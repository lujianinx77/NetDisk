$(document).ready(function(){
	$("#submit").click(function(){
		var username=$("#username").val();
		var password=$("#password").val();
		if(username == ""){
			$("#username").addClass("has-error");
		}
		if(password == ""){
			$("#password").addClass("has-error");
		}
	});
	$("#username").focus(function(){
		if($("#username").hasClass("has-error")){
			$("#username").removeClass("has-error");
		}
	});
	$("#password").focus(function(){
		if($("#password").hasClass("has-error")){
			$("#password").removeClass("has-error");
		}
	});
});