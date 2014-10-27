/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package beans;

import java.sql.*;

/**
 *
 * @author ashcrok
 */
public class UserBean implements java.io.Serializable {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql-meritwork.elasticloud.star-vault.ro/meritwork";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "jUFLRFXS59";
    
    Statement stmt = null;
    Connection conn = null;
    
    
    private String screenName;
    private String fullName;
    private String location;
    private String bio;
    
    private String output = "";
    
    
    public UserBean() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
        } catch(Exception e) {
            output += e.toString();
        }
    }
    
    public void setUser() {
        try {
            String sql;
            sql = "SELECT * "
                + "FROM bios";
            ResultSet rs = stmt.executeQuery(sql);
            
            int counter = 10;
            
            while(rs.next() && (counter > 0)) {
                output += rs.getString("screen_name") + "<br>";
                counter--;
            }
            
        } catch (Exception e) {
            output += e.toString();
        }
    }
    
    
    
    public void query() {
        
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            
            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            //STEP 4: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT screen_name, full_name, location, bio "
                + "FROM bios"
                + "WHERE id = 1";
            ResultSet rs = stmt.executeQuery(sql);
            
            //STEP 5: Extract data from result set
            //while (rs.next()) {
                //Retrieve by column name
                //screenName  = rs.getString("screen_name");
                fullName    = rs.getString("full_name");
                location    = rs.getString("location");
                bio         = rs.getString("bio");
            //}
            
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        
        } catch(SQLException se){
            output += se.toString();
        } catch(Exception e){
            output += e.toString();
        }
    }
    
    
    public String getScreenName () {
        return this.screenName;
    }
    
    public String getOutput() {
        return this.output;
    }


    
    
}
