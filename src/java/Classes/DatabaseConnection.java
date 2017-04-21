package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
	
        private final static String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	private static Connection connection = null;
	
	public static Connection getConnection(){
		try {
                     Class.forName("com.mysql.jdbc.Driver");
			//if(connection==null)
				connection = DriverManager.getConnection(DB_URL,"root","kirankumar");
		} catch (Exception e) {
			System.out.println("Cannot establish connection");
			e.printStackTrace();
		}
		return connection;
	}

}
