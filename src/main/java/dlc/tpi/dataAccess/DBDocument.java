package dlc.tpi.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Document> getDocuments(DBManager db) {
        ArrayList<Document> documents = new ArrayList<Document>();
        try {
            String SQL_QUERY = "SELECT * FROM document";
            db.prepareQuery(SQL_QUERY);
            ResultSet rs = db.executeQuery();
            while (rs.next()) {
                Document d = buildDocument(rs);
                documents.add(d);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    private static Document buildDocument(ResultSet rs) throws SQLException {
        int docId = rs.getInt("docId");
        String docName = rs.getString("docName");
        return new Document(docId, docName);
    }

}
