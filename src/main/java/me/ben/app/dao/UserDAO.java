package me.ben.app.dao;

import me.ben.app.bean.User;
/**
 * 暂时通过简单的DAO模拟后端DB必要的操作
 * @author Administrator
 *
 */
public class UserDAO {
public User selectUserByUsername(String username){
	//fake implementation , password = name
	String password = username;
	return new User(username,password);
}
}
