/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import com.sun.xml.wss.impl.misc.Base64;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
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
import javax.websocket.Decoder;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class User {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Part profilephoto;
    private String msg;
 private FileInputStream inputstream;
 
 
    public Part getProfilephoto() {
        return this.profilephoto;
    }

    public void setProfilephoto(Part profilephoto) {
        this.profilephoto = profilephoto;
    }

  

  

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

  

    public FileInputStream getInputstream() {
        return inputstream;
    }

    public void setInputstream(FileInputStream inputstream) {
        this.inputstream = inputstream;
    }
    
    public String storeprofilephoto() {
        // Create connection
        String a="";
      try{
//          a = getFileName(profilephoto);
//          File image = new File(a);
//          inputstream = new FileInputStream(image);
 InputStream stream = profilephoto.getInputStream();
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
         boolean validlogin = false;
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("update twitter_user set profilephoto = ? where username = '"+username+"'");
          st.setBinaryStream(1,stream);
          st.executeUpdate();
            
       
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
       return logout();
         
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
    
     
     private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
}
     
     public String register(){
         boolean validusername = checkloginid(username);
         boolean validemail = checkemailid(email);
         if(validusername==false&&validemail==false){
            if(phone.isEmpty()){
            phone = "0";
        }
        try{
            msg="";            
            long phone1 = Long.parseLong(phone);
        String r = addusertodb(name, username, password, email, phone1);
        if(r.equals("done")){
            msg = "User Registered";
            Thread.sleep(10);            
            return "Selectprofilephotos.xhtml?faces-redirect=true";
        }
        else{
            msg = "connection DB failed. Please Try Again";
            return "";
        }
        
        }
        catch(Exception e){
            msg = "Phone must be a number";
            return "";
        } 
         }
         else{
             if(validemail==true){
                 msg= "email is already registered";
             }
             else{
                 msg = "username already took. Please type a new username";
             }
             return "";
         }
        
       
       
        
    }
    
    public String addusertodb(String name,String username, String password,String email,long phone){
         
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
            PreparedStatement st = conn.prepareStatement("insert into twitter_user values('"+name+"','"+username+"','"+password+"','"+email+"','"+phone+"','null')");
         
          st.executeUpdate();
            msg = "User Registered";
            result = "done";
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
   
    public String login(){
        boolean validloginid = checkloginid(username);
        if(validloginid==true){
            boolean validuser = isvaliduser(username, password);
           
            if(validuser==true){
               // Login Successful.
               msg ="";               
               return "UserHomepage.xhtml";
            }           
            else{
               msg = "Invalid password";
               
            }
           
        }
        
        else{
            msg = "Invalid login ID";
        }
        return "";
    }
    
    public boolean checkloginid(String a){
          //load the Driver
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
         boolean validlogin = false;
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_user where username = '"+a+"'");
           if(rs.first()){
               validlogin = true;
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
            return validlogin;
        }
   }   
    
     public boolean checkemailid(String email){
          //load the Driver
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
         boolean validlogin = false;
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_user where email='"+email+"'");
           if(rs.first()){
               validlogin = true;
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
            return validlogin;
        }
   } 
     
     
     public boolean isvaliduser(String login,String pass){
          //load the Driver
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
        boolean validlogin = false;
        try
        {
           conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_user where username = '"+login+"' and password = '"+pass+"'");
           if(rs.first()){
               validlogin = true;
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
        return validlogin;
    }
     
     public String getfullname(){
             //load the Driver
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
        String name = "";
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from users where loginid = '"+username+"'");
           if(rs.first()){
               name = rs.getString("name");
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
        return name;
    }
     
     public String logout(){
         name = "";
         username = "";
         password = "";
         email = "";
         phone = "";
         profilephoto = null;
         inputstream = null;
         return "Homepage.xhtml?faces-redirect=true";
     }
    
   
}
