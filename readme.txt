项目描述:
1)用来测试使用Cookie来保存用户的登录信息
2)对JSP的访问默认会创建session， 这里要注意通过HTTP请求测试 HTTP Header中是否包含JsessionId


未完成的增强:
1)Cookie加密来增加安全性 [未完成]
2)设置Cookie的有效期
3)通过session listener 监控session的创建

reference:
http://blog.csdn.net/hongshan50/article/details/9180073


Test:
http://localhost:8080/CookieWeb/
1)用户名密码一致即可登录
