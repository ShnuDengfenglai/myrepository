package util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBUtil {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	private DBUtil() {}	

	static {
		try {
			load("/db.xml");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void load(String path) throws Exception{
		//相对路径"/db.xml"处理成绝对路径
		// d:/xxx/xxx/xxx/xxx/xxx/db.xml
		path =  DBUtil
				.class
				.getResource(path)
				.getPath();		

		SAXParser p = 
				SAXParserFactory
				.newInstance()//解析器工厂
				.newSAXParser();//解析器

		p.parse(path, new DefaultHandler() {
			StringBuilder sb = new StringBuilder();
			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				sb.append(new String(ch,start,length));
			}
			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				if("driver".equals(qName)) {
					driver = sb.toString().trim();
				} else if("url".equals(qName)) {
					url = sb.toString().trim();
				} else if("username".equals(qName)) {
					username = sb.toString().trim();
				} else if("password".equals(qName)) {
					password = sb.toString().trim();
				} 
			}
			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				sb.delete(0, sb.length());
			}
		});
	}



	public static Connection open() throws SQLException{
		return (Connection) DriverManager.getConnection(
				url,username,password);
	}

	public static void close(ResultSet rs) {
		if(rs == null) return;
		try{rs.close();}catch(Exception e) {}
	}
	
	public static void close(PreparedStatement stmt) {
		if(stmt == null) return;
		try{stmt.close();}catch(Exception e) {}
	}

	public static void close(Statement stmt) {
		if(stmt == null) return;
		try{stmt.close();}catch(Exception e) {}
	}

	public static void close(Connection con) {
		if(con == null) return;
		try{con.close();}catch(Exception e) {}
	}

	public static void close(ResultSet rs, Statement stmt) {
		close(rs);
		close(stmt);
	}

	public static void close(Statement stmt, Connection con) {
		close(stmt);
		close(con);
	}

	public static void close(ResultSet rs,Statement stmt,Connection con) {
		close(rs);
		close(stmt);
		close(con);
	}

	
	public static void close(ResultSet rs,PreparedStatement stmt,Connection con) {
		close(rs);
		close(stmt);
		close(con);
	}
}


