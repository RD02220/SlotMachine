//package com.slotmachine.db;
//
//import com.mysql.jdbc.Driver;
//import java.sql.*;
//
//public class DBConnection {
//	
//	private String url;
//	private Connection connection;
//
//	public DBConnection() {
//		initializeDatabase();
//	}
//	
//	public void initializeDatabase() {
//		try {
//			// Load Driver.
//			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("Driver successfully loaded.");
//		}
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	
//		// Database URL
//		url = "jdbc:mysql://apartment42.ddns.net/slotmachine";
//		
//		try {
//			connection = 
//					DriverManager.getConnection(url, "team", "password");
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void closeConnection() {
//		if (connection != null) {
//			try {
//				connection.close();
//			}
//			catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//}
