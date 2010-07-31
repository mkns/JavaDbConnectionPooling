package com.onjava.dbcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class CourseEnrollmentServlet extends HttpServlet {

	private static final long serialVersionUID = 6815122949882840759L;
	private DataSource datasource = null;
	private static int pooledCount, nonPooledCount;
	private static Logger log = Logger.getLogger(com.onjava.dbcp.CourseEnrollmentServlet.class);

	private final String sql_query = "select id, foo, bar from testdata";

	public void init() throws ServletException {
		log.debug("init() called");
		try {
			// Create a datasource for pooled connections.
			datasource = (DataSource) getServletContext().getAttribute("DBCPool");

			// Register the driver for non-pooled connections.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}

	}

	private synchronized Connection getConnection(boolean pooledConnection) throws SQLException {
		if (pooledConnection) {
			pooledCount++;
			return datasource.getConnection(); // Allocate and use a connection
			// from the pool
		} else {
			nonPooledCount++;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javatest", "javauser", "javadude");
			return con; // return a newly created object
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");

		String queryPar = req.getParameter("pool").trim();
		boolean poolEnabled = queryPar.equalsIgnoreCase("yes") ? true : false;

		pw.println("<html><head><title>Enrolled Students</title></head><body>");
		pw.println("<br><b>PooledConnectionCount:</b>" + pooledCount + ", <b>nonPooledConnectionCount:</b>" + nonPooledCount + "<br>");

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection(poolEnabled);
			/*
			 * now we are all set to fire queries and fetch the results
			 */
			pstmt = connection.prepareStatement(sql_query);

			rs = pstmt.executeQuery();

			ResultSetMetaData dbMeta = rs.getMetaData();
			pw.println("<br><table border='2'>");

			pw.println("<tr>");
			for (int col = 0; col < dbMeta.getColumnCount(); col++) {
				pw.println("<th>" + dbMeta.getColumnName(col + 1) + "</th>");
			}
			pw.println("</tr>");

			// Populating the table data

			while (rs.next()) {
				pw.println("<tr>");
				for (int col = 0; col < dbMeta.getColumnCount(); col++) {
					pw.println("<td>" + rs.getString(col + 1) + "</td>");
				}
				pw.println("</tr>");
			}
			pw.println("</table>");
			pw.println("</body></html>");

			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
			}

		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
