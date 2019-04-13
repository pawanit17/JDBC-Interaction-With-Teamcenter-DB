package com.teamcenter.jdbc.driverprograms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.teamcenter.jdbc.sqlprocessors.TcJdbcSqlProcessor;

/**
 *  The driver program for connecting to the Teamcenter database and fetch results for a query.
 *  
 *  For this exercise, the database used was Oracle. And hence, as you would see in the TcJdbcSQLProcessor class, 
 *  the Oracle thin-driver was leveraged. Typically, the JDBC drivers for Oracle database can be found here: 
 *  X:\...\Oracle\product\12.1.0\dbhome_1\jdbc\lib. The jar files present in this location are to be added to the 
 *  build path of the eclipse project.
 *  
 *  If your database differs, you would need the corresponding drivers for it. For instance, if your database is 
 *  MS SQL Server Database, then you would need the JDBC jar files for that specific database provided here by the vendor
 *  https://docs.microsoft.com/en-us/sql/connect/jdbc/microsoft-jdbc-driver-for-sql-server?view=sql-server-2017
 *  
 *  @author Pavan Kumar Dittakavi 
 */
public class TcJdbcMainDriver 
{
	final static String ITEM_AND_OWNINGUSER_QUERY = "SELECT item.pItem_id AS ItemID, userInfo.pUser_Name AS OwningUser " +
													"FROM infodba.pItem item " +
													"INNER JOIN infodba.pPOM_Application_Object pAO ON  pAO.puid = item.puid " +
													"INNER JOIN infodba.pPOM_User userInfo ON  userInfo.puid = pAO.rOwning_UserU";

	/**
	 *  The main driver method.
	 *  @param args The command line arguments to the main method in Java.
	 */	
	public static void main( String args[] )
	{
		TcJdbcSqlProcessor sqlProcessor = new TcJdbcSqlProcessor();

		ResultSet queryResultSet;
		try 
		{
			queryResultSet = sqlProcessor.executeSQL( ITEM_AND_OWNINGUSER_QUERY );

			Map<String, String> itemAndOwners;
				itemAndOwners = getItemIdAndOwners( queryResultSet );

			for( Map.Entry<String, String> itemAndOwner : itemAndOwners.entrySet() ) 
			{
			    System.out.println(itemAndOwner.getKey() + " " + itemAndOwner.getValue());
			}

			sqlProcessor.closeResources();			
		}
		catch (ClassNotFoundException e)
		{			
			e.printStackTrace();
			return;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return;
		}
	}

	/**
	 *  Method to extract the ItemIDs and the corresponding Owners from the SQL ResultSet. 
	 *  The results would be stored in the map and would be returned to the caller.
	 *  
	 *  @param queryResultSet: The input ResultSet which has to be iterated for getting the ownership information.
	 *  @return A map containing the ItemID and the Owner as persisted in Teamcenter database.
	 *  @throws SQLException If a database access error occurs or this method is called on a closed result set
	 */
	public static Map<String, String> getItemIdAndOwners( ResultSet queryResultSet ) throws SQLException
	{
		Map<String, String> itemToOwningUserMap = new java.util.HashMap<String, String>();
		
		while( queryResultSet.next() )
		{
			String itemId = queryResultSet.getString("ItemID");
			String owner = queryResultSet.getString("OwningUser");
			itemToOwningUserMap.put( itemId, owner );
		}		
		return itemToOwningUserMap;		
	}	
}
