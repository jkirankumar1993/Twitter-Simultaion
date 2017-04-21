/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.xml.wss.impl.misc.Base64;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class Search_userprofile {
    private String name;
    private String username;
    private String msg;
    private ArrayList<Tweets> alluserhomepagetweets;
    private String username_of_loggedin_user;
    private String buttontext;

    public String getButtontext() {
        boolean has = checking_whether_user1_is_following_user2();
        if(has==true){
            buttontext = "Unfollow";
        }
        else{
            buttontext = "Follow";
        }
        return buttontext;
    }

    public void setButtontext(String buttontext) {
        this.buttontext = buttontext;
    }   
    

    public String getUsername_of_loggedin_user() {
        return username_of_loggedin_user;
    }

    public void setUsername_of_loggedin_user(String username_of_loggedin_user) {
        this.username_of_loggedin_user = username_of_loggedin_user;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Tweets> getAlluserhomepagetweets() {
        return alluserhomepagetweets;
    }

    public void setAlluserhomepagetweets(ArrayList<Tweets> alluserhomepagetweets) {
        this.alluserhomepagetweets = alluserhomepagetweets;
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
    
     public String get_user_name(){
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
        String name = "";
        try
        {
              conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_user where username='"+username+"'");
           rs = st.executeQuery();
           if(rs.next()){
              return  rs.getString("name");
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
         return "";
     } 
     
      public ArrayList<Tweets> getalltweetsforuserhomepage(){
           if(alluserhomepagetweets!=null){
               alluserhomepagetweets.clear();
           }
           else{
               alluserhomepagetweets = new ArrayList<>();
           }
           
           
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
           
            rs = stat.executeQuery("select * from twitter_tweets order by tweet_time desc");
           
           while(rs.next()){
               if(rs.getString("uploader_username").equals(username)){
                   alluserhomepagetweets.add(new Tweets(rs.getInt("tweet_id"),rs.getString("uploader_name"),rs.getString("uploader_username") ,rs.getString("tweet_msg"),rs.getInt("tweet_like"), rs.getTimestamp("tweet_time") ));
               }
               else{
                   continue;
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
        return alluserhomepagetweets;
       }
      
      public String setallattributes(String username,String username1){
          this.username = username;
          name = get_user_name();
          username_of_loggedin_user = username1;
          return "Otherusersprofilepage.xhtml?faces-redirect=true";
      }
      
      public boolean checking_whether_user1_is_following_user2(){
          boolean has = false;    
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
          PreparedStatement st = conn.prepareStatement("select * from twitter_follow where username1='"+username_of_loggedin_user+"' and username2 = '"+username+"'");
           rs = st.executeQuery();
           if(rs.next()){
              has = true;
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
         return has;
      }
      
      public String follow_or_unfollow_user(){
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
            if(buttontext.equals("Follow")){
                
                PreparedStatement st = conn.prepareStatement("insert into twitter_follow values(null,'"+username_of_loggedin_user+"','"+username+"')");
                st.executeUpdate();
                buttontext = "Unfollow";
            }
            else{
                PreparedStatement st = conn.prepareStatement("delete from twitter_follow where username1='"+username_of_loggedin_user+"' and username2='"+username+"'");
                st.executeUpdate();
                buttontext = "Follow";
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
          return "Otherusersprofilepage.xhtml?faces-redirect=true";
      }
}
