package kh.semi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import kh.semi.dto.ClassDoingDTO;

public class ClassDoingDAO {
	private Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "semi";
		String password = "semi";

		return DriverManager.getConnection(url, user, password);
	}
	
	public int insertClassDoing(ClassDoingDTO dto) throws Exception{
		String sql = "insert into classdoing values(do_seq_seq.nextval,?,?,default,default,?)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
				pstat.setInt(1, dto.getDo_classid());
				pstat.setString(2, dto.getDo_userid());
				pstat.setString(3, dto.getDo_date());
				
				int result = pstat.executeUpdate();
				con.commit();
				return result;		
		}
	}	
}
