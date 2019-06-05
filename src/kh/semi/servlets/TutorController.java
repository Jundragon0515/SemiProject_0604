
package kh.semi.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kh.semi.dao.TutorDAO;
import kh.semi.dto.ClassInfoDTO;
import kh.semi.dto.UfileDTO;

@WebServlet("/tutor")
public class TutorController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		TutorDAO dao = new TutorDAO();
		ClassInfoDTO dto = new ClassInfoDTO();
		UfileDTO udto = new UfileDTO();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 E요일");
		String time = sdf.format(date);
		PrintWriter pw = response.getWriter();


		String rootPath = request.getServletContext().getRealPath("/");
		String filePath = rootPath + "files" ; //files는 본 저장소이며 임시저장소가 아니다
		String filePath2 = filePath + "/"+time;

		try {
			//cos.jar
			MultipartRequest multi = new MultipartRequest(request, filePath2, 20 * 1024 * 1024, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			while (files.hasMoreElements()) {
				String file = (String)files.nextElement();
				String file_name = multi.getFilesystemName(file);
				String ori_file_name = multi.getOriginalFileName(file);



				dto.setInfo_category(multi.getParameter("down"));
				dto.setInfo_title(multi.getParameter("inputtitle"));
				dto.setInfo_explain(multi.getParameter("explain"));
				dto.setInfo_intro(multi.getParameter("intro"));
				dto.setInfo_addr1(Integer.parseInt(multi.getParameter("zipcode")));
				dto.setInfo_addr2(multi.getParameter("addr1"));
				dto.setInfo_addr3(multi.getParameter("addr2"));
				dto.setInfo_addr4(multi.getParameter("addr3"));
				dto.setInfo_maxperson(Integer.parseInt(multi.getParameter("max")));
				dto.setInfo_price(Integer.parseInt(multi.getParameter("cash")));
				dto.setInfo_img1(multi.getFilesystemName("img"));
				dto.setInfo_img2(multi.getFilesystemName("img2"));
				dto.setInfo_img3(multi.getFilesystemName("img3"));
				//System.out.println(img);
				//			dto.setString(img2);
				//			dto.setString(img3);
				dto.setInfo_start(multi.getParameter("startdate"));
				dto.setInfo_end(multi.getParameter("enddate"));
				//			등록일
				//			별점통계
				//			조회수}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			int result = dao.test(dto);	
			System.out.println("DB등록 됨");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//			String intro = request.getParameter("intro");
	//			String category = request.getParameter("down");
	//			String zipcode = request.getParameter("zipcode");
	//			int addr1=Integer.parseInt(zipcode);
	//			String addr2 = request.getParameter("add1");
	//			String addr3 = request.getParameter("add2");
	//			String addr4 = request.getParameter("add3");
	//			String person = request.getParameter("max");
	//			int max = Integer.parseInt(person);
	//			String money = request.getParameter("cash");
	//			int cash = Integer.parseInt(money);
	//			String start = request.getParameter("startdate");
	//			String end = request.getParameter("enddate");
	//			String explain = request.getParameter("explain");
	//			String title = request.getParameter("inputtitle");

	//			dto.setInfo_category(category);
	//			dto.setInfo_title(title);
	//			dto.setInfo_explain(explain);			
	//			dto.setInfo_intro(intro);
	//			dto.setInfo_addr1(addr1);
	//			dto.setInfo_addr2(addr2);
	//			dto.setInfo_addr3(addr3);
	//			dto.setInfo_addr4(addr4);
	//			dto.setInfo_maxperson(max);
	//			dto.setInfo_price(cash);



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
