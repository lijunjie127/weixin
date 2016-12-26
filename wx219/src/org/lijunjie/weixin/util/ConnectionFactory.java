package org.lijunjie.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	
	public static  Connection getConnection(){

		Connection conn = null;
		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver");
			
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCL","sunxun","123");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_ljj", "root", "123456");
			conn = DriverManager.getConnection("jdbc:mysql://120.27.125.223:3306/weixin", "5452", "gVx9oG2s_BOQRPXYgQmr");

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
			
		
		return conn;
		
	}
}
