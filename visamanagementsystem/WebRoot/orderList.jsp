<%@page import="dao.BusinessInNoticeDao"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="util.TypeSortUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.User"%>
<%@page import="entity.ServiceContent"%>
<%@page import="dao.ServiceContentDAO"%>
<%@page import="java.util.List"%>
<%@page import="dao.BusinessInfDAO"%>
<%@page import="entity.BusinessInf" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单列表页面</title>

<link href="outer.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function doClick1(){
 var newwin = window.open("searchCom.jsp","_blank","width=500,height=550,left=400,scrollbars=yes");
}

</script>
<script language=javascript src="My97DatePicker/WdatePicker.js"></script>
</head>
<body style="background:#F0F8FF;font-size:15px;">  
<%
User user=(User)session.getAttribute("user");
int type=user.getType();
 %>
 <!-- 登录用户显示和注销按钮 -->
<input type="text" style="border:none;background-color:#F0F8FF;" readonly="readonly" value="<%="欢迎您，"+user.getRealName()+" !"%>"/>
<a href="cancelSession.do">注销</a>

<!-- <h1>订单列表页面</h1>  -->  
 <p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>订单列表页面</b></font></p>

<!-- 检索功能 的表单部分 -->
<form action="search.do" method="post">
<%
List<String> states=(List<String>)request.getAttribute("states");
 %>
 <!-- 下拉菜单选择完成状态 -->
 订单状态：
<select name="statement" style="width:140px;">
<%for(String state:states){%>
<option value="<%=state%>"><%=state %></option>
<%} %>
</select>&nbsp;&nbsp;

公司代号:<input id="companyName" type="text" name="company_id"/><input type="button" value="...." style="width:25px;" onclick="doClick1()"/>&nbsp;
业务编号:<input type="text" name="business_code"/>&nbsp;
客户姓名：<input type="text" name="customer_name"/>&nbsp;

<!-- 默认查询前两个月至今的订单 -->
<%
String beforeDate=(String)session.getAttribute("beforeDate");
String nowDate=(String)session.getAttribute("nowDate");
 %>
办理日期：<input class="Wdate" name="date1" type="text" onClick="WdatePicker()" width='270px' value="<%=beforeDate%>">-<input class="Wdate" name="date2" type="text" onClick="WdatePicker()" width='270px' value="<%=nowDate%>">&nbsp;

<!-- 办理总费用查询 -->
<%if(type==2){//财务%>
办理总费用：<input type="text"  name="sumOfCharges" />&nbsp;
<%} %>

<!-- 业务员姓名查询-->
<%
if(type==1){%><!-- 业务员 -->
<input type="text" hidden="hidden" name="salesman" value="<%=user.getRealName()%>"/>&nbsp;
<% }else if(type==2||type==3){%><!-- 财务处和管理员-->
业务员姓名：<input type="text" name="salesman"/>&nbsp;
<%} %>

<!-- 发票编号-->
发票编号：<input type="hidden" name="invoice_code" style="width:220px;"/>&nbsp;

<!-- 付款状态-->
付款状态：<select name="charge_finish">
<option value="所有">所有</option>
<option value="到账">到帐</option>
<option value="未到账">未到账</option>
</select>
<%if(type==2){%>
&nbsp;发票抬头：<input type="text" name="invoice_title" />
&nbsp;发票金额：<input type="text" name="invoice_money" style="width:80px;"/>
&nbsp;备注：<input type="text" name="remarks" style="width:80px;"/>
<%} %>

<!-- 查询按钮-->
<!-- <input type="submit" value="查询">&nbsp; --><input class="button orange middle" type="submit" value="查询" >
</form><br>

