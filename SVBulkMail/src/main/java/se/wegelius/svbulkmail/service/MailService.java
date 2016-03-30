/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.wegelius.svbulkmail.dao.MailListDao;
import se.wegelius.svbulkmail.model.Mail;

/**
 *
 * @author asawe
 */
@Path("/mail")
public class MailService {

    @Context
    private ServletContext sctx;          // dependency injection
    private static MailListDao dao; // set in populate()

    public MailService() {
    }

    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML})
    public Response getXml() {
        checkContext();
        return Response.ok(dao, "application/xml").build();
    }

    @GET
    @Path("/xml/{id: \\d+}")
    @Produces({MediaType.APPLICATION_XML}) // could use "application/xml" instead
    public Response getXml(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/xml");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public Response getJson() {
        checkContext();
        return Response.ok(toJson(dao.getAll()), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/json");
    }

    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        checkContext();
        Set<Mail> mails = dao.getAll();
        String msg = "You have " + dao.count() + " email addresses saved \n";
        for (Mail m : mails) {
            msg = msg + "email: " + m.getEmail() + ", message " + m.getMessage() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        Mail mail = dao.findByID(id);
        String msg = "email: " + mail.getEmail() + ", message " + mail.getMessage();
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    public Response createJson(@QueryParam("email") String email,
            @QueryParam("message") String message) {
        checkContext();
        String msg = null;
        System.out.println("email: " + email + " message: " + message);
        // Require both properties to create.
        if (email == null || message == null) {
            msg = "Property 'email' or 'message' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setMessage(message);
        dao.save(mail);
        int id = mail.getEmailsId();
        msg = "Mail " + id + " created: (email = " + email + " message = " + message + ").\n";
        System.out.println(msg);
        return toRequestedType(mail.getEmailsId(), "application/json");
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("email") String email,
            @QueryParam("message") String message) {
        checkContext();
        String msg = null;
        System.out.println("email: " + email + " message: " + message);
        // Require both properties to create.
        if (email == null || message == null) {
            msg = "Property 'email' or 'message' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setMessage(message);
        dao.save(mail);
        int id = mail.getEmailsId();
        msg = "Mail " + id + " created: (email = " + email + " message = " + message + ").\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@FormParam("id") int id,
            @FormParam("email") String email,
            @FormParam("message") String message) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (email == null && message == null) {
            msg = "Neither who nor what is given: nothing to edit.\n";
        }

        Mail m = dao.findByID(id);
        if (m == null) {
            msg = "There is no email with ID " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Update.
        if (email != null) {
            m.setEmail(email);
        }
        if (message != null) {
            m.setMessage(message);
        }
        dao.update(m);
        msg = "Mail " + id + " has been updated.\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        Mail m = dao.findByID(id);
        if (m == null) {
            msg = "There is no email with ID " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.delete(m);
        msg = "Email " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new MailListDao();
        }
    }

    // Add a new email to the list.
    private int addEmail(String email, String message) {
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setMessage(message);
        dao.save(mail);
        return mail.getEmailsId();
    }

    // Prediction --> JSON document
    private String toJson(Mail mail) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(mail);
        } catch (Exception e) {
        }
        return json;
    }

    // PredictionsList --> JSON document
    private String toJson(Set<Mail> mlist) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(mlist);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        Mail mail = dao.findByID(id);
        if (mail == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(mail), type).build();
        } else {
            return Response.ok(mail, type).build(); // toXml is automatic
        }
    }
}
