package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import entity.BusinessInf;

public class TypeSortUtil {

	public static List<String> getTypeSort(String str) throws SQLException{
		List<String> list=new ArrayList<String>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from arctypesort where name=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, str);
		rs=stmt.executeQuery();
		String sort=null;
		if(rs.next()){
			sort=rs.getString("sort");
		}
		
		sql="select * from arctypeid where sort=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, sort);
		rs=stmt.executeQuery();
		while(rs.next()){
			String s=rs.getString("typename");
			list.add(s);
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}
	
	
}
