package com.lu.login.dao;

import com.lu.login.entities.*;
public interface UserInfoDAO {
	public UserInfo login(String username,String password);
	public void save(UserInfo userinfo);
	public UserInfo getUserInfoByUsername(String username);
	public Boolean modifyPasswordByEmail(String username, String email,String newPassword);

}
