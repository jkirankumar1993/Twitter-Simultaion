/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.xml.wss.impl.misc.Base64;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;
import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;


/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class uploadimage {
    
    private Part file;
    private String a;
    private FileInputStream inputstream;
     
    

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    

 
    
     public String storeImage(String name,String username,String password,String email,String phone) {
        // Create connection
         a="kiran";   String result = "";
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
           
        }
        String a="";
       final String DB_URL = "jdbc:mysql://localhost:3306/twitter?useSSL=false";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
         
        try
        {
           conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
            try{
          a = getFileName(file);
          File image = new File(a);
          inputstream = new FileInputStream(image);
         PreparedStatement st = conn.prepareStatement("insert into twitter_user values('kiran','kiran','kiran','kiran','8327488399',?)");
          st.setBinaryStream(1,(InputStream) inputstream,(int) (image.length()));
          st.executeUpdate();
          a="success";
          a = "User Registered";
            result = "done";
      }
      catch(Exception e){
          
      }
            
        }
        catch(Exception e)
        {
            result = "error";
            
            e.printStackTrace();
        }
        finally
        {
            try{
                conn.close();
                stat.close();
                
            }
            catch(Exception e)
            {
            e.printStackTrace();
            }
            
        }
       return result;
      
      
         
        }
     
     public String getimage(){
           String result = "";
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
           
        }
        String a="";
       final String DB_URL = "jdbc:mysql://localhost:3306/twitter?useSSL=false";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
         
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
         try{
           
           PreparedStatement st = conn.prepareStatement("select * from twitter_user");
           rs = st.executeQuery();
           while(rs.next()){
              return  Base64.encode(rs.getBytes(6));
           }
         }
         catch(Exception ex){
             
         }
        }
        catch(Exception e)
        {
            result = "error";
            
            e.printStackTrace();
        }
        finally
        {
            try{
                conn.close();
                stat.close();
                
            }
            catch(Exception e)
            {
            e.printStackTrace();
            }
            
        }
      
         
         return null;
     }
     
     public String mes(){
         if(a!=""){
             return a;
         }
         else{
             return "";
         }
     }
     
     private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
}
         
    }
    

