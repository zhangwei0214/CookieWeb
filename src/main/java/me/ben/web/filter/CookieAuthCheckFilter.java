package me.ben.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ben.util.CookieUtils;

public class CookieAuthCheckFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//如果是login页面或登录验证请求 则不去检查cookie
		String uri = req.getRequestURI();
		//  uri="/CookieWeb/"
		if(uri.endsWith("/") || uri.endsWith("/login.do") || uri.endsWith("/logout.do") ||uri.indexOf("login.jsp") >=0){
			//no need verify here , return
			chain.doFilter(request, response);
			return;
		}
		
		//url need validate cookie
		try{
		CookieUtils.validateCredentialCookie(req);
		}
		catch(Exception e){
			//如果有任何错误,clearCookie
			CookieUtils.clearCookie((HttpServletResponse)response);
			resp.sendRedirect("login.jsp?msg=Filter Reject Request, cookie error: " + e.getMessage());
			return;
		}
		
		//No Exception happens, chain continue...
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
