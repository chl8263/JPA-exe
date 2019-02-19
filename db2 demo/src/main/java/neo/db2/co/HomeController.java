package neo.db2.co;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.db2.jcc.DB2Driver;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		testDB2();
		return "home";
	}
	
	
	private void testDB2(){
		String urlPrefix = "jdbc:db2:";
	    String url;
	    String user;
	    String password;
	    String empNo;
	    String empNo2; 
	    Connection con;
	    Statement stmt;
	    ResultSet rs;
	    
	    System.out.println ("**** Enter class EzJava");
	    
	    url = urlPrefix + "//dbrain.iptime.org:60000/SPIDER";
	    user = "spider";
	    password = "spider";
	    
	    
	    try 
	    {                                                              
	      // Load the driver

	    	DB2Driver db2Driver = new DB2Driver();
	      Class.forName("com.ibm.db2.jcc.DB2Driver");
	      System.out.println("**** Loaded the JDBC driver");

	      // Create the connection using the IBM Data Server Driver for JDBC and SQLJ
	      con = DriverManager.getConnection (url, user, password); 
	      // Commit changes manually
	      con.setAutoCommit(false);
	      System.out.println("**** Created a JDBC connection to the data source");

	      // Create the Statement
	      stmt = con.createStatement(); 
	      System.out.println("**** Created JDBC Statement object");

	      // Execute a query and generate a ResultSet instance
	      rs = stmt.executeQuery("SELECT EMPNO FROM EMPLOYEE"); 
	      System.out.println("**** Created JDBC ResultSet object");

	      // Print all of the employee numbers to standard output device
	      while (rs.next()) {
	        empNo = rs.getString(1);
	        System.out.println("Employee number = " + empNo);
	      }
	      System.out.println("**** Fetched all rows from JDBC ResultSet");
	      // Close the ResultSet
	      rs.close();
	      System.out.println("**** Closed JDBC ResultSet");
	      
	      // Close the Statement
	      stmt.close();
	      System.out.println("**** Closed JDBC Statement");

	      // Connection must be on a unit-of-work boundary to allow close
	      con.commit();
	      System.out.println ( "**** Transaction committed" );
	      
	      // Close the connection
	      con.close(); 
	      System.out.println("**** Disconnected from data source");

	      System.out.println("**** JDBC Exit from class EzJava - no errors");

	    }
	    
	    catch (ClassNotFoundException e)
	    {
	      System.err.println("Could not load JDBC driver");
	      System.out.println("Exception: " + e);
	      e.printStackTrace();
	    }

	    catch(SQLException ex)  
	    {
	      System.err.println("SQLException information");
	      while(ex!=null) {
	        System.err.println ("Error msg: " + ex.getMessage());
	        System.err.println ("SQLSTATE: " + ex.getSQLState());
	        System.err.println ("Error code: " + ex.getErrorCode());
	        ex.printStackTrace();
	        ex = ex.getNextException(); // For drivers that support chained exceptions
	      }
	    }
	}
	
}
