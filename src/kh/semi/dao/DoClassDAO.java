package kh.semi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kh.semi.dto.DoClassDTO;


public class DoClassDAO
{
	private DataSource ds;
	
	public DoClassDAO() throws Exception
	{
		Context ctx = new InitialContext();
		Context compenv = (Context)ctx.lookup("java:/comp/env");
		this.ds = (DataSource)compenv.lookup("jdbc");
	}
	
	public Connection getConnection() throws Exception
	{
		return ds.getConnection();
	}

	public List<DoClassDTO> selectDoingClass(String id, int page) throws Exception
	{
		String sql = 	"select * " + 
						"from (select row_number() over(order by DO_DATE) as rown, " + 
								"i.INFO_CLASSID as CLASSID, i.INFO_TUTORID as TUTORID, i.INFO_TITLE as TITLE, i.INFO_IMG1 as IMG, d.DO_DATE as \"DATE\" " + 
								"from CLASSINFO i,CLASSDOING d " + 
								"WHERE (i.INFO_CLASSID = d.DO_CLASSID) " + 
								"AND (d.DO_USERID = ?) " + 
								"AND ( TRUNC(SYSDATE) <= d.DO_DATE)) " + 
						"WHERE ROWN BETWEEN ? and ?";
		
		List<DoClassDTO> list = new ArrayList<DoClassDTO>();
		
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, id);
			pstat.setInt(2, page * 3 - 2);
			pstat.setInt(3, page * 3);
			try
			(
				ResultSet rs = pstat.executeQuery();
			)
			{
				while(rs.next())
				{
					list.add(new DoClassDTO(rs.getInt("CLASSID"), rs.getString("TUTORID"), rs.getString("TITLE"), rs.getString("IMG"), rs.getDate("DATE")));
				}
				
				return list;
			}
		}
	}
	
	public List<DoClassDTO> selectDoneClass(String id, int page) throws Exception
	{
		String sql = 	"select * " + 
						"from (select row_number() over(order by DO_DATE DESC) as rown, " + 
								"i.INFO_CLASSID as CLASSID, i.INFO_TUTORID as TUTORID, i.INFO_TITLE as TITLE, i.INFO_IMG1 as IMG, d.DO_DATE as \"DATE\" " + 
								"from CLASSINFO i,CLASSDOING d " + 
								"WHERE (i.INFO_CLASSID = d.DO_CLASSID) " + 
								"AND (d.DO_USERID = ?) " + 
								"AND ( TRUNC(SYSDATE) > d.DO_DATE)) " + 
						"WHERE ROWN BETWEEN ? and ?";
		
		List<DoClassDTO> list = new ArrayList<DoClassDTO>();
		
		try
		(
			Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
		)
		{
			pstat.setString(1, id);
			pstat.setInt(2, page * 3 - 2);
			pstat.setInt(3, page * 3);
			try
			(
				ResultSet rs = pstat.executeQuery();
			)
			{
				while(rs.next())
				{
					list.add(new DoClassDTO(rs.getInt("CLASSID"), rs.getString("TUTORID"), rs.getString("TITLE"), rs.getString("IMG"), rs.getDate("DATE")));
				}
				
				return list;
			}
		}
	}
	
	public int selectDoingCount(String id) throws Exception
	{
		String sql = 	"select count(*) " + 
						"from CLASSINFO i,CLASSDOING d " + 
						"WHERE (i.INFO_CLASSID = d.DO_CLASSID) " + 
						"AND (d.DO_USERID = ?) " + 
						"AND ( TRUNC(SYSDATE) <= d.DO_DATE)";
		
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
				if(rs.next())
				{
					return rs.getInt("count(*)");
				}
				else
				{
					return 0;
				}
			}
		}
	}
	
	public int selectDoneCount(String id) throws Exception
	{
		String sql = 	"select count(*) " + 
						"from CLASSINFO i,CLASSDOING d " + 
						"WHERE (i.INFO_CLASSID = d.DO_CLASSID) " + 
						"AND (d.DO_USERID = ?) " + 
						"AND ( TRUNC(SYSDATE) > d.DO_DATE)";
		
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
				if(rs.next())
				{
					return rs.getInt("count(*)");
				}
				else
				{
					return 0;
				}
			}
		}
	}
}
