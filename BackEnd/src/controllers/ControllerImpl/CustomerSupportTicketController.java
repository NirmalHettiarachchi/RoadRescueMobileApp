package controllers.ControllerImpl;


import utils.CrudUtil;
import models.CustomerSupportTicketModels;
import models.SupportTicket;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerSupportTicketController {
    public boolean update(Connection connection, int ticketId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"UPDATE support_ticket SET status=? WHERE ticket_id=?","closed",ticketId);
    }

    public boolean add(Connection connection, SupportTicket supportTicket, CustomerSupportTicketModels customerSupportTicket) throws SQLException, ClassNotFoundException, ParseException {

        // Custommergen or cs Report thanedi hadena support ticket eka

        Timestamp timestamp = dateConvert(supportTicket.getTimestamp());

        boolean boolValue1 = CrudUtil.executeUpdate(connection, "INSERT INTO support_ticket values(?,?,?,?,?)",
                supportTicket.getTicketId(), supportTicket.getTitle(), supportTicket.getDescription(), supportTicket.getTicketStatus(), timestamp);

       /* boolean boolValue2 = CrudUtil.executeUpdate(connection, "INSERT INTO customer_support_ticket values(?,?,?)",
                customerSupportTicket.getTicketId(), customerSupportTicket.getCustomerId(), customerSupportTicket.getSupportMemberId());*/

        if (boolValue1){

            //methana table deken ekkata hari data watune nathi nm data watuna table data eka remove karanna mathaka athuwa

            return true;
        }else {
            return false;
        }

    }

    private Timestamp dateConvert(String timestamp) throws ParseException {
        String dateFormat = "dd.MM.yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date utilDate = sdf.parse(timestamp);

        return new Timestamp(utilDate.getTime());

    }

    public JsonArray getAllPendingSupportTicket(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM support_ticket WHERE status=?", "open");

        JsonArrayBuilder pendingSupportTickets = Json.createArrayBuilder();

        while (resultSet.next()){
            SupportTicket supportTicket = new SupportTicket(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getTimestamp(5) + "");
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("ticketId",supportTicket.getTicketId());
            objectBuilder.add("title",supportTicket.getTitle());
            objectBuilder.add("date",supportTicket.getTimestamp());
            objectBuilder.add("status",supportTicket.getTicketStatus());
            pendingSupportTickets.add(objectBuilder.build());
        }
        return pendingSupportTickets.build();
    }
}
