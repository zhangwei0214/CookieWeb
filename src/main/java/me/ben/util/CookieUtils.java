package me.ben.util;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;  
import org.apache.commons.lang3.StringUtils;

import me.ben.app.bean.User;
import me.ben.app.dao.UserDAO;

//import org.apache.commons.codec.binary.Base64;  

public class CookieUtils {

	// 保存cookie时的cookieName

	private final static String cookieDomainName = "CookieWeb";

	// 加密cookie时的网站自定码 deferred
	//private final static String webKey = "123456";

	// 设置cookie有效期是两个星期，根据需要自定义
	private final static int cookieMaxAge = 60 * 60 * 24 * 7 * 2;

	// 保存Cookie到客户端-------------------------------------------------------------------------

	// 在CheckLogonServlet.java中被调用

	// 传递进来的user对象中封装了在登陆时填写的用户名与密码
	public static void saveCookie(User user, HttpServletResponse response) {

		// cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);

		//passwordMd5
		// MD5加密密码
		String passwordMd5 = getMD5(user.getPassword());

		// 将要被保存的完整的Cookie值
		String cookieValue = user.getUserName() + ":" + validTime + ":"
				+ passwordMd5;

		// 再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = new String(Base64.encodeBase64(cookieValue.getBytes()));

		// 开始保存Cookie
		Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);

		// 存两年(这个值应该大于或等于validTime)
		cookie.setMaxAge(cookieMaxAge);
		// cookie有效路径是网站根目录
		cookie.setPath("/");

		// 向客户端写入
		response.addCookie(cookie);
	}

	// 读取Cookie,自动完成登陆操作----------------------------------------------------------------

	/**
	 * check cookie中保存的用户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void validateCredentialCookie(HttpServletRequest request)
			throws Exception {
		// 如果cookieValue为空,返回,
		String cookieValue= getCookieValue(request, cookieDomainName);
		if (StringUtils.isBlank(cookieValue)) {
			//要求filter必须处理这个异常
			//自定义异常  添加errCode?
			throw new Exception("Cache value not Found");
		}

		// 先得到的CookieValue进行Base64解码
		//String cookieValueAfterDecode64 = new String(Base64.decodeBase64(cookieValue), "utf-8");
		String cookieValueAfterDecode64 = new String(Base64.decodeBase64(cookieValue));
		
		// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
		String cookieValues[] = cookieValueAfterDecode64.split(":");

		if (cookieValues.length != 3) {
			throw new Exception("Cache value's format is invalid!");
		}

		// 判断是否在有效期内,过期就删除Cookie
		long validTimeInCookie = new Long(cookieValues[1]);

		if (validTimeInCookie < System.currentTimeMillis()) {
			throw new Exception("Cache is out of date, deleted it, please login again!");
		}

		// 取出cookie中的用户名,并到数据库中检查这个用户名
		String username = cookieValues[0];
		// 根据用户名到数据库中检查用户是否存在
		UserDAO ud = new UserDAO();

		User user = ud.selectUserByUsername(username);

		// 如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
		if(user == null){
			throw new Exception("User not exist in DB,removed by Administrator?");
		}
		
		String passwordMd5Cookie = cookieValues[2];

		String passwordMd5DB = getMD5(user.getPassword());

		// 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
		if (passwordMd5DB.equals(passwordMd5Cookie)) {
			//不写session, 本应用不用session
			//HttpSession session = request.getSession(true);
			//session.setAttribute("user", user);
			//chain.doFilter(request, response);
			
			//everything is ok here, return back to filter
			return;
		}
	}

	// 用户注销时,清除Cookie,在需要时可随时调用-----------------------------------------------------

	public static void clearCookie(HttpServletResponse response) {

		Cookie cookie = new Cookie(cookieDomainName, null);

		cookie.setMaxAge(0);

		cookie.setPath("/");

		response.addCookie(cookie);

	}
	
	/*
	 * to be done
	 * 从request中解析出来用户名
	 */
	public static User getLoginUser(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		String cookieValue="";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookieDomainName.equals(cookies[i].getName())) {
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}

		String cookieValueAfterDecode64="";
		//不清楚什么是要要用encoding参数
		//cookieValueAfterDecode64 = new String(Base64.decodeBase64(cookieValue),"utf-8");
		cookieValueAfterDecode64 = new String(Base64.decodeBase64(cookieValue));

		// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆

		String cookieValues[] = cookieValueAfterDecode64.split(":");
		
		String username = cookieValues[0];

		// 根据用户名到数据库中检查用户是否存在
		UserDAO ud = new UserDAO();

		User user = ud.selectUserByUsername(username);

		return user;
	}

	/**
	 * zhangwei add 2015-10-18
	 * @param value
	 * @return
	 */
	private static String getMD5(String str) {
		return DigestUtils.md5Hex(str);
	}
	
	private static String getCookieValue(HttpServletRequest request,String cookieDomainName) {

		// 根据cookieName取cookieValue
		Cookie[] cookies = request.getCookies();

		String cookieValue = null;

		if (cookies != null) {

			for (int i = 0; i < cookies.length; i++) {

				if (cookieDomainName.equals(cookies[i].getName())) {
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}
		
		return cookieValue;
	}

	
	// 获取Cookie组合字符串的MD5码的字符串----[Deferred]------------------------------------------------------------
	/*
		private static String getMD5(String value) {
			
			String result = null;

			try {

				byte[] valueByte = value.getBytes();

				MessageDigest md = MessageDigest.getInstance("MD5");

				md.update(valueByte);

				result = toHex(md.digest());

			} catch (NoSuchAlgorithmException e) {

				e.printStackTrace();

			}

			return result;

		}

		// 将传递进来的字节数组转换成十六进制的字符串形式并返回
		private static String toHex(byte[] buffer) {

			StringBuffer sb = new StringBuffer(buffer.length * 2);

			for (int i = 0; i < buffer.length; i++) {

				sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));

				sb.append(Character.forDigit(buffer[i] & 0x0f, 16));

			}

			return sb.toString();

		}
		*/
}