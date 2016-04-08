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
<%
User u=(User)session.getAttribute("user");
 %>
 <!-- 登录用户显示和注销按钮 -->
&nbsp;&nbsp;<input type="text" style="border:none;" readonly="readonly" value="<%="欢迎您，"+u.getRealName()+" !"%>"/>
<a href="cancelSession.do">注销</a>

<!-- <p class="p bordered" style="text-align:center"><font color="#606060" >订单管理页面</font></p> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>用户列表页面</b></font></p>

<div style="margin-left:300px;width:750px;" >

<!-- 用户列表-->
<table border="0" cellspacing="1" cellpadding="5" width="750px" >
<!-- 表头 -->

<tr>
	 <!-- <td bgcolor="#B0C4DE"><b>ID</b></td> -->
	 <td bgcolor="#B0C4DE"><b>用户名</b></td>
	 <td bgcolor="#B0C4DE"><b>密码</b></td>
	 <td bgcolor="#B0C4DE"><b>用户类型</b></td> 
	 <td bgcolor="#B0C4DE"><b>真实姓名</b></td> 
	 <td bgcolor="#B0C4DE"><b>是否启用</b></td>
	 <td bgcolor="#B0C4DE"><b>操作1</b></td> 
	 <td bgcolor="#B0C4DE"><b>操作2</b></td>
</tr>

<%List<User> users=(List<User>)request.getAttribute("users");
if(users==null){
users=UserDao.getUsers();
}
for(User user:users){ 
String stopflag=user.getStopflag();
String userType=user.getType()+"";
if("1".equals(stopflag)){//可以使用
stopflag="启用";
}else if("0".equals(stopflag)){//已经停用
stopflag="停用";
}

if("1".equals(userType)){
userType="业务员";
}else if("2".equals(userType)){
userType="财务";
}else if("3".equals(userType)){
userType="管理员";
}
%>
<tr>
	<td><%=user.getUserName() %></td>
 	<td><%=user.getPassword() %></td> 
 	<td><%=userType %></td> 
 	<td><%=user.getRealName() %></td> 
 	<td><%=stopflag %></td> 
 	<td><a href="preUpdateUser.do?id=<%=user.getId()%>" class="button blue small" >修改 </a></td> 
 	<td><a href="deleteUser.do?id=<%=user.getId()%>" class="button blue small" >删除</a></td>
 </tr>
<%} %>
</table><br>

<a href="addUser.jsp" class="button orange middle" >添加</a>
<a href="list.do" class="button orange middle" >进入订单列表页面</a>
<a href="serviceMaintain.do" class="button orange middle" >出境服务内容维护页面</a>
<a href="serviceMaintainIn.do" class="button orange middle" >入境服务内容维护页面</a>
<a href="companyMaintain.do" class="button orange middle" >进入公司维护页面</a>
</div>
</body>
</html>