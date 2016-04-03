/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmailclient.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asawe
 */
@XmlRootElement
public class Mail {
    private int emailsId;
    private String email;
    private String message;

    public Mail() {
    }

    public Mail(int id, String email, String message) {
        this.emailsId = id;
        this.email = email;
        this.message = message;
    }

    public int getEmailsId() {
        return emailsId;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public void setEmailsId(int id) {
        this.emailsId = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
