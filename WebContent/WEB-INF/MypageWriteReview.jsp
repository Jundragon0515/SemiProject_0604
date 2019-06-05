<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    
<style>

    #textarea
    {
        width: 100%;
    }
    #star_div
    {
        text-align: right;
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
		
		$("#submit_btn").on("click", function()
		{
			$("#form").submit();
		});
		        
	});
        
	onload = function()
	{
		
		
              
	};
        
</script>
</head>
<body>

	
	<form id="form" action="writeReview.mypage" method="post">
	
		<div class="container mt-3">
	    
		    <div class="row">
		        
		        <div class="col-8 my-auto">
		            
		            <h3>리뷰 작성</h3>
		            
		        </div>
		        <div id="star_div" class="col-4 my-auto">
		            
		            <select name="star">
		                
		                <option value="5">★★★★★</option>
		                <option value="4">★★★★☆</option>
		                <option value="3">★★★☆☆</option>
		                <option value="2">★★☆☆☆</option>
		                <option value="1">★☆☆☆☆</option>
		                
		            </select>
		            
		        </div>
		        <div class="col-8 my-auto">
		            
		            <h4>${ title }</h4>
		            
		        </div>
		    </div>
		    
		    <div class="row">
		        
		        <div class="col-12">
		            
		            <textarea name="text" id="textarea" cols="100" rows="10"></textarea>
		            
		        </div>
		        <div class="col-12">
		            
		            <input id="submit_btn" class="btn btn-primary" type="button" value="전송">
		            
		        </div>
		        
		        <div class="col-12">
		        	
		        	<input name="c_id" type="hidden" value=${ c_id }>
		        	<input name="m_id" type="hidden" value=${ m_id }>
		        	<input name="date" type="hidden" value=${ date }>
		        	
		        </div>
		        
		    </div>
		    
		</div>
	
	</form>


</body>
</html>