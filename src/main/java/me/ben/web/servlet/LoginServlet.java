package me.ben.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ben.app.bean.User;
import me.ben.app.dao.UserDAO;
import me.ben.util.CookieUtil;
/**
 * 
 * @author Administrator
 *[2015-10-18] 使用加密方式存储cookie,@see CookieUtil
 */
public class LoginServlet extends HttpServlet{
		//public static final String COOKIE_USERNAME="username";
		//public static final String COOKIE_DOMAIN="CookieWeb";	//not used yet, need consider expire date
		//public static final int TWOWEEKS=60*60*24*7*2;
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			String username=req.getParameter("username");
			String password=req.getParameter("password");
			String passwordDB = new UserDAO().selectUserByUsername(username).getPassword();
			if(password.equals(passwordDB)){
				/*
				//登录成功， 存cookie
				Cookie cookie = new Cookie(COOKIE_USERNAME,username);
				cookie.setMaxAge(TWOWEEKS);	//两周的过期时间
				resp.addCookie(cookie);
				resp.sendRedirect("welcome.jsp?msg=Login successfully, Welcome " + username);
				*/
				CookieUtil.saveCookie(new User(username,password), resp);
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
