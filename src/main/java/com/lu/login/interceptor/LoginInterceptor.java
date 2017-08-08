package com.lu.login.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lu.login.service.Slf4jTest;

public class LoginInterceptor implements HandlerInterceptor {
	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, 
		      HttpServletResponse response, Object handler){
		String username = (String) request.getSession().getAttribute("username");
		if(username == null || username == ""){
			try {
				log.info("IP:{},[非法访问]-结果:被拦截.",request.getRemoteAddr());
				response.sendRedirect(request.getContextPath()+"/userinfoManager/index");
			} catch (IOException e) {
				
				log.error(e.getMessage());
			}
			return false;
		}
		//防止收到的数据为乱码
		response.setCharacterEncoding("UTF-8");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
