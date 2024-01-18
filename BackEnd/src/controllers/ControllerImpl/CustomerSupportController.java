package controllers.ControllerImpl;

import utils.CrudUtil;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSupportController {

    public JsonObject getCustomerSupportMemberByContact(Connection connection, String contactNum) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM customer WHERE contact_num=?", contactNum);

        if (resultSet.next()) {
            int cstSupportId = resultSet.getInt(1);
            String cstSupportF_name = resultSet.getString(2);
            String cstSupportL_name = resultSet.getString(3);
            String cstSupportContactNum = resultSet.getString(4);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("cstSupportId",cstSupportId);
            objectBuilder.add("cstSupportF_name",cstSupportF_name);
            objectBuilder.add("cstSupportL_name",cstSupportL_name);
            objectBuilder.add("cstSupportContactNum",cstSupportContactNum);
            return  objectBuilder.build();
        }
        return null;
    }
}
