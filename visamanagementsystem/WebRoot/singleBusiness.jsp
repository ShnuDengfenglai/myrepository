
<%@page import="entity.User"%>
<%@page import="entity.ServiceContent"%>
<%@page import="dao.ServiceContentDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.BusinessInf"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单详细信息</title>
<link href="outer.css" rel="stylesheet" type="text/css">

</head>
<body style="background:#F0F8FF;">

<!-- <h1>查看详细订单信息</h1> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>查看详细订单信息</b></font></p>
<%
User user=(User)request.getSession().getAttribute("user");
int type=user.getType();
BusinessInf bi=(BusinessInf)request.getAttribute("bi");
List<ServiceContent> services=ServiceContentDAO.getServicesByBusinessCode(bi.getBusiness_code());
String bc=bi.getBusiness_code();
String invoiceCode=bi.getInvoice_code();
String invoiceTitle=bi.getInvoice_title();
String invoiceMoney=bi.getInvoice_money()+"";
String chargeState=bi.getCharge_finish();
String cn=bi.getCustomer_name();
String rmks=bi.getRemaks();
if(cn.length()>12){
cn=cn.substring(0, 12)+"..";
}

if(rmks.length()>5){
rmks=rmks.substring(0,5)+"..";
}
 %>
<!-- 订单列表的表格部分 -->
<form action="checkFinish.do" method="post">

<!-- 财务 -->
<%if(type==2){//财务可以改 %>
<!-- 发票编号 -->
<%if(invoiceCode==null){//没有发票编号%>
发票编号：<input type="text" name="invoice_code" style="width:220px;"/><br>
<%}else{//有发票编号%>
发票编号：<input type="text" name="invoice_code" style="width:220px;" value="<%=invoiceCode%>"/><br>
<%} %>

<!-- 发票抬头 -->
<%if(invoiceTitle==null){//没有发票抬头%>
发票抬头：<input type="text" name="invoice_title" style="width:220px;" readonly="readonly"/><br>
<%}else{//有发票抬头%>
发票抬头：<input type="text" name="invoice_title" style="width:220px;" value="<%=invoiceTitle%>" readonly="readonly"/><br>
<%} %>

<!-- 发票金额 -->
发票金额：<input type="text" name="invoice_money" style="width:220px;" value="<%=invoiceMoney%>"  readonly="readonly" /><br>

<!--付款状态-->
<%if("到账".equals(chargeState)){//到帐%>
付款状态：<select name="charge_finish">
<option value="到帐" selected="selected">到帐</option>
<option value="未到账">未到账</option>
</select><br><br>
<%}else{//未到账%>
付款状态：<select name="charge_finish">
<option value="到账">到帐</option>
<option value="未到账" selected="selected">未到账</option>
</select><br><br>
<%} %>

<!-- 业务员 -->
<%}else if(type==1||type==3){//业务员，不能改 %>
<!-- 发票编号 -->
<%if(invoiceCode==null){//没有发票编号%>
发票编号：<input type="text" name="invoice_code" style="width:220px;" readonly="readonly"/><br>
<%}else{//有发票编号%>
发票编号：<input type="text" name="invoice_code" style="width:220px;" value="<%=invoiceCode%>" readonly="readonly"/><br>
<%} %>

<!-- 发票抬头 -->
<%if(invoiceTitle==null){//没有发票抬头%>
发票抬头：<input type="text" name="invoice_title" style="width:220px;" value="null"/><br>
<%}else{//有发票抬头%>
发票抬头：<input type="text" name="invoice_title" style="width:220px;" value="<%=invoiceTitle%>"/><br>
<%} %>

<!-- 发票金额 -->
发票金额：<input type="text" name="invoice_money" style="width:220px;" value="<%=invoiceMoney%>" readonly="readonly" /><br>

<!--付款状态-->
<%if("到帐".equals(chargeState)){//到帐%>
付款状态：<select name="charge_finish" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
<option value="到帐" selected="selected">到帐</option>
<option value="未到账">未到账</option>
</select><br><br>
<%}else{//未到账%>
付款状态：<select name="charge_finish" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
<option value="到帐">到帐</option>
<option value="未到账" selected="selected">未到账</option>
</select><br><br>
<%} %>
<%} %>



