<%@page import="entity.NoticeList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>noticeList</title>
<link href="outer.css" rel="stylesheet" type="text/css">
</head>
<body style="background:#F0F8FF;">
<%
List<NoticeList> nls=(List<NoticeList>)request.getAttribute("nls");
 %>
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>通知书列表页面</b></font></p>
<div style="margin:0 auto;width:1300px;font-size:10px;" >

<!-- 搜索条件 -->
<form action="searchNotice.do" method="post">
客户公司：<input type="text" name="customCompany" >&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" value="搜索"><br><br>
</form>

<table border="1" cellspacing="0" cellpadding="5" width="1300px" >
<!-- 表头 -->
<tr>
	<td bgcolor="#B0C4DE"><b>通知书代码</b></td> 
	<td bgcolor="#B0C4DE"><b>公司中文名</b></td> 
	<td bgcolor="#B0C4DE"><b>公司英文名</b></td> 
	<td bgcolor="#B0C4DE"><b>客户公司名</b></td> 
	<td bgcolor="#B0C4DE"><b>户名</b></td>
 	<td bgcolor="#B0C4DE"><b>账号</b></td>
 	<td bgcolor="#B0C4DE"><b>支行</b></td>
 	<td bgcolor="#B0C4DE"><b>业务员</b></td>
 	<td bgcolor="#B0C4DE"><b>提交时间</b></td>
 	<td bgcolor="#B0C4DE"><b>操作</b></td>
</tr>


<%for(NoticeList nl:nls){ %>
<tr>
	<td ><b><%=nl.getNoticeCode() %></b></td> 
	<td ><b><%=nl.getComChName() %></b></td> 
	<td ><b><%=nl.getComEnName() %></b></td> 
	<td ><b><%=nl.getCusComName() %></b></td> 
	<td ><b><%=nl.getUserName() %></b></td>
 	<td ><b><%=nl.getAccount() %></b></td>
 	<td ><b><%=nl.getBankName() %></b></td>
 	<td ><b><%=nl.getSalesman() %></b></td>
 	<td ><b><%=nl.getProduceTime() %></b></td>
 	<td width="100px">
 	<a href="noticeHistory.do?noticeCode=<%=nl.getNoticeCode()%>">查看</a>|
 	<a href="deleteNotice.do?noticeCode=<%=nl.getNoticeCode()%>">删除</a> 
 	</td>
</tr>
<%} %>
</table><br>
<a href="list.do" class="button orange middle">返回</a>
</div>
</p>

</body>
</html>