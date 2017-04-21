/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class Email {
    private String username;
    private String password;
    private String useremail;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
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
    
    public void sendemail(){
        msg = "";
        if(checkemailid()){
          username = "testtwitterproject1993@gmail.com";
        password = "lionlion1";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port","587");
        
        Session session = Session.getInstance(props,new javax.mail.Authenticator() {
       protected PasswordAuthentication getPasswordAuthentication(){
           return new PasswordAuthentication(username, password);
       }
        });
        try{
            String newpass = "";
            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
             char c = chars[random.nextInt(chars.length)];
                sb.append(c);
                }
            newpass = sb.toString();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(useremail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(useremail));
            message.setSubject("Password Retrival Testing");
            message.setContent("<h:body>Password is :"+newpass+" </body>","text/html; charset=utf-8");
            Transport.send(message);
            updatepassword(newpass);
        }
        catch(Exception e){
            
        }  
        }
        else{
            msg = "Invalid Email ID";
        }
        
    }
    
     public boolean checkemailid(){
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
           
            rs = stat.executeQuery("select * from twitter_user where email = '"+useremail+"'");
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
     
     public void updatepassword(String pass){
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
         
        try
        {
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
           int i = stat.executeUpdate("update twitter_user set password = '"+pass+"' where email = '"+useremail+"' ");
           
       
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
}
