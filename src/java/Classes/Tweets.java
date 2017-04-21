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
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class Tweets {
    private int id;
    private String name;
    private String username;
    private String tweet_msg;
    private int tweet_like;
    private Timestamp tweet_time;
    

    
    public final String getButtontext(String username,int id) {
        String liketext="";
        boolean has = checking_whether_user_liked_tweet(username,id);
        if(has==true){
            liketext = "Unlike";
        }
        else{
            liketext = "Like";
        }
        return liketext;
    }
     public boolean checking_whether_user_liked_tweet(String username,int id){
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
          PreparedStatement st = conn.prepareStatement("select * from twitter_likes where username='"+username+"' and tweet_id = '"+id+"'");
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
 public String like_or_unlike_tweet(String username,int id){
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
            String liketext = getButtontext(username, id);
              conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
            if(liketext.equals("Like")){
                
                PreparedStatement st = conn.prepareStatement("insert into twitter_likes values(null,'"+username+"','"+id+"')");
                st.executeUpdate();
                int i = stat.executeUpdate("update twitter_tweets set tweet_like = tweet_like + 1 where tweet_id = '"+id+"'");
            }
            else{
                PreparedStatement st = conn.prepareStatement("delete from twitter_likes where username='"+username+"' and tweet_id='"+id+"'");
                st.executeUpdate();
                int i = stat.executeUpdate("update twitter_tweets set tweet_like = tweet_like - 1 where tweet_id = '"+id+"'");
                
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
                if(rs!=null)
                    rs.close();
                
            }
            catch(Exception e)
            {
            e.printStackTrace();
            }
            
        }
          return "UserHomepage.xhtml?faces-redirect=true";
      }

    public Tweets(int id, String name, String username, String tweet_msg, int tweet_like, Timestamp tweet_time) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.tweet_msg = tweet_msg;
        this.tweet_like = tweet_like;
        this.tweet_time = tweet_time;
       
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTweet_msg() {
        return tweet_msg;
    }

    public void setTweet_msg(String tweet_msg) {
        this.tweet_msg = tweet_msg;
    }

    public int getTweet_like() {
        return tweet_like;
    }

    public void setTweet_like(int tweet_like) {
        this.tweet_like = tweet_like;
    }

    public Timestamp getTweet_time() {
        return tweet_time;
    }

    public void setTweet_time(Timestamp tweet_time) {
        this.tweet_time = tweet_time;
    }
    
    public String gettweetimg(){
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
          PreparedStatement st = conn.prepareStatement("select * from twitter_tweets where tweet_id='"+id+"'");
           rs = st.executeQuery();
           if(rs.next()){
               if(rs.getBlob("tweet_img")==null)
               {
                   return "";
               }
               else
               {
              return  Base64.encode(rs.getBytes("tweet_img"));
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
          
        }
      catch(Exception e){
          
      }
         return null;
    }
    
  
    
}
