package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.BusinessInNotice;
import entity.BusinessInf;
import entity.NoticeList;

public class BusinessInNoticeDao {
	
	
	public static void deleteBusinessInNoticeByNoticeCode(String noticeCode) throws SQLException {
		//插入表notice_list中数据
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from business_in_notice where notice_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, noticeCode);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}
	
	public static boolean ifBusinessCodeBelongToBins(String businessCode) throws SQLException{
		List<BusinessInNotice> bins=getBusinessInNotice();
		for (BusinessInNotice bin : bins) {
			String bc=bin.getBusinessCode();
			if(businessCode.equals(bc)){
				return true;
			}
		}
		return false;
	}
	
	public static List<BusinessInNotice> getBusinessInNotice() throws SQLException{
		List<BusinessInNotice> bins=new ArrayList<BusinessInNotice>();
		BusinessInNotice bin=null;
		
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from business_in_notice";
		stmt=(Statement) con.createStatement();
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			String s1=rs.getString("id");
			int id=Integer.parseInt(s1);
			String s2=rs.getString("notice_code");
			String s3=rs.getString("business_code");
			bin=new BusinessInNotice(id, s2, s3);
			bins.add(bin);
		}
		DBUtil.close(rs, stmt, con);
		return bins;
	}

	public static void insertBisInNotice(String noticeCode,List<BusinessInf> bis) throws SQLException {
		String businessCode=null;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		con = DBUtil.open();
		String sql="insert into business_in_notice(notice_code,business_code) values(?,?)";
		stmt=con.prepareStatement(sql);
		for(BusinessInf bi:bis){
			businessCode=bi.getBusiness_code();
			stmt.setString(1, noticeCode);
			stmt.setString(2, businessCode);
			stmt.executeUpdate();
		}
		
		DBUtil.close(rs, stmt, con);
	}
	
	public static List<BusinessInNotice> getBusinessInNoticeByNoticeCode(String noticeCode) throws SQLException{
		List<BusinessInNotice> bins=new ArrayList<BusinessInNotice>();
		BusinessInNotice bin=null;
		
		NoticeList nl=null;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from business_in_notice where notice_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, noticeCode);
		rs=stmt.executeQuery();

		while(rs.next()){
			String s1=rs.getString("id");
			int id=Integer.parseInt(s1);
			String s3=rs.getString("business_code");
			bin=new BusinessInNotice(id, noticeCode, s3);
			bins.add(bin);
		}
		DBUtil.close(rs, stmt, con);
		return bins;
	}
	

	public static List<BusinessInf> getBisByNoticeCode(String noticeCode) throws SQLException {
		List<BusinessInf> bis=new ArrayList<BusinessInf>();
		List<BusinessInNotice> bins=getBusinessInNoticeByNoticeCode(noticeCode);
		for (BusinessInNotice bin : bins) {
			String bc=bin.getBusinessCode();
			BusinessInf bi=BusinessInfDAO.getBusinessInfByBusinessCode(bc);
			bis.add(bi);
		}
		return bis;
	}
	
}
