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
import se.wegelius.svbulkmail.dao.CorruptedMailListDao;
import se.wegelius.svbulkmail.model.CorruptedMail;

/**
 *
 * @author asawe
 */
@Path("/corrupted")
public class CorruptedMailService {

    @Context
    private ServletContext sctx;          // dependency injection
    private static CorruptedMailListDao dao; // set in populate()

    public CorruptedMailService() {
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
        Set<CorruptedMail> mails = dao.getAll();
        String msg = "You have " + dao.count() + " email addresses saved \n";
        for (CorruptedMail m : mails) {
            msg = msg + "email: " + m.getCorruptedMail() + ", message " + m.getCorruptedMailMsg() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        CorruptedMail mail = dao.findByID(id);
        String msg = "email: " + mail.getCorruptedMail() + ", message " + mail.getCorruptedMailMsg();
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
        CorruptedMail mail = new CorruptedMail();
        mail.setCorruptedMail(email);
        mail.setCorruptedMailMsg(message);
        dao.save(mail);
        int id = mail.getCorruptedMailId();
        msg = "Mail " + id + " created: (email = " + email + " message = " + message + ").\n";
        System.out.println(msg);
        return toRequestedType(mail.getCorruptedMailId(), "application/json");
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
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
        CorruptedMail mail = new CorruptedMail();
        mail.setCorruptedMail(email);
        mail.setCorruptedMailMsg(message);
        dao.save(mail);
        int id = mail.getCorruptedMailId();
        msg = "Mail " + id + " created: (email = " + email + " message = " + message + ").\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id,
            @FormParam("email") String email,
            @FormParam("message") String message) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (email == null && message == null) {
            msg = "Neither who nor what is given: nothing to edit.\n";
        }

        CorruptedMail m = dao.findByID(id);
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
            m.setCorruptedMail(email);
        }
        if (message != null) {
            m.setCorruptedMailMsg(message);
        }
        dao.update(m);
        msg = "Mail " + id + " has been updated.\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        CorruptedMail m = dao.findByID(id);
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
            dao = new CorruptedMailListDao();
        }
    }

    // Add a new email to the list.
    private int addEmail(String email, String message) {
        CorruptedMail mail = new CorruptedMail();
        mail.setCorruptedMail(email);
        mail.setCorruptedMailMsg(message);
        dao.save(mail);
        return mail.getCorruptedMailId();
    }

    // Prediction --> JSON document
    private String toJson(CorruptedMail mail) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(mail);
        } catch (Exception e) {
        }
        return json;
    }

    // PredictionsList --> JSON document
    private String toJson(Set<CorruptedMail> mlist) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(mlist);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        CorruptedMail mail = dao.findByID(id);
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
