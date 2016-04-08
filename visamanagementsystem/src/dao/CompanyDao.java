package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.Company;
import entity.User;

public class CompanyDao {
	
	public static void updateComMaintain(int id,String companyId,String companyName) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Company company=null;

		con = DBUtil.open();
		String sql="update company_list set company_id=?,company_name=? where id=?";
		stmt=(PreparedStatement) con.prepareStatement(sql);
		stmt.setString(1, companyId);
		stmt.setString(2, companyName);
		stmt.setInt(3, id);
		stmt.executeUpdate();

		DBUtil.close(rs, stmt, con);
	}
	
	public static Company getCompanyById(int id) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Company company=new Company();

		con = DBUtil.open();
		String sql="select * from company_list where id=?";
		stmt=(PreparedStatement) con.prepareStatement(sql);
		stmt.setInt(1, id);
		rs=stmt.executeQuery();

		if(rs.next()){
			company.setId(id);
			company.setCompanyId(rs.getString("company_id"));
			company.setCompanyName(rs.getString("company_name"));
		}

		DBUtil.close(rs, stmt, con);
		return company;
	}

	public static String getCompanyNameByCI(String ci) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String companyName=null;

		con = DBUtil.open();
		String sql="select * from company_list where company_id=?";
		stmt=(PreparedStatement) con.prepareStatement(sql);
		stmt.setString(1, ci);
		rs=stmt.executeQuery();

		if(rs.next()){
			companyName=rs.getString("company_name");
		}

		DBUtil.close(rs, stmt, con);
		return companyName;
	}
	
	public static List<Company> getCompanies() throws SQLException{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<Company> list=new ArrayList<Company>();

		con = DBUtil.open();
		String sql="select * from company_list";
		stmt=(Statement) con.createStatement();
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			int id=rs.getInt("id");
			String companyId=rs.getString("company_id");
			String companyName=rs.getString("company_name");
			Company com=new Company(id, companyId, companyName);
			list.add(com);
		}

		DBUtil.close(rs, stmt, con);
		return list;
	}
	
	public static boolean insertCompanyInf(String ci, String cn) throws SQLException {
		Connection con=null;
		Statement stmt=null;
		PreparedStatement stmt1=null;
		ResultSet rs=null;
		
		con = DBUtil.open();
		String sql="select * from company_list";
		stmt=(Statement) con.createStatement();
		rs=stmt.executeQuery(sql);
		while(rs.next()){
			String companyId=rs.getString("company_id");
			String companyName=rs.getString("company_name");
			if(companyId.equals(ci)||companyName.equals(cn)){
				return false;
			}
		}
		sql="insert into company_list( company_id, company_name) values(?,?)";
		stmt1=con.prepareStatement(sql);
		stmt1.setString(1, ci);
		stmt1.setString(2, cn);

		stmt1.executeUpdate();
		DBUtil.close(rs, stmt1, con);
		stmt.close();
		return true;
	}

	public static void deleteCompanyMaintainById(int id) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from company_list where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}

	
	
}
