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

#contents_div  img {
	width: 100%;
	height: 100%;
}

.class_btn_div {
	text-align: center;
}

#navi_div {
	text-align: center;
}

#done_class_btn {
	color: cornflowerblue;
	font-weight: bold;
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
		    var popOption = "width=450, height=300, resizable=no, scrollbars=no, status=no top=100, left=100;";
		    
		    open("changeImg.mypage", "", popOption);
	    });
	    
	    $("#profile_nickname_btn").on("click", function()
	    {
		    location.href = "person.mypage";
	    });
	    
	    // 이 위로는 공통 함수
	    
	    $(document).on("click", ".review_class_btn", function()
	    {
            var popOption = "width=500, height=500, resizable=no, scrollbars=no, status=no top=100, left=100;";
			var classTitle = $(this).siblings(".class_title").val();
			var classDate = $(this).siblings(".class_date").val();
			var classId = $(this).siblings(".class_id").val();
            
            open("review.mypage?c_id="+classId+"&date="+classDate+"&title="+classTitle, "", popOption);
	    });
	    
	    $(document).on("click", ".detail_class_btn", function()
	    {
	       location.href = "detail.page?id=" + $(this).attr("name");
	    });
	           	    
	    $(document).on("click", ".navi_btn", function()
	    {
	    	location.href = "done.mypage?page=" + $(this).val();
	    });
	    $(document).on("click","#prev_btn",function()
	    {
	    	location.href = "done.mypage?page="+${ startNavi - 1};
	    });
	    $(document).on("click","#next_btn",function()
	    {
	    	location.href = "done.mypage?page="+${ endNavi + 1};
	    });
	    
	    if(${ currentPage } % 5 == 0)
		{
			$($(".navi_btn")[${ 4 }]).css("color", "red");
		}
		else
		{
			$($(".navi_btn")[${ currentPage %5 - 1 }]).css("color", "red");
		}
	    
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

		<div id="contents_row" class="row mt-5">

			<div id="contents_outter_div" class="col-10">

				<div class="row">

					<div id="menu_div" class="col-lg-3">

						<div class="row">

							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="ing_class_btn" class="btn btn-warning py-3">
									수강 중 클래스
								</button>

							</div>

							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="done_class_btn" class="btn btn-warning py-3">
									
									<h5>
										<strong>수강 한 클래스</strong>
									</h5>
									
								</button>

							</div>

							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="person_info_btn" class="btn btn-warning py-3">개인
									정보 수정</button>

							</div>
							
							<div class="col-6 col-md-4 col-lg-12 my-1">

								<button id="tutor_page_btn" class="btn btn-warning py-3">
										
									튜터 페이지
										
								</button>

							</div>
							
						</div>

					</div>

					<div id="contents_div" class="col-lg-9">

						<c:forEach var="i" begin="1" end="${ listsize }" step="1">

							<div class="row class_div my-3">

								<div class="col-12">

									<div class="row">

										<div class="class_img_div col-lg-2 my-auto">

											${ list[i-1].img }
										</div>

										<div class="class_text_div col-lg-7 my-auto">

											<h4>${ list[i-1].title }</h4>
											${ list[i-1].tutorId } <br> 
											${ list[i-1].date } <br>

										</div>

										<div class="class_btn_div col-lg-3 my-auto">

											<input class="btn btn-primary detail_class_btn mx-3" name=${ list[i-1].classId } type="button" value="자세히">
											<input class="btn btn-primary review_class_btn mx-3" name=${ list[i-1].classId } type="button" value="리뷰 작성">
											
											<input class="class_title" type="hidden" value="${ list[i-1].title }">
											<input class="class_date" type="hidden" value="${ list[i-1].date }">
											<input class="class_id" type="hidden" value="${ list[i-1].classId }">
											
										</div>
										
									</div>

								</div>

							</div>

						</c:forEach>

					</div>



				</div>

				<div id="navi_row" class="row my-3">

					<div id="navi_div" class="col-12">

						<c:if test="${ needPrev }">
							<input id="prev_btn" type="button" class="btn btn-link" value="<">
						</c:if>
						<c:forEach var="i" begin="${ startNavi }" end="${ endNavi }">
							<input class="btn navi_btn" name="${ i }" type="button"
								value="${ i }">
						</c:forEach>
						<c:if test="${ needNext }">
							<input id="next_btn" type="button" class="btn btn-link" value=">">
						</c:if>

					</div>

				</div>

			</div>

		</div>


	</div>

</body>
</html>
