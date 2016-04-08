<%@page import="dao.UserDao"%>
<%@page import="entity.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="outer.css" rel="stylesheet" type="text/css">

</head>

<body style="background:#F0F8FF;">

<!-- <h1>修改用户页面</h1> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>修改用户页面</b></font></p>

<div style="margin-left:50px;">
<%
User user=(User)request.getAttribute("user");
String stopflag=user.getStopflag();
String type=user.getType()+"";
%>
<form action="updateUser.do" method="post"><br>
用户名：<input type="text" name="username" value=<%=user.getUserName() %> /><br><br>
密码：<input type="password" name="pwd" value=<%=user.getPassword() %> /><br><br>

<!-- 用户类型 -->
<%if("1".equals(type)){%>
用户类型：
<select name="type">
<option value="1" selected="selected">业务员</option>
<option vlaue="2">财务</option>
<option value="3">管理员</option>
</select><br><br>
<%}else if("2".equals(type)){%>
用户类型：
<select name="type">
<option value="1">业务员</option>
<option vlaue="2" selected="selected">财务</option>
<option value="3">管理员</option>
</select><br><br>
<%}else if("3".equals(type)){%>
用户类型：
<select name="type">
<option value="1">业务员</option>
<option vlaue="2">财务</option>
<option value="3" selected="selected">管理员</option>
</select><br><br>
<%}%>

真实姓名：<input type="text" name="realname" value=<%=user.getRealName() %> /><br><br>

<!-- 用户使用状态 -->
<%if("1".equals(stopflag)){//启用状态 %>
用户控制：
<select name="stopflag">
<option value="1" selected="selected">启用</option>
<option value="0">停用</option>
</select><br><br>
<%}else if("0".equals(stopflag)){ //停用状态%>
用户控制：
<select name="stopflag">
<option value="1">启用</option>
<option value="0" selected="selected">停用</option>
</select><br><br>
<%} %>

<input type="hidden" name="id" value=<%=user.getId() %>  /><br>

<!-- <input type="submit" value="提交"/> -->
<input class="button orange middle" type="submit" value="提交" >

<a href="manageUser.do" class="button orange middle">返回</a>
</form>

</div>
</body>
</html>