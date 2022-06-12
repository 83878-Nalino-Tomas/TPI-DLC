package dlc.tpi.service;

import dlc.tpi.entity.VocabularyEntry;
import dlc.tpi.dataAccess.DBPost;
import dlc.tpi.entity.Document;
import dlc.tpi.entity.Post;
import dlc.tpi.util.DBManager;

import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;

public class SearchService {

    private static String delim = "[\\.\\n\\s*,;]+";

    public static List<Document> search(String query, Hashtable<String, VocabularyEntry> vocabulary, DBManager db,
            List<Document> docList) {

        String[] terms = query.split(delim);
        HashMap<String, List<Post>> queryPost = new HashMap<String, List<Post>>();
        TreeSet<VocabularyEntry> queryVocabulary = new TreeSet<VocabularyEntry>();
        HashMap<Integer, Document> docMap = new HashMap<Integer, Document>();
        int docSize = docList.size();
        processQuery(db, queryVocabulary, vocabulary, terms, queryPost);

        for (VocabularyEntry entry : queryVocabulary.descendingSet()) {
            List<Post> postList = queryPost.get(entry.getWord());
            for (Post post : postList) {
                if (!docMap.containsKey(post.getDocId())) {
                    Document doc = docList.get(post.getDocId() - 1).clone();
                    float idf = entry.getIdf(docSize);
                    float relevence = post.getRelevance(idf);
                    doc.incrementIr(relevence);
                    doc.appendContext(post.getContext(), entry.getWord());
                    docMap.put(post.getDocId(), doc);
                } else {
                    Document doc = docMap.get(post.getDocId());
                    float idf = entry.getIdf(docSize);
                    float relevence = post.getRelevance(idf);
                    doc.incrementIr(relevence);
                    doc.appendContext(post.getContext(), entry.getWord());
                }
            }
        }
        List<Document> result = new ArrayList<Document>(docMap.values());
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    public static void processQuery(DBManager db, TreeSet<VocabularyEntry> queryVocabulary,
            Hashtable<String, VocabularyEntry> vocabulary, String[] terms, HashMap<String, List<Post>> posting) {

        for (String term : terms) {
            term = term.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            VocabularyEntry entry = vocabulary.get(term);
            if (entry != null) {
                queryVocabulary.add(entry);
                List<Post> termPost = DBPost.getWordPost(db, term);
                posting.put(term, termPost);
            }
        }
    }

    public static File getFile(List<Document> docList, int id) {
        return docList.get(id + 1).obtenerFile();
    }

}
