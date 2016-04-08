<%@page import="entity.NoticeList"%>
<%@page import="dao.CompanyDao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="dao.ServiceContentDAO"%>
<%@page import="entity.ServiceContent"%>
<%@page import="java.util.List"%>
<%@page import="entity.BusinessInf"%>
<%@ page language="java" contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>noticeHistory</title>

<link href="outer.css" rel="stylesheet" type="text/css">
</head>
<body>
<div  style=" margin-left:250px;  width:800px; ">

<%
List<BusinessInf> bis=(List<BusinessInf>)request.getAttribute("bis");
NoticeList nl=(NoticeList)request.getAttribute("nl");
//System.out.println(nl);
String comName=nl.getCusComName();
String salesman=nl.getSalesman();
String companyChineseName=nl.getComChName();
String companyEnglishName=nl.getComEnName();
String userName=nl.getUserName();
String account=nl.getAccount();
String bankName=nl.getBankName();

int govSum=0;
int serSum=0;
int speSum=0;
 %>
 
<h2 style="text-align:center;" ><%=companyChineseName %></h2>
<h2 style="text-align:center;" ><%=companyEnglishName %></h2>
<h1 style="text-align:center;">付款通知书</h1>
<!-- 致XX公司 -->
<p><b>致
<input value="<%=comName %>" type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:100px;"" />
公司</b></p>
<p>下表为我司为贵司办理的服务明细单，服务内容及费用，请核对后及时汇款致我司。</p><br>
<table border="1" cellspacing="0" cellpadding="5" width="800" height="450"  style="">
<!-- 表格第一行 -->
<tr>
<td><b>业务编号</b></td> <td><b>姓名</b></td> <td><b>办理内容</b></td> <td><b>政府收费</b></td> <td><b>服务费</b></td> <td><b>总计</b></td>
</tr>

<%
for(BusinessInf bi:bis){
List<ServiceContent> services=ServiceContentDAO.getServicesByBusinessCode(bi.getBusiness_code());
String cn=bi.getCustomer_name();
if(cn.length()>25){
cn=cn.substring(0,25)+"..";
}
 %>
<tr>
<td><%=bi.getBusiness_code()%></td> <td><%=cn %></td>

<!-- 多项服务内容的展开 -->
<!-- 服务内容-->
<td style="padding:0px;">
 <table border="1"  cellspacing="0" cellpadding="5" style="border:none;height:100%;width:100%;">
 <%for(ServiceContent service:services){ %>
 <tr><td><%=service.getContent()%>
 </td></tr>
 <%} %>
 </table>
</td> 

<!-- 政府收费-->
<td style="padding:0px;">
 <table border="1"  cellspacing="0" cellpadding="5" style="border:none;height:100%;width:100%;">
 <%for(ServiceContent service:services){ 
 govSum+=service.getGov_charge();
 %>
 <tr><td><%=service.getGov_charge()+"元"%></td></tr>
 <%} %>
 </table>
</td> 

<!-- 服务费-->
<td style="padding:0px;">
 <table border="1"  cellspacing="0" cellpadding="5" style="border:none;height:100%;width:100%;">
 <%for(ServiceContent service:services){ 
 serSum+=service.getSer_charge();
 speSum+=service.getSpe_charge();
 %>
 <tr><td><%=service.getSer_charge()+service.getSpe_charge()+"元"%></td></tr>
 <%} %>
 </table>
</td> 

<!-- 办理总费用 -->
<td style="padding:0px;">
 <table border="1"  cellspacing="0" cellpadding="5" style="border:none;height:100%;width:100%;">
 <%for(ServiceContent service:services){ %>
 <tr><td><%=(service.getSpe_charge()+service.getSer_charge()+service.getGov_charge())+"元"%></td></tr>
 <%} %>
 </table>
</td> 
</tr>
<%
}
 %>

</table><br><br>
<%
int sum=govSum+serSum+speSum;
 %>

 其中政府收费：<input type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:100px;" value="<%=govSum %>    元" />  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      
 服务费：    <input type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:100px;" value="<%=serSum+speSum %>    元" /><br><br>     
总计：<input type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:100px;" value="<%=sum %>    元" />
<br><br>

<!-- 左下角位置 -->
<p>我司银行户名：<%=userName %></p>
<p>我司银行账号：<%=account %></p>
<p>我司银行开户行：<%=bankName %></p>
<p>请于见票后7日内汇款上述总计金额至我公司账号，谢谢贵公司合作！</p><br>
<p>业务员 ：<%=salesman %></p>

<!-- 右下角 -->
<p style="text-align:right;"><%=companyChineseName %></p>
<%
String time_now=nl.getProduceTime();
String[] arr=time_now.split("-");
String year=arr[0];
String month=arr[1];
String day=arr[2];
 %>
<p style="text-align:right;">
<input value="<%=year %>" type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:50px;"" />年
<input value="<%=month %>" type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:50px;"" />月
<input value="<%=day %>" type="text" style="border-top:0px;border-left:0px;border-right:0px;border-bottom:2px solid;width:50px;"" />日
</p>

<a href="noticeList.do" class="button orange middle">返回</a>&nbsp;
</div>

</body>
</html>