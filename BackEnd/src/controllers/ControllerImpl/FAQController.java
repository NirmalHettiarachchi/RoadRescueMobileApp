package controllers.ControllerImpl;


import utils.CrudUtil;
import models.FAQModel;

import javax.json.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FAQController {

    public JsonArray getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM faq");
        JsonArrayBuilder faqArray = Json.createArrayBuilder();

        while (rst.next()){
            FAQModel faqModel = new FAQModel(String.valueOf(rst.getInt(1)), rst.getString(2), rst.getString(3));
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id",faqModel.getFaqId());
            objectBuilder.add("question",faqModel.getQuestion());
            objectBuilder.add("answer",faqModel.getAnswer());
            faqArray.add(objectBuilder.build());
        }
        return faqArray.build();
    }

    public boolean add(Connection connection, FAQModel newFAQ) throws SQLException, ClassNotFoundException {

        if (alreadyExits(connection,newFAQ)) {
            return CrudUtil.executeUpdate(connection, "INSERT INTO faq (question,answer) values(?,?)", newFAQ.getQuestion(), newFAQ.getAnswer());
        }
        return false;
    }



    public boolean update(Connection connection, FAQModel updateFAQ) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"UPDATE faq SET answer=? WHERE question=?",updateFAQ.getAnswer(),updateFAQ.getQuestion());

    }


    private boolean alreadyExits(Connection connection, FAQModel checkFAQ) throws SQLException, ClassNotFoundException {

        JsonArray all = getAll(connection);

        for (JsonValue temp:
                all) {
            if (temp.asJsonObject().getString("question").equalsIgnoreCase(checkFAQ.getQuestion())){
                return false;
            }
        }
        return true;
    }

    public boolean delete(Connection connection, String question) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM faq WHERE question=?",question);
    }
}
