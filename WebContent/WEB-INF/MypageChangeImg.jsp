<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>exit</title>
    
    <style>
        .box
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

        });
        
        onload = function()
        {
              
        };
    </script>
    
    
</head>
<body>
    <form action="img.mypage" method="post" enctype="multipart/form-data">
        
        <div class="box">
            
            <input class="btn my-3" name="item" type="file"><br>
            <input class="btn btn-warning" type="submit" value="수정">
            
        </div>
        
    </form>
</body>
</html>