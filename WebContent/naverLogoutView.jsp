<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버로그아웃</title>
</head>
<body>
	<script>
	var newWindow = window.open("naverLogoutPop.login","","width=300px,height=300px");
	window.setTimeout(function(){
		newWindow.close();
		alert("로그아웃 되었습니다.");
		location.href = "mainHomePage.jsp";
    },500);
	</script>
</body>
</html>