项目描述:
1)用来测试使用Cookie来保存用户的登录信息
2)对JSP的访问默认会创建session， 这里要注意通过HTTP请求测试 HTTP Header中是否包含JsessionId
3)Cookie加密来增加安全性
4)设置Cookie的有效期
5)通过session listener 监控session的创建

reference:
http://blog.csdn.net/hongshan50/article/details/9180073


Test:
http://localhost:8080/CookieWeb/
1)用户名密码一致即可登录

已知缺陷:
1)如果被hack工具截包, 还是能伪造登录系统 (只是黑客无法解密密码)
---------------------------------------------Update Log--------------------------------------------------
[2015-10-18]
1) add cookie encrytion
2) cookie util use session , need remove dependency (for HA scenario)
