package com.kennyscott.javadbconnectionpooling;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class InitContextListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event) {
		Logger log = Logger.getRootLogger();
		PropertyConfigurator.configureAndWatch("/etc/mkns/javadbconnectionpooling_log4j.properties");	
		log.info("log4j started");
	}
	
	public void contextDestroyed(ServletContextEvent e) {
		LogManager.shutdown();
	}

}
