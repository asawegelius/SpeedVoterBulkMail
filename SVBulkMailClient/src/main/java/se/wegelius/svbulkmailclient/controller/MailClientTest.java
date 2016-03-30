/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmailclient.controller;

import com.sun.jersey.api.client.UniformInterfaceException;
import se.wegelius.svbulkmailclient.client.MailClient;

/**
 *
 * @author asawe
 */
public class MailClientTest {

    public static void main(String args[]) throws UniformInterfaceException {
        MailClient mc = new MailClient();
        System.out.println(mc.getPlain());

        System.out.println(mc.getJson(1).getEntity(String.class));
        
        System.out.println(mc.delete(Integer.toString(6)).getEntity(String.class));
        
        System.out.println(mc.createJson("no5@wegelius.se", "blablabla").getEntity(String.class));
    }
}
