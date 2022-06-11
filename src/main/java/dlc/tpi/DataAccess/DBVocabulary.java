package dlc.tpi.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Hashtable;

import dlc.tpi.entity.VocabularyEntry;
import dlc.tpi.util.DBManager;

public class DBVocabulary {
    public static long[] insertVocabulary(Hashtable<String, VocabularyEntry> vocabulary, DBManager db) {
        long[] results = new long[2];
        long updated = 0;
        long inserted = 0;
        try {
            String SQL_UPDATE = "UPDATE dlc.vocabulary SET  maxTermFrecuency = ?, numRep = ? WHERE word = ?";
            String SQL_INSERT = "INSERT INTO dlc.vocabulary (word, maxTermFrecuency, numRep) VALUES(?,?,?)";
            PreparedStatement stUpdate = db.getConnection().prepareStatement(SQL_UPDATE);
            PreparedStatement stInsert = db.getConnection().prepareStatement(SQL_INSERT);

            for (String w : vocabulary.keySet()) {
                VocabularyEntry entry = vocabulary.get(w);
                if (!entry.inDataBase()) {
                    stInsert.setString(1, w);
                    stInsert.setInt(2, entry.getMaxTf());
                    stInsert.setInt(3, entry.getNr());
                    stInsert.addBatch();
                    entry.setDataBase(true);
                    inserted++;
                } else if (entry.needUpdate()) {
                    stUpdate.setInt(1, entry.getMaxTf());
                    stUpdate.setInt(2, entry.getNr());
                    stUpdate.setString(3, w);
                    stUpdate.addBatch();
                    updated++;
                }
            }

            stInsert.executeBatch();
            stUpdate.executeBatch();
            stInsert.close();
            stUpdate.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        results[0] = inserted;
        results[1] = updated;
        return results;
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
            VocabularyEntry vEntry = new VocabularyEntry(word, maxTf, nr);
            vEntry.setDataBase(true);
            vocabulary.put(word, vEntry);
        }

        return vocabulary;
    }
}
