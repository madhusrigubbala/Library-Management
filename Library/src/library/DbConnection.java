package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class DbConnection {
         private static String url="jdbc:mysql://localhost:3306/librarymanagement";
         private static String username="root";
         private static String password="root";
         public static Connection getConnection() {
        	 try {
        		 return DriverManager.getConnection(url,username,password);
        		 
        	 }catch (Exception e) {
        		 e.printStackTrace();
        	 }
        	 return null;
         }
        
        
}