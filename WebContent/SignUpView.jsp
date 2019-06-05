<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
if(${result == "성공"}){
	alert("회원가입 성공");
}else if(${result == "실패"}){
	alert("회원가입 실패");
}
location.href="mainHomePage.jsp";
</script>
</body>
</html>