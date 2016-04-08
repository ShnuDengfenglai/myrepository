<%@page import="dao.CompanyDao"%>
<%@page import="entity.Company"%>
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
<title>companyMaintain</title>

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

<!-- <h1>服务内容维护页面</h1> -->
<p class="p bordered" style="text-align:center"><font color="black" size=5px ><b>公司维护页面</b></font></p>

<!-- <div style="margin-left:50px;"> -->
<div style="margin:0 auto;width:650px;" >


<!-- 用户列表-->
<table border="0" cellspacing="1" cellpadding="5" width="650px" >
<!-- 表头 -->
<tr>
	<td bgcolor="#B0C4DE"><b>公司代号</b></td> 
	<td bgcolor="#B0C4DE"><b>公司名</b></td> 
	<td bgcolor="#B0C4DE"><b>操作1</b></td>
 	<td bgcolor="#B0C4DE"><b>操作2</b></td>
</tr>

<%
List<Company> companies=(List<Company>)request.getAttribute("companies");
if(companies==null){
companies=CompanyDao.getCompanies();
}
for(Company company:companies){ %>
<tr>
 	<td><%=company.getCompanyId() %></td> 
 	<td><%=company.getCompanyName()%></td>
   	<td><a href="preUpdatecompanyMaintain.do?id=<%=company.getId()%>" class="button blue small" >修改</a></td> 
   	<td><a href="deleteCompanyMaintain.do?id=<%=company.getId()%>" class="button blue small">删除</a></td>
</tr>
<%} %>
</table><br>

<a href="addCompanyMaintain.jsp" class="button orange middle">添加公司</a>&nbsp;
<a href="manageUser.do" class="button orange middle">返回</a>&nbsp;
</div>
</body>
</html>