package com.onjava.dbcp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class DBCPoolingListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(com.onjava.dbcp.DBCPoolingListener.class);

	public void contextInitialized(ServletContextEvent sce) {

		log.debug("contextInitialized() called");
		try {
			// Obtain our environment naming context
			Context envCtx = (Context) new InitialContext().lookup("java:comp/env");

			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			log.debug("Going to set the username, password and url");
			BasicDataSource bds = (BasicDataSource) ds;
			bds.setPassword("javadude");
			bds.setUsername("javauser");
			bds.setUrl("jdbc:mysql://localhost/javatest");
			sce.getServletContext().setAttribute("DBCPool", ds);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
