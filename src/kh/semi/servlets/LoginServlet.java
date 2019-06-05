package kh.semi.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jdk.nashorn.internal.parser.JSONParser;
import kh.semi.dao.MemberDAO;
import kh.semi.dto.MemberDTO;

@WebServlet("*.login")
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		String url = request.getRequestURI().substring(request.getContextPath().length() + 1);
		System.out.println(url);
		if(url.equals("kakao.login"))
		{
			String info = request.getParameter("json").substring(1, request.getParameter("json").length() - 1);
			JsonParser jp = new JsonParser();
			JsonObject root = jp.parse(info).getAsJsonObject();
			JsonObject prop = root.get("properties").getAsJsonObject();
			JsonObject account = root.get("kakao_account").getAsJsonObject();

			String id = "k" + root.get("id").toString();

			try
			{
				MemberDAO dao = new MemberDAO();
				if( ! dao.kakaoLoginCheck(id))
				{
					String nickname = prop.get("nickname").toString();
					nickname = nickname.substring(1, nickname.length() - 1);
					String ageRange = "기타";
					String birthday = "";
					String gender = "";

					try
					{
						ageRange = account.get("age_range").toString();
						ageRange = ageRange.substring(1, ageRange.length() - 1);
						ageRange = ageRange.substring(0, 2);
					}catch(NullPointerException e)
					{
					}

					try
					{
						birthday = account.get("birthday").toString();
						birthday = birthday.substring(1, birthday.length() - 1);
					}catch(NullPointerException e)
					{
					}

					try
					{
						gender = account.get("gender").toString();
						gender = gender.substring(1, gender.length() - 1);

						if(gender.equals("male"))
						{
							gender = "M";
						}else if(gender.equals("female"))
						{
							gender = "F";
						}
					}catch(NullPointerException e)
					{
					}

					int result = dao.kakaoSignUp(
							new MemberDTO(id, dao.toSha256("password"), nickname, gender, ageRange, birthday)
							);

					if(result > 0)
					{
						request.getSession().setAttribute("loginId", id);
						request.getSession().setAttribute("loginType", "kakao");
						request.getRequestDispatcher("mainHomePage.jsp").forward(request, response);
					}else
					{
						System.out.println("DB INSERT ERROR");
					}
				}else
				{
					request.getSession().setAttribute("loginId", id);
					request.getSession().setAttribute("loginType", "kakao");
					request.getRequestDispatcher("mainHomePage.jsp").forward(request, response);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}else if(url.equals("email.login"))
		{
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			try
			{
				MemberDAO dao = new MemberDAO();

				//				if(dao.emailLoginCheck(id, pw))
				if(dao.emailLoginCheck(id, dao.toSha256(pw)))
				{
					request.getSession().setAttribute("loginType", "normal");
					request.getSession().setAttribute("loginId", id);
					String type = dao.selectTypeById(id);
					request.getSession().setAttribute("type", type);
					writer.append("Y");
				}else
				{
					writer.append("N");
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}

		}else if(url.equals("logout.login"))
		{
			//			String type = (String)request.getSession().getAttribute("loginType");
			//			String id = (String)request.getSession().getAttribute("loginId");
			//			
			//			if(type.equals("normal"))
			//			{
			//				request.getSession().invalidate();
			//				response.sendRedirect("Homepage.jsp");
			//			}
			//			else if(type.equals("kakao"))
			//			{
			//				request.getSession().invalidate();
			//				response.sendRedirect("Homepage.jsp");
			//			}

			request.getSession().invalidate();
			response.sendRedirect("mainHomePage.jsp");
		}else if(url.equals("idcheck.login"))
		{
			MemberDAO dao;
			try
			{
				dao = new MemberDAO();

				String id = request.getParameter("id");
				System.out.println(id);

				try
				{
					System.out.println(dao.getIdCheck(id));
					if(id.equals(""))
					{
						response.getWriter().write("3");
					}else
					{
						response.getWriter().write(dao.getIdCheck(id) + "");
					}
				}catch(Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}else if(url.equals("signup.login"))
		{
			try
			{
				MemberDAO dao = new MemberDAO();
				String idcheck = request.getParameter("idcheckvar");
				String pwcheck = request.getParameter("pwcheckvar");
				String nicknamecheck = request.getParameter("nicknamecheckvar");
				String phonecheck = request.getParameter("phonecheckvar");

				String id = request.getParameter("id");// 아이디
				String pw = request.getParameter("pw");// 패스워드
				String nickname = request.getParameter("nickname");// 닉네임
				String gender = request.getParameter("gender");// 성별
				String birth = request.getParameter("birth");// 날짜 1900-05-07

				String year = dao.getMyLoginYear(birth);// 날짜에서 년도 ex)1900 가져오기
				int a = Integer.parseInt(year);

				String ageRange = dao.getMyLoginAgeRange(year);// 년도 뒷자리 ex) 00
				int intagerange = Integer.parseInt(ageRange);// 년도 뒷자리 int 형으로 바꾸기
				String month_day = dao.getMyLoginMonth_Day(birth);// 날짜에서 월일값 가져오기 ex)05-07
				String month = dao.getMyLoginYearMonth(month_day);// 월 ex)05
				String day = dao.getMyLoginYearDay(month_day);// 일 ex)07
				String monthday = month + day; // 월일 ex)0507

				String phone = request.getParameter("phone");
				String resultage = "";
				String result = "";
				if(a < 2000)
				{ // 1900 년대생이면
					if(10 <= intagerange && intagerange < 20)
					{
						resultage = "100";
					}else if(20 <= intagerange && intagerange < 30)
					{
						resultage = "90";
					}else if(30 <= intagerange && intagerange < 40)
					{
						resultage = "80";
					}else if(40 <= intagerange && intagerange < 50)
					{
						resultage = "70";
					}else if(50 <= intagerange && intagerange < 60)
					{
						resultage = "60";
					}else if(60 <= intagerange && intagerange < 70)
					{
						resultage = "50";
					}else if(70 <= intagerange && intagerange < 80)
					{
						resultage = "40";
					}else if(80 <= intagerange && intagerange < 90)
					{
						resultage = "30";
					}else if(90 <= intagerange)
					{
						resultage = "20";
					}
				}else
				{// 20000 년대생이면
					if(0 <= intagerange && intagerange < 10)
					{
						resultage = "10";
					}else if(10 <= intagerange)
					{
						resultage = "기타";
					}
				}

				try
				{
					if(dao.getInsert(new MemberDTO(id, pw, nickname, gender, resultage, monthday, phone)) > 0)
					{
						System.out.println("성공");
						result = "성공";
					}else
					{
						System.out.println("실패");
						result = "실패";
					}
				}catch(Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("result", result);
				request.getRequestDispatcher("SignUpView.jsp").forward(request, response);
			}catch(Exception e)
			{
				e.printStackTrace();
				response.sendRedirect("error.html");
			}
		}else if(url.equals("naverLogin.login")) { //네이버로그인
			String clientId = "gB29fVbvRSeR2nP3nDLJ";//애플리케이션 클라이언트 아이디값";
			String redirectURI = URLEncoder.encode("http://localhost:8080/naverLoginResult.login", "UTF-8");///callback 주소
			String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
			// 상태 토큰으로 사용할 랜덤 문자열 생성
			SecureRandom random = new SecureRandom();
			String state = new BigInteger(130, random).toString(32);
			// 세션 또는 별도의 저장 공간에 상태 토큰을 저장
			System.out.println("state : " + state);
			request.getSession().setAttribute("state", state);
			apiURL += "&client_id=" + clientId;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&state=" + state;
			response.sendRedirect(apiURL);
		}else if(url.equals("naverLoginResult.login")) {
			String clientId = "gB29fVbvRSeR2nP3nDLJ";//애플리케이션 클라이언트 아이디값";
			String clientSecret = "Zdr7BUcINY";//애플리케이션 클라이언트 시크릿값";
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			String redirectURI = URLEncoder.encode("http://localhost:8080/naverLoginResult.login", "UTF-8");
			String apiURL;
			apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;
			String access_token = "";
			String refresh_token = "";
			System.out.println("apiURL="+apiURL);
			try {//접근토큰 받아오기.
				URL apiUrl = new URL(apiURL);
				HttpURLConnection con = (HttpURLConnection)apiUrl.openConnection();
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				BufferedReader br;
				System.out.println("responseCode= "+responseCode);
				if(responseCode==200) { // 정상 호출
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				} else {  // 에러 발생
					br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}
				String inputLine;
				StringBuffer res = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					res.append(inputLine);
				}
				br.close();
				if(responseCode==200) {
					System.out.println("정상 : " +res.toString());
					JsonParser parsing = new JsonParser();
					Object obj = parsing.parse(res.toString());
					JsonObject jsonObj = (JsonObject)obj;

					access_token = jsonObj.get("access_token").getAsString();//###
					access_token = URLEncoder.encode(access_token, "UTF-8"); //인코딩
					refresh_token = jsonObj.get("refresh_token").getAsString();//###
				}else {
					//접근정상아닐때...
				}
				System.out.println("access_token : " +access_token);
				System.out.println("refresh_token : " + refresh_token);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("../board/error.board");
			}
			if(access_token != "") { // access_token을 잘 받아왔다면
				try {
					String apiurl = "https://openapi.naver.com/v1/nid/me";
					URL url2 = new URL(apiurl);
					HttpURLConnection con = (HttpURLConnection)url2.openConnection();
					con.setRequestMethod("GET");
					String header = "Bearer "+access_token;
					con.setRequestProperty("Authorization", header);
					int responseCode = con.getResponseCode();
					BufferedReader br;
					if(responseCode==200) { // 정상 호출
						br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					} else {  // 에러 발생
						br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
					}
					String inputLine;
					StringBuffer res = new StringBuffer();
					while ((inputLine = br.readLine()) != null) {
						res.append(inputLine);
					}
					br.close();
					JsonParser parsing = new JsonParser();
					Object obj = parsing.parse(res.toString());
					JsonObject jsonObj = (JsonObject)obj;
					JsonObject resObj = (JsonObject)jsonObj.get("response");

					//왼쪽 변수 이름은 원하는 대로 정하면 된다. 
					//단, 우측의 get()안에 들어가는 값은 와인색 상자 안의 값을 그대로 적어주어야 한다.
					String naverCode = resObj.get("id").getAsString();
					String email = null;
					if(resObj.get("email")!=null) {
						email = resObj.get("email").getAsString();
					}
					String name = null;
					if(resObj.get("name")!=null) {
						name = resObj.get("name").getAsString();
					}
					//birthday
					//age
					String birthday = null;
					if(resObj.get("birthday")!=null) {
						birthday = resObj.get("birthday").getAsString();
					}
					String agerange = null;
					if(resObj.get("age")!=null) {
						agerange = resObj.get("age").getAsString();
					}
					String gender = null;
					if(resObj.get("gender")!=null) {
						gender = resObj.get("gender").getAsString();
						if(gender.equals("male"))
						{
							gender = "M";
						}else if(gender.equals("female"))
						{
							gender = "F";
						}
					}
					String nickname = null;
					if(resObj.get("nickname")!=null) {
						nickname = resObj.get("nickname").getAsString();
					}else {
						nickname = name;
					}
					if(nickname == null) {
						nickname = "임시 닉네임";
					}
					System.out.println("id : " + naverCode);
					System.out.println("email : " + email);
					System.out.println("name : " + name);
					System.out.println("birthday : " + birthday);
					System.out.println("agerange : " + agerange);
					System.out.println("gender : " + gender);
					System.out.println("nickname : " + nickname);

					//여기부터 다시 짜기--------------------------------------
					//request.setAttribute("naverCode",naverCode);
					//request.setAttribute("email",email);
					//request.setAttribute("name",name);

					//접근 토큰 삭제요청
					//https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id={클라이언트 아이디}
					//&client_secret={클라이언트 시크릿}&access_token={접근 토큰}&service_provider=NAVER




					//request.getSession().setAttribute("delete", deleteToken);
					//https://nid.naver.com/oauth2.0/token?grant_type=delete
					//&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&access_token=c8ceMEJisO4Se7uGCEYKK1p52L93bHXLnaoETis9YzjfnorlQwEisqemfpKHUq2gY&service_provider=NAVER
					//https://nid.naver.com/oauth2.0/token?grant_type=delete
					//&client_id=gB29fVbvRSeR2nP3nDLJ&client_secret=Zdr7BUcINY&access_token=AAAANlRQYZdv8HEK10LJmInjqefzrQ2EI2SmCL7YhUAhA3qAKxWU_MJH5OghRE9pFE3Np3aj4puq09nXekVCpnvLkKU&service_provider=NAVER


					//
					MemberDAO dao = new MemberDAO();
					if(!dao.kakaoLoginCheck("N"+naverCode)) {
						long currentTime = System.currentTimeMillis();
						MemberDTO dto = new MemberDTO("N"+naverCode,currentTime+"",nickname,gender,agerange,birthday,null,null,"normal",null);
						int result = dao.getInsert(dto);
						//request.setAttribute("result", result);
						if(result<=0) {
							System.out.println("DB INSERT ERROR");
						}
					}
					request.getSession().setAttribute("loginId", "N"+naverCode);
					request.getSession().setAttribute("loginType", "Naver");
					request.getRequestDispatcher("mainHomePage.jsp").forward(request, response);


					//접근토큰삭제 -> 탈퇴할때? 없애기... 
//					String deleteToken = "https://nid.naver.com/oauth2.0/token?grant_type=delete";
//					deleteToken +="&client_id=" + clientId;
//					deleteToken +="&client_secret=" +clientSecret;
//					deleteToken +="&access_token=" + access_token;
//					deleteToken +="&service_provider=NAVER";
//					System.out.println("deleteToken : " + deleteToken);


					//String deleteToken = (String)request.getSession().getAttribute("delete");
					//					URL url2 = new URL(deleteToken);
					//					HttpURLConnection con2 = (HttpURLConnection)url2.openConnection();
					//					con2.setRequestMethod("GET");
					//					int responseCode2 = con2.getResponseCode();
					//					BufferedReader br2;
					//					if(responseCode2==200) { // 정상 호출
					//						br2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
					//					} else {  // 에러 발생
					//						br2 = new BufferedReader(new InputStreamReader(con2.getErrorStream()));
					//					}
					//					String inputLine2;
					//					StringBuffer res2 = new StringBuffer();
					//					while ((inputLine2 = br2.readLine()) != null) {
					//						res2.append(inputLine2);
					//					}
					//					br2.close();
					//					//request.getSession().removeAttribute("delete");
					//					System.out.println(res2);
					//					JsonParser parsing2 = new JsonParser();
					//					JsonObject jsonObj2 = parsing2.parse(res2.toString()).getAsJsonObject();
					//					JsonElement resultObj2 = jsonObj2.get("result");
					//					System.out.println(resultObj2.toString());
					//					//response.sendRedirect("index.io");	
					//					System.out.println("접근토큰 삭제완료");

				}catch(Exception e) {
					e.printStackTrace();
					response.sendRedirect("error.html");
				}
			}
		}else if(url.equals("naverLogout.login")) {
			request.getSession().invalidate();
			response.sendRedirect("naverLogoutView.jsp");
		}else if(url.equals("naverLogoutPop.login")) {
			response.sendRedirect("https://nid.naver.com/nidlogin.logout?returl=http://www.naver.com");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