<table border="1" cellspacing="0" cellpadding="5" width="100%">
<!-- 第一行 -->
<tr>
<td><b>公司代号</b></td> <td><b>业务编号</b></td> <td><b>姓名</b></td> <td><b>办理日期</b></td> <td><b>办理内容</b></td> <td><b>政府收费</b></td> <td><b>服务费</b></td> <td><b>特殊收费</b></td> <td><b>办理总费用</b></td> <td><b>业务员</b></td> <td><b>备注</b></td> 
</tr>

 

<tr>
<td><%=bi.getCompany_id() %></td> <td><%=bc%></td> <td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></td> <td><%=bi.getDate()%></td> 


<!-- 多项服务内容的展开 -->
<!-- 服务内容-->
<!-- 业务员和管理员 -->
<%if(type==1||type==3){//业务员和管理员，可以勾选 %>
<td>
 <table>
 <%for(ServiceContent service:services){ 
 String content=service.getContent();
 if(content.length()>15){
 content=content.substring(0, 15)+"..";
 }%>
 <tr><td><a title="<%=service.getContent()%>"><%=content%></a>
 <!-- 若finish值为1则复选框默认为勾选状态，否则为不勾选状态 -->
 <% 
 if(service.getFinish()==1){
 %>
 <input type="checkbox" checked="checked" name="finish" value=<%=service.getBusiness_code()+"/"+service.getContent()%> />
 <% }else{%>
 <input type="checkbox" name="finish" value=<%=service.getBusiness_code()+"/"+service.getContent()%> />
<% }%>
 </td></tr>
 <%} %>
 </table>
</td> 

<!-- 财务 -->
<%}else if(type==2){//财务，不可以勾选 %>
<td>
 <table>
 <%for(ServiceContent service:services){ 
 String content=service.getContent();
 if(content.length()>15){
 content=content.substring(0, 15)+"..";
 }%>
 <tr><td><a title="<%=service.getContent()%>"><%=content%></a>
 <!-- 若finish值为1则复选框默认为勾选状态，否则为不勾选状态 -->
 <% 
 if(service.getFinish()==1){
 %>
 <input onclick="return false;" type="checkbox" checked="checked" name="finish" value=<%=service.getBusiness_code()+"/"+service.getContent()%> />
 <% }else{%>
 <input onclick="return false;" type="checkbox" name="finish" value=<%=service.getBusiness_code()+"/"+service.getContent()%> />
<% }%>
 </td></tr>
 <%} %>
 </table>
</td> 
<%} %>

<!-- 政府收费-->
<td>
<table>
 <%for(ServiceContent service:services){ %>
 <tr><td><%=service.getGov_charge()+"元"%></td></tr>
 <%} %>
 </table>
</td> 

<!-- 服务费-->
<td>
<table>
 <%for(ServiceContent service:services){ %>
 <tr><td><%=service.getSer_charge()+"元"%></td></tr>
 <%} %>
 </table>
</td> 

<!-- 特殊收费-->
<td>
<table>
 <%for(ServiceContent service:services){ %>
 <tr><td><%=service.getSpe_charge()+"元"%></td></tr>
 <%} %>
 </table>
</td> 

<!-- 办理总费用 -->
<td>
<table>
 <%for(ServiceContent service:services){ %>
 <tr><td><%=(service.getSpe_charge()+service.getSer_charge()+service.getGov_charge())+"元"%></td></tr>
 <%} %>
 </table>
</td> 


<td><%=bi.getSalesman() %></td> <td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td>  
</tr>

</table><br>

订单开始日期：<%=bi.getDate() %><br>
订单结束日期：<%=bi.getDateFinish() %><br><br>

<!-- 返回按钮-->
<!-- <a href="list.do"><input class="button orange middle" type="button"  value="返回"/></a>&nbsp; -->
<a href="list.do" class="button orange middle" >返回</a>&nbsp;

<!-- 提交按钮 -->
<input class="button orange middle" type="submit" value="提交"/>
<!-- 隐藏按钮传递business_code的值 -->
<input type="hidden" name="hide" value=<%=bi.getBusiness_code() %> />
</form>
</body>
</html>