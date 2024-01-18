package servlet;


import controllers.ControllerImpl.CustomerSupportController;
import controllers.ControllerImpl.CustomerSupportTicketController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.CustomerSupportTicketModels;
import models.SupportTicket;
import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;


@WebServlet(urlPatterns = "/customerSupport")
public class CustomerSupportServlet extends HttpServlet {

   CustomerSupportTicketController cusSupportTicket =new CustomerSupportTicketController();
   CustomerSupportController customerSupportController=new CustomerSupportController();
   @Resource(name="java:comp/env/roadRescue")
   DataSource ds;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Call  me");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();
            JsonArray allPendingSupportTicket = cusSupportTicket.getAllPendingSupportTicket(connection);
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",200);
            response.add("message","Successfully get all support tickets.");
            response.add("data",allPendingSupportTicket);
            writer.print(response.build());
            connection.close();
        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",500);
            response.add("message","SQLException");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",500);
            response.add("message","ClassNotFoundException");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String option = jsonObject.getString("option");



        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        switch (option){
            case "registration":


                int ticketId = jsonObject.getInt("supportTickerId");
                int customerSupport = jsonObject.getInt("CustomerSupport");
                int tickerOwner = jsonObject.getInt("tickerOwner");
                String createdDate = jsonObject.getString("createdDate");
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String solution = jsonObject.getString("solution");
                String status = jsonObject.getString("status");

                SupportTicket supportTicket = new SupportTicket(ticketId,title,description,status,createdDate);
                CustomerSupportTicketModels customerSupportTicket = new CustomerSupportTicketModels(ticketId, tickerOwner, customerSupport);


                try {
                    Connection connection = ds.getConnection();

                    boolean result = cusSupportTicket.add(connection, supportTicket, customerSupportTicket);

                    if (result) {
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        response.add("status",200);
                        response.add("message","Customer Support ticket created successfully.");
                        response.add("data","");
                        writer.print(response.build());
                    }else {
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        response.add("status",400);
                        response.add("message","Customer Support ticket created failed.");
                        response.add("data","");
                        writer.print(response.build());
                    }
                    connection.close();
                } catch (SQLException e) {
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    response.add("status",500);
                    response.add("message","SQLException error.");
                    response.add("data",e.getLocalizedMessage());
                    writer.print(response.build());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    response.add("status",500);
                    response.add("message","ClassNotFoundException error.");
                    response.add("data",e.getLocalizedMessage());
                    writer.print(response.build());
                    e.printStackTrace();
                } catch (ParseException e) {
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    response.add("status",400);
                    response.add("message","ParseException error. Convert string type date to Timestamp type ");
                    response.add("data",e.getLocalizedMessage());
                    writer.print(response.build());
                    e.printStackTrace();
                }

                break;

            case "login":
                try {
                    String contactNum = jsonObject.getString("mobileNo");
                    Connection connection = ds.getConnection();
                    JsonObject customerSupporter = customerSupportController.getCustomerSupportMemberByContact(connection, contactNum);

                    if (customerSupporter==null){
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        objectBuilder.add("status",400);
                        objectBuilder.add("message","Error");
                        objectBuilder.add("data", "This customer is not registered!");
                        writer.print(objectBuilder.build());
                        return;
                    }
                    HashMap<String, Object> claims = new HashMap<>();
                    String jwToken = Jwts.builder().setClaims(claims).setSubject(customerSupporter.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(new Date(System.currentTimeMillis() + 18000 * 1000)).signWith(SignatureAlgorithm.HS512, "roadRescue@key123").compact();

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    objectBuilder.add("status",200);
                    objectBuilder.add("message","Successfully Login...!");

                    JsonObjectBuilder dataObject = Json.createObjectBuilder();
                    dataObject.add("token",jwToken);
                    dataObject.add("customerSupporter",customerSupporter);
                    objectBuilder.add("data",dataObject.build());
                    writer.print(objectBuilder.build());

                    connection.close();

                } catch (SQLException e) {
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    response.add("status",500);
                    response.add("message","SQLException error.");
                    response.add("data",e.getLocalizedMessage());
                    writer.print(response.build());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    response.add("status",500);
                    response.add("message","ClassNotFoundException error.");
                    response.add("data",e.getLocalizedMessage());
                    writer.print(response.build());
                    e.printStackTrace();
                }
                break;
            default:break;
        }



        // solution eka add karala ne database eke kothankatwath

        // customer_support_ticket cannot enter data because already entered  data  this tables customer & customer support member


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        int ticketId = jsonObject.getInt("supportTickerId");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = ds.getConnection();
            boolean result = cusSupportTicket.update(connection, ticketId);

            if (result) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status",200);
                objectBuilder.add("message","Support ticket is closed");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("status",400);
                objectBuilder.add("message","Support ticket isn't closed (client side error).");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }
        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectBuilder.add("status",500);
            objectBuilder.add("message","SQLException error.");
            objectBuilder.add("data",e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            objectBuilder.add("status",500);
            objectBuilder.add("message","ClassNotFoundException error.");
            objectBuilder.add("data",e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

