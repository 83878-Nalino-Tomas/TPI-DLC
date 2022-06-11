package dlc.tpi.dataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dlc.tpi.entity.Post;
import dlc.tpi.util.DBManager;

public class DBPost {

    public static void insertPost(HashMap<String, Post> post, DBManager db) {
        String word = null;
        try {

            String SQL_INSERT = "INSERT INTO dlc.post (word, docId, termFrecuency,ctx) VALUES(?,?,?,?)";
            PreparedStatement st = db.getConnection().prepareStatement(SQL_INSERT);

            for (String w : post.keySet()) {
                word = w;
                st.setString(1, w);
                Post actualPost = post.get(w);
                st.setInt(2, actualPost.getDocId());
                st.setInt(3, actualPost.getTermfrecuency());
                st.setString(4, actualPost.getContext());
                st.addBatch();
            }
            st.executeBatch();
        } catch (Exception e) {
            System.out.println(e + word);
        }
    }

    public static List<Post> getWordPost(DBManager db, String word) {
        List<Post> post = new ArrayList<>();
        try {
            String SQL_SELECT = "SELECT * FROM dlc.post WHERE word = ? ORDER BY termFrecuency DESC";
            PreparedStatement st = db.getConnection().prepareStatement(SQL_SELECT);
            st.setString(1, word);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Post p = buildPost(rs);
                post.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public static Post buildPost(ResultSet rs) throws SQLException {
        int docId = rs.getInt("docId");
        int termFrecuency = rs.getInt("termFrecuency");
        String context = rs.getString("ctx");
        return new Post(docId, termFrecuency, context);
    }
}
