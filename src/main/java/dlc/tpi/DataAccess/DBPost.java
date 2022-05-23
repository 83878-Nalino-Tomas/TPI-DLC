package dlc.tpi.DataAccess;

import java.sql.PreparedStatement;
import java.util.HashMap;


import dlc.tpi.Entity.Post;
import dlc.tpi.Utils.DBManager;

public class DBPost {

    public static void insertPost(HashMap<String, Post> post, DBManager db) {
        try {
            int i = 0;

            String SQL_INSERT = "INSERT INTO dlc.post (word, docId, termFrecuency,ctx) VALUES(?,?,?,?)";
            PreparedStatement st = db.getConnection().prepareStatement(SQL_INSERT);

            for (String w : post.keySet()) {
                st.setString(1, w);
                Post actualPost = post.get(w);
                st.setInt(2, actualPost.getDocId());
                st.setInt(3, actualPost.getTermfrecuency());
                st.setString(4, actualPost.getContext());
                st.addBatch();
                i++;
                if (i == post.size()) {
                    st.executeBatch();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
