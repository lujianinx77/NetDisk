package com.lu.login.service;

import com.lu.login.entities.UserInfo;

public interface UserInfoService {
	UserInfo login(String username,String password);
	void save(UserInfo userinfo);
	UserInfo getUserInfoByUsername(String username);
	Boolean modifyPasswordByEmail(String username, String email,String newPassword);
}
