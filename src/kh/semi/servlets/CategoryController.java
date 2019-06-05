package kh.semi.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.semi.dao.CategoryDAO;
import kh.semi.dto.CategoryDTO;


@WebServlet("*.category")
public class CategoryController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String cmd = requestURI.substring(ctxPath.length());
		System.out.println(cmd);
		CategoryDAO dao = new CategoryDAO();
		if(cmd.contentEquals("/info.category")) {
			System.out.println("-----------------reset"); //검사
			try {
				//페이지네비
				String nowPage = request.getParameter("nowPage");
				int endNavi = CategoryDAO.endNavi;
				int startNavi = CategoryDAO.startNavi;
				System.out.println("endNavi"+endNavi);
				
				//System.out.println("nowPage : "+nowPage);
				int currentPage = 0;
				int recordTotalCount = 0;
				if(nowPage==null) {				
					currentPage = 1;
				}else if (nowPage.contentEquals("다음>")){
					currentPage= endNavi+1;	 
				}else if (nowPage.contentEquals("<이전")){
					currentPage = startNavi-3;
				}else {
					currentPage = Integer.parseInt(nowPage);
				}
				int end = currentPage * CategoryDAO.recordCountPerPage;
				int start = (currentPage * CategoryDAO.recordCountPerPage) - (CategoryDAO.recordCountPerPage - 1);
				//System.out.println(start + "start, " + end + "end");
								
				//셀렉트, 카테고리, 지역 가져오기
				String select = request.getParameter("select");
				String ssSelect = (String) request.getSession().getAttribute("ssSelect");
				String category = request.getParameter("category");
				String ssCategory = (String) request.getSession().getAttribute("ssCategory");
				String addr = request.getParameter("addr");
				String ssAddr = (String) request.getSession().getAttribute("ssAddr");
				
				if(select == null) {
					select = ssSelect;
				}
				
				if(ssCategory == null) {
					ssCategory = "main";
				}
				if(category == null) {
					category = ssCategory;
				}	
				
				if(addr == null) {
					addr = ssAddr;
				}else if(addr.equals("all")) {
					addr = null;
					request.getSession().removeAttribute("ssAddr");
				}
				
				List<CategoryDTO> list = null;	
				System.out.println("select:"+select+" category:"+category+" addr:"+addr+" ssAddr:" +ssAddr);
				
				
				//1.추천 부분	
				if(select == null && category.contentEquals("main") &&  addr == null) {
					System.out.println("여기1");
					request.getSession().setAttribute("ssSelect", select);
					request.getSession().setAttribute("ssCategory", "main");
					list = dao.getInfoBySelect(select, start, end);		
				}
				//1-1. 추천 부분 - 셀렉트있는 경우			
				if (category.contentEquals("main") && addr == null) {
					System.out.println("여기1-1");
					request.getSession().setAttribute("ssSelect", select);
					request.getSession().setAttribute("ssCategory", "main");
					list = dao.getInfoBySelect(select, start, end);	
					recordTotalCount = dao.recordTotalCount();
	
				//2. 카테고리 부분
				}else if(addr == null){
					System.out.println("여기2");
					System.out.println("카테고리:"+category);
					System.out.println("select:"+select);
					request.getSession().setAttribute("ssSelect", select);
					request.getSession().setAttribute("ssCategory", category);
					
					String dbCate = null;
					if(category.equals("design")) {
						dbCate = "디자인";
					}else if(category.equals("it")) {
						dbCate = "IT";
					}else if(category.equals("lang")) {
						dbCate = "언어";
					}else if(category.equals("life")) {
						dbCate = "라이프스타일";
					}else if(category.equals("money")) {
						dbCate = "재테크";
					}
					
					list = dao.getInfoByCategory(select, dbCate, start, end);
					recordTotalCount = dao.getTotalByMenu("info_category", dbCate);
					
				//3. 지역 부분	
				}else{	
					System.out.println("여기3");
					request.getSession().setAttribute("ssSelect", select);
					request.getSession().setAttribute("ssAddr", addr);
					System.out.println("addr:"+addr);
					if(addr.equals("se")) {
						addr = "서울";
					}else if(addr.equals("kk")) {
						addr = "경기";
					}else if(addr.equals("ch")) {
						addr = "충청";
					}else if(addr.equals("ks")) {
						addr = "경상";
					}else if(addr.equals("jl")) {
						addr = "전라";
					}else if(addr.equals("kwjj")) {
						addr = "강원제주";
					}		
					list = dao.getInfoByLocation(select, addr, start, end);
					recordTotalCount = dao.getTotalByMenu("info_addr2", addr);
				}
				
				request.setAttribute("list", list);	
				request.setAttribute("size", list.size());
				//페이지 네비 마무리
				List<String> navi = dao.getNavi(currentPage, recordTotalCount);
				int size = navi.size();
				request.setAttribute("navi", navi);
				request.setAttribute("size", size);
				request.setAttribute("currentPage", currentPage);
				request.getRequestDispatcher("category.jsp").forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
