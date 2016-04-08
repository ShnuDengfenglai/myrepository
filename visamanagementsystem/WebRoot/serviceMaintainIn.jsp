<%@page import="dao.ServiceMaintainInDao"%>
<%@page import="entity.ServiceMaintainIn"%>
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
<title>serviceMaintainIn</title>

<style type="text/css">
        .tr_w{background-color: #c0e0f7;  }
        .tr_g{background-color: #defcfe;  }
       
</style>

<link href="outer.css" rel="stylesheet" type="text/css">

<script src="jquery-1.8.3.min.js" type="text/javascript"></script>

<script type="text/javascript">
        $(function () {
            table_tr();
        });
        function table_tr(){
            $("tr:odd").addClass("tr_g"); //先排除第一行,然后给奇数行添加样式
            $("tr:even").addClass("tr_w"); //先排除第一行,然后给偶数行添加样式
        }
</script>



</head>
<body style="background:#F0F8FF;">

<!-- <h1>入境服务内容维护页面</h1> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>入境服务内容维护页面</b></font></p>

<!-- <div style="margin-left:50px;"> -->
<div style="margin:0 auto;width:650px;" >


<!-- 用户列表-->
<table border="0" cellspacing="1" cellpadding="5" width="650px" >
<!-- 表头 -->
<tr>
	<td bgcolor="#B0C4DE"><b>服务内容</b></td> 
	<td bgcolor="#B0C4DE"><b>政府收费</b></td> 
	<td bgcolor="#B0C4DE"><b>服务收费</b></td> 
	<td bgcolor="#B0C4DE"><b>特殊收费</b></td> 
	<td bgcolor="#B0C4DE"><b>操作1</b></td>
 	<td bgcolor="#B0C4DE"><b>操作2</b></td>
</tr>

<%
List<ServiceMaintainIn> sms=(List<ServiceMaintainIn>)request.getAttribute("sms");
if(sms==null){
sms=ServiceMaintainInDao.getServiceMaintainIns();
}
for(ServiceMaintainIn sm:sms){ %>
<tr>
 	<td><%=sm.getContent() %></td> 
 	<td><%=sm.getGovCharge() %></td>
 	<td><%=sm.getSerCharge() %></td>
   	<td><%=sm.getSpeCharge() %></td> 
   	<td><a href="preUpdateServiceMaintainIn.do?id=<%=sm.getId()%>" class="button blue small">修改</a></td> 
   	<td><a href="deleteServiceMaintainIn.do?id=<%=sm.getId()%>" class="button blue small">删除</a></td>
</tr>
<%} %>
</table><br>

<a href="addServiceMaintainIn.jsp" class="button orange middle" >添加服务项</a>&nbsp;
<a href="manageUser.do" class="button orange middle">返回</a>&nbsp;
</div>
</body>
</html>