package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.ContentSelect;
import entity.ServiceContent;

public class ServiceContentDAO {
	
//	public static void main(String[] args) throws SQLException {
//		ServiceContentDAO dao=new ServiceContentDAO();
//		dao.updateFinish("5151516", "加急体检预约", 0);
//		System.out.println("插入成功");
//	}
	
	public static ServiceContent ifCSBelongToSCs(ContentSelect cs,List<ServiceContent> scs){
		String content=cs.getContent();
		
		for (ServiceContent sc : scs) {
			String con=sc.getContent();
			if(content.equals(con)){
				return sc;
			}
		}
		return null;
	}
	
	public static int judgeBusinessStatement(List<ServiceContent> services) throws SQLException{
		/* 返回值代表的含义
		 * 0: 一个都没完成
		 * 1: 至少完成一个但没有全部完成
		 * 2: 全部完成
		 * 3: 出错
		 * */
		
		int i=0;
		for(ServiceContent service:services){
			int finish=service.getFinish();
			if(finish==1){
				i+=1;
			}
		}
		int j=services.size();
		
		if(i==0){//表示一项都没有完成
			return 0;
		}else if(i>0&&i<j){//完成至少一项但没有全部完成
			return 1;
		}else if(i==j){//表示全部完成
			return 2;
		}else{
			return 3;
		}
	}
	
	public static void updateFinish(String business_code,String content,int finish) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="update service_content set finish=? where business_code=? and content=?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, finish);
		stmt.setString(2, business_code);
		stmt.setString(3, content);
		
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);

	}
	
	public static void deleteServices(String business_code) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from service_content where business_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, business_code);

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);

	}
	
	public static void updateServiceContent(String business_code,String content,int gov_charge,int ser_charge,int spe_charge,int finish) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		con = DBUtil.open();
		String sql="insert into service_content values(?,?,?,?,?,?)";
		stmt=con.prepareStatement(sql);
		
		stmt.setString(1, business_code);
		stmt.setString(2, content);
		stmt.setInt(3, gov_charge);
		stmt.setInt(4, ser_charge);
		stmt.setInt(5, spe_charge);
		stmt.setInt(6, finish);
		
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}
	
	
	public static void insertServiceContent(String business_code,String content,int gov_charge,int ser_charge,int spe_charge) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		con = DBUtil.open();
		String sql="insert into service_content values(?,?,?,?,?,?)";
		stmt=con.prepareStatement(sql);
		
		stmt.setString(1, business_code);
		stmt.setString(2, content);
		stmt.setInt(3, gov_charge);
		stmt.setInt(4, ser_charge);
		stmt.setInt(5, spe_charge);
		stmt.setInt(6, 0);
		
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}

	public static List<ServiceContent> getServicesByBusinessCode(String businessCode) throws SQLException{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<ServiceContent> services=new ArrayList<ServiceContent>();
		
		con = DBUtil.open();
		stmt=(Statement) con.createStatement();
		String sql="select * from service_content";
		rs=stmt.executeQuery(sql);
		
		while(rs.next()){
			String s1=rs.getString("business_code");
			String s2=rs.getString("content");
			int s3=rs.getInt("gov_charge");
			int s4=rs.getInt("ser_charge");
			int s5=rs.getInt("spe_charge");
			int s6=rs.getInt("finish");
			
			if(businessCode.equals(s1)){
				ServiceContent service=new ServiceContent(s1, s2, s3, s4, s5, s6);
				services.add(service);
			}
		}
		
		DBUtil.close(rs, stmt, con);
		return services;
	}

	public static int getFinishByContent(String content, List<ServiceContent> services) throws SQLException {
		int finish=0;
		for (ServiceContent service : services) {
			String con=service.getContent();
			if(content.equals(con)){
				finish=service.getFinish();
			}
		}
		return finish;
	}
	
//	public static void main(String[] args) throws SQLException {
//		ServiceContentDAO dao=new ServiceContentDAO();
//		List<ServiceContent> services=dao.getServicesByBusinessCode("13657982961");
//		for(ServiceContent service:services){
//			System.out.println(service.getBusiness_code()+","+service.getContent()+","+service.getGov_charge()+","+service.getSer_charge()+","+service.getSpe_charge()+","+service.getFinish());
//		}
//	}
	
}
