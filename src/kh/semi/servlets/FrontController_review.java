package kh.semi.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.semi.dao.ReviewDAO;
import kh.semi.dto.ReviewDTO;

@WebServlet("*.review")
public class FrontController_review extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUri = request.getRequestURI();
		String cxtpath = request.getContextPath();
		String cmd = reqUri.substring(cxtpath.length());

		ReviewDTO dto = new ReviewDTO();

		if(cmd.contentEquals("/index.review")) {

			int currentPage = 0;
			String test = request.getParameter("collapse");
			request.setAttribute("show", test);
			try {
				ReviewDAO dao = new ReviewDAO();
				String currentPageResult = request.getParameter("currentPage");

				if(currentPageResult!=null) {
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}else {
					currentPage = 1;
				}
				int recordCountPerPage = 3;

				int end = currentPage * recordCountPerPage;
				int start = end - (recordCountPerPage-1);

				List<ReviewDTO> list = dao.selectAll(start, end);
				request.setAttribute("list", list);

				int recordTotalCount = dao.boardCount();
				String resultNavi = dao.getNavi(currentPage, recordTotalCount, recordCountPerPage);
				request.setAttribute("recordTotalCount", recordTotalCount);
				request.setAttribute("resultNavi", resultNavi);
				request.getRequestDispatcher("review.jsp").forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if(cmd.contentEquals("/index.review")) {

		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
