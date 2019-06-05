<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>관리자 마이페이지</title>

<style>
div {
	border: 1px solid black;
	box-sizing: border-box;
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

#navi_row {
	justify-content: center;
}

#navi_div {
	text-align: center;
}

#tutorList_btn {
	color: cornflowerblue;
	font-weight: bold;
}

.row * {
	text-align: center;
}

#searchSelect {
	width: 200px;
	margin: 0px;
}
.navi_page_btn{
	border-style : none;
	background-color : white;
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
	    	    
	    $("#profile_img_btn").on("click", function()
	    {
		    var popOption = "width=300, height=300, resizable=no, scrollbars=no, status=no top=100, left=100;";
		    
		    open("changeImg.mypage", "", popOption);
	    });
	    
	    $("#toApplyTutor").on("click",function(){//승인하기 버튼 눌렀을때
	    	var checkedIdLength =  $(".checkedId:checked").length;
	    	if(checkedIdLength==0){
	    		alert("튜터승인할 아이디를 선택해주세요");
	    		return;
	    	}
	    	var applyTutorForm = $('<form></form>');
	        $(applyTutorForm).attr('action', 'tutorUpgrade.admin');
	        $(applyTutorForm).attr('method', 'post');
	        $(applyTutorForm).appendTo('body');
	        
	        $(".checkedId").each(function(i,item){
	        	if($(item).prop("checked")==true){
	        		var val = $(item).val();
	        		alert(val);
	        		var input = $('<input type="hidden" value="'+val+'" name="checkedId">');
	        		$(applyTutorForm).append(input);
	        	}
	        });       
	        $(applyTutorForm).submit();
	    });
	    
	    $(".navi_page_btn").on("click",function(){
            	var page = $(this).text();
            	alert(page); //확인용
            	if(page=="<이전"){
            		page = ${beginpage}-1;
            	}else if(page=="다음>"){
            		page = ${endpage}+1;
            	}
            	location.href = "clickPage.admin?currentPage="+page;
	    });
    });

</script>


</head>
<body>


	<div class="container col-12 mt-3">

		<div id="header_row" class="row">

			<div class="col-10">

				<div class="row">

					<div id="logo_div" class="col-lg-3 d-none d-xl-block">

						<a href="#"> <img id="logo_img" src="nmnb.jpg" alt="">

						</a>

					</div>

					<div id="search_div" class="col-lg-12 col-xl-6 my-auto">

						<div class="row">

							<div class="col-lg-9">

								<input id="search_text" class="form-control" type="text"
									placeholder="input text to search">

							</div>

							<div class="col-lg-3">

								<input id="search_btn" class="btn" type="button" value="검색">

							</div>

						</div>


					</div>

					<div id="event_div" class="col-lg-3 d-none d-xl-block">

						<a href="#"> <img id="event_img" src="nmnb.jpg" alt="">

						</a>

					</div>

				</div>

			</div>

		</div>

		<div id="profile_row" class="row">

			<div class="col-10 my-3">

				<div class="row">

					<div class="col-lg-6">

						<div class="row">

							<div id="profile_img_div" class="col-lg-4">

								<button id="profile_img_btn" class="btn">${ dto.m_photo }

								</button>

							</div>

							<div id="profile_text_div" class="col-lg-8 my-auto">

								<button id="profile_nickname_btn" class="btn">

									<h3>${ dto.m_nickname }님환영합니다.</h3>

								</button>

								<br>

								<button class="btn">뭔가 쓸 거 같은 공간</button>

							</div>

						</div>

					</div>

					<div class="col-lg-6">

						<div class="row">

							<div class="col-md-12 my-auto">무언가에 쓸 수 있을 거 같은 공간</div>

						</div>

					</div>

				</div>

			</div>

		</div>

		<div id="contents_row" class="row mt-5">

			<div class="col-10">

				<div class="row">

					<div id="menu_div" class="col-lg-3">

						<div class="row">

							<div class="col-12">

								<button id="tutorList_btn" class="btn btn link my-3">
									<h5>
										<strong>튜터요청목록</strong>
									</h5>
								</button>

							</div>

						</div>

					</div>
					<div id="contents_div" class="col-lg-9">

						<div class="row header">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<span>튜터 승인 요청 목록</span>
							</div>
						</div>
						<!--					order-(size)-순서-->
						<div class="row topNavi">
							<!--			d-xs-none -> d-none -->
							<div class="col-lg-2 col-md-2 d-none d-md-block">No</div>
							<div class="col-lg-2 col-md-2 col-sm-5 col-6">회원ID</div>
							<div class="col-lg-3 col-md-3 col-sm-5 col-6">회원닉네임</div>
							<div class="col-lg-3 col-md-3 d-none d-md-block">튜터신청일자</div>
							<div class="col-lg-2 col-md-2 col-sm-2 d-2">선택</div>
						</div>
						<div class="row content">
							<c:choose>
								<c:when test="${upgradeList==null}">
									<div class="col-lg-12 col-md-12 col-sm-12">
										<span>튜터 승인 요청 목록이 없습니다.</span>
									</div>
								</c:when>
								<c:otherwise>
									<div class="p-0 col-lg-12 col-md-12 col-sm-12">
										<c:forEach var="list" items="${upgradeList}">
											<div class="m-0 p-0 row lineContent">
												<div class="col-lg-2 col-md-2 d-none d-md-block">${list.up_seq }</div>
												<div class="col-lg-2 col-md-2 col-sm-5 col-6">${list.up_id }</div>
												<div class="col-lg-3 col-md-3 col-sm-5 col-6">${list.up_nickname }</div>
												<div class="col-lg-3 col-md-3 d-none d-md-block">${list.up_applydate }</div>
												<div class="col-lg-2 col-md-2 col-sm-2 d-2">
													<input class="checkedId" type="checkbox"
														value="${list.up_id }">
												</div>
											</div>
										</c:forEach>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="row bottomNavi">
							<div class="col-lg-3 col-md-3 col-sm-3"></div>
							<div class="col-lg-6 col-md-6 col-sm-6">
								<c:forEach var="n" items="${navi}">
									<button type="button" class="navi_page_btn">${n}</button>
								</c:forEach>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3"></div>
						</div>
						<div class="row footer">
							<div class="col-lg-3 col-md-3 col-sm-3">
								<input id="toApplyTutor" class="footBtn" type="button"
									value="튜터승인하기">
							</div>
							<div class="col-lg-9 col-md-9 col-sm-9 text-right">
								<select id="searchSelect" class="custom-select mb-2">
									<option selected value="way">검색방법</option>
									<option value="memberId">아이디</option>
									<option value="nickname">닉네임</option>
								</select> <input id="searchInput" type="text" class="footText"
									placeholder="검색입력"> <input id="search"
									class="footBtn mr-3" type="button" value="검색">
							</div>
						</div>

					</div>
				</div>

			</div>

		</div>

	</div>


	</div>

</body>
</html>