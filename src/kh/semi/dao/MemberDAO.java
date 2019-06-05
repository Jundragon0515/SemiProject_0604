package kh.semi.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kh.semi.dto.MemberDTO;

public class MemberDAO
{
	private DataSource ds;

	public MemberDAO() throws Exception
	{
		Context ctx = new InitialContext();
		Context compenv = (Context)ctx.lookup("java:/comp/env");
		this.ds = (DataSource)compenv.lookup("jdbc");
	}

	public Connection getConnection() throws Exception
	{
		return ds.getConnection();
	}

	public boolean kakaoLoginCheck(String id) throws Exception
	{
		String sql = "SELECT * FROM MEMBER WHERE M_ID = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				)
		{
			pstat.setString(1, id);
			try(
					ResultSet rs = pstat.executeQuery();
					)
			{
				return rs.next();
			}
		}
	}

	public int kakaoSignUp(MemberDTO dto) throws Exception
	{
		String sql = "INSERT INTO MEMBER(m_id,m_password,m_nickname,m_gender,m_agerange,m_monthday,m_type,m_photo) " + 
				"VALUES (?,?,?,?,?,?,'normal','<img src=\"\" alt=\"이미지가 없습니다\">')";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				)
		{
			pstat.setString(1, dto.getM_id());
			pstat.setString(2, dto.getM_password());
			pstat.setString(3, dto.getM_nickname());
			pstat.setString(4, dto.getM_gender());
			pstat.setString(5, dto.getM_agerange());
			pstat.setString(6, dto.getM_monthday());

			int result = pstat.executeUpdate();
			con.commit();

			return result;
		}
	}

	public boolean emailLoginCheck(String id, String pw) throws Exception
	{
		String sql = "select * from member where m_id = ? and m_password = ?";
		try
		(
				Connection con = this.getConnection();
				PreparedStatement pstet = con.prepareStatement(sql);
				)
		{
			pstet.setString(1, id);
			pstet.setString(2, pw);

			ResultSet rs = pstet.executeQuery();
			return rs.next();
		}
	}

	public int getIdCheck(String id)throws Exception{ // 아이디 첵크
		String sql = "select * from member where m_id=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstet = con.prepareStatement(sql);
				){
			pstet.setString(1,id);
			ResultSet rs = pstet.executeQuery();
			if(rs.next()) {
				return 1;
			}else {
				return 0;
			}
		}
	}

	public int getInsert(MemberDTO dto)throws Exception{ // 회원가입
		String sql = "insert into member(m_id,m_password,m_nickname,m_gender,m_agerange,m_monthday,m_phone,m_type,m_photo) "
				+ "values(?,?,?,?,?,?,?,'normal','<img src=\"\" alt=\"이미지가 없습니다\">')";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstet = con.prepareStatement(sql);
				){
			pstet.setString(1,dto.getM_id());
			pstet.setString(2,this.toSha256(dto.getM_password()));
			pstet.setString(3,dto.getM_nickname());
			pstet.setString(4,dto.getM_gender());
			pstet.setString(5,dto.getM_agerange());
			pstet.setString(6,dto.getM_monthday());
			pstet.setString(7,dto.getM_phone());
			int result = pstet.executeUpdate();
			con.commit();
			return result;
		}
	}

	public String getMyLoginYear(String birth)throws Exception{ // 달력에서의  년도 값 뺴오기
		Pattern infoPattern = Pattern.compile("(^....)");
		Matcher infoMatcher = infoPattern.matcher(birth);
		if(infoMatcher.find()){ // find가 group보다 선행되어야 합니다.
			return infoMatcher.group(); // ERROR
		}
		return birth;
	}

	public String getMyLoginAgeRange(String birth)throws Exception{// 년도 값에서 ex) 97년생인지 구별하기
		Pattern infoPattern = Pattern.compile("(..$)");
		Matcher infoMatcher = infoPattern.matcher(birth);
		if(infoMatcher.find()){ // find가 group보다 선행되어야 합니다.
			return infoMatcher.group(); // ERROR
		}
		return birth;
	}

	public String getMyLoginMonth_Day(String birth)throws Exception{// 달력에서 월일 - 포함해서 뺴오기
		Pattern infoPattern = Pattern.compile("(..-..$)");
		Matcher infoMatcher = infoPattern.matcher(birth);
		if(infoMatcher.find()){ // find가 group보다 선행되어야 합니다.
			return infoMatcher.group(); // ERROR
		}
		return birth;
	}
	public String getMyLoginYearMonth(String birth)throws Exception{// 월 의 값만 가져오기
		Pattern infoPattern = Pattern.compile("(^..)");
		Matcher infoMatcher = infoPattern.matcher(birth);
		if(infoMatcher.find()){ // find가 group보다 선행되어야 합니다.
			return infoMatcher.group(); // ERROR
		}
		return birth;
	}
	public String getMyLoginYearDay(String birth)throws Exception{// 일 의 값만 가져오기
		Pattern infoPattern = Pattern.compile("(..$)");
		Matcher infoMatcher = infoPattern.matcher(birth);
		if(infoMatcher.find()){ // find가 group보다 선행되어야 합니다.
			return infoMatcher.group(); // ERROR
		}
		return birth;
	}

	public String toSha256(String str)
	{
		String SHA = "";
		try
		{
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < byteData.length ; i ++ )
			{
				sb.append(Integer.toString( ( byteData[i] & 0xff ) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			SHA = null;
		}
		return SHA;
	}

	public String selectTypeById(String id) throws Exception{
		String sql = "select m_type from member where m_id = ? ";
		ResultSet rs = null;
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);

				){
			pstat.setString(1, id);
			rs = pstat.executeQuery();
			String type = "";
			if(rs.next()) {
				type = rs.getString(1);
			}
			return type;
		}finally {
			if(rs!=null) {
				rs.close();
			}
		}
	}

	public String[] getTutorImg(String tutorId) throws Exception{
		String sql = "select m_nickname, m_phone from member where m_id = ?";
		ResultSet rs = null;
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, tutorId);
			rs = pstat.executeQuery();
			String[] nick_photo = new String[2];
			if(rs.next()) {
				nick_photo[0] = rs.getString(1);
				nick_photo[1] = rs.getString(2);
			}
			return nick_photo;
		}finally {
			if(rs !=null) {
				rs.close();
			}
		}
	}
}
