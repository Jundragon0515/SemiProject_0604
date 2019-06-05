<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
#layer {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	display: flex;
	align-items: center;
	justify-content: center;
	display: -webkit-flex; -webkit-align-item; center;
	-webkit-justify-content: center;
}

div {
	border: 1px solid black;
	box-sizing: border-box;
}

.row>div {
	text-align: center;
}

#email_login_btn, #kakao_login_btn, #sign_up_btn ,#toMain_btn{
	width: 222px;
	height: 49px;
}

#naver_login_btn {
	width: 222px;
	height: 49px;
	background-color: transparent;
	border: none;
	cursor: pointer;
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

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<script>
	$(function() {
		$("#email_login_btn").on("click", function() {
			location.href = "EmailLogin.jsp";
		});

		$("#kakao-login-btn")
				.on(
						"click",
						function() {
							Kakao.init('13fe5c08665b4e8a48dc83219f00ee79');
							Kakao.Auth
									.loginForm({
										success : function(authObj) {
											var obj = JSON.stringify(authObj);
											Kakao.API
													.request({
														url : '/v2/user/me',
														success : function(res) {
															var resobj = JSON
																	.stringify(res);
															// 					        console.log(resobj);

															var $form = $('<form></form>');
															$form
																	.attr(
																			'action',
																			'kakao.login');
															$form.attr(
																	'method',
																	'post');
															$form
																	.appendTo('body');

															var idx = $('<input type="hidden" value=`'+resobj+'` name="json">');

															$form.append(idx);
															$form.submit();
														},
														fail : function(error) {
															alert(JSON
																	.stringify(error));
														}
													})
										}
									});
						})

		$("#naver_login_btn").on("click", function() {
			location.href = "naverLogin.login";
		});
		$("#sign_up_btn").on("click", function() {
			location.href = "SignUp.jsp";
		});
		$("#toMain_btn").on("click", function() {
			location.href = "mainHomePage.jsp";
		});

	});

	onload = function() {
		
	};
</script>
</head>
<body>

	<div id="layer">

		<div class="row">

			<div class="col-12 my-1">

				<input id="email_login_btn" class="btn btn-primary" type="button"
					value="이메일로 로그인">

			</div>

			<div class="col-12 my-1">

				<a id="kakao-login-btn"> <img id="kakao-login-btn"
					src="https://kauth.kakao.com/public/widget/login/kr/kr_02_medium.png"
					style="cursor: pointer"
					onmouseover="this.src='https://kauth.kakao.com/public/widget/login/kr/kr_02_medium_press.png'"
					onmouseout="this.src='https://kauth.kakao.com/public/widget/login/kr/kr_02_medium.png'">
				</a>

			</div>

			<div class="col-12 my-1">
				<a> <img id="naver_login_btn" height="49px"
					src="naverLogin_icon.PNG" />
				</a>
			</div>

			<div class="col-12 mt-5">

				<input id="sign_up_btn" class="btn btn-primary" type="button"
					value="회원 가입">

			</div>
			<div class="col-12 my-2">

				<input id="toMain_btn" class="btn btn-primary" type="button"
					value="메인화면으로 돌아가기">

			</div>

		</div>

	</div>



</body>
</html>