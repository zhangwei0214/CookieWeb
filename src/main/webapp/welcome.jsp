<%@ page language="java" contentType="text/html;  charset=UTF-8;" pageEncoding="UTF-8"%>
<%@ page import="me.ben.util.CookieUtils"%>
<%@ page session="false" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome to Cookie Test Project</title>
</head>
<body>
<%
//获取当前登录的用户名
String username = CookieUtils.getLoginUser(request).getUserName();
%>
当前登录用户:<%=username %>

<a href="logout.do">Logout</a>
</body>
</html>