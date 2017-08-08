package com.lu.login.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lu.login.entities.UserInfo;
import com.lu.login.service.Slf4jTest;
import com.lu.login.service.UserInfoService;

@Controller
@RequestMapping(value="userinfoManager/")
public class UserInfoController {
	private static Logger log = LoggerFactory.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	private final String LOGIN_ACTION = "loginAction";
	private final String RE_INSPECTION_ACTION = "reInspectionAction";
	private final String REGISTER_ACTION = "registerAction";
	private final String GET_PASSWORD_ACTION = "getPasswordAction";
	private final String RESPONSE_SUCCESS = "success";
	private final String RESPONSE_FAIL = "fail";
	/* 
	 * */
	@RequestMapping(value=LOGIN_ACTION,method=RequestMethod.POST)
	public void loginAction(@RequestParam("username")String username
			,@RequestParam("password")String password
			,HttpServletResponse response
			,HttpSession session){
		UserInfo ret = userInfoService.login(username,password);
		String result="";
		if(null == ret)
			result=RESPONSE_FAIL;
		else{
			session.setAttribute("username", ret.getUsername());
			result=RESPONSE_SUCCESS;
		}
		log.info("用户:{},[登陆]-结果:{}",username,result);
		try{
			response.getWriter().write(result);
		}catch(Exception e)
		{
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=RE_INSPECTION_ACTION,method=RequestMethod.POST)
	public void  reInspection(@RequestParam("username")String username,HttpServletResponse response)
	{
		String result="";
		if(userInfoService.getUserInfoByUsername(username) != null)
			result = RESPONSE_FAIL;
		else
			result = RESPONSE_SUCCESS;
		try{
		response.getWriter().write(result);
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=REGISTER_ACTION,method=RequestMethod.POST)
	public void registerAction(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("email")String email,
			HttpServletResponse response){
		UserInfo userinfo=new UserInfo();
		userinfo.setUsername(username);
		userinfo.setPassword(password);
		userinfo.setEmail(email);
		userinfo.setCreatetime(new Date());
		userInfoService.save(userinfo);
		log.info("用户:{},[注册账号]-结果:注册成功.",username);
		try{
			response.getWriter().write(RESPONSE_SUCCESS);
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=GET_PASSWORD_ACTION,method=RequestMethod.POST)
	public void getPasswordAction(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("email")String email,
			HttpServletResponse response){
		String result="";
		if(userInfoService.modifyPasswordByEmail(username, email, password))
			result = RESPONSE_SUCCESS;
		else
			result = RESPONSE_FAIL;
		log.info("用户:{},[修改密码]-结果:{}",username,result);
		try{
			response.getWriter().write(result);
		}catch(Exception e)
		{
			log.error(e.getMessage());
		}
	}

}
