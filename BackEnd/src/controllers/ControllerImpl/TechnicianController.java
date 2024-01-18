package controllers.ControllerImpl;

import utils.CrudUtil;
import models.TechnicianModel;

import javax.json.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicianController {

    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM technician");
        JsonArrayBuilder technicianArray = Json.createArrayBuilder();

        while (rst.next()) {
            TechnicianModel technicianModel = new TechnicianModel(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4),rst.getTimestamp(5), rst.getString(6));
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",technicianModel.getId());
            objectBuilder.add("fName",technicianModel.getfName());
            objectBuilder.add("lName",technicianModel.getlName());
            objectBuilder.add("contact",technicianModel.getContact());
            objectBuilder.add("timestamp", Json.createValue(technicianModel.getTimestamp().toString()));
            objectBuilder.add("status",technicianModel.getStatus());
            technicianArray.add(objectBuilder.build());
        }
        return technicianArray.build();
    }

    public boolean add(Connection connection,TechnicianModel technicianModel) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT into technician (f_name, l_name, contact_num, timestamp, availability_status) values(?,?,?,?,?)",
                technicianModel.getfName(),technicianModel.getlName(),technicianModel.getContact(),technicianModel.getTimestamp(),technicianModel.getStatus());
    }

    public boolean update(Connection connection,TechnicianModel technicianModel) throws SQLException, ClassNotFoundException {
        System.out.println(technicianModel.toString());
        return CrudUtil.executeUpdate(connection,"UPDATE technician SET f_name=?,l_name=?,contact_num=? WHERE technician_id=?",technicianModel.getfName(),technicianModel.getlName(),technicianModel.getContact(),technicianModel.getId());
    }

    public boolean delete(Connection connection,String techId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM technician WHERE technician_id=?",techId);
    }
}
