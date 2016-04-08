package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.BusinessInf;
import entity.ContentSelect;
import entity.ServiceMaintain;
import entity.ServiceMaintainIn;
import entity.User;

public class ServiceMaintainInDao {

	//	public static void main(String[] args) throws SQLException {
	//		getServiceMaintains();
	//	}
	
	
//	public static List<ServiceMaintain> getServiceMaintainsInByCI(String companyId) throws SQLException{
//		Connection con=null;
//		PreparedStatement stmt=null;
//		ResultSet rs=null;
//		ServiceMaintain sm=null;
//		List<ServiceMaintain> list=new ArrayList<ServiceMaintain>();
//
//		con = DBUtil.open();
//		String sql="select * from service_maintain_in where company_id=?";
//		stmt=con.prepareStatement(sql);
//		stmt.setString(1, companyId);
//		rs=stmt.executeQuery();
//
//		while(rs.next()){
//			int id=rs.getInt("id");
//			String c=rs.getString("content");
//			int gov=rs.getInt("gov_charge");
//			int ser=rs.getInt("ser_charge");
//			int spe=rs.getInt("spe_charge");
//
//			sm=new ServiceMaintain(id,c, gov, ser, spe);
//			list.add(sm);
//		}
//
//		DBUtil.close(rs, stmt, con);
//		return list;
//	}
	
	public static void insertSerMaintainIn(String content, int gov, int ser, int spe) throws SQLException {
		String portId=ServiceMaintainDao.getPortId();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="insert into service_maintain_in values(?,?,?,?,?)";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, portId);
		stmt.setString(2, content);
		stmt.setInt(3, gov);
		stmt.setInt(4, ser);
		stmt.setInt(5,spe );

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}

	public static void deleteServiceMaintainInById(int id) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from service_maintain_in where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}

	public static void updateSerMaintainIn(int id,String content, int gov, int ser, int spe) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="update service_maintain_in set content=? , gov_charge=? , ser_charge=? , spe_charge=? where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, content);
		stmt.setInt(2, gov);
		stmt.setInt(3, ser);
		stmt.setInt(4,spe );
		stmt.setInt(5,id );

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}

	public static ServiceMaintainIn getServiceMaintainInById(String id) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ServiceMaintainIn sm=null;

		con = DBUtil.open();
		String sql="select * from service_maintain_in where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, id);
		rs=stmt.executeQuery();

		if(rs.next()){
			String c=rs.getString("content");
			int gov=rs.getInt("gov_charge");
			int ser=rs.getInt("ser_charge");
			int spe=rs.getInt("spe_charge");

			sm=new ServiceMaintainIn(id, c, gov, ser, spe);
		}

		DBUtil.close(rs, stmt, con);
		return sm;
	}

	public static List<ServiceMaintainIn> getServiceMaintainIns() throws SQLException{
		List<ServiceMaintainIn> list=new ArrayList<ServiceMaintainIn>();
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		stmt=(Statement) con.createStatement();
		String sql="select * from service_maintain_in";
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			String  s0=rs.getString("id");
			String s3=rs.getString("content");
			int s4=rs.getInt("gov_charge");
			int s5=rs.getInt("ser_charge");
			int s6=rs.getInt("spe_charge");

			ServiceMaintainIn sm = new ServiceMaintainIn(s0, s3, s4, s5, s6);
			list.add(sm);
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}

	

	public static List<ContentSelect> getContentSelectsIn() throws SQLException {
		List<ContentSelect> list=new ArrayList<ContentSelect>();
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from service_maintain_in ";
		stmt=(Statement) con.prepareStatement(sql);
		rs=stmt.executeQuery(sql);
		
		while(rs.next()){
			String  s1=rs.getString("id");
			String s2=rs.getString("content");
			int s3=rs.getInt("gov_charge");
			int s4=rs.getInt("ser_charge");
			int s5=rs.getInt("spe_charge");
			ContentSelect cs = new ContentSelect(s1,s2, s3, s4, s5);
			list.add(cs);
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}


}
