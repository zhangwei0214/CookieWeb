<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="me.ben.util.CookieUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
<%
//如果用户已经登录 -> welcome.jsp
String username = CookieUtils.getLoginUser(request).getUserName();
if(!StringUtils.isBlank(username)){
	response.sendRedirect("welcome.jsp");
	return;
}
String msg = request.getParameter("msg");
boolean isBlank = StringUtils.isBlank(msg);



%>
<c:if test="${isBlank==false}">通过
MSG:<%=msg%>
</c:if>
<h2>请登录</h2>  
  
  <form method="POST" action="login.do">
   Login UserName: <input type="text" name="username"><br>  
   Login Password: <input type="password" name="password" ><br>  
   <input type="submit" value="Send"><br>  
  <form> 
</body>
</html>