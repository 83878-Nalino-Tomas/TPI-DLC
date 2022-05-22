package dlc.tpi.DataAccess;

import java.sql.PreparedStatement;
import java.util.HashMap;

import dlc.tpi.Entity.VocabularyEntry;
import dlc.tpi.Utils.DBManager;

public class DBVocabulary {
    public static void insertVocabulary(HashMap<String, VocabularyEntry> vocabulary) {
        DBManager db = new DBManager();
        try {
            db.connect();
            int i = 0;

            String SQL_INSERT = "INSERT INTO dlc.vocabulary (word, maxTermFrecuency, numRep) VALUES(?,?,?)";
            PreparedStatement st = db.getConnection().prepareStatement(SQL_INSERT);

            for (String w : vocabulary.keySet()) {
                st.setString(1, w);
                VocabularyEntry entry = vocabulary.get(w);
                st.setInt(2, entry.getMaxTf());
                st.setInt(3, entry.getNr());
                st.addBatch();
                i++;
                if (i == vocabulary.size()) {
                    st.executeBatch();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}
