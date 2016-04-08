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

<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>添加出境服务项页面</b></font></p>
 
<div style="margin-left:50px;">
<!-- <h1>添加内容维护信息</h1> -->

<form action="addServiceMaintain.do" method="post"><br>
服务内容：<input type="text" name="c"  /><br><br>
政府收费：<input type="text" name="gov"  /><br><br>
服务费：<input type="text" name="ser" /><br><br>
特殊收费：<input type="text" name="spe"  /><br><br>

<input class="button orange middle" type="submit" value="提交"/>
<a href="serviceMaintain.do" class="button orange middle">返回</a>
</form>

</div>
</body>
</html>