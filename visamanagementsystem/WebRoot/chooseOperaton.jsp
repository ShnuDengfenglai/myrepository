<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>chooseOperation</title>
</head>

<%
User user=(User)request.getSession().getAttribute("user");
int type=user.getType();
 %>
<body>
<div  style=" width:400px;margin:0 auto; margin-top:50px;">
<form action="submitOperation.do" method="post">

<b>请选择您的操作：</b><br><br>
<%if(type==1){//业务员%>
<input type="radio" name="operation" value="operation1" checked="checked" > 显示勾选订单<br><br>
<input type="radio" name="operation" value="operation3" > 统一输入发票抬头（业务员）<br><br>
<%}else if(type==2){//财务%>
<input type="radio" name="operation" value="operation1" checked="checked"> 显示勾选订单<br><br>
<input type="radio" name="operation" value="operation2" > 统一输入发票编号、发票金额、付款状态（财务）<br><br>
<%}else if(type==3){//管理员%>
<input type="radio" name="operation" value="operation1" checked="checked"> 显示勾选订单<br><br>
<input type="radio" name="operation" value="operation2" > 统一输入发票编号、发票金额、付款状态（财务）<br><br>
<input type="radio" name="operation" value="operation3" > 统一输入发票抬头（业务员）<br><br>
<%}%>

<input type="submit" value="提交">

</form>
</div>
</body>
</html>