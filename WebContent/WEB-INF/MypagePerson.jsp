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

#person_info_btn {
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
		var attention = ${ dto.m_attention };
		if(attention != [ "없음" ])
		{
			attention.forEach(function(item, index, array)
			{
				$("#"+item).attr("class","btn btn-primary my-1 active");
				$("#"+item).children("input:checkbox").prop("checked", true);
			});
		}
		
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
		    var popOption = "width=450, height=300, resizable=no, scrollbars=no, status=no top=100, left=100;";
		    
		    open("changeImg.mypage", "", popOption);
	    });
	    
	    $("#profile_nickname_btn").on("click", function()
	    {
		    location.href = "person.mypage";
	    });
	    
	    // 이 위로는 공통 함수
	    
	    $("#pw_btn").on("click", function()
	    {
		    var popOption = "width=450, height=450, resizable=no, scrollbars=no, status=no top=100, left=100;";
		    
		    open("changePw.mypage", "", popOption);
	    });
	    
	    $("#submit_btn").on("click", function()
	    {
		    $("#info_form").submit();
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

								<button id="person_info_btn" class="btn btn-warning py-3">
									<h5>
										<strong>개인 정보 수정</strong>
									</h5></button>

							</div>
							
							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="tutor_page_btn" class="btn btn-warning py-3">
										
									튜터 페이지
										
								</button>

							</div>
							
						</div>

					</div>

					<div id="contents_div" class="col-lg-9">

						<div class="row my-2 mb-3">

							<div class="col-lg-12 my-auto">

								<h2>
									<strong>개인 정보 수정</strong>
								</h2>

							</div>

						</div>

						<div class="row my-2">

							<div class="col-lg-3 my-auto">

								<h4>아이디</h4>

							</div>

							<div class="col-lg-9">

								<label class="form-control my-auto">
								
									${ dto.m_id }
								
								</label>

							</div>

						</div>

						<div class="row my-2">

							<div class="col-lg-3 my-auto">

								<h4>비밀번호</h4>

							</div>

							<div class="col-lg-9">

								<input id="pw_btn" class="btn" type="button" value="비밀번호 수정">

							</div>

						</div>

						<form id="info_form" action="update.mypage" method="post">

							<div class="row my-2">

								<div class="col-lg-3 my-auto">

									<h4>닉네임</h4>

								</div>

								<div class="col-lg-9 my-auto">

									<input class="form-control" type="text" name="nickname"
										value="${ dto.m_nickname }">

								</div>

							</div>

							<div class="row my-2">

								<div class="col-lg-3 my-auto">

									<h4>관심사</h4>

								</div>

								<div class="col-lg-9">

									<div class="btn-group-toggle" data-toggle="buttons">

										<label id="at1" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at1">관심사 1</label>
										<label id="at2" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at2">관심사 2</label>
										<label id="at3" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at3">관심사 3</label>
										<label id="at4" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at4">관심사 4</label>
										<label id="at5" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at5">관심사 5</label>
										<label id="at6" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at6">관심사 6</label>
										<label id="at7" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at7">관심사 7</label>
										<label id="at8" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at8">관심사 8</label>
										<label id="at9" class="btn btn-warning my-1"><input
											type="checkbox" name="attention" value="at9">관심사 9</label>

									</div>
								</div>

							</div>

							<div class="row mt-5 mb-1">

								<div id="update_btn_div" class="col-12">

									<input id="submit_btn" class="btn btn-primary" type="button"
										value="수정하기">

								</div>

							</div>

						</form>





					</div>

				</div>

			</div>

		</div>


	</div>

</body>
</html>
