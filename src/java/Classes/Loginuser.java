/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

/**
 *
 * @author jkira
 */
@ManagedBean
@SessionScoped
public class Loginuser {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Part profilephoto;
    private String msg;
 private FileInputStream inputstream;
 private String searchterm;
 private ArrayList<Search_user> searchresultset;
 private ArrayList<Tweets> alluserhomepagetweets;
 private String sendingmessage;
 private String searchterm_user;
 private boolean renderimage;
 private String currentpassword;
 private String newpassword;
 private String retypenewpass;
  private ArrayList<tweetReplies> allTweetReplies;

    public String getCurrentpassword() {
        return currentpassword;
    }

    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getRetypenewpass() {
        return retypenewpass;
    }

    public void setRetypenewpass(String retypenewpass) {
        this.retypenewpass = retypenewpass;
    }
 
 

 
 
 
    public boolean HaveImage(String value)
    {
        if(!value.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
 
    public boolean isRenderimage() {
        return renderimage;
    }

    public void setRenderimage(boolean renderimage) {
        this.renderimage = renderimage;
    }

    public String getSearchterm_user() {
        return searchterm_user;
    }

    public void setSearchterm_user(String searchterm_user) {
        this.searchterm_user = searchterm_user;
    }
    
    
   
   

    public String getSendingmessage() {
        return sendingmessage;
    }

    public void setSendingmessage(String sendingmessage) {
        this.sendingmessage = sendingmessage;
    }

    public ArrayList<Tweets> getAlluserhomepagetweets() {
        return alluserhomepagetweets;
    }

    public void setAlluserhomepagetweets(ArrayList<Tweets> alluserhomepagetweets) {
        this.alluserhomepagetweets = alluserhomepagetweets;
    }
 
 //tweet attributes
 
 private String tweet_msg;
 private Part tweet_img;
 private Timestamp tweet_time;

    public String getTweet_msg() {
        return tweet_msg;
    }

    public void setTweet_msg(String tweet_msg) {
        this.tweet_msg = tweet_msg;
    }

    public Part getTweet_img() {
        return tweet_img;
    }

    public void setTweet_img(Part tweet_img) {
        this.tweet_img = tweet_img;
    }

    public Timestamp getTweet_time() {
        return tweet_time;
    }

    public void setTweet_time(Timestamp tweet_time) {
        this.tweet_time = tweet_time;
    }
 

    public ArrayList<Search_user> getSearchresultset() {
        return searchresultset;
    }

    public void setSearchresultset(ArrayList<Search_user> searchresultset) {
        this.searchresultset = searchresultset;
    }
 

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
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

    public Part getProfilephoto() {
        return profilephoto;
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

    public FileInputStream getInputstream() {
        return inputstream;
    }

    public void setInputstream(FileInputStream inputstream) {
        this.inputstream = inputstream;
    }
 
     public String login(){
        boolean validloginid = checkloginid(username);
        if(validloginid==true){
            boolean validuser = isvaliduser(username, password);
           
            if(validuser==true){
               // Login Successful.
               msg ="";      
               alluserhomepagetweets = new ArrayList<>();
               return "UserHomepage.xhtml?faces-redirect=true";
            }           
            else{
               msg = "Invalid password";
               return "Homepage.xhtml?faces-redirect=true";
            }
           
        }
        
        else{
            msg = "Invalid login ID";
            return "Homepage.xhtml?faces-redirect=true";
        }
       
    }
     
     
    // checking whether the typed username exists or not
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
            //connect to db
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
    
    //checking whether password is matching to username
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
            //connect to db
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
 
     // to get profile photo of the user
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
            //connect to db
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
       
       // to get the username of the user
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
            //connect to db
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
       
       
       public String get_following_count(){
          int count = 0;
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
            //connect to db
         conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_follow where username1='"+username+"'");
           rs = st.executeQuery();
           while(rs.next()){
              count++;
           }           
           
           return Integer.toString(count);
       
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
         return Integer.toString(count) ;
     } 
       
       public String get_follower_count(){
          int count = 0;
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_follow where username2='"+username+"'");
           rs = st.executeQuery();
           while(rs.next()){
              count++;
           }           
           
           return Integer.toString(count);
       
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
         return Integer.toString(count) ;
     } 
       
       public String search(){
           if(searchterm==null){
               searchterm = "";
           }
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
        searchresultset = new ArrayList<>();
        try
        {
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
           
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_user where username='"+searchterm+"' or name='"+searchterm+"'");
           rs = st.executeQuery();
           while(rs.next()){
                 searchresultset.add(new Search_user(rs.getString("name"),rs.getString("username")));
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
                searchterm = "";
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
         return "Searchresult.xhtml?faces-redirect=true";
       }
       
       public String search_user_for_conversation(){
             if(searchterm==null){
               searchterm = "";
           }
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
        searchresultset = new ArrayList<>();
        try
        {
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
           
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_user where username='"+searchterm_user+"' or name='"+searchterm_user+"'");
           rs = st.executeQuery();
           while(rs.next()){
                 searchresultset.add(new Search_user(rs.getString("name"),rs.getString("username")));
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
                searchterm_user = "";
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
           return "Messaging_search.xhtml?faces-redirect=true";
       }
       
       public String gotouserhomepage(){
           return "UserHomepage.xhtml?faces-redirect=true";
       }
       
       //getting tweet uploaders profile pic
       public String getuserprofilephoto(String username){
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
            //connect to db
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
       
       public String tweet(){
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
        tweet_time = new Timestamp(System.currentTimeMillis());
        
        try
        {
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
          
            stat = conn.createStatement();
            name = getfullname();
            char[] k = tweet_msg.toCharArray();
            for(int i=0;i<k.length;i++){
                if(k[i]=='@'){
                    i++;
                    String a = "";
                    for(int j=i;j<k.length;j++){                        
                        if(k[j]!=' '){
                            a+=k[j];
                            i++;
                        }
                        else{
                            
                            break;
                        }
                    }
                    rs = stat.executeQuery("select * from twitter_user where username = '"+ a+"'");
                    boolean has = false;
                    if(rs.first()){
                        has = true;
                    }
                    if(has==true){
                        String notification_msg = "you have been mention in "+username+"  tweet";
                        int p = stat.executeUpdate("insert into twitter_notifications values(null,'"+a+"','"+notification_msg+"')");
                    }
                }
            }
          if(tweet_img==null){
              String notification_msg = "You have tweeted a tweet";
              int p = stat.executeUpdate("insert into twitter_notifications values(null,'"+username+"','"+notification_msg+"')");
              PreparedStatement st = conn.prepareStatement("insert into twitter_tweets(tweet_id,uploader_name,uploader_username,tweet_msg,tweet_like,tweet_time) values(null,'"+name+"','"+username+"','"+tweet_msg+"','0','"+tweet_time+"')");
              st.executeUpdate();
          }
          else{
//              String a="";
//               a = getFileName(tweet_img);
//          File image = new File(a);
//          inputstream = new FileInputStream(image);
                   InputStream stream = tweet_img.getInputStream();
              PreparedStatement st = conn.prepareStatement("insert into twitter_tweets values(null,'"+name+"','"+username+"','"+tweet_msg+"',?,'0','"+tweet_time+"')");
              st.setBinaryStream(1,stream,(int)tweet_img.getSize());
              st.executeUpdate();
              String notification_msg = "You have tweeted a tweet";
              int p = stat.executeUpdate("insert into twitter_notifications values(null,'"+username+"','"+notification_msg+"')");
              tweet_msg = "";
              tweet_img = null; 
          }
           
       
        }
        catch(Exception e)
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
           return "UserHomepage.xhtml?faces-redirect=true";
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
       
       public String gototweetpage(){
           return "Tweet.xhtml?faces-redirect=true";
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
        
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_user where username = '"+username+"'");
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
       
       //shows tweets of the user and his following users tweets
       public ArrayList<Tweets> getalltweetsforuserhomepage(){
         alluserhomepagetweets.clear();
           
           
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
         
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_tweets order by tweet_time desc");
            ArrayList<String> following = getall_following_username(username);
           while(rs.next()){
               if(rs.getString("uploader_username").equals(username)){
                   alluserhomepagetweets.add(new Tweets(rs.getInt("tweet_id"),rs.getString("uploader_name"),rs.getString("uploader_username") ,rs.getString("tweet_msg"),rs.getInt("tweet_like"), rs.getTimestamp("tweet_time") ));
               }
               else if(following.contains(rs.getString("uploader_username"))){
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
       
       public ArrayList<String> getall_following_username(String username){
           ArrayList<String> following = new ArrayList<>();
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
           
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_follow where username1 = '"+username+"'");
           while(rs.next()){
               following.add(rs.getString("username2"));
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
        return following;
       }
       
       public ArrayList<String> getall_follwers_username(String username){
             ArrayList<String> following = new ArrayList<>();
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_follow where username2 = '"+username+"'");
           while(rs.next()){
               following.add(rs.getString("username1"));
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
        return following;
       }
       
       public String fullname(String username){
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_user where loginid = '"+username+"'");
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
       
       //Method to get users with whom the user had chatted before
       public ArrayList<String> getall_conversation_list(){
           ArrayList<String> users = new ArrayList<>();
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
         
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_conversation where username1 = '"+username+"' or username2 = '"+username+"' ");
           while(rs.next()){
               if(rs.getString("username1").equals(username)){
                   users.add(rs.getString("username2"));
               }
               else{
                   users.add(rs.getString("username1"));
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
        return users;
       }
       
       //Method to get conversation between two users
      public ArrayList<conversation> get_allconversation(String username1){
          ArrayList<conversation> conversation = new ArrayList<>();
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_conversationmessages where (username1 = '"+username+"' and username2 = '"+username1+"') or (username1='"+username1+"' and username2='"+username+"') order by time desc");
           while(rs.next()){
               conversation.add(new conversation(rs.getString("username1"), rs.getString("username2"), rs.getString("message"), rs.getTimestamp("time")));
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
        return conversation;
      }
       
       public String sendmessage(String username1,String username2){
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
            //connect to db
           conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
           Timestamp ts = new Timestamp(System.currentTimeMillis());
           boolean has = know_whether_there_is_already_conversation(username1, username2);
           if(has==false){
               int j = stat.executeUpdate("insert into twitter_conversation values('"+username1+"','"+username2+"')");
           }
           
            int i = stat.executeUpdate("insert into twitter_conversationmessages values('"+username1+"','"+username2+"','"+sendingmessage+"','"+ts+"')");
           
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
        
        return "Conversation.xhtml?faces-redirect=true";
    }
    
    public boolean know_whether_there_is_already_conversation(String username1,String username2){
        boolean has=false;  
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
           
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_conversationmessages where (username1 = '"+username1+"' and username2 = '"+username2+"') or (username1='"+username2+"' and username2='"+username1+"')");
           if(rs.first()){
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
        return has;
    }
    
     public String goto_conversation(String a){
        searchterm_user = a;
        return "Conversation.xhtml?faces-redirect=true";
    }
     
     public List<String> completeText(String query){
         List<String> results = new ArrayList<>();
         ArrayList<String> names = getallfullnames();
        for(int i = 0; i < names.size(); i++) {
            if(names.get(i).startsWith(query)){
                results.add(names.get(i));
            }
            
        }         
        
        return results;
     }
     
     public ArrayList<String> getallfullnames(){
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
        ArrayList<String> names = new ArrayList<>();
        try
        {
            //connect to db
          conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
         
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_user");
           while(rs.next()){
               names.add(rs.getString("name")); 
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
       public String changepassword(){
         if(isvaliduser(username, currentpassword)){
             if(newpassword.equals(retypenewpass)){
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            stat = conn.createStatement();
           
           int i = stat.executeUpdate("update twitter_user set password = '"+newpassword+"' where username = '"+username+"'");
           msg = "Password Changed Succesfully";
           String notification_msg = "You have changed your password";
              int p = stat.executeUpdate("insert into twitter_notifications values(null,'"+username+"','"+notification_msg+"')");
       
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
             else{
                 msg = "Your new password and retype password are not matching";
             }
         }
         else{
             msg = "Wrong Password";
         }
         return "Changepassword.xhtml?faces-redirect=true";
     }
     
     public String logout(){
         msg = "";
         return "Homepage.xhtml?faces-redirect=true";
     }
     
     /*
     Date : 11/27/2016
     */
     public ArrayList<String> getallnotifications(){
         ArrayList<String> notifications = new ArrayList<>();
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
          
            stat = conn.createStatement();
           
            rs = stat.executeQuery("select * from twitter_notifications where username = '"+username+"'");
           while(rs.next()){
               notifications.add(rs.getString("notification_msg"));
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
        return notifications;
     }
     
     public String gotonotifications(){
         return "Notifications.xhtml?faces-redirect=true";
     }
     
     public String doReply(ActionEvent event) throws FileNotFoundException 
       {
           tweetReplies replies = new tweetReplies();
           replies.getId();
           
           replies.doReply(event);
           return "DisplayTwtReply";
       }
      public ArrayList<tweetReplies> getall_tweet_replies(int id)
       {
            if(allTweetReplies!=null)
            {
                allTweetReplies.clear();
            }
            else
            {
                allTweetReplies = new ArrayList<>();
            }
            
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            final String DB_URL = "jdbc:mysql://localhost:3306/twitter?useSSL=false";
            Connection conn = null;
            Statement stat = null;
            ResultSet rs = null;
            
            try
            {
                conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
                
                stat = conn.createStatement();
                
                rs = stat.executeQuery("select * from twitter_replies where tweet_id ='" + id + "'order by rep_time desc");
                while(rs.next())
                {
                    allTweetReplies.add(new tweetReplies(rs.getInt("reply_id"), rs.getInt("tweet_id"), rs.getString("replier_id"), rs.getString("rep_message"), rs.getTimestamp("rep_time")));
                }
                
            }
            catch(SQLException e)
            {
                System.out.println("connection to Database failed.");
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    rs.close();
                    stat.close();
                    conn.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            
            return allTweetReplies;
       }
      public String retweeting(String actualuploader, String actualtweet,InputStream img )
    {
       
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
        PreparedStatement ps = null;
        
        try
        {
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
           
            stat = conn.createStatement();
            Timestamp ts=new Timestamp(System.currentTimeMillis());
           
              actualtweet ="retweet by "+username+",\n original tweet by: "+actualuploader+"\n " +  actualtweet;
             
             if(img==null)
             {
                int i=stat.executeUpdate("insert into twitter_tweets values"
                     + "(null,'"+actualuploader+"', '"+username+"', '"+actualtweet
                     +"', 'null', '0', '"+ts+"')");
                 
             }
             else
             {
//                 int i =stat.executeUpdate("insert into twitter_tweets values"
//                     + "(null,'"+actualuploader+"', '"+username+"', '"+actualtweet
//                     +"','"+img+"','0', '"+ts+"')");
                ps = conn.prepareStatement("insert into twitter_tweets values"
                     + "(null,'"+actualuploader+"', '"+username+"', '"+actualtweet
                     +"',?,'0', '"+ts+"')");
                ps.setBinaryStream(1,img,img.available());
                ps.executeUpdate();
             }         
        
        }
        catch(SQLException e)
        {
            System.out.println("Connection to database failed!");
            e.printStackTrace();            
        } catch (IOException ex) {
            Logger.getLogger(Loginuser.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                conn.close();
                stat.close();
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
                
        return "UserHomePage.xhtml?faces-redirect=true";
    }
 
   public InputStream gettweetimgforretweet(int id)
    {
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
            //connect to db
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
           
            stat = conn.createStatement();
          PreparedStatement st = conn.prepareStatement("select * from twitter_tweets where tweet_id='"+id+"'");
           rs = st.executeQuery();
           if(rs.next()){
              return  rs.getBinaryStream("tweet_img");
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


   public int getlikescount(int id)
   {
       int likescount=0;
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
           
            rs = stat.executeQuery("select * from twitter_tweets where tweet_id='"+id+"'");
           if(rs.first()){
               likescount=rs.getInt("tweet_like");
           }
       
        }
        catch(SQLException e)
        {
            System.out.println("connection to Database failed.");
            e.printStackTrace();
            likescount=0;
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
       return likescount;
   }
}
