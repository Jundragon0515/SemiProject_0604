package kh.semi.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import kh.semi.dao.DoClassDAO;
import kh.semi.dao.PersonDAO;
import kh.semi.dao.ReviewDAO;
import kh.semi.dao.UpgradeDAO;
import kh.semi.dto.DoClassDTO;
import kh.semi.dto.PersonDTO;


@WebServlet("*.mypage")
public class MypageServlets extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	static int recordCountPerPage = 3;
	static int naviCountPerPage = 5;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String url = request.getRequestURI().substring(request.getContextPath().length() + 1);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String m_id = (String)request.getSession().getAttribute("loginId");
		request.getSession().setAttribute("loginId", m_id);
		
		System.out.println(url);
		if(url.equals("doing.mypage"))
		{
			try
			{
				PersonDAO dao = new PersonDAO();
				PersonDTO dto = dao.selectById(m_id);
				
				request.setAttribute("dto", dto);
				
//				if(dto.getM_type().equals("admin")) {//관리자 라면 관리자 페이지로 보내기.
//					request.getRequestDispatcher("/WEB-INF/adminMypage.jsp").forward(request, response);
//				}
				
				
				DoClassDAO dcdao = new DoClassDAO();
				
				int currentPage;
				

				currentPage = Integer.parseInt(request.getParameter("page"));

				
				List<DoClassDTO> list = dcdao.selectDoingClass(m_id, currentPage);
				
				int recordTotalCount = dcdao.selectDoingCount(m_id);
				
				int pageTotalCount;
				
				boolean needPrev = true;
				boolean needNext = true;
				
				if( recordTotalCount % recordCountPerPage == 0)
				{
					pageTotalCount = recordTotalCount / recordCountPerPage;
				}
				else
				{
					pageTotalCount = recordTotalCount / recordCountPerPage + 1;
				}

				if(currentPage < 1)
				{
					currentPage = 1;
				}
				else if(currentPage > pageTotalCount)
				{
					currentPage = pageTotalCount;
				}
				
				int startNavi = (currentPage - 1) / naviCountPerPage * naviCountPerPage + 1;
				int endNavi = startNavi + naviCountPerPage - 1;
				if(endNavi > pageTotalCount)
				{
					endNavi = pageTotalCount;
				}
				
				if(startNavi == 1)
				{
					needPrev = false;
				}
				if(endNavi == pageTotalCount)
				{
					needNext = false;
				}
				
				request.setAttribute("list", list);
				request.setAttribute("listsize", list.size());
				
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("needPrev", needPrev);
				request.setAttribute("needNext", needNext);
				request.setAttribute("startNavi", startNavi);
				request.setAttribute("endNavi", endNavi);
			
				request.getRequestDispatcher("/WEB-INF/MypageDoing.jsp?page="+currentPage).forward(request, response);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(url.equals("done.mypage"))
		{
			
			try
			{
				PersonDAO dao = new PersonDAO();
				PersonDTO dto = dao.selectById(m_id);
				
				request.setAttribute("dto", dto);
				
				DoClassDAO dcdao = new DoClassDAO();
				
				int currentPage;
				

				currentPage = Integer.parseInt(request.getParameter("page"));

				
				List<DoClassDTO> list = dcdao.selectDoneClass(m_id, currentPage);
				
				int recordTotalCount = dcdao.selectDoneCount(m_id);
				
				int pageTotalCount;
				
				boolean needPrev = true;
				boolean needNext = true;
				
				if( recordTotalCount % recordCountPerPage == 0)
				{
					pageTotalCount = recordTotalCount / recordCountPerPage;
				}
				else
				{
					pageTotalCount = recordTotalCount / recordCountPerPage + 1;
				}

				if(currentPage < 1)
				{
					currentPage = 1;
				}
				else if(currentPage > pageTotalCount)
				{
					currentPage = pageTotalCount;
				}
				
				int startNavi = (currentPage - 1) / naviCountPerPage * naviCountPerPage + 1;
				int endNavi = startNavi + naviCountPerPage - 1;
				if(endNavi > pageTotalCount)
				{
					endNavi = pageTotalCount;
				}
				
				if(startNavi == 1)
				{
					needPrev = false;
				}
				if(endNavi == pageTotalCount)
				{
					needNext = false;
				}
				
				request.setAttribute("list", list);
				request.setAttribute("listsize", list.size());
				
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("needPrev", needPrev);
				request.setAttribute("needNext", needNext);
				request.setAttribute("startNavi", startNavi);
				request.setAttribute("endNavi", endNavi);
			
				request.getRequestDispatcher("/WEB-INF/MypageDone.jsp?page="+currentPage).forward(request, response);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(url.equals("person.mypage"))
		{
			try
			{
				PersonDAO dao = new PersonDAO();
				PersonDTO dto = dao.selectById(m_id);
				
				request.setAttribute("dto", dto);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("/WEB-INF/MypagePerson.jsp").forward(request, response);
		}
		else if(url.equals("update.mypage"))
		{
			try
			{
				PersonDAO dao = new PersonDAO();
				
				String m_nickname = request.getParameter("nickname").replace("<script>", "");
				
				if(m_nickname == null)
				{
					response.sendRedirect("error.html");
				}
				
				int result1 = dao.updateNicknameById(m_nickname, m_id);
				
				String[] attention = request.getParameterValues("attention");
				
				String collection = "";
				
				if(attention == null)
				{
					
				}
				else
				{
					collection = "[ ";
					
					for(int i = 1 ; i <= attention.length ; i++)
					{
						if(i == attention.length)
						{
							collection = collection + "\"" + attention[i-1] + "\"";
						}
						else
						{
							collection = collection + "\"" + attention[i-1] + "\"" + ", ";
						}
					}
					
					collection = collection + " ]";
				}
				
				int result2 = dao.updateAttentionById(collection.replace("<script>", ""), m_id);
					
				if((result1 == 0) || (result2 == 0))
				{
					response.sendRedirect("error.html");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			response.sendRedirect("person.mypage");
		}
		else if(url.equals("changePw.mypage"))
		{
			request.getRequestDispatcher("/WEB-INF/MypageChangePW.jsp").forward(request, response);
		}
		else if(url.equals("pw.mypage"))
		{
			String pw = request.getParameter("pw").replace("<script>", "");
			
			try
			{
				PersonDAO dao = new PersonDAO();
				String changedPW = dao.toSha256(pw);
				
				dao.updatePwById(changedPW, m_id);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			response.sendRedirect("close.html");
		}
		else if(url.equals("changeImg.mypage"))
		{
			request.getRequestDispatcher("/WEB-INF/MypageChangeImg.jsp").forward(request, response);
		}
		else if(url.equals("img.mypage"))
		{
			String rootPath = this.getServletContext().getRealPath("/");
			String filePath = rootPath + "files";
			
			File uploadPath = new File(filePath);
			if(!uploadPath.exists())
			{
				uploadPath.mkdir();
			}
			
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setRepository(new File(rootPath + "WEB-INF/temp"));
			
			ServletFileUpload sfu = new ServletFileUpload(diskFactory);
			sfu.setFileSizeMax(10 * 1024 * 1024);
			
			try
			{
				FileItem fi = sfu.parseRequest(request).get(0);
				PersonDAO dao = new PersonDAO();	
				
				String src = dao.selectImgById(m_id);
				src = src.substring(10,src.length());
				src = src.substring(0,src.length()-1);
				
				File delFile = new File(rootPath + src);
				if(delFile.exists())
				{
					delFile.delete();
				}
					
				if(fi.getSize() == 0)
				{
					response.sendRedirect("error.html");
				}
				else
				{
					while(true)
					{
						try
						{
							String tmpName = System.currentTimeMillis() + "_" + fi.getName();
							fi.write(new File(filePath + "/" + tmpName));
							
							String file = "<img src=/files/"+tmpName+">";
							dao.updateImgById(file,m_id);
							
							break;
						}
						catch(org.apache.commons.io.FileExistsException e)
						{
							System.out.println("파일 이름 재설정");
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				response.sendRedirect("close.html");
			}
			
		}
		else if(url.equals("review.mypage"))
		{
			request.setAttribute("m_id", request.getSession().getAttribute("loginId"));
			request.setAttribute("c_id", request.getParameter("c_id"));
			request.setAttribute("date", request.getParameter("date"));
			request.setAttribute("title", request.getParameter("title"));
			
			request.getRequestDispatcher("/WEB-INF/MypageWriteReview.jsp").forward(request, response);
		}
		else if(url.equals("writeReview.mypage"))
		{
			try
			{
				
				ReviewDAO dao = new ReviewDAO();
				
				String time = request.getParameter("date");
				
				int year = Integer.parseInt(time.substring(2,4));
				int month = Integer.parseInt(time.substring(5,7));
				int day = Integer.parseInt(time.substring(8,10));
				
				@SuppressWarnings("deprecation")
				Date date = new Date(year, month-1, day);
				
				dao.insertReview(request.getParameter("c_id"),
					request.getParameter("m_id"), 
					request.getParameter("text"), 
					request.getParameter("star"),
					date
					);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			response.sendRedirect("close.html");
		}
		else if(url.equals("tutor.mypage"))
		{
			try
			{
				PersonDAO pdao = new PersonDAO();
				PersonDTO pdto = pdao.selectById(m_id);
				request.setAttribute("dto", pdto);
				
				if(pdto.getM_type().equals("tutor"))
				{
					request.setAttribute("check", "tutor");
				}
				else
				{
					UpgradeDAO udao = new UpgradeDAO();
					Boolean check = udao.selectById(pdto.getM_id());
					request.setAttribute("check", check);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("/WEB-INF/MypageApplyTutor.jsp").forward(request, response);
		}
		else if(url.equals("apply.mypage"))
		{
			try
			{
				String id = request.getParameter("id");
				String nickname = request.getParameter("nickname");
				
				UpgradeDAO dao = new UpgradeDAO();
				int result = dao.insertNew(id, nickname);
				
				if(result > 0)
				{
					request.getRequestDispatcher("tutor.mypage").forward(request, response);
				}
				else
				{
					response.sendRedirect("error.html");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
}
