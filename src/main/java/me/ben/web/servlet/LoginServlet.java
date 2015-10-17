package me.ben.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{
		public static final String COOKIE_USERNAME="username";
		public static final String COOKIE_DOMAIN="CookieWeb";	//not used yet, need consider expire date
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			String username=req.getParameter("username");
			String password=req.getParameter("password");
			if(username.equals(password)){
				//登录成功， 存cookie
				Cookie cookie = new Cookie(COOKIE_USERNAME,username);
				resp.addCookie(cookie);
				resp.sendRedirect("welcome.jsp?msg=Login successfully, Welcome " + username);
			}else{
				//登录失败 ，  提示
				resp.sendRedirect("welcome.jsp?msg=Login Failed, username&password not match!");
			}
			
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(req, resp);
		}
}
