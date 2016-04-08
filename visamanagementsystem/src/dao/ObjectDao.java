package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import util.DBUtil;
import entity.Company;

public class ObjectDao {
	
	
	public static String getCompanyEnglishNameByChineseName(String companyChineseName) throws SQLException {
		String englishName=null;
		//String chineseName=null;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		con=DBUtil.open();
		String sql="select * from company_inf where chinese_name=?";
		stmt=(PreparedStatement) con.prepareStatement(sql);
		stmt.setString(1, companyChineseName);
		rs=stmt.executeQuery();
		if(rs.next()){
			englishName=rs.getString("english_name");
			System.out.println(englishName);
		}
		rs.close();
		stmt.close();
		con.close();
		return englishName;
	} 

	public static String[] getAccountAndBankNameByUserName(String userName) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String[] arr=new String[2];
		
		con = DBUtil.open();
		String sql="select * from bank_inf where user_name=?";
		stmt=(PreparedStatement) con.prepareStatement(sql);
		stmt.setString(1, userName);
		rs=stmt.executeQuery();

		if(rs.next()){
			String account=rs.getString("account");
			String bankName=rs.getString("bank_name");
			arr[0]=account;
			arr[1]=bankName;
		}
		
		con.close();
		stmt.close();
		rs.close();
		return arr;
	} 
	
	
	
}
