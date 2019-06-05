<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Document</title>

<style>

/*         header	 */
* {
	font-family: 'Noto Sans KR', sans-serif;
}
div {
	padding: 0px;
}
#header {
	width: 100%;
	margin: auto;
	margin: 0px;
	padding: 0px;
	text-align: center;
	justify-content: center;
}
#container_div, #header
{
/* 	padding-left: 0px !important; */
/*     padding-right: 0px !important; */
	margin: 0px !important;
}
#search
{
	text-align: center !important;
	justify-content: center !important;
}
#searchbox {
	height: 38px;
	position: relative;
	top: 4px;
	border: 1px solid #ffba00;
	border-radius: 10px;
	padding: 10px;
}
#logo {
	position: relative;
	bottom: 20px;
	cursor: pointer;
}
#header {
	height: 100%;
	width: 100%;
	padding: 64px 32px;
	
}
.headBtn {
	color: white !important;
}
.headBtn:hover {
	background-color: burlywood;
	border: 1px solid burlywood;
	color: white;
}
/*         header	 */


#header_div{
 	border: 1px solid #FFC107;
 	box-sizing: border-box;
 } 

#profile_div_row, #profile_div
{
	text-align: center;
	justify-content: center;
}

#header_row, #profile_row, #contents_row {
	justify-content: center;
}

#logo_div, #event_div, #profile_img_div {
	text-align: center;
}

#logo_img, #event_img {
	width: 200px;
	height: 100px;
}

#search_div {
	text-align: center;
}

#search_text {
	width: 100%;
}

#profile_img_div img {
	width: 100px;
	height: 100px;
	border-radius: 50%;
}

.class_div {
	justify-content: center;
}

.class_img_div {
	text-align: center;
}

.class_text_div {
	text-align: center;
}

.class_img {
	width: 100px;
	height: 100px;
}

#navi_div {
	text-align: center;
}

#update_btn_div {
	text-align: center;
}

#update_btn {
	width: 30%;
}

#tutor_page_btn {
	color: cornflowerblue;
}
#ing_class_btn, #done_class_btn, #person_info_btn, #tutor_page_btn
{
	width: 100%;
	height: 100%;
}
</style>

<script src="https://code.jquery.com/jquery-3.4.0.min.js">
	
</script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script>
	$(function()
    {
	    $("#search_btn").on("click", function()
	    {
		    location.href = "query.query?query=" + $("#search_text").val();
	    });
	    
	    $("#ing_class_btn").on("click", function()
	    {
		    location.href = "doing.mypage?page=1";
	    });
	    
	    $("#done_class_btn").on("click", function()
	    {
		    location.href = "done.mypage?page=1";
	    });
	    
	    $("#person_info_btn").on("click", function()
	    {
		    location.href = "person.mypage?page=1";
	    });
	    
	    $("#tutor_page_btn").on("click", function()
		{
			location.href = "tutor.mypage?page=1";
		});
	    
	    $("#profile_img_btn").on("click", function()
	    {
		    var popOption = "width=300, height=300, resizable=no, scrollbars=no, status=no top=100, left=100;";
		    
		    open("changeImg.mypage", "", popOption);
	    });
	    
	    $("#profile_nickname_btn").on("click", function()
	    {
		    location.href = "person.mypage";
	    });
	    
    });
    
    onload = function()
    {
	    
    };
</script>


</head>
<body>


	<div id="container_div" class="container col-12 mt-3">

		<!--           HEADER-->
            <div id=header class=row>
			<div class="col-12 col-lg-3">
				<img src="logo.png" id=logo>
			</div>
			<div class="col-12 col-lg-6">
					<form id=search class="form-inline my-2 my-lg-0">
						<div class="row justify-content-center">
							<div class="col-12">
								<input type="search" placeholder="취미를 검색해 보세요!"
									aria-label="Search" id=searchbox>
								<button class="btn btn-warning my-2 my-sm-0 headBtn"
									type="submit">Search</button>
							</div>
						</div>
					</form>
				</div>
			<div class="col-12 col-lg-3">
				<c:choose>
					<c:when test="${loginId==null}">
						<button id="toLogin" class="btn btn-warning my-2 my-sm-0 headBtn"
							type="button">login</button>
						<button id="toSignup" class="btn btn-warning my-2 my-sm-0 headBtn"
							type="button">signup</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-warning my-2 my-sm-0 headBtn" type="submit"
							id=mypage_btn>mypage</button>
						<button class="btn btn-warning my-2 my-sm-0 headBtn" type="submit"
							id=logout_btn>logout</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
            <!--           HEADER-->

		<div id="profile_row" class="row">

			<div class="col-10 my-3">

				<div id="profile_div_row" class="row">

					<div class="col-lg-6">

						<div id="profile_div" class="row">

							<div id="profile_img_div" class="col-lg-4">

								<button id="profile_img_btn" class="btn">${ dto.m_photo }

								</button>

							</div>

							<div id="profile_text_div" class="col-lg-8 my-auto">

								<button id="profile_nickname_btn" class="btn">

									<h3>${ dto.m_nickname } 님 환영합니다.</h3>

								</button>

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

		<div id="contents_row" class="row my-5">

			<div class="col-10">

				<div class="row">

					<div id="menu_div" class="col-lg-3">

						<div class="row">

							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="ing_class_btn" class="btn btn-warning py-3">
									수강 중 클래스
								</button>

							</div>

							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="done_class_btn" class="btn btn-warning py-3">수강
									한 클래스</button>

							</div>

							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="person_info_btn" class="btn btn-warning py-3">개인
									정보 수정</button>

							</div>
							
							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="tutor_page_btn" class="btn btn-warning py-3">
										
									<h5>
										<strong>튜터 페이지</strong>
									</h5>
										
								</button>

							</div>
							
						</div>

					</div>

					<div id="contents_div" class="col-lg-9">

						<c:if test="${ check=='true' }">
						
							<span>현재 신청 대기중</span>
							
						</c:if>
						
						<c:if test="${ check=='false' }">
						
							<form id="apply_form" action="apply.mypage" method="post">
						
								<input name="id" type="hidden" value="${ dto.m_id }">
								<input name="nickname" type="hidden" value="${ dto.m_nickname }">
								
								<input type="submit">
								
							</form>
							
						</c:if>
						
						<c:if test="${ check=='tutor' }">
						
							<script>
							
								location.href = "ForTutor.jsp";
							
							</script>
							
						</c:if>
												

					</div>

				</div>

			</div>

		</div>


	</div>

</body>
</html>