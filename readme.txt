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

[2015-11-08]
1)使用mvn jetty:run 
	使用jetty 运行应用程序 (这样就不需要tomcat了)
	http://www.blogjava.net/fancydeepin/archive/2012/06/23/maven-jetty-plugin.html
	注意: jetty run默认的话使用/作为context path， 该项目已经配置成使用项目名称 测试 http://localhost:8080/CookieWeb

2)使用 tomcat plugin 部署到standalone tomcat 服务器 (热部署 所以需要tomcat运行)
	http://my.oschina.net/angel243/blog/178554
	tomcat 部署  前提:1) tomcat已经启动 2)tomcat中配置了admin用户  3) maven setting.xml 配置了tomcat7 server
	http://blog.csdn.net/god_wot/article/details/12748983 
	mvn tomcat7:deploy
	mvn tomcat7:redeploy
	mvn tomcat7:undeploy

3) 使用resource plugin 把程序的war包拷贝到tomcat服务器(或者其他任何地方) -> 测试通过  
    只是资源拷贝, 理论上这种task最好用ant来做
	遇到的问题(要把configuration 提到execution外面)
	http://stackoverflow.com/questions/10923944/maven-resources-plugin-error-using-copy-resources-goal-resources-outputdire
	
	命令 mvn resources:copy-resources
	
4) 增加ant配置  
    build.xml 调用mvn编译 发布到tomcat 重启tomcat
	build_standalone.xml ant自己编译(前提是要用mvn的dependency 命令拷贝jar文件)
	
	
思考:
1) 完全不使用session会造成很多DB查询， 使用session跟cookie配合应该能提高效率，注意session跨server丢失需要从cookie重建
2) salt ?

