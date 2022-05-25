package dlc.tpi.dataAccess;

import java.sql.PreparedStatement;
import java.util.HashMap;

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

}
