   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.sql.Statement;

   public class Sample
    {
    public static void main(String[] args) throws ClassNotFoundException
     {
      // load the sqlite-JDBC driver using the current class loader
      Class.forName("org.sqlite.JDBC");

      Connection connection = null;
      try
      {
         // create a database connection
         connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);  // set timeout to 30 sec.


         statement.executeUpdate("DROP TABLE IF EXISTS API");
         statement.executeUpdate("DROP TABLE IF EXISTS CLIENT");
         statement.executeUpdate("DROP TABLE IF EXISTS [JAVA MODULE]");
         statement.executeUpdate("DROP TABLE IF EXISTS [API USAGE]");
         statement.executeUpdate("CREATE TABLE [API] (API_ID STRING PRIMARY KEY, Package STRING, Class STRING, Method STRING, Field STRING)");
         statement.executeUpdate("CREATE TABLE [CLIENT] (Client_ID STRING PRIMARY KEY, Client_name STRING, Client_Version STRING, Release_Data DATE)");
         statement.executeUpdate("CREATE TABLE [JAVA MODULE] (Java_Module_ID STRING PRIMARY KEY, Module name STRING, Module_Directory STRING, Client_ID STRING ,FOREIGN KEY(Client_ID) REFERENCES CLIENT(Client_ID) )");
         statement.executeUpdate("CREATE TABLE [API USAGE] (API_Usage_ID STRING PRIMARY KEY, Usage_Count INT, Client_ID STRING, API_ID STRING, Java_Module_ID STRING, FOREIGN KEY(Client_ID) REFERENCES CLIENT(Client_ID), FOREIGN KEY(API_ID) REFERENCES API(API_ID), FOREIGN KEY(Java_Module_ID) REFERENCES [JAVA MODULE](Java_Module_ID))");
     }
      
     catch(SQLException e){  System.err.println(e.getMessage()); }       
     finally {         
           try {
                 if(connection != null)
                    connection.close();
                 }
           catch(SQLException e) {  // Use SQLException class instead.          
              System.err.println(e); 
            }
     }
     }
    }