package com.lu.login.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lu.login.entities.UserInfo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"applicationContextTest.xml"})
public class UserInfoServiceImplTest {
	@Autowired
	private UserInfoService userInfoService;
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	static public Logger logger = LoggerFactory.getLogger(UserInfoServiceImplTest.class); 
	@Test
	public void testSave() {
		UserInfo ui = new UserInfo();
		ui.setUsername("lujianxin77");
		ui.setPassword("147859682wo");
		ui.setEmail("lujianixn77@outlook.com");
		userInfoService.save(ui);
		
	}
	@Test
	public void testModifyPassword(){
		assertTrue( userInfoService.modifyPasswordByEmail("lujianxin77", "lujianxin77@outlook.com"
				, "wiaini1314"));
		assertFalse( userInfoService.modifyPasswordByEmail("lujianxin77", "123lujianxin77@outlook.com"
				, "wiaini1314"));
	}

}
