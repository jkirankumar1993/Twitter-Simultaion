/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;


import java.sql.Timestamp;


/**
 *
 * @author jkira
 */

public class conversation {
   
    private String username1;
    private String username2;
    private String message;
    private Timestamp timestamp;
     

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public conversation(String username1, String username2, String message, Timestamp timestamp) {
        this.username1 = username1;
        this.username2 = username2;
        this.message = message;
        this.timestamp = timestamp;
    }
    
   
}
