package dao;

import java.io.IOException;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import entity.BusinessInNotice;
import entity.BusinessInf;
import entity.ContentSelect;
import entity.NoticeList;
import entity.ServiceContent;

public class BusinessInfDAO {
	
	public static void updateInvoiceMoney(List<BusinessInf> bis,String invoiceMoney) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		con = DBUtil.open();
		int im=Integer.parseInt(invoiceMoney);
		String bc=null;
		String sql="update business_inf set invoice_money=? where business_code=?";
		stmt=con.prepareStatement(sql);
		for(BusinessInf bi:bis){
			bc=bi.getBusiness_code();
			stmt.setInt(1, im);
			stmt.setString(2, bc);
			stmt.executeUpdate();
		}
		DBUtil.close(rs, stmt, con);
	}
	
	public static void updateInvoiceTitle(String[] businessCodes,String invoiceTitle) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		con = DBUtil.open();
		String sql="update business_inf set invoice_title=? where business_code=?";
		for(String bc:businessCodes){
			stmt=con.prepareStatement(sql);
			stmt.setString(1, invoiceTitle);
			stmt.setString(2, bc);
			stmt.executeUpdate();
		}
		DBUtil.close(rs, stmt, con);
	}
	
	public static void updateInvoiceCodeMoneyState(String[] businessCodes,String invoiceCode,String chargeFinish) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		con = DBUtil.open();
		//System.out.println("chargeFinish="+chargeFinish);
		String sql="update business_inf set invoice_code=?,charge_finish=? where business_code=?";
		for(String bc:businessCodes){
			stmt=con.prepareStatement(sql);
			stmt.setString(1, invoiceCode);
			stmt.setString(2, chargeFinish);
			stmt.setString(3, bc);
			stmt.executeUpdate();
		}
		DBUtil.close(rs, stmt, con);
	}
	
	public static List<BusinessInf> getSelectedBis(List<BusinessInf> bis, String[] strs){
		List<BusinessInf> bis1=new ArrayList<BusinessInf>();
		boolean f=false;
		for(BusinessInf bi:bis){
			String businessCode=bi.getBusiness_code();
			for(String bc:strs){
				if(bc.equals(businessCode)){
					f=true;
				}
			}
			if(f==true){
				bis1.add(bi);
				f=false;
			}
		}
		return bis1;
	}
	
	

	public static List<BusinessInf> getSelectedBusinessInfs(String selectedState,List<BusinessInf> bis) throws SQLException {
		List<BusinessInf> finishAll=new ArrayList<BusinessInf>();
		List<BusinessInf> finishSome=new ArrayList<BusinessInf>();
		List<BusinessInf> noStart=new ArrayList<BusinessInf>();

		for(BusinessInf bi:bis){
			List<ServiceContent> services = ServiceContentDAO.getServicesByBusinessCode(bi.getBusiness_code());
			int statement=ServiceContentDAO.judgeBusinessStatement(services);
			if(statement==0){
				noStart.add(bi);
			}else if(statement==1){
				finishSome.add(bi);
			}else if(statement==2){
				finishAll.add(bi);
			}
		}

		if("全部完成".equals(selectedState)){
			return finishAll;
		}else if("完成一部分".equals(selectedState)){
			return finishSome;
		}else if("未开始".equals(selectedState)){
			return noStart;
		}else{
			return bis;
		}
	}


	public static String getBusinessCode(String ci,String time) throws SQLException{
		//15-12-21
		String[] arr=time.split("-");
		time=arr[0]+arr[1]+arr[2];

		List<BusinessInf> list = getBusinessInfs();
		List<String> bcs=new ArrayList<String>();
		for(BusinessInf bi:list){
			bcs.add(bi.getBusiness_code());
		}

		Random r=new Random();	
		String str=ci+time;
		String s=null;
		String st=null;
		int i=0;
		int j=0;
		int k=0;

		loop1:
			while(true){
				i=r.nextInt(10);
				j=r.nextInt(10);
				k=r.nextInt(10);
				s=""+i+j+k;	
				st=str+s;	
				for (String bc : bcs) {
					if(st.equals(bc)){
						continue loop1;
					}
				}
				break;
			}
		return st;
	}

	//	public static void main(String[] args) throws SQLException {
	//		insertOrder("shnu", "刘欢", "2015-11-20", "熊波元", "到附近的空间发", request, response)
	//	}

	public static void deleteContentsByBC(String bc) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from service_content where business_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, bc);
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}


	public static void insertOrder1(String company_id, String business_code,String customer_name, String date, String salesman, String remaks,HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{

		String[] selectedIds=request.getParameterValues("content");
		//当新订单中有服务内容时才能插入该订单
		if(!(selectedIds==null)){
			BusinessInfDAO.updateBusinessInf(company_id, business_code, customer_name, date, salesman, remaks);
			List<ServiceContent> services = ServiceContentDAO.getServicesByBusinessCode(business_code);
			deleteContentsByBC(business_code);
			//获取所有的服务内容
			List<ContentSelect> allCS = ServiceMaintainDao.getContentSelects();
			List<ContentSelect> all = ServiceMaintainInDao.getContentSelectsIn();
			allCS.addAll(all);

			for(String id:selectedIds){
				String gov=id+"gov";
				String ser=id+"ser";
				String spe=id+"spe";

				int govCharge=Integer.parseInt(request.getParameter(gov));
				int serCharge=Integer.parseInt(request.getParameter(ser));
				int speCharge=Integer.parseInt(request.getParameter(spe));

				String con=null;
				for(ContentSelect cs:allCS){
					String abbr=cs.getAbbr()+"";
					if(id.equals(abbr)){
						con=cs.getContent();
					}
				}
				int finish=ServiceContentDAO.getFinishByContent(con,services);

				//System.out.println(con+"  "+govCharge+"  "+serCharge+"  "+speCharge);
				ServiceContentDAO.updateServiceContent(business_code,con,govCharge, serCharge, speCharge,finish);
			}
		}

		request.getRequestDispatcher("/list.do").forward(request, response);
	}

	public static void insertOrder(String company_id, String customer_name, String date, String salesman, String remaks,HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		//自动生成业务编号
		java.util.Date today1=new java.util.Date();
		String time = new SimpleDateFormat("yy-MM-dd").format(today1);
		String business_code=getBusinessCode(company_id, time);
		//把business_code存入session中。
		request.getSession().setAttribute("business_code_file", business_code);
		//判断是否已经存在该订单
		BusinessInf inf = findBusinessInfbyBusinessCode(business_code);
		//当订单列表中已经有这条订单的时候，新订单就不插入
		if(inf==null){
			String[] selectedIds=request.getParameterValues("content");
			//当新订单中有服务内容时才能插入该订单
			if(!(selectedIds==null)){
				BusinessInfDAO.insertBusinessInf(company_id, business_code, customer_name, date, salesman, remaks);

				//获取所有的服务内容
				List<ContentSelect> allCS = ServiceMaintainDao.getContentSelects();
				List<ContentSelect> all = ServiceMaintainInDao.getContentSelectsIn();
				allCS.addAll(all);

				for(String id:selectedIds){
					String gov=id+"gov";
					String ser=id+"ser";
					String spe=id+"spe";

					int govCharge=Integer.parseInt(request.getParameter(gov));
					int serCharge=Integer.parseInt(request.getParameter(ser));
					int speCharge=Integer.parseInt(request.getParameter(spe));

					String con=null;
					for(ContentSelect cs:allCS){
						String abbr=cs.getAbbr()+"";
						if(id.equals(abbr)){
							con=cs.getContent();
						}
					}
					//System.out.println(con+"  "+govCharge+"  "+serCharge+"  "+speCharge);
					ServiceContentDAO.insertServiceContent(business_code,con,govCharge, serCharge, speCharge);
				}
			}
		}
		
	}

	//where语句中发票金额部分
	private static String getInvoiceMoney(int s){
		if(s==0){//输入框中空着不填，传过来的就是0
			return "";
		}else{
			return "invoice_money="+s;
		}
	}
	
	//where语句中备注部分1
			private static String getRemarks1(String s){
				if("".equals(s)){
					return "";
				}else{
					return "remaks="+"'"+s+"'"+" and ";
				}
			}
	
	//where语句中发票抬头部分1
		private static String getInvoiceTitle1(String s){
			if("".equals(s)){
				return "";
			}else{
				return "invoice_title="+"'"+s+"'"+" and ";
			}
		}

	//where语句中付款状态部分1
	private static String getPayState1(String s){
		if("".equals(s)){
			return "";
		}else{
			return "charge_finish="+"'"+s+"'"+" and ";
		}
	}

	//where语句中付款状态部分
	private static String getPayState(String s){
		if("".equals(s)){
			return "";
		}else{
			return "charge_finish="+"'"+s+"'";
		}
	}

	//where语句中发票编号部分
	private static String getInvoiceCode(String s){
		if("".equals(s)){
			return "";
		}else{
			return "invoice_code="+"'"+s+"'"+" and ";
		}
	}

	//where语句中业务员部分
	private static String getSalesName(String s){
		if("".equals(s)){
			return "";
		}else{
			return "salesman="+"'"+s+"'"+" and ";
		}
	}

	
	//where语句中时间段部分
	private static String getDuring(String s1,String s2){
		//时间列名>='2010-12-12 00:00:00' and 时间列名<='2011-03-31 23:59:59'
		if(!("".equals(s1))&&!("".equals(s2))){
			return "(date>="+"'"+s1+"'"+" and date<="+"'"+s2+"'"+") and ";
		}else{
			return "";
		}
	}

	//where语句中客户姓名部分
	private static String getCustomerName(String s){

		if("".equals(s)){
			return "";
		}else{
			return "customer_name="+"'"+s+"'"+" and ";
		}
	}

	//where语句中业务编号部分
	private static String getBusinessCode(String s){
		if("".equals(s)){
			return "";
		}else{
			return "business_code="+"'"+s+"'"+" and ";
		}
	}

	//where语句中公司代号部分
	private static String getCompanyId(String s){
		if("".equals(s)){
			return "";
		}else{
			return "company_id="+"'"+s+"'"+" and ";
		}
	}


	public static List<BusinessInf> searchBusinessInfs1(String companyId,String businessCode,String customerName,String date1,String date2,int sumOfCharges,String salesman, String invoiceCode, String chargeState,String invoiceTitle, String remarks, int invoiceMoney) throws SQLException{
		//System.out.println("remarks:"+remarks);
		List<BusinessInf> list=new ArrayList<BusinessInf>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();

		String sql="select * from business_inf where ";
		String where=getCompanyId(companyId)+getBusinessCode(businessCode)+getCustomerName(customerName)+getDuring(date1, date2)+getSalesName(salesman)+getInvoiceCode(invoiceCode)+getPayState1(chargeState)+getInvoiceTitle1(invoiceTitle)+getRemarks1(remarks)+getInvoiceMoney(invoiceMoney);
		sql+=where;

		if("select * from business_inf where ".equals(sql)){
			sql="select * from business_inf";
		}else if("and".equals(sql.substring(sql.lastIndexOf("a"), sql.length()-1))){
			sql=sql.substring(0, sql.lastIndexOf("a")-1);
		}
		System.out.println("sql语句:"+sql);

		stmt= con.prepareStatement(sql);
		rs=stmt.executeQuery();
		while(rs.next()){
			String s1=rs.getString("company_id");
			String s2=rs.getString("business_code");
			String s3=rs.getString("customer_name");
			String s4=rs.getString("date");
			String s5=rs.getString("salesman");
			String s6=rs.getString("remaks");
			String s7=rs.getString("invoice_code");
			String s8=rs.getString("charge_finish");
			String s9=rs.getString("invoice_title");
			int s10=rs.getInt("invoice_money");
			String s11=rs.getString("date_finish");
			String s12=rs.getString("file_name");
			
			List<ServiceContent> services=ServiceContentDAO.getServicesByBusinessCode(s2);
			int s=0;
			for(ServiceContent sc:services){
				int gov=sc.getGov_charge();
				int ser=sc.getSer_charge();
				int spe=sc.getSpe_charge();
				s+=gov+ser+spe;
			}
			
			if(sumOfCharges==s||sumOfCharges==0){
				BusinessInf bi = new BusinessInf(s1, s2, s3, s4, s5, s6, s7, s8,s9,s10,s11,s12);
				list.add(bi);
			}
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}

	public static List<BusinessInf> searchBusinessInfs(String companyId, String businessCode,String customerName,String date1,String date2,String salesman, String invoiceCode, String chargeState) throws SQLException{

		List<BusinessInf> list=new ArrayList<BusinessInf>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();

		String sql="select * from business_inf where ";
		String where=getCompanyId(companyId)+getBusinessCode(businessCode)+getCustomerName(customerName)+getDuring(date1, date2)+getSalesName(salesman)+getInvoiceCode(invoiceCode)+getPayState(chargeState);
		sql+=where;

		if("select * from business_inf where ".equals(sql)){
			sql="select * from business_inf";
		}else if("and".equals(sql.substring(sql.lastIndexOf("a"), sql.length()-1))){
			sql=sql.substring(0, sql.lastIndexOf("a")-1);
		}
		System.out.println(sql);

		stmt= con.prepareStatement(sql);
		rs=stmt.executeQuery();
		while(rs.next()){
			String s1=rs.getString("company_id");
			String s2=rs.getString("business_code");
			String s3=rs.getString("customer_name");
			String s4=rs.getString("date");
			String s5=rs.getString("salesman");
			String s6=rs.getString("remaks");
			String s7=rs.getString("invoice_code");
			String s8=rs.getString("charge_finish");
			String s9=rs.getString("invoice_title");
			int s10=rs.getInt("invoice_money");
			String s11=rs.getString("date_finish");
			String s12=rs.getString("file_name");

			BusinessInf bi = new BusinessInf(s1, s2, s3, s4, s5, s6, s7, s8,s9,s10,s11,s12);
			list.add(bi);
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}

	public static BusinessInf findBusinessInfbyBusinessCode(String business_code) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		BusinessInf bi=null;

		con = DBUtil.open();
		String sql="select * from business_inf where business_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, business_code);

		rs=stmt.executeQuery();
		while(rs.next()){
			String s1=rs.getString("company_id");
			String s2=rs.getString("business_code");
			String s3=rs.getString("customer_name");
			String s4=rs.getString("date");
			String s5=rs.getString("salesman");
			String s6=rs.getString("remaks");
			String s7=rs.getString("invoice_code");
			String s8=rs.getString("charge_finish");
			String s9=rs.getString("invoice_title");
			int s10=rs.getInt("invoice_money");
			String s11=rs.getString("date_finish");
			String s12=rs.getString("file_name");
			
			bi = new BusinessInf(s1, s2, s3, s4, s5, s6, s7, s8,s9,s10,s11,s12);

		}

		DBUtil.close(rs, stmt, con);
		return bi;
	}

	public static void deleteBusinessInf(String business_code) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="delete from business_inf where business_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, business_code);

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);

	}
	
	
	public static void updateFilePathByBusinessCode(String businessCode,String fileName) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="update business_inf set file_name=? where business_code=?";
		stmt=con.prepareStatement(sql);

		stmt.setString(1,fileName);
		stmt.setString(2,businessCode);

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);

	}

	public static void updateBusinessInf(String company_id,String business_code,String customer_name,
			String	date,String	salesman,String remaks) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		String sql="update business_inf set company_id=?,customer_name=?,date=?,salesman=?,remaks=? where business_code=?";
		stmt=con.prepareStatement(sql);

		stmt.setString(1, company_id);
		stmt.setString(2, customer_name);
		Date datetime=Date.valueOf(date);
		stmt.setDate(3, (java.sql.Date) datetime);
		stmt.setString(4, salesman);
		stmt.setString(5, remaks);
		stmt.setString(6,business_code );

		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);

	}

	public static void insertBusinessInf(String company_id,String business_code,String customer_name,
			String	date,String	salesman,String remaks) throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		//String invoiceTitle=CompanyDao.getCompanyNameByCI(company_id);

		con = DBUtil.open();
		String sql="insert into business_inf values(?,?,?,?,?,?,?,?,?,?,?,?)";
		stmt=con.prepareStatement(sql);

		stmt.setString(1, company_id);
		stmt.setString(2, business_code);
		stmt.setString(3, customer_name);
		Date datetime=Date.valueOf(date);
		stmt.setDate(4, datetime);
		stmt.setString(5, salesman);
		stmt.setString(6, remaks);
		stmt.setString(7, null);
		stmt.setString(8, "未到账");
		stmt.setString(9, "");
		stmt.setInt(10, 0);
		stmt.setDate(11, datetime);
		stmt.setString(12, "");
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);

	}


	public static List<BusinessInf> getBusinessInfs() throws SQLException{
		List<BusinessInf> list=new ArrayList<BusinessInf>();
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;

		con = DBUtil.open();
		stmt=(Statement) con.createStatement();
		String sql="select * from business_inf";
		rs=stmt.executeQuery(sql);

		while(rs.next()){
			String s1=rs.getString("company_id");
			String s2=rs.getString("business_code");
			String s3=rs.getString("customer_name");
			String s4=rs.getString("date");
			String s5=rs.getString("salesman");
			String s6=rs.getString("remaks");
			String s7=rs.getString("invoice_code");
			String s8=rs.getString("charge_finish");
			String s9=rs.getString("invoice_title");
			int s10=rs.getInt("invoice_money");
			String s11=rs.getString("date_finish");
			String s12=rs.getString("file_name");

			BusinessInf bi = new BusinessInf(s1, s2, s3, s4, s5, s6, s7, s8,s9,s10,s11,s12);
			list.add(bi);
			//System.out.println(s1+","+s2+","+s3+","+s4+","+s5+","+s6);
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}

	public static void updateICAndCFByBC(String businessCode, String invoiceCode, String chargeFinish, String invoiceTitle, int invoiceMoney)  throws SQLException{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		con = DBUtil.open();
		
		if("到帐".equals(chargeFinish)){//到账就要多修改一个值：date_finish
			//System.out.println("到账");
			String sql="update business_inf set invoice_code=?,charge_finish=?,invoice_title=?,invoice_money=?,date_finish=? where business_code=?";
			stmt=con.prepareStatement(sql);
			
			java.util.Date date=new java.util.Date();
			SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			Date datetime =Date.valueOf(fmt.format(date));
			
			stmt.setString(1, invoiceCode);
			stmt.setString(2, chargeFinish); 
			stmt.setString(3, invoiceTitle);
			stmt.setInt(4, invoiceMoney);
			stmt.setDate(5, datetime);
			stmt.setString(6, businessCode);
		}else{
			//System.out.println("未到账");
			String sql="update business_inf set invoice_code=?,charge_finish=?,invoice_title=?,invoice_money=? where business_code=?";
			stmt=con.prepareStatement(sql);

			stmt.setString(1, invoiceCode);
			stmt.setString(2, chargeFinish);
			stmt.setString(3, invoiceTitle);
			stmt.setInt(4, invoiceMoney);
			stmt.setString(5, businessCode);
		} 
		stmt.executeUpdate();
		DBUtil.close(rs, stmt, con);
	}


	public static List<BusinessInf> getSomeOfBis(List<BusinessInf> bis, int n) {
		List<BusinessInf> list=new ArrayList<BusinessInf>();
		int size=bis.size();
		if(n<=size){
			for(int i=size-1;i>size-n-1;i--){
				list.add(bis.get(i));
			}
		}else{
			for(int i=size-1;i>=0;i--){
				list.add(bis.get(i));
			}
		}
		return list;
	}

	public static BusinessInf getBusinessInfByBusinessCode(String businessCode) throws SQLException {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		BusinessInf bi=null;
		
		con = DBUtil.open();
		String sql="select * from business_inf where business_code=?";
		stmt=con.prepareStatement(sql);
		stmt.setString(1, businessCode);
		rs=stmt.executeQuery();

		if(rs.next()){
			String company_id=rs.getString("company_id");
			String business_code=rs.getString("business_code");
			String customer_name=rs.getString("customer_name");
			String date=rs.getString("date");
			String salesman=rs.getString("salesman");
			String remaks=rs.getString("remaks");
			String invoice_code=rs.getString("invoice_code");
			String charge_finish=rs.getString("charge_finish");
			String invoice_title=rs.getString("invoice_title");
			int invoice_money=Integer.parseInt(rs.getString("invoice_money"));
			String dateFinish=rs.getString("date_finish");
			String fileName=rs.getString("file_name");
			
			bi=new BusinessInf(company_id, business_code, customer_name, date, salesman, remaks, invoice_code, charge_finish, invoice_title, invoice_money,dateFinish,fileName);
		}
		DBUtil.close(rs, stmt, con);
		return bi;
	}


}
