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

<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>添加用户页面</b></font></p>
<div style="margin-left:50px;">
<!-- <h1>添加用户页面</h1> -->

<form action="addUser.do" method="post"><br>
用户名：<input type="text" name="username"/><br><br>
密码：<input type="password" name="pwd"/><br><br>
用户类型：
<select name="type">
<option value="1">业务员</option>
<option value="2">财务</option>
<option value="3">管理员</option>
</select>
<br><br>
真实姓名：<input type="text" name="realname"/><br><br>

<input class="button orange middle" type="submit" value="提交"/>
<a href="manageUser.do" class="button orange middle">返回</a>
</form>

</div>
</body>
</html>