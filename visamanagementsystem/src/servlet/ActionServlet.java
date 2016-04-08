package servlet;

import java.io.File;
import java.io.IOException;


import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import util.TypeSortUtil;

import dao.BusinessInNoticeDao;
import dao.BusinessInfDAO;
import dao.CompanyDao;
import dao.NoticeListDao;
import dao.ObjectDao;
import dao.ServiceContentDAO;
import dao.ServiceMaintainDao;
import dao.ServiceMaintainInDao;
import dao.UserDao;
import entity.BusinessInf;
import entity.Company;
import entity.NoticeList;
import entity.ServiceContent;
import entity.ServiceMaintain;
import entity.ServiceMaintainIn;
import entity.User;

public class ActionServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String uri=request.getRequestURI();
		uri=uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));

		System.out.println("uri:"+uri);

		try {
			//反射法调用方法
			Class cl = Class.forName("servlet.ActionServlet");
			Method m=cl.getDeclaredMethod(uri, new Class[]{HttpServletRequest.class,HttpServletResponse.class});
			m.invoke(this,new Object[]{request,response});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==1){
				String businessCode=(String)request.getSession().getAttribute("business_code_file");
				PrintWriter out=response.getWriter();
				//file就是上传的文件。
				 File file=null ;
				 //maxFileSize：上传的文件最大是5M。
				   int maxFileSize = 5000 * 1024;
				   //maxMemSize：存储文件的最大内存。
				   int maxMemSize = 5000 * 1024;
				   //int maxMemSize = 120* 1024;
				   ServletContext context = getServletContext();
				   //filePath:上传文件保存的位置。
				   String filePath = context.getInitParameter("file-upload");

				   // 验证上传内容了类型
				   String contentType = request.getContentType();
				   if ((contentType.indexOf("multipart/form-data") >= 0)) {//请求是上传文件。
				      DiskFileItemFactory factory = new DiskFileItemFactory();
				      // 内存最大可缓存尺寸。
				      factory.setSizeThreshold(maxMemSize);
				      //指定数据超过内存最大可缓存尺寸时，临时文件目录。
				      factory.setRepository(new File("c:\\temp"));

				      // 创建一个新的文件上传处理程序
				      ServletFileUpload upload = new ServletFileUpload(factory);
				      // 设置最大上传的文件大小
				      upload.setSizeMax( maxFileSize );
				      try{ 
				         // 解析获取的文件
				         List fileItems = upload.parseRequest(request);

				         // 处理上传的文件
				         Iterator i = fileItems.iterator();

				         out.println("<html>");
				         out.println("<head>");
				         out.println("<title>JSP File upload</title>");  
				         out.println("</head>");
				         out.println("<body>");
				         while ( i.hasNext () ) 
				         {
				            FileItem fi = (FileItem)i.next();
				            if ( !fi.isFormField () )	//fi.isFormField是用来判断表单是不是普通类型。“！fi.isFormField”表示是file类型。
				            {
				            // fieldName表示获取的什么类型，在这里的值是file.
				            String fieldName = fi.getFieldName();
				            //System.out.println("fieldName:"+fieldName);
				            String fileName = fi.getName();
				            
				            boolean isInMemory = fi.isInMemory();
				            long sizeInBytes = fi.getSize();
				           // System.out.println(isInMemory+"  "+sizeInBytes);
				            
				            // 写入文件
				            if( fileName.lastIndexOf("\\") >= 0 ){//文件名里面有“\\”，这种情况没有理解。
				            file = new File( filePath , 
				            fileName.substring( fileName.lastIndexOf("\\"))) ;
				            //System.out.println("1."+fileName.substring( fileName.lastIndexOf("\\")));
				            }else{//文件名里面没有“\\”。
				            file = new File( filePath ,
				            fileName.substring(fileName.lastIndexOf("\\")+1)) ;
				            //System.out.println("2."+fileName.substring( fileName.lastIndexOf("\\")+1));
				            //将上传的文件名更新到数据库的对应订单中。
				            BusinessInfDAO.updateFilePathByBusinessCode(businessCode,fileName);
				            }
				            fi.write( file ) ;
				            out.println("Uploaded Filename: " + filePath + 
				            fileName + "<br>");
				            }
				         }
				         out.println("</body>");
				         out.println("</html>");
				      }catch(Exception ex) {
				         System.out.println(ex);
				      }
				   }else{
				      out.println("<html>");
				      out.println("<head>");
				      out.println("<title>Servlet upload</title>");  
				      out.println("</head>");
				      out.println("<body>");
				      out.println("<p>No file uploaded</p>"); 
				      out.println("</body>");
				      out.println("</html>");
				   }
			}
		}
		response.sendRedirect("list.do");
			
	}
	
	private void searchNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==2){
				String customCompany=request.getParameter("customCompany");
				List<NoticeList> nls=NoticeListDao.getNoticeListByCustomCompany(customCompany);
				//处理搜索条件为空的情况
				if("".equals(customCompany)){
					nls=null;					
				}
				
				//System.out.println("nls="+nls);
				request.getSession().setAttribute("nls", nls);				
				response.sendRedirect("noticeList.do");
			}
		}
	}
	
	private void deleteNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==2){
				String noticeCode=request.getParameter("noticeCode");
				NoticeListDao.deleteNoticeListByNoticeCode(noticeCode);
				response.sendRedirect("noticeList.do");
			}
		}
	}
	
	private void noticeHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==2){
				String noticeCode=request.getParameter("noticeCode");
				NoticeList nl=NoticeListDao.getNoticeListByNoticeCode(noticeCode);
				List<BusinessInf> bis=BusinessInNoticeDao.getBisByNoticeCode(noticeCode);
				request.setAttribute("nl", nl);
				request.setAttribute("bis", bis);
				request.getRequestDispatcher("/noticeHistory.jsp").forward(request, response);
			}
		}
	}
	
	private void noticeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==2){//财务与管理员
				List<NoticeList> nls = NoticeListDao.getNoticeLists();
				
				//根据条件搜索到的NoticeList集合
				List<NoticeList> noticeLists=(List<NoticeList>) request.getSession().getAttribute("nls");
				request.getSession().removeAttribute("nls");
				//表示有搜索到结果
				if(!(noticeLists==null)){
					nls=noticeLists;
				}
				
				request.setAttribute("nls", nls);
				request.getRequestDispatcher("/noticeList.jsp").forward(request, response);
			}
		}
	}
	
	private void submitNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==1){
				String comChName=request.getParameter("com_ch_name");
				String comEnName=request.getParameter("com_en_name");
				String cusComName=request.getParameter("cus_com_name");
				String userName=request.getParameter("user_name");
				String account=request.getParameter("account");
				String bankName=request.getParameter("bank_name");
				String salesman=request.getParameter("salesman");
				String produceTime=request.getParameter("produce_time");
				String invoiceMoney=request.getParameter("invoice_money");
				String noticeCode=request.getParameter("notice_code");
				List<BusinessInf> bis=(List<BusinessInf>) request.getSession().getAttribute("bis1");
				BusinessInfDAO.updateInvoiceMoney(bis, invoiceMoney);
				NoticeListDao.insertNotice(comChName,comEnName,cusComName,userName,account,bankName,salesman,bis,produceTime,noticeCode);
				response.sendRedirect("list.do");
			}
		}
	}
	
	private void companyMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				List<Company> companies=CompanyDao.getCompanies();
				request.setAttribute("companies", companies);
				request.getRequestDispatcher("/companyMaintain.jsp").forward(request, response);
			}
		}
	}
	
	private void updateInvoiceTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==1){
				String invoiceTitle=request.getParameter("invoiceTitle");
				System.out.println(invoiceTitle);
				String[] bcs=(String[]) request.getSession().getAttribute("bcs");
				System.out.println(bcs.length);
				request.getSession().removeAttribute("bcs");
				BusinessInfDAO.updateInvoiceTitle(bcs, invoiceTitle);
				response.sendRedirect("list.do");
			}
		}
	}
	
	private void updateInvoiceCodeMoneyState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==2){
				String invoiceCode=request.getParameter("invoiceCode");
//				String money=request.getParameter("invoiceMoney");
//				int invoiceMoney=Integer.parseInt(money);
				String invoiceState=request.getParameter("invoiceState");
				String[] bcs=(String[]) request.getSession().getAttribute("bcs");
				request.getSession().removeAttribute("bcs");
				BusinessInfDAO.updateInvoiceCodeMoneyState(bcs, invoiceCode, invoiceState);
				response.sendRedirect("list.do");
			}
		}
	}
	
	private void submitOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==1||type==2){
				String operation=request.getParameter("operation");
				if(operation.equals("operation1")){//显示勾选订单
					List<BusinessInf> bis=(List<BusinessInf>) request.getSession().getAttribute("bis1");
					request.getSession().removeAttribute("bis1");
					String[] bcs=(String[]) request.getSession().getAttribute("bcs");
					request.getSession().removeAttribute("bcs");
					bis=BusinessInfDAO.getSelectedBis(bis, bcs);
					request.getSession().setAttribute("selectedBis", bis);
					response.sendRedirect("list.do");
				}else if(operation.equals("operation2")){//统一输入发票编号、发票金额、付款状态
					request.getRequestDispatcher("/invoiceCodeMoneyState.jsp").forward(request, response);
				}else if(operation.equals("operation3")){//统一输入发票抬头
					request.getRequestDispatcher("/invoiceTitle.jsp").forward(request, response);
				}
				
			}
		}
	}
	
	private void selectBusi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==1||type==2){
				String[] bcs=request.getParameterValues("selectedBis");
				request.getSession().setAttribute("bcs", bcs);
				//判断是否一个都没有勾选
				if(bcs==null){//没有勾选
					response.sendRedirect("list.do");
				}else{//有勾选
					request.getRequestDispatcher("/chooseOperaton.jsp").forward(request, response);
				}
				
			}
		}
	}
	
	private void preEditBusiness(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3||type==1){
				String bc=request.getParameter("business_code");
				BusinessInf bi = BusinessInfDAO.findBusinessInfbyBusinessCode(bc);
				List<ServiceContent> scs = ServiceContentDAO.getServicesByBusinessCode(bc);
				request.setAttribute("bi", bi);
				request.setAttribute("scs", scs);
				request.getRequestDispatcher("/updateOldBus.jsp").forward(request, response);
			}
		}
	}

	private void addCompanyMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		PrintWriter out = response.getWriter();
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				String ci=request.getParameter("company_id");
				String cn=request.getParameter("company_name");
				boolean b=CompanyDao.insertCompanyInf(ci,cn);
				if(b==true){
					//ServiceMaintainDao.insertCompanyMaintains(ci,cn);
					response.sendRedirect("companyMaintain.do");
				}else{
					out.print("<h1 style='color:red;'>对不起,你插入的公司id或公司名已存在！</h1><br/><a href='addCompanyMaintain.jsp'><input type='button'  value='返回' /></a>");	
					out.close();
				}

			}
		}else{
			response.sendRedirect("index.jsp");
		}
	}

	private void addServiceMaintainIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				String content=request.getParameter("c");
				int gov=Integer.parseInt(request.getParameter("gov"));
				int ser=Integer.parseInt(request.getParameter("ser"));
				int spe=Integer.parseInt(request.getParameter("spe"));
				ServiceMaintainInDao.insertSerMaintainIn(content,gov,ser,spe);
				response.sendRedirect("serviceMaintainIn.do");
			}
		}
	}

	private void addServiceMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				String content=request.getParameter("c");
				int gov=Integer.parseInt(request.getParameter("gov"));
				int ser=Integer.parseInt(request.getParameter("ser"));
				int spe=Integer.parseInt(request.getParameter("spe"));
				ServiceMaintainDao.insertSerMaintain(content,gov,ser,spe);
				response.sendRedirect("serviceMaintain.do");
			}
		}
	}
	
	private void deleteCompanyMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				CompanyDao.deleteCompanyMaintainById(id);
				response.sendRedirect("companyMaintain.do");
			}
		}
	}
	
	private void deleteServiceMaintainIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				ServiceMaintainInDao.deleteServiceMaintainInById(id);
				response.sendRedirect("serviceMaintainIn.do");
			}
		}
	}

	private void deleteServiceMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				ServiceMaintainDao.deleteServiceMaintainById(id);
				response.sendRedirect("serviceMaintain.do");
			}
		}
	}

	private void updateCompanyMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				String companyId=request.getParameter("ci");
				String companyName=request.getParameter("cn");
				CompanyDao.updateComMaintain(id,companyId,companyName);
				response.sendRedirect("companyMaintain.do");
			}
		}
	}
	
	private void updateServiceMaintainIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				String content=request.getParameter("c");
				int gov=Integer.parseInt(request.getParameter("gov"));
				int ser=Integer.parseInt(request.getParameter("ser"));
				int spe=Integer.parseInt(request.getParameter("spe"));
				ServiceMaintainInDao.updateSerMaintainIn(id,content,gov,ser,spe);

				response.sendRedirect("serviceMaintainIn.do");
			}
		}
	}

	private void updateServiceMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				String content=request.getParameter("c");
				int gov=Integer.parseInt(request.getParameter("gov"));
				int ser=Integer.parseInt(request.getParameter("ser"));
				int spe=Integer.parseInt(request.getParameter("spe"));
				ServiceMaintainDao.updateSerMaintain(id,content,gov,ser,spe);

				response.sendRedirect("serviceMaintain.do");
			}
		}
	}
	
	private void preUpdatecompanyMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				Company com=CompanyDao.getCompanyById(id);

				request.setAttribute("company", com);
				request.getRequestDispatcher("/updateCompanyMaintain.jsp").forward(request, response);
			}
		}
	}
	
	private void preUpdateServiceMaintainIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				String id=request.getParameter("id");
				ServiceMaintainIn sm=ServiceMaintainInDao.getServiceMaintainInById(id);

				request.setAttribute("sm", sm);
				request.getRequestDispatcher("/updateServiceMaintainIn.jsp").forward(request, response);
			}
		}
	}

	private void preUpdateServiceMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				String id=request.getParameter("id");
				ServiceMaintain sm=ServiceMaintainDao.getServiceMaintainById(id);

				request.setAttribute("sm", sm);
				request.getRequestDispatcher("/updateServiceMaintain.jsp").forward(request, response);
			}
		}
	}
	
	private void serviceMaintainIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				List<ServiceMaintainIn> sms=ServiceMaintainInDao.getServiceMaintainIns();	
				request.setAttribute("sms",sms );
				request.getRequestDispatcher("/serviceMaintainIn.jsp").forward(request, response);
			}
		}
	}

	private void serviceMaintain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				List<ServiceMaintain> sms=ServiceMaintainDao.getServiceMaintains();	
				request.setAttribute("sms",sms );
				request.getRequestDispatcher("/serviceMaintain.jsp").forward(request, response);
			}
		}
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				String username=request.getParameter("username");
				String pwd=request.getParameter("pwd");
				int leixin=Integer.parseInt(request.getParameter("type"));
				String realname=request.getParameter("realname");
				String stopflag=request.getParameter("stopflag");
				UserDao.updateUser(id,username,pwd,leixin,realname,stopflag);
				response.sendRedirect("manageUser.do");
			}
		}
	}

	private void preUpdateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				User u = UserDao.getUserbyId(id);
				request.setAttribute("user", u);
				request.getRequestDispatcher("/updateUser.jsp").forward(request, response);
			}
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				int id=Integer.parseInt(request.getParameter("id"));
				UserDao.deleteUserByIdUser(id);
				response.sendRedirect("manageUser.do");
			}
		}
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User)request.getSession().getAttribute("user");
		if(!(user==null)){
			int type=user.getType();
			if(type==3){
				String username=request.getParameter("username");
				String pwd=request.getParameter("pwd");
				int leixin=Integer.parseInt(request.getParameter("type"));
				String realname=request.getParameter("realname");
				UserDao.insertUser(username,pwd,leixin,realname);
				response.sendRedirect("manageUser.do");
			}
		}
	}

	//管理用户的操作
	private void manageUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user = (User)request.getSession().getAttribute("user");
		if(user==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			System.out.println("用户为空");
		}else{
			System.out.println("用户不为空");
			List<User> users = UserDao.getUsers();
			request.setAttribute("users", users);
			request.getRequestDispatcher("/userList.jsp").forward(request, response);
		}

	}

	//生成付款通知书请求
	private void notice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User) request.getSession().getAttribute("user");
		int type=user.getType();
		PrintWriter out = response.getWriter();
		if(type==1||type==3){//业务员和管理员
			//查询条件筛选后的BusinessInf集合
			List<BusinessInf> bis=(List<BusinessInf>)request.getSession().getAttribute("bis1");
			String companyChineseName=request.getParameter("company_chinese_name");
			String userName=request.getParameter("user_name");
			String[] arr=ObjectDao.getAccountAndBankNameByUserName(userName);
			String companyEnglishName=ObjectDao.getCompanyEnglishNameByChineseName(companyChineseName);
			String account=arr[0];
			String bankName=arr[1];
			String noticeCode=NoticeListDao.generateNoticeCode();
			request.setAttribute("noticeCode", noticeCode);
			request.setAttribute("companyChineseName",companyChineseName );
			request.setAttribute("companyEnglishName", companyEnglishName);
			request.setAttribute("userName",userName );
			request.setAttribute("account",account );
			request.setAttribute("bankName", bankName);
			request.setAttribute("bis", bis);
			request.getRequestDispatcher("/notice.jsp").forward(request, response);
		}else if(type==2){//财务
			out.print("<h1 style='color:red;'>对不起，您没有权限生成付款通知书！</h1><br/><a href='list.do'><input type='button'  value='返回列表页面' /></a>");

		}
		out.close();
	}

	private void cancelSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		request.getSession().removeAttribute("user");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	//查询功能
	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		User user=(User) request.getSession().getAttribute("user");
		int type=user.getType();

		//下拉框选择订单状态
		String selectedState=request.getParameter("statement");

		String companyId=request.getParameter("company_id");
		String businessCode=request.getParameter("business_code");
		String customerName=request.getParameter("customer_name");
		String date1=request.getParameter("date1");
		String date2=request.getParameter("date2");
		String salesman=request.getParameter("salesman");
		String invoiceCode=request.getParameter("invoice_code");
		String chargeState=request.getParameter("charge_finish");

		if("所有".equals(chargeState)){
			chargeState="";			
		}
		
		List<BusinessInf> bis=null;
		if(type==1||type==3){//业务员和管理员
			bis=BusinessInfDAO.searchBusinessInfs(companyId,businessCode, customerName, date1, date2, salesman,invoiceCode,chargeState);
		}else{//财务
			int invoiceMoney=0;
			int sumOfCharges=0;
			String im=request.getParameter("invoice_money");
			String soc=request.getParameter("sumOfCharges");
			String invoiceTitle=request.getParameter("invoice_title");
			String remarks=request.getParameter("remarks");
			if(!"".equals(im)){
				invoiceMoney=Integer.parseInt(im);
			}
			if(!"".equals(soc)){
				sumOfCharges=Integer.parseInt(soc);
			}
			
			bis=BusinessInfDAO.searchBusinessInfs1(companyId, businessCode,customerName, date1, date2,sumOfCharges, salesman,invoiceCode,chargeState,invoiceTitle,remarks,invoiceMoney);
		}
		
		//根据选择的状态筛选订单
		bis=BusinessInfDAO.getSelectedBusinessInfs(selectedState,bis);

		request.setAttribute("bis", bis);
		//System.out.println("转发前");
		request.getRequestDispatcher("/list.do").forward(request, response);
		//System.out.println("转发后");
	}

	//勾选服务内容
	private void checkFinish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		PrintWriter out = response.getWriter();
		String[] str=request.getParameterValues("finish");
		String businessCode=request.getParameter("hide");
		String invoiceCode=request.getParameter("invoice_code");
		String invoiceTitle=request.getParameter("invoice_title");
		//System.out.println(invoiceTitle);
		int invoiceMoney=0;
		String im=request.getParameter("invoice_money");
		if(!"".equals(im)){
			invoiceMoney=Integer.parseInt(im);
		}
		String chargeFinish=request.getParameter("charge_finish");
		
		BusinessInfDAO.updateICAndCFByBC(businessCode,invoiceCode,chargeFinish,invoiceTitle,invoiceMoney);
		//System.out.println(businessCode);

		List<ServiceContent> services=ServiceContentDAO.getServicesByBusinessCode(businessCode);
		//System.out.println("提取service成功");
		//一个都没有勾选
		if(str==null){
			//System.out.println(str);
			for (ServiceContent sc : services) {
				String content=sc.getContent();
				ServiceContentDAO.updateFinish(businessCode, content, 0);
			}
			//至少有一个被勾选
		}else{
			for (ServiceContent sc : services) {
				String content=sc.getContent();
				boolean b=false;

				for(String s:str){
					String con=s.substring(s.indexOf("/")+1);
					if(content.equals(con)){
						b=true;
					}
				}
				//System.out.println(b);

				if(b){
					ServiceContentDAO.updateFinish(businessCode, content, 1);
					//System.out.println(1);
				}else{
					ServiceContentDAO.updateFinish(businessCode, content, 0);
					//System.out.println(0);
				}
			}
		}

