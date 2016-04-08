package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.DBUtil;

import com.mysql.jdbc.Connection;

import entity.BusinessInf;
import entity.NoticeList;

public class NoticeListDao {
	
	public  static List<NoticeList> getNoticeListByCustomCompany(String customCompany) throws SQLException{
		List<NoticeList> nls=new ArrayList<NoticeList>();
		NoticeList nl=null;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from notice_list where cus_com_name=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, customCompany);
		rs=stmt.executeQuery();

		while(rs.next()){
			String s1=rs.getString("notice_code");
			String s2=rs.getString("com_ch_name");
			String s3=rs.getString("com_en_name");
			String s4=rs.getString("cus_com_name");
			String s5=rs.getString("user_name");
			String s6=rs.getString("account");
			String s7=rs.getString("bank_name");
			String s8=rs.getString("salesman");
			String s9=rs.getString("produce_time");
			nl = new NoticeList(s1, s2, s3, s4, s5, s6, s7, s8,s9);
			nls.add(nl);
		}
		return nls;
	}
	
	public  static List<NoticeList> getNoticeLists() throws SQLException{
		List<NoticeList> nls=new ArrayList<NoticeList>();
		NoticeList nl=null;
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from notice_list";
		stmt=con.createStatement();
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			String s1=rs.getString("notice_code");
			String s2=rs.getString("com_ch_name");
			String s3=rs.getString("com_en_name");
			String s4=rs.getString("cus_com_name");
			String s5=rs.getString("user_name");
			String s6=rs.getString("account");
			String s7=rs.getString("bank_name");
			String s8=rs.getString("salesman");
			String s9=rs.getString("produce_time");
			nl = new NoticeList(s1, s2, s3, s4, s5, s6, s7, s8,s9);
			nls.add(nl);
		}
		return nls;
	}


	public static String getNoticeCode(String visaCompany, String time) throws SQLException {
		Random r=new Random();
		String i=null;
		String j=null;
		String k=null;
		String three=null;
		String noticeCode=null;
		List<NoticeList> nls=getNoticeLists();
		
		while(true){
			//产生一个三位随机数
			i=r.nextInt(9)+1+"";
			j=r.nextInt(9)+1+"";
			k=r.nextInt(9)+1+"";
			 three=i+j+k;
			 boolean a=false;
			
			 //看notice_list库中通知书代码的后三位是否有相同的
			for(NoticeList nl:nls){
				noticeCode=nl.getNoticeCode();
				noticeCode=noticeCode.substring(noticeCode.length()-3);
				if(three.equals(noticeCode)){
					a=true;
				}
			}
			
			if(!a){//没有相同的通指数代码
				break;//跳出while循环
			}
		}
		
		noticeCode=visaCompany+time+three;
		return noticeCode;
	}
	
	public static void deleteNoticeListByNoticeCode(String noticeCode) throws SQLException {
		//插入表notice_list中数据
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from notice_list where notice_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, noticeCode);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
		BusinessInNoticeDao.deleteBusinessInNoticeByNoticeCode(noticeCode);
	}

	public static void insertNotice(String comChName, String comEnName,String cusComName, String userName, String account,String bankName, String salesman, List<BusinessInf> bis,String produceTime, String noticeCode) throws SQLException {
		//插入表notice_list中数据
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="insert into notice_list values(?,?,?,?,?,?,?,?,?)";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, noticeCode);
		stmt.setString(2, comChName);
		stmt.setString(3, comEnName);
		stmt.setString(4, cusComName);
		stmt.setString(5, userName);
		stmt.setString(6, account);
		stmt.setString(7, bankName);
		stmt.setString(8, salesman);
		stmt.setString(9, produceTime);

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
		
		//插入表business_in_notice中数据
		BusinessInNoticeDao.insertBisInNotice(noticeCode,bis);
		
	}
	
	public static NoticeList getNoticeListByNoticeCode(String noticeCode) throws SQLException {
		NoticeList nl=null;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="select * from notice_list where notice_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, noticeCode);
		rs=stmt.executeQuery();

		if(rs.next()){
			String s1=rs.getString("notice_code");
			String s2=rs.getString("com_ch_name");
			String s3=rs.getString("com_en_name");
			String s4=rs.getString("cus_com_name");
			String s5=rs.getString("user_name");
			String s6=rs.getString("account");
			String s7=rs.getString("bank_name");
			String s8=rs.getString("salesman");
			String s9=rs.getString("produce_time");
			nl=new NoticeList(s1, s2, s3, s4, s5, s6, s7, s8,s9);
			DBUtil.close(rs, stmt, con);
			//System.out.println(s8);
		}
		//System.out.println(nl.getCusComName());
		return nl;
		
	}

	public static String generateNoticeCode() throws SQLException {
		java.util.Date today1=new java.util.Date();
		String time = new SimpleDateFormat("yy-MM-dd").format(today1);
		String[] arr=time.split("-");
		time=arr[0]+arr[1]+arr[2];
		String visaCompany="xinwudao";
		String noticeCode=getNoticeCode(visaCompany, time);
		return noticeCode;
	}


	



}