<form action="selectBusi.do" method="post">
<!-- 订单列表的表格部分 -->
<table border="0" cellspacing="1" cellpadding="5" width="100%">
<!-- 第一行 -->
<%if(type==2){//财务 %>
<tr>
	<td bgcolor="#B0C4DE"><center><b>选择</b></center></td>
	<td bgcolor="#B0C4DE"><center><b>公司代号</b></center></td>
 	<td bgcolor="#B0C4DE"><center><b>业务编号</b></center></td>
  	<td bgcolor="#B0C4DE"><center><b>姓名</b></center></td> 
  	<td bgcolor="#B0C4DE"><center><b>办理日期</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>办理总费用</b></center></td> 
    <td bgcolor="#B0C4DE"><center><b>业务员</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>发票编号</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>发票抬头</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>发票金额</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>付款状态</b></center></td>
     <td bgcolor="#B0C4DE"><center><b>备注</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>操作</b></center></td> 
</tr>
<%}else if(type==1||type==3){//业务员和管理员%>
<tr>
	<td bgcolor="#B0C4DE"><center><b>选择</b></center></td>
	<td bgcolor="#B0C4DE"><center><b>公司代号</b></center></td>
 	<td bgcolor="#B0C4DE"><center><b>业务编号</b></center></td>
  	<td bgcolor="#B0C4DE"><center><b>姓名</b></center></td> 
  	<td bgcolor="#B0C4DE"><center><b>办理日期</b></center></td>
   	<td bgcolor="#B0C4DE"><center><b>办理内容</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>政府收费</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>服务费</b></center></td> 
    <td bgcolor="#B0C4DE"><center><b>特殊收费</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>办理总费用</b></center></td> 
    <td bgcolor="#B0C4DE"><center><b>业务员</b></center></td> 
    <td bgcolor="#B0C4DE"><center><b>备注</b></center></td> 
    <td bgcolor="#B0C4DE"><center><b>发票抬头</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>付款状态</b></center></td>
    <td bgcolor="#B0C4DE"><center><b>操作</b></center></td> 
</tr>
<%} %>


<%
List<BusinessInf> bis=(List<BusinessInf>)request.getAttribute("bis");
if(bis==null){
bis=BusinessInfDAO.getBusinessInfs();
}
 %>
 
<%
for(BusinessInf bi:bis){
String busiCode=bi.getBusiness_code();
List<ServiceContent> services=ServiceContentDAO.getServicesByBusinessCode(busiCode);
//判断该业务订单是否已经生成过付费通知书，若已经生成过则返回值为true.
boolean produceNotice=BusinessInNoticeDao.ifBusinessCodeBelongToBins(busiCode);
String cn=bi.getCustomer_name();
String rmks=bi.getRemaks();
String account=bi.getCharge_finish();
if(cn.length()>7){
cn=cn.substring(0,7)+"..";
}

if(rmks.length()>5){
rmks=rmks.substring(0,5)+"..";
}

String content=null;
int gov_charges=0;
int ser_charges=0;
int spe_charges=0;
int sum=0;
if(!(services==null)){
content=services.get(0).getContent()+"...";
gov_charges=0;
ser_charges=0;
spe_charges=0;
sum=0;

for(ServiceContent service:services){
gov_charges+=service.getGov_charge();
ser_charges+=service.getSer_charge();
spe_charges+=service.getSpe_charge();
}

sum=gov_charges+ser_charges+spe_charges;
}else{
content="服务内容为空！！！";
gov_charges=0;
ser_charges=0;
spe_charges=0;
sum=0;
}

int statement=ServiceContentDAO.judgeBusinessStatement(services);
//System.out.println(statement);
 %>
 
<!-- 订单显示不同颜色 -->
<%if("到账".equals(account)){//显示到帐%>
 <tr style="background-color:#80f40c;">
 <td><input type="checkbox" name="selectedBis" value="<%=bi.getBusiness_code() %>" /></td>
<%if(type==3){//管理员%>
<td><%=bi.getCompany_id() %></td>
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>|<a href="delete.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >删除</a>
<%}else if(type==2){//财务%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><%=bi.getInvoice_code()%></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getInvoice_money()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td><%=bi.getRemaks()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a  class="button gray small" >编辑</a>|<a  class="button gray small" >删除</a>
<%}else if(type==1){//业务员%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|
<!-- 处理该订单是否生成过通知书，生成过则不能再编辑。 -->
<%if(produceNotice){//该订单生成过通知书%>
<a class="button gray small" >编辑</a>
<%}else{%>
<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>
<%} %>
|<a  class="button gray small" >删除</a>
<%} %> 
</td>
</tr>

 <%}else if(statement==0){//一项都没有完成%>
 <tr style="background-color:#FF6347;">
 <td><input type="checkbox" name="selectedBis" value="<%=bi.getBusiness_code() %>" /></td>
<%if(type==3){//管理员%>
<td><%=bi.getCompany_id() %></td>
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>|<a href="delete.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >删除</a>
<%}else if(type==2){//财务%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><%=bi.getInvoice_code()%></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getInvoice_money()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td><%=bi.getRemaks()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a  class="button gray small" >编辑</a>|<a  class="button gray small" >删除</a>
<%}else if(type==1){//业务员%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|
<!-- 处理该订单是否生成过通知书，生成过则不能再编辑。 -->
<%if(produceNotice){//该订单生成过通知书%>
<a class="button gray small" >编辑</a>
<%}else{%>
<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>
<%} %>
|<a  class="button gray small" >删除</a>
<%} %> 
</td>
</tr>

 <% }else if(statement==1){//至少完成一项，但没有全部完成%>
 <tr style="background-color:#0e80f2;">
 <td><input type="checkbox" name="selectedBis" value="<%=bi.getBusiness_code() %>" /></td>
<%if(type==3){//管理员%>
<td><%=bi.getCompany_id() %></td>
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>|<a href="delete.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >删除</a>
<%}else if(type==2){//财务%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><%=bi.getInvoice_code()%></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getInvoice_money()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td><%=bi.getRemaks()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a  class="button gray small" >编辑</a>|<a  class="button gray small" >删除</a>
<%}else if(type==1){//业务员%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|
<!-- 处理该订单是否生成过通知书，生成过则不能再编辑。 -->
<%if(produceNotice){//该订单生成过通知书%>
<a class="button gray small" >编辑</a>
<%}else{%>
<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>
<%} %>
|<a  class="button gray small" >删除</a>
<%} %> 
</td>
</tr>

 <%}else if(statement==2){//全部都完成%>
 <tr >
 <td><input type="checkbox" name="selectedBis" value="<%=bi.getBusiness_code() %>" /></td>
<%if(type==3){//管理员%>
<td><%=bi.getCompany_id() %></td>
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>|<a href="delete.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >删除</a>
<%}else if(type==2){//财务%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><%=bi.getInvoice_code()%></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getInvoice_money()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td><%=bi.getRemaks()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|<a  class="button gray small" >编辑</a>|<a  class="button gray small" >删除</a>
<%}else if(type==1){//业务员%>
<td><%=bi.getCompany_id() %></td> 
<td><%=bi.getBusiness_code()%></td> 
<td><a title="<%=bi.getCustomer_name()%>"><%=cn %></a></a></td> 
<td><%=bi.getDate()%></td> 
<td><%=content%></td> 
<td><%=gov_charges%></td> 
<td><%=ser_charges%></td> 
<td><%=spe_charges%></td> 
<td><%=sum%></td> 
<td><%=bi.getSalesman() %></td> 
<td><a title="<%=bi.getRemaks()%>"><%=rmks%></a></td> 
<td><%=bi.getInvoice_title()%></td> 
<td><%=bi.getCharge_finish()%></td> 
<td>
<a href="check.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >查询</a>|
<!-- 处理该订单是否生成过通知书，生成过则不能再编辑。 -->
<%if(produceNotice){//该订单生成过通知书%>
<a class="button gray small" >编辑</a>
<%}else{%>
<a href="preEditBusiness.do?business_code=<%=bi.getBusiness_code()%>" class="button blue small" >编辑</a>
<%} %>
|<a  class="button gray small" >删除</a>
<%} %> 
</td>
</tr>
<%}%>

<%
}
 %>
