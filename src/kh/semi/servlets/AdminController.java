package kh.semi.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.semi.dao.PersonDAO;
import kh.semi.dao.UpgradeDAO;
import kh.semi.dto.PersonDTO;
import kh.semi.dto.UpgradeDTO;


@WebServlet("*.admin")
public class AdminController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String cmd = uri.substring(ctx.length());
		System.out.println(cmd);

		UpgradeDAO u_dao = new UpgradeDAO();

		try {
			if(cmd.equals("/mypage.admin")) { //메인-> 마이페이지 클릭해서 들어온경우 1페이지 띄워주기
				//1. 튜터등록 목록 보내줘야함. 
				int currentPage = 1;
				List<UpgradeDTO> upgradeList = u_dao.selectByPageUpgradeList(currentPage);
				if(upgradeList.size()!=0) {
					request.setAttribute("upgradeList", upgradeList);
				}
				//2. 네비게이터 보내줘야함
				List<UpgradeDTO> allList = u_dao.selectAllupgradeList();
				List<String> naviList = u_dao.getNavi(currentPage, allList.size());
				request.setAttribute("navi", naviList);
				
				String beginPage = naviList.get(0);
				if(naviList.get(0).equals("<이전")) {
					beginPage = naviList.get(1);
				}
				String endPage = naviList.get(naviList.size()-1);
				if(naviList.get(naviList.size()-1).equals("다음>")) {
					endPage = naviList.get(naviList.size()-2);
				}
				request.setAttribute("beginPage", beginPage);
				request.setAttribute("endPage", endPage);
				//3. 로그인한 관리자 정보 보내주기.
				PersonDAO p_dao = new PersonDAO();
				String loginId = (String)request.getSession().getAttribute("loginId");
				PersonDTO p_dto = p_dao.selectById(loginId);
				request.setAttribute("dto", p_dto);

				request.getRequestDispatcher("WEB-INF/adminMypage.jsp").forward(request, response);
			}else if(cmd.equals("/tutorUpgrade.admin")) {
				String[] apply_checkedId = request.getParameterValues("checkedId");

				//1.해당id를 upgrade테이블에서 삭제 
				int deleteResult = u_dao.deleteUpgradeById(apply_checkedId);
				//2.member테이블에서 type 을 'tutor' 로 변경하기.
				int updateResult = u_dao.updateTypeToTutorById(apply_checkedId);
				System.out.println("삭제 : " + deleteResult);
				System.out.println("업데이트 : " + updateResult);
				if(deleteResult!=updateResult) {
					System.out.println("튜터삭제오류");
				}
				request.setAttribute("result", deleteResult);
				request.getRequestDispatcher("WEB-INF/adminTutorUpgradeView.jsp").forward(request, response);
			}else if(cmd.equals("/clickPage.admin")) {
				int currentPage = Integer.parseInt(request.getParameter("currentPage"));
				List<UpgradeDTO> upgradeList = u_dao.selectByPageUpgradeList(currentPage);
				if(upgradeList.size()!=0) {
					request.setAttribute("upgradeList", upgradeList);
				}
				
				List<UpgradeDTO> allList = u_dao.selectAllupgradeList();
				List<String> naviList = u_dao.getNavi(currentPage, allList.size());
				request.setAttribute("navi", naviList);
				
				String beginPage = naviList.get(0);
				if(naviList.get(0).equals("<이전")) {
					beginPage = naviList.get(1);
				}
				String endPage = naviList.get(naviList.size()-1);
				if(naviList.get(naviList.size()-1).equals("다음>")) {
					endPage = naviList.get(naviList.size()-2);
				}
				request.setAttribute("beginPage", beginPage);
				request.setAttribute("endPage", endPage);
				//3. 로그인한 관리자 정보 보내주기.
				PersonDAO p_dao = new PersonDAO();
				String loginId = (String)request.getSession().getAttribute("loginId");
				PersonDTO p_dto = p_dao.selectById(loginId);
				request.setAttribute("dto", p_dto);

				request.getRequestDispatcher("WEB-INF/adminMypage.jsp").forward(request, response);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
