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
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;
import static jdk.nashorn.internal.objects.NativeError.getFileName;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Nimisha
 */
//@Named(value = "tweetReplies")
@ManagedBean
@SessionScoped
public class tweetReplies implements Serializable {

    //private int id;
    private UIInput replier_id;
    private String rep_msg;
    private Timestamp rep_time;
    private Part rep_img;
    protected FileInputStream inputstream;
    private UIInput tweet_id;

    private int id;
    private String rep_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRep_id() {
        return rep_id;
    }

    public void setRep_id(String rep_id) {
        this.rep_id = rep_id;
    }

    public Part getRep_img() {
        return rep_img;
    }

    public void setRep_img(Part rep_img) {
        this.rep_img = rep_img;
    }
    
    private int twt_id;
    public tweetReplies(int id, int tweet_id, String rep_id, String rep_msg, Timestamp rep_time)
    {
        this.id = id;
        this.rep_id = rep_id;
        this.rep_msg = rep_msg;
        this.rep_time = rep_time;
        this.twt_id = tweet_id;
    }

    public int getTwt_id() {
        return twt_id;
    }

    public void setTwt_id(int twt_id) {
        this.twt_id = twt_id;
    }
    
    public UIInput getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(UIInput tweet_id) {
        this.tweet_id = tweet_id;
    }

    public FileInputStream getInputstream() {
        return inputstream;
    }

    public void setInputstream(FileInputStream inputstream) {
        this.inputstream = inputstream;
    }

    public tweetReplies(UIInput tweet_id, UIInput replier_id, String rep_msg, Timestamp rep_time) {
        this.tweet_id = tweet_id;
        this.replier_id = replier_id;
        this.rep_msg = rep_msg;
        this.rep_time = rep_time;
        //this.rep_img = rep_img;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public UIInput getReplier_id() {
        return replier_id;
    }

    public void setReplier_id(UIInput replier_id) {
        this.replier_id = replier_id;
    }

    public String getRep_msg() {
        return rep_msg;
    }

    public void setRep_msg(String rep_msg) {
        this.rep_msg = rep_msg;
    }

    public Timestamp getRep_time() {
        return rep_time;
    }

    public void setRep_time(Timestamp rep_time) {
        this.rep_time = rep_time;
    }

//    public Part getRep_img() {
//        return rep_img;
//    }
//
//    public void setRep_img(Part rep_img) {
//        this.rep_img = rep_img;
//    }
    
    
        
    public tweetReplies() {
        
    }
    
    
    public String doReply(ActionEvent event) throws FileNotFoundException {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean ok = false;
        System.out.println(getRep_msg());
        System.out.println(getTweet_id().getValue());
        System.out.println(getReplier_id().getValue());
        
        final String DB_URL = "jdbc:mysql://localhost:3306/twitter?useSSL=false";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        rep_time = new Timestamp(System.currentTimeMillis());
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            
            if(rep_img == null){
                ps = conn.prepareStatement("insert into twitter_replies(tweet_id,replier_id,rep_message,rep_time) values("+getTweet_id().getValue()+",'" + getReplier_id().getValue() + "','" + rep_msg + "','" + rep_time + "')");
                ps.executeUpdate();
            }
            else{
                InputStream stream = rep_img.getInputStream();
                ps = conn.prepareStatement("insert into twitter_replies(tweet_id,replier_id,rep_message,rep_img,rep_time) values("+getTweet_id().getValue()+",'" + getReplier_id().getValue() + "','" + rep_msg + "',?, '" + rep_time + "')");
                ps.setBinaryStream(1,stream,(int)rep_img.getSize());
                ps.executeUpdate();
            }
            ok=true;
            rep_msg = "";
        }
        catch(SQLException e)
        {
            System.out.println("connection to Database failed.");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        } catch (IOException ex) 
        {
            Logger.getLogger(tweetReplies.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try{
                if(ps!=null)
                    ps.close();
                if(conn!=null)
                    conn.close();
                if(rs!=null)
                    rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        // to check the output
        if (ok) {
            context.addCallbackParam("saved", true);
        } else {
            context.addCallbackParam("saved", false);
        }
        return null;
    }
    
    public String getreplyimg()
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
            conn = DriverManager.getConnection(DB_URL, "root", "kirankumar");
            stat = conn.createStatement();
            PreparedStatement st = conn.prepareStatement("select * from twitter_replies where reply_id='"+id+"'");
            rs = st.executeQuery();
            if(rs.first())
            {
              return  Base64.encode(rs.getBytes("rep_img"));
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
    
}
