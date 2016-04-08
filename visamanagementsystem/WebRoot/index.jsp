<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
  	<link href="outer.css" rel="stylesheet" type="text/css">
    <base href="<%=basePath%>">
    
    <title>登陆页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
	#div1 {
	width: 300px;
	height: 114px;
	margin-top: 160px;
	margin-left: 327px;
	padding: 30px 10px;
	text-align: center; 
	vertical-align: middle;
}

#submit1 {
	margin-right: 140px;
	width: 50px;
	height: 30px;
}
	
	</style>
  </head>
  
  <body style="background:url(images/login3.png);" />
  <!-- 用户名和密码输入框 -->
  <div id="div1">
  <form action="login.do" method="post"/>
用户名：<input type="text" name="name"/><br/><br/>
   
密码:<input  type="password" name="pwd"/><br/><br/>
 
<input id="submit1" class="button blue middle" type="submit" value="登录 "/>  
 </form>
 </div>
  </body>
</html>


