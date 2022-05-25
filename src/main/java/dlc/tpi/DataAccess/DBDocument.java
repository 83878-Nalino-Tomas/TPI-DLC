package dlc.tpi.dataAccess;

import java.sql.ResultSet;
import java.util.HashSet;

import dlc.tpi.entity.Document;
import dlc.tpi.util.DBManager;

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
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashSet<String> getDocuments(DBManager db){
        HashSet<String> documents = new HashSet<>();
        try {
            String SQL_QUERY = "SELECT docName FROM document";
            db.prepareQuery(SQL_QUERY);
            ResultSet rs = db.executeQuery();
            while(rs.next()) {
                documents.add(rs.getString(1));
            }
            rs.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

}
