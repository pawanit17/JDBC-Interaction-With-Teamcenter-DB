package com.teamcenter.jdbc.sqlprocessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  The SQL Processor engine that can establish a session with the Teamcenter Oracle Database
 *  and execute some queries and returns the results. This class also has API for closing the
 *  open resources.
 * 
 *  @author Pavan Kumar Dittakavi 
 */
public class TcJdbcSqlProcessor 
{
	/**
	 * The database name to which we are connecting.
	 */	
	private final String dbName = "TC_DB";

	/**
	 * The database server to which we are connecting.
	 */		
	private final String dbServer = "localhost";
	
	/**
	 * The database port to which we are connecting.
	 */
	private final int dbPort = 1521;
	
	/**
	 * The database user name to use.
	 */
	private final String dbUserName = "infodba";

	/**
	 * The database password to use.
	 */	
	private final String dbPassword = "infodba";

	/**
	 * The database connection instance.
	 */	
	private Connection dbConnection;
	
	/**
	 * The JDBC statement class to execute queries.
	 */	
	private Statement sqlStatement;

	/**
	 * The SQL query result set.
	 */	
	private ResultSet sqlResultSet;
	
	final String JBDC_DATABASE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_THIN_DRIVER_CONFIG = "jdbc:oracle:thin";
	
	public TcJdbcSqlProcessor()
	{		
	}

	/**
	 *  Method to establish a connection with the underlying Oracle database using the JDBC thin-driver. 
	 *  
	 *  @return A connection object encapsulating the actual database connection to the program.
	 *  @throws ClassNotFoundException If an attempt to load the class specified in Class.forName method fails. 
	 *  @throws	SQLException  If a database access error occurs or the url is null
	 */	
	public Connection getDbConnection() throws ClassNotFoundException, SQLException
	{
		System.out.println("-------- Initiating JDBC Connection To " + dbName +" ------");

        Class.forName( JBDC_DATABASE_DRIVER );

        System.out.println("Oracle JDBC Driver Registered!");

      	dbConnection = DriverManager.getConnection( JDBC_THIN_DRIVER_CONFIG + ":@ " + dbServer + ":" + dbPort + ":" + dbName, dbUserName, dbPassword );

		System.out.println("-------- Successfully connected to " + dbName +" ------");
      	
      	return dbConnection;
    }

	/**
	 *  Method to execute the SQL provided as input. This method would also own the responsibility
	 *  for creating a connection object if there isn't already one existing. 
	 *  
	 *  @param sqlToExecute: The SQL provided by the called to execute.
	 *  @return The SQL ResultSet containing the response of the SQL execution.
	 *  @throws SQLException If a database access error occurs or this method is called on a closed connection.
	 *  @throws	ClassNotFoundException If the Database driver class could not be loaded for a connection.
	 */	
	public ResultSet executeSQL( String sqlToExecute ) throws SQLException, ClassNotFoundException
	{
		if( dbConnection == null )
		{
			dbConnection = this.getDbConnection(); 
		}

		sqlStatement = dbConnection.createStatement();

		sqlResultSet = sqlStatement.executeQuery(sqlToExecute);

		System.out.println("-------- Successfully extracted results from " + dbName +" ------");

		return sqlResultSet;
	}

	/**
	 *  Method to close the resources. 
	 *  
	 *  @throws SQLException If a database access error occurs.
	 */	
	public void closeResources() throws SQLException 
	{
		if( sqlResultSet != null ) sqlResultSet.close();
		if( sqlStatement != null ) sqlStatement.close();
		if( dbConnection != null ) dbConnection.close();

		System.out.println("-------- Successfully closed all connections to " + dbName +" ------");
	}
}
