package kh.semi.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kh.semi.dto.PersonDTO;


public class PersonDAO
{
	private DataSource ds;
	
	public PersonDAO() throws Exception
	{
		Context ctx = new InitialContext();
		Context compenv = (Context)ctx.lookup("java:/comp/env");
		this.ds = (DataSource)compenv.lookup("jdbc");
	}
	
	public Connection getConnection() throws Exception
	{
		return ds.getConnection();
	}
	
	public PersonDTO selectById(String m_id) throws Exception
	{
		String sql = "SELECT * FROM MEMBER WHERE M_ID = ?";
		PersonDTO dto = null;
		String attention = "";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, m_id);
			try
			(
				ResultSet rs = pstat.executeQuery();
			)
			{
				if(rs.next())
				{
					if(rs.getString("m_attention") != null)
					{
						attention = rs.getString("m_attention");
					}
					else
					{
						attention = "[ \"없음\" ]";
					}
					dto = new PersonDTO(rs.getString("m_id"), rs.getString("m_nickname"), rs.getString("m_photo"), rs.getString("m_type"), attention);
				}
				return dto;
			}
		}
	}
	
	public String selectImgById(String m_id) throws Exception
	{
		String sql = "SELECT M_PHOTO FROM MEMBER WHERE M_ID = ?";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, m_id);
			try
			(
				ResultSet rs = pstat.executeQuery();
			)
			{
				if(rs.next())
				{
					return rs.getString("M_PHOTO");
				}
			}
			return "";
		}
	}
	
	public int updateNicknameById(String m_nickname ,String m_id) throws Exception
	{
		String sql = "UPDATE MEMBER SET M_NICKNAME = ? WHERE M_ID = ?";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, m_nickname);
			pstat.setString(2, m_id);
			int result = pstat.executeUpdate();
			con.commit();
			
			return result;
		}
	}
	
	public int updateAttentionById(String m_attention ,String m_id) throws Exception
	{
		String sql = "UPDATE MEMBER SET M_ATTENTION = ? WHERE M_ID = ?";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, m_attention);
			pstat.setString(2, m_id);
			int result = pstat.executeUpdate();
			con.commit();
			
			return result;
		}
	}
	
	public int updatePwById(String m_pw ,String m_id) throws Exception
	{
		String sql = "UPDATE MEMBER SET M_PASSWORD = ? WHERE M_ID = ?";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, m_pw);
			pstat.setString(2, m_id);
			int result = pstat.executeUpdate();
			con.commit();
			
			return result;
		}
	}
	
	public int updateImgById(String file ,String m_id) throws Exception
	{
		String sql = "UPDATE MEMBER SET M_PHOTO = ? WHERE M_ID = ?";
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, file);
			pstat.setString(2, m_id);
			int result = pstat.executeUpdate();
			con.commit();
			
			return result;
		}
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
			for(int i = 0 ; i < byteData.length ; i++)
			{
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
}
