package model;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class MamakDatabase {
	
	// Ensure Driver Class
	public static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	// Initialize Logger
	private static final Logger log = Logger.getGlobal();
	
	public Connection connectDatabase() throws SQLException, IOException {
		Connection connection = null;
		
		log.info("Load MYSQL Java Driver");
        try {
            //Step 1: Load MySQL Java driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
        log.info("Loading application properties");
        
        // Create new properties
        Properties properties = new Properties();
        // Get Properties file called 'application.properties' on servlet resource folder location
        properties.load(MamakDatabase.class.getClassLoader().getResourceAsStream("application.properties"));

        log.info("Connecting to the database");        
        try {
        	connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch(SQLException e) {
        	// Else, connect main url without 'databaseName' property
        	connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
        }
        
        log.info("Database connection: " + connection.getCatalog());
        
        
        // Ensure current databaseName is equal to set property
        if(connection.getCatalog().contentEquals(properties.getProperty("databaseName"))) {
        	
        	createTable(connection);
        	
        	// Return normally
        	return connection;
        } else {
        	
        	// Create new databaseName using execute query
        	try(Statement s = connection.createStatement()){
        		log.info("Create new database called '" + properties.getProperty("databaseName") + "'");
        		s.execute("CREATE DATABASE " + properties.getProperty("databaseName"));
        		s.execute("USE " + properties.getProperty("databaseName"));
        		
        		// Create new table called 'poll' and its attributes as Database Setup
        		createTable(connection);
        	}
        }
        
        return connection;
	}
	
	private static void createTable(Connection connection) throws IOException {
		BufferedReader reader = null;
		String singleLine = "";
		try {
			// create statement object
			Statement statement = connection.createStatement();
			// Initialise file reader
			reader = new BufferedReader(new FileReader(MamakDatabase.class.getClassLoader().getResource("initialize.sql").getPath()));
			String line = null;
			// read script line by line
			while ((line = reader.readLine()) != null) {
				// execute query
				singleLine = singleLine.concat(line);
			}
			statement.execute(singleLine);
			log.info("Table Created");
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("Error creating the table(s). Message:\n" + e.getMessage());
		} finally {
			// close file reader
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	public void closeDatabase(Connection connection) throws IOException, SQLException {
		log.info("Closing database connection");
		connection.close();
	}
}