//		out.print("<h1>提交成功！</h1><br/><a href='list.do'><input type='button'  value='返回列表页面' /></a>");
//
//		out.close();
		//System.out.println("排错1");
		request.getRequestDispatcher("/rebackOrderList.jsp").forward(request, response);
		//System.out.println("排错2");
	}

	private void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String businessCode=request.getParameter("business_code");
		//System.out.println(businessCode);
		BusinessInf bi = BusinessInfDAO.findBusinessInfbyBusinessCode(businessCode);
		request.setAttribute("bi", bi);
		System.out.println("转发钱");
		request.getRequestDispatcher("/singleBusiness.jsp").forward(request, response);

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String businessCode=request.getParameter("business_code");
		//System.out.println(businessCode);
		BusinessInfDAO.deleteBusinessInf(businessCode);
		ServiceContentDAO.deleteServices(businessCode);
		request.getRequestDispatcher("/list.do").forward(request, response);
		System.out.println("删除订单成功");
	}


	private void updateOldBus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User) request.getSession().getAttribute("user");

		if(user==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			//用户已登录
		}else {
			PrintWriter out = response.getWriter();
			int type=user.getType();
			String company_id=request.getParameter("company_id");
			String business_code=request.getParameter("business_code");
			//System.out.println("business_code="+business_code);

			String customer_name=request.getParameter("customer_name");
			String date=request.getParameter("date");
			String salesman=request.getParameter("salesman");
			String remaks=request.getParameter("remaks");

			if(type==2){//财务
				out.print("<h1 style='color:red;'>对不起，您没有权限添加订单！</h1><br/><a href='list.do'><input type='button'  value='返回列表页面' /></a>");
			}else if(type==1||type==3){//业务员
				BusinessInfDAO.insertOrder1(company_id,business_code, customer_name, date, salesman, remaks, request, response);
				request.getRequestDispatcher("/list.do").forward(request, response);
			}
			out.close();
		}
	}

	private void addNewBus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			//用户已登录
		}else {
			PrintWriter out = response.getWriter();
			int type=user.getType();
			String company_id=request.getParameter("company_id");
			String customer_name=request.getParameter("customer_name");
			String date=request.getParameter("date");
			String salesman=request.getParameter("salesman");
			String remaks=request.getParameter("remaks");

			if(type==2){//财务
				out.print("<h1 style='color:red;'>对不起，您没有权限添加订单！</h1><br/><a href='list.do'><input type='button'  value='返回列表页面' /></a>");
			}else if(type==1||type==3){//业务员
				BusinessInfDAO.insertOrder(company_id, customer_name, date, salesman, remaks, request, response);
				request.getRequestDispatcher("/UploadServletTest.jsp").forward(request, response);
			}
			out.close();
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//System.out.println("调用login方法");
		String userName=(String) request.getParameter("name");
		String passWord=(String) request.getParameter("pwd");
		//System.out.println(userName+", "+passWord);
		PrintWriter out=response.getWriter();

		if(UserDao.login(userName, passWord)){
			User user=UserDao.getUserbyNameAandPwd(userName, passWord);
			int type=user.getType();
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			if(type==3){//管理员
				response.sendRedirect("manageUser.do");
			}else{
				response.sendRedirect("list.do");
			}

		}else{
			out.print("<h1>登陆失败</h1>");
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

		User user = (User)request.getSession().getAttribute("user");
		if(user==null){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			//登录成功之后
		}else{
			int type=user.getType();
			List<BusinessInf> bis=null;
			SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			Date now=new Date();
			int m=now.getMonth()-2;
			Date before=new Date();
			before.setMonth(m);
			String nowDate=fmt.format(now);
			String beforeDate=fmt.format(before);
			List<String> states = TypeSortUtil.getTypeSort("完成的状态");

			if(type==1){//业务员
				String realName=user.getRealName();
				//默认搜索的后的bis
				bis=BusinessInfDAO.searchBusinessInfs("","", "", beforeDate, nowDate, realName,"","");
				//取bis中最新的七条bi，时间按照从新到久往下排列
				bis=BusinessInfDAO.getSomeOfBis(bis,7);
			}else if(type==2||type==3){//财务处
				//(String companyId,String businessCode,String customerName,String date1,String date2,String salesman, String invoiceCode, String chargeState,int invoiceMoney)
				bis=BusinessInfDAO.searchBusinessInfs1("", "","", beforeDate, nowDate, 0,"","","","","",0);
			}

			//处理来自search那边的请求数据
			List<BusinessInf> infs =(List<BusinessInf>) request.getAttribute("bis");
			if(!(infs==null)){
				bis=infs;
			}
			
			//处理来自复选框那边的请求数据
			List<BusinessInf> selectedBis =(List<BusinessInf>) request.getSession().getAttribute("selectedBis");
			if(!(selectedBis==null)){
				bis=selectedBis;
				request.getSession().removeAttribute("selectedBis");
			}

			//将集合存入session
			request.setAttribute("bis", bis);
			//将默认时间段存入session
			request.getSession().setAttribute("beforeDate", beforeDate);
			request.getSession().setAttribute("nowDate", nowDate);
			//获取完成状态
			request.setAttribute("states", states);

			//转发到订单列表页面
			request.getRequestDispatcher("/orderList.jsp").forward(request,response);
		}

	}


}