</table><br>
<input type="submit" value="提交">
</form>

 <p style="color:red;">(红色显示表示订单还未开始处理，蓝色表示至少完成一项服务内容，白色表示订单已经全部完成，绿色表示该订单已经到帐。)</p>
<!-- 添加按钮-->
<%if(type==1||type==3){//业务员%>
<a href="addNewBus.jsp" class="button orange middle" >添加</a>&nbsp;&nbsp;&nbsp;
<%}else if(type==2){//财务%>
<!-- <a href="javascript:;"><input type="button" disabled="true" value="添加"/></a> --><a href="javascript:;"><input class="button gray middle" type="button" disabled="true" value="添加" ></a>&nbsp;&nbsp;&nbsp;
<%}%>

<%
session.setAttribute("bis1", bis);
 %>

 <!-- 生成付款通知书按钮 -->
<% if(type==2){//财务%>
<a class="button gray middle">生成付款通知书</a>
 <%}else if(type==1||type==3){//业务员和管理员%>
<a href="chooseCompany.jsp" class="button orange middle" >生成付款通知书</a>
 <%}%>

 <!-- 返回到用户列表按钮 -->
<% if(type==1||type==2){%>
<input class="button gray middle" type="button" value="返回到用户列表" >
 <%}else if(type==3){%>
 <a  href="manageUser.do" class="button orange middle">返回到用户列表页面</a>
 <%}%>
 
<!--  进入到通知书列表页面 -->
 <%if(type==2||type==3){ %>
 <a  href="noticeList.do" class="button orange middle" >进入通知书列表页面</a>
 <% } %>
</body>
</html>