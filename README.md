# JDBC-Interaction-With-Teamcenter-DB
JDBC stands for Java DataBase Connectivity. In other words, it is a framework for a Java developer to connect to a given database and execute queries and see the results.

Using JDBC, one can connect to databases like DB2, Oracle or MySQL etc. To achieve this, usually vendors of these databases provide some Jar files that contain the JDBC drivers for that specific database.

For instance, Oracle database provides the JDBC Jar files for different Java versions in the ORACLE_HOME directory as shown here: https://i.imgur.com/5gUSPLY.png

These jar files are the pre-requisite for the JDBC connection to work. That is because as you would see in the code, "oracle.jdbc.driver.OracleDriver" exists in this Jar files and not having these classes 
would consequently give you ClassNotFoundExceptions, while you try to load the driver Class via the Class.forName method.

In this repository, we would use JDBC to connect to Teamcenter Oracle database directly ( skipping the corporate servers / Teamcenter sessions ) and we would be usign the JDBC type 4 driver for the same. Our agenda is to extract the list of Item IDs present in the database along with their Owner names and print them out to the console.

Thanks,
Pavan.
