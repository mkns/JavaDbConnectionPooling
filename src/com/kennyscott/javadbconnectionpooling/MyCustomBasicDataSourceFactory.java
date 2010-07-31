package com.kennyscott.javadbconnectionpooling;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

public class MyCustomBasicDataSourceFactory extends BasicDataSourceFactory {

	private static Logger log = Logger.getLogger(com.kennyscott.javadbconnectionpooling.MyCustomBasicDataSourceFactory.class);

	@SuppressWarnings("unchecked")
	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {

		/*
		 * General idea from http://bit.ly/aMJEdj
		 * 
		 * Ultimately, this class shows that you don't need to store your db
		 * credentials in the Context file, which many people won't want to do.
		 * Instead, you store non-sensitive stuff in context.xml but you store
		 * your sensitive stuff anywhere else you want, and you load it in some
		 * other way. This example isn't loading it in from anywhere else, we're
		 * just using a hard-coded string, but clearly you'd read the password
		 * some other way using Java and set the password that way.
		 */

		log.debug("getting object instance...");
		Object o = super.getObjectInstance(obj, name, nameCtx, environment);
		if (o != null) {
			BasicDataSource ds = (BasicDataSource) o;
			log.debug("Going to set username, password and url");
			ds.setPassword("javadude");
			ds.setUsername("javauser");
			ds.setUrl("jdbc:mysql://localhost/javatest");
			return ds;
		} else {
			// oh shit.
			return null;
		}
	}

}
