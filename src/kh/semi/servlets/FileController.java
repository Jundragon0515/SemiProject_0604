package kh.semi.servlets;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import kh.semi.dao.TutorDAO;
import kh.semi.dao.UfileDAO;
import kh.semi.dto.ClassInfoDTO;
import kh.semi.dto.UfileDTO;


@WebServlet("*.file")
public class FileController extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUri = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String cmd = reqUri.substring(ctxPath.length());

		UfileDAO dao = new UfileDAO();
		TutorDAO daos = new TutorDAO();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy�뀈 MM�썡 dd�씪 E�슂�씪");
		String time = sdf.format(date);
		
		if(cmd.equals("/upload.file")) {
		
			String rootPath = request.getServletContext().getRealPath("/");
			String filePath = rootPath + "files" ; //files�뒗 蹂� ���옣�냼�씠硫� �엫�떆���옣�냼媛� �븘�땲�떎
			String filePath2 = filePath + "/"+time;

			//System.out.println(rootPath); //�뙆�씪�뾽濡쒕뱶 寃쎈줈
			System.out.println(filePath2);
			File uploadPath = new File(filePath2);
			if(!uploadPath.exists()) {//�빐�떦 �뤃�뜑媛� 議댁옱�븯吏� �븡�뒗�떎硫� mkdir濡� 留뚮뱾�뼱�씪
				uploadPath.mkdir();
			}
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setRepository(new File(rootPath + "/WEB-INF/tmp")); 
			// �뾽濡쒕뱶 �릺�뒗 �뙆�씪�뱾�쓣 �뿬湲곗뿉 蹂듭궗�빐以꾧쾶 . 寃쎈줈 吏��젙 �븞�븯硫� �씠�긽�븳 �뤃�뜑�뿉 ���옣 �맖
			ServletFileUpload sfu = new ServletFileUpload(diskFactory);
			sfu.setSizeMax(500 * 1024 * 1024);//�뾽濡쒕뱶 �뙆�씪�궗�씠利� �넻�젣. 500硫붽�(�떒�쐞�뒗 1諛붿씠�듃) //�뿬湲곌퉴吏� �맖
		
			try {
				List<FileItem> items = sfu.parseRequest(request);//�꽆�뼱�삩 由ы�섏뒪�듃瑜� 遺꾩꽍�븳�떎. 由ы꽩媛믪� List
				for(FileItem fi : items) {
					if(fi.getSize() == 0) {continue;}//�뙆�씪�궗�씠利덇� 0�씠硫� for-each臾� �걡湲�
					if(fi.isFormField()) {//�뙆�씪�씤吏� �븘�땶吏� 寃��궗. �뙆�씪�씠 �븘�땶�뜲(�뀓�뒪�듃媛숈�) parameter濡� 諛쏆� 嫄� if臾몄쑝濡� �룎由ш퀬
						String paramString = new String(fi.getString().getBytes("ISO-8859-1"), "utf8") ;
						System.out.println(fi.getFieldName() + " : " + paramString);
						//�뙆�씪怨� �뀓�뒪�듃 �븳踰덉뿉 �꽆湲곌린. 0521 異붽��궡�슜
					}else {
					UfileDTO dto = new UfileDTO(); //�뙆�씪 �븯�굹�떦 dto媛� �븯�굹 ���빞 �븿
					ClassInfoDTO dtos = new ClassInfoDTO();
					dto.setOriFileName(fi.getName());//�겢�씪媛� 蹂대궦 �뙆�씪 �씠由�(origin file)�� fi�씠�떎. �겢�씪媛� 諛쏆쓣 吏꾩쭨 �씠由�
					dto.setFileSize(fi.getSize());				
					dto.setFilePath(filePath2);
					
					String tempFileName = null;//�꽌踰꾩뿉�꽌 ���옣�븯�젮怨� 留뚮뱺 �엫�떆 �씠由�
					while(true) {
						try {
							long tempTime = System.currentTimeMillis();
							tempFileName = tempTime+"_"+fi.getName();//�씠誘몄� �삱由� �븣 ��愿꾪샇 �궘�젣
							fi.write(new File(filePath2+"/"+tempFileName));
							dto.setServerFileName(tempFileName);
							
							break;
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
		
					response.setCharacterEncoding("utf8");
					response.getWriter().append("files/" +time + "/"+dto.getServerFileName());//�씠嫄� 諛쏆� ajax �겢�씪�뒗 肄섏넄�뿉 異쒕젰�븳�떎
					//time�씠�씪�뒗 �뤃�뜑瑜� 異붽��뻽�쑝誘�濡� append 寃쎈줈�룄 異붽��빐�빞 �븳�떎
				
					int result = dao.insert(dto);
					
				}//�궗吏꾩씠 �벑濡앸릺�뒗 else臾�
				}
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("error.jsp");
			}
		}else if(cmd.equals("/filelist.file")) {
			try {
				List<UfileDTO> fileList = dao.selectAll();
				request.setAttribute("filelist", fileList);
				request.getRequestDispatcher("filelist.jsp").forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("error.jsp");
			}
		}
		
		
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}





