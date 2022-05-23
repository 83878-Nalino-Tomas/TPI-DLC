package dlc.tpi.DataAccess;

import java.sql.ResultSet;

import dlc.tpi.Entity.Document;
import dlc.tpi.Utils.DBManager;

public class DBDocument {

    public static void insertDoc(Document doc, DBManager db) {
        try {
            String SQL_INSERT = "INSERT INTO dlc.document (docName) VALUES (?)";
            db.prepareUpdate(SQL_INSERT);
            db.setString(1, doc.getDocName());
            ResultSet rs = db.executeUpdate().getGeneratedKeys();
            if (rs.first()) {
                int newDocId = rs.getInt(1);
                doc.setDocId(newDocId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
