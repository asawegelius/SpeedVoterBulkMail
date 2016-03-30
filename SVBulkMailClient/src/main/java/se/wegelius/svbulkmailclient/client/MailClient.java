/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmailclient.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;
import se.wegelius.svbulkmailclient.model.Mail;

/**
 * Jersey REST client generated for REST resource:MailService [/]<br>
 * USAGE:
 * <pre>
 *        MailClient client = new MailClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author asawe
 */
public class MailClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/SVBulkMail/rest/mail";

    public MailClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI);
    }

    public <T> T getXml(Class<T> responseType, String id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("xml/{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

        public ClientResponse createJson(String email, String message) throws UniformInterfaceException {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("email", email);
        queryParams.add("message", message);
        System.out.println(queryParams.toString());
        ClientResponse response = webResource.queryParams(queryParams).path("json/create").post(ClientResponse.class);
        return response;
    }
        
    public ClientResponse create(String email, String message) throws UniformInterfaceException {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("email", email);
        queryParams.add("message", message);
        System.out.println(queryParams.toString());
        ClientResponse response = webResource.queryParams(queryParams).path("plain/create").post(ClientResponse.class);
        return response;
    }

    public ClientResponse update() throws UniformInterfaceException {
        return webResource.path("update").put(ClientResponse.class);
    }

    public ClientResponse delete(String id) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("plain/delete/{0}", new Object[]{id})).delete(ClientResponse.class);
    }

    public String getPlain() throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("plain");
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public ClientResponse getJson(int id) {
        GenericType<Mail> gType = new GenericType<Mail>() {
        };
        return getJson(ClientResponse.class, Integer.toString(1));
    }

    public <T> T getJson(Class<T> responseType, String id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("json/{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.destroy();
    }

}
