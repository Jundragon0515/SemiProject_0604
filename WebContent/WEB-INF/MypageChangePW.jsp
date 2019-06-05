<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>exit</title>
    
    <style>
        #btn_div
        {
            text-align: center;
        }
    </style>
    
    <script src="https://code.jquery.com/jquery-3.4.0.min.js">
    </script>
    
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
    <script>
        
        $(function()
        {
            $("#pw_text ,#pwc_text").on("input", function() 
            {
                if ((($("#pw_text").val() !== "") && ($("#pwc_text").val() !== "")) && ($("#pw_text").val() == $("#pwc_text").val())) 
                {
                    $("#pwc_label").text("비밀번호가 일치합니다.");
                    $("#pwc_label").css("color","green");
                }
                else
                {
                    $("#pwc_label").text("비밀번호가 일치하지 않습니다.");
                    $("#pwc_label").css("color","red");
                }
            });
            
            $("#submit_btn").on("click", function()
			{
            	if($("#pwc_label").text() == "비밀번호가 일치합니다.")
            	{
            		$("#pw_form").submit();
            	}
            	else
            	{
            		alert("입력란을 확인하세요");
            	}
			});
        });
        
        onload = function()
        {
              
        };
    </script>
    
    
</head>
<body>
    <form id="pw_form"action="pw.mypage" method="post">
        <h3>비밀번호</h3>
        <input id="pw_text" class="form-control my-1" name="pw" type="password" placeholder="비밀번호">
        <h3>비밀번호 확인</h3>
        <input id="pwc_text" class="form-control my-1" type="password" placeholder="비밀번호 확인">
        <label id="pwc_label"></label><br>
        
        <div id="btn_div">
            
            <input id="submit_btn" class="btn btn-primary" type="button" value="수정">
            
        </div>
        
    </form>
</body>
</html>