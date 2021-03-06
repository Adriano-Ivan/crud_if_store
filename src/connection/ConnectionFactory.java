package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exceptions.DatabaseException;

public class ConnectionFactory {
	
	private static Connection connection = null;

	public static Connection getConnection(){
		
		if(connection == null) {
		
			try {
			
//				Properties props = loadProperties();
//				String url = props.getProperty("dburl");
//				String password = props.getProperty("password");
//				String user = props.getProperty("user");
				//Class.forName("com.mysql.jdbc.Driver"); 
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/if_store", "root", "admin");
			
				return connection;
				
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			} 
		
		} else {
			return connection;
		}
		
	}
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public static void closeConnection() {
		try {
			
			connection.close();
		
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
	}

}
