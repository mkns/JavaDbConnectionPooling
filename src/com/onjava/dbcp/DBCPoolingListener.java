package com.onjava.dbcp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBCPoolingListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(com.onjava.dbcp.DBCPoolingListener.class);

	public void contextInitialized(ServletContextEvent sce) {

		log.debug("contextInitialized() called");
		try {
			// Obtain our environment naming context
			Context envCtx = (Context) new InitialContext().lookup("java:comp/env");

			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			sce.getServletContext().setAttribute("DBCPool", ds);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
