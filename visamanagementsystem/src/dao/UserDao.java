package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.User;

import util.DBUtil;

public class UserDao {
	
	public static void updateUser(int id,String username, String pwd, int leixin,String realname, String stopflag) throws SQLException {
		
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="update user set username=? , password=? , type=? , realname=?,stopflag=? where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, pwd);
		stmt.setInt(3, leixin);
		stmt.setString(4, realname);
		stmt.setString(5, stopflag);
		stmt.setInt(6, id);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}
	
	public static User getUserbyId(int id) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		User user=null;

		con = DBUtil.open();
		String sql="select * from user where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, id);
		rs=stmt.executeQuery();

		if(rs.next()){
			int i=rs.getInt("id");
			String userName=rs.getString("username");
			String password=rs.getString("password");
			int type=rs.getInt("type");
			String realname=rs.getString("realname");
			String stopflag=rs.getString("stopflag");
			user=new User(i, userName, password, type,realname,stopflag);
		}

		DBUtil.close(rs, stmt, con);
		return user;
	}
	
	public static void deleteUserByIdUser(int id) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from user where id=?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
		
	}

	public static void insertUser(String username, String pwd, int leixin,String realname) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="insert into user(username,password,type,realname,stopflag) values(?,?,?,?,?)";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, pwd);
		stmt.setInt(3, leixin);
		stmt.setString(4, realname);
		stmt.setString(5, "1");
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}

	public static List<User> getUsers() throws SQLException{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<User> users=new ArrayList<User>();
		User user=null;

		con = DBUtil.open();
		String sql="select * from user";
		stmt=(Statement) con.createStatement();
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			int id=rs.getInt("id");
			String userName=rs.getString("username");
			String password=rs.getString("password");
			int type=rs.getInt("type");
			String realname=rs.getString("realname");
			String stopflag=rs.getString("stopflag");
			user=new User(id, userName, password, type,realname,stopflag);
			users.add(user);
		}

		DBUtil.close(rs, stmt, con);
		return users;
	}

	public static User getUserbyNameAandPwd(String name,String pwd) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		User user=null;

		con = DBUtil.open();
		String sql="select * from user where username=? and password=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, name);
		stmt.setString(2, pwd);
		rs=stmt.executeQuery();

		while(rs.next()){
			int id=rs.getInt("id");
			String userName=rs.getString("username");
			String password=rs.getString("password");
			int type=rs.getInt("type");
			String realname=rs.getString("realname");
			String stopflag=rs.getString("stopflag");
			user=new User(id, userName, password, type,realname,stopflag);
		}

		DBUtil.close(rs, stmt, con);
		return user;
	}

	public static boolean login(String name,String pwd) throws SQLException{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		stmt=(Statement) con.createStatement();
		String sql="select * from user";
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			int id=rs.getInt("id");
			String userName=rs.getString("username");
			String password=rs.getString("password");
			int type=rs.getInt("type");
			String stopflag=rs.getString("stopflag");
			//System.out.println(id+","+userName+","+password+","+type);
			if(name.equals(userName)&&pwd.equals(password)&&"1".equals(stopflag)){
				return true;
			}
		}

		DBUtil.close(rs, stmt, con);
		return false;
	}

	

	



}
