package se.wegelius.svbulkmail.model;
// Generated Mar 29, 2016 8:03:14 PM by Hibernate Tools 4.3.1



/**
 * Mail generated by hbm2java
 */
public class Mail  implements java.io.Serializable {


     private Integer emailsId;
     private String email;
     private String message;

    public Mail() {
    }

    public Mail(String email, String message) {
       this.email = email;
       this.message = message;
    }
   
    public Integer getEmailsId() {
        return this.emailsId;
    }
    
    public void setEmailsId(Integer emailsId) {
        this.emailsId = emailsId;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }




}


