package kh.semi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kh.semi.dto.UpgradeDTO;
import kh.semi.statics.AdminNaviStatics;

public class UpgradeDAO {

	private Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "semi";
		String password = "semi";

		return DriverManager.getConnection(url, user, password);
	}

	
	/**
	 * 튜터요청리스트에 insert
	 * @return int 1 success 0 error
	 * @throws Exception
	 */
	public int insertNew(String id, String nickname) throws Exception
	{
		String sql = "insert into upgrade values(up_seq_seq.nextval, ?, ?, default)";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, id);
			pstat.setString(2, nickname);

			int result = pstat.executeUpdate();
			con.commit();

			return result;
		}
	}
	
	public boolean selectById(String id) throws Exception
	{
		String sql = "select * from upgrade where up_id = ?";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, id);
			try
			(
				ResultSet rs = pstat.executeQuery();
			)
			{
				return rs.next();
			}
		}
	}

	
	

	/**
	 * 튜터요청리스트에 있는 목록 불러오기
	 * @return
	 * @throws Exception
	 */
	public List<UpgradeDTO> selectAllupgradeList() throws Exception{
		String sql = "select * from upgrade";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<UpgradeDTO> list = new ArrayList<>();
			while(rs.next()) {
				int up_seq = rs.getInt("up_seq");
				String up_id = rs.getString("up_id");
				String up_nickname = rs.getString("up_nickname");
				Date up_applydate = rs.getDate("up_applydate");
				list.add(new UpgradeDTO(up_seq,up_id,up_nickname,up_applydate));
			}
			return list;
		}
	}
	/**
	 * 해당 페이지에 띄워야할 list얻기위해 begin , end
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public int[] getRecordPerPageBeginEnd(int currentPage) throws Exception{
		int recordCountPerPage = AdminNaviStatics.recordCountPerPage;

		int begin = 1 + recordCountPerPage*(currentPage-1);
		int end = recordCountPerPage*currentPage;

		return new int[] {begin,end};
	}

	public List<UpgradeDTO> selectByPageUpgradeList(int currentPage) throws Exception{
		String sql = "select row_number()over(order by up_seq) no , upgrade.* from upgrade";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			List<UpgradeDTO> list = new ArrayList<>();

			int[] tmp = this.getRecordPerPageBeginEnd(currentPage);
			int begin = tmp[0];
			int end = tmp[1];

			while(rs.next()) {
				if(rs.getInt(1)>=begin) {
					int up_seq = rs.getInt("up_seq");
					String up_id = rs.getString("up_id");
					String up_nickname = rs.getString("up_nickname");
					Date up_applydate = rs.getDate("up_applydate");
					list.add(new UpgradeDTO(up_seq,up_id,up_nickname,up_applydate));
				}
				if(rs.getInt(1)==end) {
					break;
				}
			}
			return list;
		}
	}

	/**
	 * 페이지 네비게이터
	 * @param currentPage
	 * @param listSize
	 * @return
	 * @throws Exception
	 */
	public List<String> getNavi(int currentPage/*서블릿으로부터 받아옴*/,int listSize) throws Exception{

		//0.현재 내가 보고있는 페이지가 몇 페이지 인지
		//int currentPage = 10;//가정

		//네비게이터 제작을 위한 3step
		//1. DB의 게시판테이블의 전체 레코드 갯수(글 갯수)가 몇개인지
		//-> select count(*) from board
		//int recordTotalCount = 253; // 일단 DB에서 직접해보는것말고 자바 코드만 짜기위해 임의로 숫자 넣어서 진행함
		int recordTotalCount= listSize;


		//2. 한 페이지에 몇개의 글이 보이게 할 것 인지
		int recordCountPerPage = AdminNaviStatics.recordCountPerPage; //이 숫자는 임의로 정해도 괜찮지만 연산(나눗셈..등)을 위해서 10이 제일 적당함

		//3. 한 페이지에 네비게이터가 총 몇개가 보이게 할 것인지
		int naviCountPerPage = AdminNaviStatics.naviCountPerPage; //이것도 임의로 10으로 정해둠.

		//전체 페이지 수
		int pageTotalCount = (int)Math.ceil((double)recordTotalCount/recordCountPerPage);	

		//현재페이지 오류 검출 및 정정
		if(currentPage < 1) { //최소보다 작을경우
			currentPage = 1;	
		}else if(currentPage > pageTotalCount) { //가능한 페이지수 이상으로 넘어갈경우
			currentPage = pageTotalCount;
		}

		//현재있는 위치를 기준으로  시작페이지와 끝페이지의 정보를 얻어내야한다.
		int startNavi;	//현재있는 위치를 기준으로  시작페이지
		int endNavi;	//현재있는 위치를 기준으로 끝페이지
		startNavi = (currentPage-1)/naviCountPerPage*naviCountPerPage+1;
		endNavi = startNavi + (naviCountPerPage-1);
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		//왼쪽 오른쪽 표시
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}

		List<String> list = new ArrayList<>();
		if(needPrev) {
			list.add("<이전");
		}
		for(int i =startNavi ; i<=endNavi; i++) {
			list.add(i+"");
		}
		if(needNext) {
			list.add("다음>");
		}
		return list;
	}

	/**
	 * 
	 * @param idList : upgrade테이블에서 삭제하려는 id의 String 배열
	 * @return 몇 행 삭제되었는지 / 제대로 삭제되었다면, 인수값으로 넘긴 String배열의 길이와 같다.
	 * @throws Exception
	 */
	public int deleteUpgradeById(String[] idList) throws Exception{
		String sql = "delete upgrade where up_id = ? ";
		for(int i=0; i<idList.length-1 ; i++) {
			sql += " or up_id = ? ";
		}
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);

				){
			for(int i=0; i<idList.length;i++) {
				pstat.setString(i+1, idList[i]);
			}
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}
	
	public int updateTypeToTutorById(String[] idList)throws Exception{
		String sql = "update member set m_type = 'tutor' where m_id = ?";
		for(int i =0; i<idList.length-1; i++) {
			sql += " or m_id = ? ";
		}
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				
				){
			for(int i=0; i<idList.length;i++) {
				pstat.setString(i+1, idList[i]);
			}
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

}
