<%@ page language="java" contentType="text/html; pageEncoding=UTF-8"%>
<%@ page session="false" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome to Cookie Test Project</title>
</head>
<body>
Message:<%=request.getParameter("msg") %>

<a href="logout.do">Logout</a>
</body>
</html>