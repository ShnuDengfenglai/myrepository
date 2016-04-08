<%@page import="dao.ServiceMaintainInDao"%>
<%@page import="dao.ServiceContentDAO"%>
<%@page import="entity.ServiceContent"%>
<%@page import="entity.BusinessInf"%>
<%@page import="dao.ServiceMaintainDao"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="entity.User"%>
<%@page import="util.TypeSortUtil"%>
<%@page import="entity.ContentSelect"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link href="outer.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function searchCom(){
 var newwin = window.open("searchCom.jsp","_blank","width=500,height=550,left=400,scrollbars=yes");
}

function outport(){
var div1=document.getElementById("div1");
var spans1=div1.getElementsByTagName("span");
var div2=document.getElementById("div2");
var spans2=div2.getElementsByTagName("span");
for(var i=0;i<spans1.length;i++){
var span1=spans1[i];
span1.style.display="block";
}
for(var i=0;i<spans2.length;i++){
var span2=spans2[i];
span2.style.display="none";
}
}

function inport(){
var div1=document.getElementById("div1");
var spans1=div1.getElementsByTagName("span");
var div2=document.getElementById("div2");
var spans2=div2.getElementsByTagName("span");
for(var i=0;i<spans1.length;i++){
var span1=spans1[i];
span1.style.display="none";
}
for(var i=0;i<spans2.length;i++){
var span2=spans2[i];
span2.style.display="block";
}
}

</script>
<script language=javascript src="My97DatePicker/WdatePicker.js"></script>
</head>

<body style="background:#F0F8FF;">
<!-- <h1>修改旧订单页面</h1> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>修改旧订单页面</b></font></p>

<!--在表单外面加一层div整理移动表单-->
<div style="margin-left:20px;">
<!--添加业务的表单-->
<%
BusinessInf bi=(BusinessInf)request.getAttribute("bi");
List<ServiceContent> scs=(List<ServiceContent>)request.getAttribute("scs");
 %>
<form action="updateOldBus.do?business_code=<%=bi.getBusiness_code() %>" method="post">

<!-- 从子窗口中获取公司代号 -->
公司代号:<input id="companyName" type="text" name="company_id" value="<%=bi.getCompany_id() %>" /><input type="button" value="...." style="width:25px;" onclick="searchCom()"/><br/>

<!-- 业务编号：<input type="text" name="business_code"/><br/> -->
客户姓名：<input type="text" name="customer_name" style="width:220px;" value="<%=bi.getCustomer_name() %>"/><br/>
办理日期：<input type="text" name="date" onClick="WdatePicker()" width='270px' value="<%=bi.getDate() %>" /><br/><br/>

<!-- 服务内容部分 -->
<%
List<ContentSelect> css=ServiceMaintainDao.getContentSelects();
List<ContentSelect> cssIn=ServiceMaintainInDao.getContentSelectsIn();
 %>
<b>请勾选需要的服务内容:</b> &nbsp;<input type="button" value="出境" onclick="outport()">  &nbsp;<input type="button" value="入境" onclick="inport()">

<div id="div1">
<%for(ContentSelect cs:css){
ServiceContent sc=ServiceContentDAO.ifCSBelongToSCs(cs, scs);
if(!(sc==null)){%>
<span style="display:none;"><input type="checkbox" name="content" value="<%=cs.getAbbr()%>" checked="checked"/><%=cs.getContent()%>&nbsp;（政府收费:<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"gov" %>" value=<%=sc.getGov_charge() %>>,服务费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"ser" %>" value=<%=sc.getSer_charge() %> >,特殊收费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"spe" %>" value=<%=sc.getSpe_charge() %>>）</span>
<%}else{%>
<span style="display:none;"><input type="checkbox" name="content" value="<%=cs.getAbbr()%>"/><%=cs.getContent()%>&nbsp;（政府收费:<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"gov" %>" value=<%=cs.getGovCharge() %>>,服务费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"ser" %>" value=<%=cs.getSerCharge() %>>,特殊收费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"spe" %>" value=<%=cs.getSpeCharge() %>>）</span>
<%}%>

<%} %>
</div>
<div id="div2">
<%for(ContentSelect cs:cssIn){
ServiceContent sc=ServiceContentDAO.ifCSBelongToSCs(cs, scs);
if(!(sc==null)){%>
<span style="display:none;"><input type="checkbox" name="content" value="<%=cs.getAbbr()%>" checked="checked"/><%=cs.getContent()%>&nbsp;（政府收费:<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"gov" %>" value=<%=sc.getGov_charge() %>>,服务费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"ser" %>" value=<%=sc.getSer_charge() %> >,特殊收费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"spe" %>" value=<%=sc.getSpe_charge() %>>）</span>
<%}else{%>
<span style="display:none;"><input type="checkbox" name="content" value="<%=cs.getAbbr()%>"/><%=cs.getContent()%>&nbsp;（政府收费:<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"gov" %>" value=<%=cs.getGovCharge() %>>,服务费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"ser" %>" value=<%=cs.getSerCharge() %>>,特殊收费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"spe" %>" value=<%=cs.getSpeCharge() %>>）</span>
<%}%>

<%} %>
</div>

<br/>
<!-- 自动填写业务员姓名 -->
<%User user=(User)session.getAttribute("user");
int type=user.getType();
String realname=user.getRealName();
if(type==1){ %>
业务员姓名：<input type="text" name="salesman" value="<%=realname %>" readonly="readonly"/><br/>
<%}else if(type==3){ %>
业务员姓名：<input type="text" name="salesman" value="<%=bi.getSalesman() %>" /><br/>
<%} %>
备注：<input type="text" name="remaks" value="<%=bi.getRemaks() %>"/><br/><br>

<input type="submit" class="button orange middle"  value="提交">

<a href="list.do" class="button orange middle" >返回</a>
</form>
</div>
</body>
</html>