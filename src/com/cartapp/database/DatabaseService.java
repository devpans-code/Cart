package com.cartapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
	private static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cartapp", "root", "");
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return con;
		}
	}

	public static int insert(String insertQuery) {
		Connection conn = getConnection();
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			int val = 0;
			if (rs.next()){
			    val=rs.getInt(1);
			}
			return val;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection(conn);
		}
		return 0;
	}
	
	public static int update(String updateQuery) {
		Connection conn = getConnection();
		Statement st = null;
		try {
			st = conn.createStatement();
			int val = st.executeUpdate(updateQuery);
			return val;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection(conn);
		}
		return 0;
	}
	
	public static int delete(String deleteQueary) {
		Connection conn = getConnection();
		Statement st = null;
		try {
			st = conn.createStatement();
			return st.executeUpdate(deleteQueary);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} finally {
			closeConnection(conn);
		}
		return 0;
	}
	
	public static ResultSet select(String selectQuery) {
		Connection conn = getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(selectQuery);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		closeConnection(conn);
		return null;
	}

	private static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
