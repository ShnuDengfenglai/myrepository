<%@page import="dao.ServiceMaintainInDao"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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

<!-- <h1>填写新订单信息页面</h1> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>添加新订单页面</b></font></p>

<!--在表单外面加一层div整理移动表单-->
<div style="margin-left:20px;">
<!--添加业务的表单-->
<form action="addNewBus.do" method="post">

<!-- 从子窗口中获取公司代号 -->
公司代号:<input id="companyName" type="text" name="company_id" /><input type="button" value="...." style="width:25px;" onclick="searchCom()"/><br/>

<!-- 业务编号：<input type="text" name="business_code"/><br/> -->
客户姓名：<input type="text" name="customer_name" style="width:220px;"/><br/>
办理日期：<input type="text" name="date" onClick="WdatePicker()" width='270px' /><br/><br/>

<!-- 服务内容部分 -->
<%
List<ContentSelect> css=ServiceMaintainDao.getContentSelects();
List<ContentSelect> csis=ServiceMaintainInDao.getContentSelectsIn();
 %>
<b>请勾选需要的服务内容:</b> &nbsp;<input type="button" value="出境" onclick="outport()">  &nbsp;<input type="button" value="入境" onclick="inport()">

<!-- 服务内容列表 -->
<div id="div1">
<%for(ContentSelect cs:css){ %>
<span  style="display: none;"><input type="checkbox" name="content" value="<%=cs.getAbbr()%>"/><%=cs.getContent()%>&nbsp;（政府收费:<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"gov" %>" value=<%=cs.getGovCharge() %>>,服务费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"ser" %>" value=<%=cs.getSerCharge() %>>,特殊收费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"spe" %>" value=<%=cs.getSpeCharge() %>>）</span> 
<%} %>
</div>
<div id="div2">
<%for(ContentSelect cs:csis){ %>
<span  style="display: none;"><input type="checkbox" name="content" value="<%=cs.getAbbr()%>"/><%=cs.getContent()%>&nbsp;（政府收费:<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"gov" %>" value=<%=cs.getGovCharge() %>>,服务费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"ser" %>" value=<%=cs.getSerCharge() %>>,特殊收费：<input style="width:60px;" type="text" name="<%=cs.getAbbr()+"spe" %>" value=<%=cs.getSpeCharge() %>>）</span> 
<%} %>
</div>


<br/>
<!-- 自动填写业务员姓名 -->
<%User user=(User)session.getAttribute("user");
int type=user.getType();
String realname=user.getRealName();
if(type==1){ %>
业务员姓名：<input type="text" name="salesman" value="<%=realname %>" readonly="readonly"/><br/>
<%}else if(type==2||type==3){ %>
业务员姓名：<input type="text" name="salesman" /><br/>
<%} %>

备注：<input type="text" name="remaks"/><br/>

<input class="button orange middle" type="submit" value="提交">

<a href="list.do" class="button orange middle" >返回</a>
</form>
</div>
</body>
</html>