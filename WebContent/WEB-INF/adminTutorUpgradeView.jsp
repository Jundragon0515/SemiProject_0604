<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>튜터승인결과</title>
</head>
<body>
	<script>
		if(${result}>0){
			alert("튜터승인완료");
		}else{
			alert("튜터승인실패");
		}
	location.href = "mypage.admin";
	</script>
</body>
</html>