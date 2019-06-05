<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클래스 구매 결과</title>
</head>
<body>
	<script>
		if(${result==1}){
			alert("구매완료");
		}else{
			alert("구매실패");
		}
		location.href = "clickClass.classInfo?classId="+${classId};
	</script>
</body>
</html>