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
1) 如果被hack工具截包, 还是能伪造登录系统 (只是黑客无法解密密码)
2) filter中取到的用户名密码需要到DB验证， filter会频繁做这件事， 效率会致命

---------------------------------------------Update Log--------------------------------------------------
[2015-10-18]
1) add cookie encrytion
2) cookie util use session , need remove dependency (for HA scenario) [Done]
3) remove redirect parameter for welcome.jsp
Note:此版本支持HA, 未使用session

[2015-11-01]
1) 开启session的创建， 在HA环境下只要能根据cookie中的值重建session中的值就好， 不必刻意不创建session
2) 增加在线人数统计功能(OnlineCounter) , 在HA的环境下需要把人数统计的变量放到分布式缓存中(e.g. memcached)
Note: login.jsp不要开启session  不然logout之后(session销毁), 重定向到login.jsp又会重建session,导致在线人数统计失效

思考:
1) 完全不使用session会造成很多DB查询， 使用session跟cookie配合应该能提高效率，注意session跨server丢失需要从cookie重建
2) salt ?

