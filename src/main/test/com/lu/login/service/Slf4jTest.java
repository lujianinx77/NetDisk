package com.lu.login.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
	private static Logger log = LoggerFactory.getLogger(Slf4jTest.class);
	@Test
	public void LogjTest(){
		log.error("error");
		log.debug("debug");
		log.warn("warn");
		log.info("info");
	}

}
