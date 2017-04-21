/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class Search_user {
    private String name;
    private String username;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Search_user(String name, String username) {
        this.name = name;
        this.username = username;
    }

    
      public String getuserprofilephoto(){
        try{
          try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return to internalError.xhtml
            
        }
      final String DB_URL = "jdbc:mysql://localhost:3306/twitter?useSSL=false";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_user where username='"+username+"'");
           rs = st.executeQuery();
           if(rs.next()){
              return  Base64.encode(rs.getBytes("profilephoto"));
           }           
           
       
        }
        catch(SQLException e)
        {
            System.out.println("connection to Database failed.");
            e.printStackTrace();
        }
        finally
        {
            try{
                conn.close();
                stat.close();
                rs.close();
                
            }
            catch(Exception e)
            {
            e.printStackTrace();
            }
            
        }
          
        }
      catch(Exception e){
          
      }
         return null;
     }    
      
      public ArrayList<String> getallusernames(String query){
          ArrayList<String> names = new ArrayList<>();
          try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return to internalError.xhtml
            
        }
        final String DB_URL = "jdbc:mysql://localhost:3306/twitter?useSSL=false";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_user");
           rs = st.executeQuery();
           while(rs.next()){
               if(rs.getString("name").startsWith(query)){
                   names.add(rs.getString("name"));
               }              
           }           
           
       
        }
        catch(SQLException e)
        {
            System.out.println("connection to Database failed.");
            e.printStackTrace();
        }
        finally
        {
            try{
                conn.close();
                stat.close();
                rs.close();
                
            }
            catch(Exception e)
            {
            e.printStackTrace();
            }
            
        }
        return names;
      }
}
