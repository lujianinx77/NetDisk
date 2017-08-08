package com.lu.login.service;

import com.lu.login.entities.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lu.login.dao.UserInfoDAO;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService{
	@Autowired
	private UserInfoDAO userInfoDAO;
	@Override
	public UserInfo login(String username, String password) {
		return userInfoDAO.login(username, password);
	}

	@Override
	public void save(UserInfo userinfo) {
		userInfoDAO.save(userinfo);
		
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	@Override
	public UserInfo getUserInfoByUsername(String username) {
		return userInfoDAO.getUserInfoByUsername(username);
	}

	@Override
	public Boolean modifyPasswordByEmail(String username, String email,String newPassword) {
		return userInfoDAO.modifyPasswordByEmail(username, email,newPassword);
	}

}
