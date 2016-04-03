/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmailclient.client;

import com.sun.jersey.api.client.UniformInterfaceException;

/**
 *
 * @author asawe
 */
public class MailClientTest {

    public static void main(String args[]) throws UniformInterfaceException {
        MailClient mc = new MailClient();
        System.out.println(mc.getPlain());
        System.out.println(mc.getJson().getEntity(String.class));

        System.out.println(mc.getJson(1).getEntity(String.class));
        System.out.println("updated: " + mc.updateJson(17, "no5@wegelius.se", "new message").getEntity(String.class));

        System.out.println("updated plain: " + mc.updateJson(24, "no2@wegelius.se", "new message too").getEntity(String.class));
        System.out.println(mc.delete(Integer.toString(29)).getEntity(String.class));

        System.out.println(mc.createJson("no5@wegelius.se", "blablabla").getEntity(String.class));
    }
}
