<%@page import="entity.Company"%>
<%@page import="java.util.List"%>
<%@page import="dao.CompanyDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>搜索公司名</title>
<link href="outer.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function doInput() {
		var inputContent = document.getElementById("input").value;

		var div = document.getElementById("div");
		var spans = div.getElementsByTagName("span");
		for ( var i = 0; i < spans.length; i++) {
			var span = spans[i];
			var content = span.innerHTML;
			var result = content.indexOf(inputContent);
			if (result == -1) {
				span.style.display = "none";
			} else {
				span.style.display = "block";
			}

		}
	}

	function doClick(id) {
		var span = document.getElementById(id);
		var input = document.getElementById("input");
		var con = span.innerHTML.split("|")[1];
		input.value = con;
	}
	
	function doClick1() {
		var parent=window.opener;
		var companyName=parent.document.getElementById("companyName");
		var input=document.getElementById("input");
		companyName.value=input.value;
		setTimeout("self.close()",0);
	}
</script>
</head>

<body style="background:#F0F8FF;">

<form id="myForm">
	<div style="margin: 0 auto;width:400px;">
		<%
			List<Company> coms = CompanyDao.getCompanies();
		%>

		搜索[<input id="input" type="text" oninput="doInput()">]<br>

		<div id="div">
			<%
				for (Company com : coms) {
			%>
			<span id="<%=com.getId()%>" style="display:block;cursor:pointer;"
				onclick="doClick('<%=com.getId()%>')"><%=com.getCompanyName()%>|<%=com.getCompanyId()%></span>
			<%
				}
			%>
			<input class="button orange middle" type="button" onclick="doClick1()"  value="提交">
		</div>
	</div>
	</form>
</body>
</html>