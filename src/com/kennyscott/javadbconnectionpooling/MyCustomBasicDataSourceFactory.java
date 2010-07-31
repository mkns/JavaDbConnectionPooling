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
		 * Ultimately, this is just casting the object to a BasicDataSource. If
		 * we don't do this, the cast in DBCPoolingListener doesn't work.
		 */

		log.debug("getting object instance...");
		Object o = super.getObjectInstance(obj, name, nameCtx, environment);
		if (o != null) {
			BasicDataSource ds = (BasicDataSource) o;
			log.debug("I'm doing virtually nothing, by the way.");
			return ds;
		} else {
			// oh shit.
			return null;
		}
	}

}
