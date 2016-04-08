<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>invoiceCodeMoneyState</title>
</head>
<body>
<div  style=" width:400px;margin:0 auto; margin-top:50px;">
<form action="updateInvoiceCodeMoneyState.do" method="post">
发票编号：<input type="text" name="invoiceCode"><br><br>
<!-- 发票金额：<input type="hidden" name="invoiceMoney"><br><br> -->
付款状态：<input type="text" name="invoiceState"><br><br>
<input type="submit" valu="提交" />
</form>
</div>
</body>
</html>