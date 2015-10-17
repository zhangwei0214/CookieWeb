package me.ben.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

public class LogoutServlet extends HttpServlet{
		public static final String COOKIE_USERNAME="username";
		public static final String COOKIE_DOMAIN="CookieWeb";	//not used yet, need consider expire date
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			
				Cookie cookie = new Cookie(COOKIE_USERNAME,null);
				//0 	立即删除 
				//负数 	浏览器关闭时自动删除
				cookie.setMaxAge(0);
				//cookie.setMaxAge(-1);
				resp.addCookie(cookie);
				resp.sendRedirect("login.jsp?msg=Logout successfully!");
				HttpSession session = req.getSession(false);	//默认的getSession() = getSession(true); getSession(false) 不自动创建session
				if(session != null)
					session.invalidate();
			
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(req, resp);
		}
}
