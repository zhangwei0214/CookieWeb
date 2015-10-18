

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="me.ben.web.servlet.LoginServlet" %>
<%@ page session="false" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
MSG:<%=request.getParameter("msg")%>

<h2>请登录</h2>  
  
  <form method="POST" action="login.do">
   Login UserName: <input type="text" name="username"><br>  
   Login Password: <input type="password" name="password" ><br>  
   <input type="submit" value="Send"><br>  
  <form> 
</body>
</html>