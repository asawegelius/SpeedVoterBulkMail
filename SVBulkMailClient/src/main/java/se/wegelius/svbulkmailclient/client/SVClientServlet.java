/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmailclient.client;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author asawe
 */
public class SVClientServlet extends HttpServlet {

    MailClient client = new MailClient();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SVClientServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SVClientServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("in server doget and content type is " + request.getParameter("content_type"));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String methodType = request.getParameter("method_type");
        int id;
        if (request.getParameter("id") == "") {
            id = -1;
        } else {
            id = Integer.parseInt(request.getParameter("id"));
        }

        String email = request.getParameter("email");
        String message = request.getParameter("message");
        switch (request.getParameter("method_type")) {
            case "GET":
                if (id == -1) {
                    switch (request.getParameter("content_type")) {
                        case "application/json":
                            System.out.println(client.getJson().getEntity(String.class));
                            break;
                        case "application/plain":
                            System.out.println(client.getPlain());
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (request.getParameter("content_type")) {
                        case "application/json":
                            System.out.println(client.getJson(id).getEntity(String.class));
                            break;
                        case "application/plain":
                            //TODO
                            break;
                        default:
                            break;
                    }
                }
                break;
            case "POST":
                switch (request.getParameter("content_type")) {
                    case "application/json":
                        System.out.println(client.createJson(email, message).getEntity(String.class));
                        break;
                    case "application/plain":
                        System.out.println(client.create(email, message));
                        break;
                    default:
                        break;
                }
                break;
            case "PUT":
                switch (request.getParameter("content_type")) {
                    case "application/json":
                        System.out.println(client.updateJson(id, email, message).getEntity(String.class));
                        break;
                    case "application/plain":
                        System.out.println(client.updatePlain(id, email, message));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        response.sendRedirect("index.jsp");
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        System.out.println("in server doput and content type is " + request.getParameter("content_type"));
        switch (request.getParameter("content_type")) {
            case "application/json":
                client.updateJson(id, email, message);
                break;
            case "application/plain":
                client.updatePlain(id, email, message);
                break;
            default:
                break;
        }
        response.sendRedirect("index.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
