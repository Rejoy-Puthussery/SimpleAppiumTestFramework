package utilities;

/*
	Java class to connect to the sqlite database
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {

	public static Connection connectDB(String url){
		
		Connection connection = null;
		System.out.println("Connecting Sqlite database...");

		try{
			connection = DriverManager.getConnection(url);
			System.out.println("Sqlite Database connected!");
		} catch (SQLException e) {
			System.out.println("Cannot connect the Sqlite database!/n" + e.getMessage());
			throw e;
		}finally {
			return connection;
		}
	}
	
	public static void disconnectDB(Connection connection) throws Exception {
		try {
			connection.close();
			System.out.println("Closed the DB conneciton successfully");
		}catch (Exception e) {
			System.out.println("DB connection close failed" + e.getMessage());
			throw e;
		}
	}

}
