<%@page import="dao.ServiceMaintainDao"%>
<%@page import="entity.ServiceMaintain"%>
<%@page import="dao.UserDao"%>
<%@page import="entity.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>addCompanyMaintain</title>
<link href="outer.css" rel="stylesheet" type="text/css">
</head>
<body style="background:#F0F8FF;">

<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>添加公司页面</b></font></p>

<div style="margin-left:50px;">
<!-- <h1>添加公司维护页面</h1> -->
<form action="addCompanyMaintain.do" method="post">
公司代号：<input type="text" name="company_id" /><br><br>
公司名：<input type="text" name="company_name" /><br><br>
<input class="button orange middle" type="submit" value="提交"/>&nbsp;
<a href="companyMaintain.do" class="button orange middle">返回</a>
</form>
</div>
</body>
</html>