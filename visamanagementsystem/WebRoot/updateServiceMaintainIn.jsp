<%@page import="entity.ServiceMaintainIn"%>
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
<title>Insert title here</title>
<link href="outer.css" rel="stylesheet" type="text/css">

</head>
<body style="background:#F0F8FF;">

<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>修改服务维护页面</b></font></p>
 
<div style="margin-left:50px;">
<!-- <h1>修改服务维护页面</h1> -->
<%
ServiceMaintainIn sm=(ServiceMaintainIn)request.getAttribute("sm");
%>
<form action="updateServiceMaintainIn.do" method="post"><br>
服务内容：<input type="text" name="c" value=<%=sm.getContent() %> /><br><br>
政府收费：<input type="text" name="gov" value=<%=sm.getGovCharge() %> /><br><br>
服务费：<input type="text" name="ser" value=<%=sm.getSerCharge() %> /><br><br>
特殊收费：<input type="text" name="spe" value=<%=sm.getSpeCharge() %> /><br><br>
<input type="hidden" name="id"  value=<%=sm.getId() %> />

<!-- <input type="submit" value="提交"/> -->
<input class="button orange middle" type="submit" value="提交" >
<a href="serviceMaintainIn.do" class="button orange middle">返回</a>
</form>

</div>
</body>
</html>