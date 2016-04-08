<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="outer.css" rel="stylesheet" type="text/css">
</head>
<body>
<br><br>

<form action="notice.do" method="post">
公司名字:
<select name="company_chinese_name">
<option value="上海新鹜岛出入境服务有限公司">上海新鹜岛出入境服务有限公司</option>
<option value="上海新鹜岛投资咨询有限公司">上海新鹜岛投资咨询有限公司</option>
</select>
<br><br>

银行户名：
<select name="user_name">
<option value="上海新鹭岛出入境服务有限公司">上海新鹭岛出入境服务有限公司</option>
<option value="上海新鹭岛投资咨询有限公司">上海新鹭岛投资咨询有限公司</option>
<option value="陆文珺">陆文珺</option>
</select><br><br>

<input type="submit" value="提交" />&nbsp;<a href="list.do" class="button orange middle" >返回</a>
</form>
</body>
</html>