package dlc.tpi.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Hashtable;

import dlc.tpi.Entity.VocabularyEntry;
import dlc.tpi.Utils.DBManager;

public class DBVocabulary {
    public static void insertVocabulary(Hashtable<String, VocabularyEntry> vocabulary, DBManager db) {
        try {
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
        }

    }

    public static Hashtable<String, VocabularyEntry> loadVocabulary(DBManager db) {
        Hashtable<String, VocabularyEntry> vocabulary = null;
        try {
            String SQL_QUERY = "SELECT word, maxTermFrecuency, numRep FROM dlc.vocabulary";
            db.prepareQuery(SQL_QUERY);
            ResultSet rs = db.executeQuery();
            vocabulary = buildVocabulary(rs);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vocabulary;
    }

    private static Hashtable<String, VocabularyEntry> buildVocabulary(ResultSet rs) throws SQLException {
        Hashtable<String, VocabularyEntry> vocabulary = new Hashtable<>();

        while (rs.next()) {
            String word = rs.getString(1);
            int maxTf = rs.getInt(2);
            int nr = rs.getInt(3);
            VocabularyEntry vEntry = new VocabularyEntry(maxTf, nr);
            vocabulary.put(word, vEntry);
        }

        return vocabulary;
    }
}
