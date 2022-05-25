package dlc.tpi.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import dlc.tpi.DataAccess.DBPost;
import dlc.tpi.Entity.*;
import dlc.tpi.Utils.DBManager;

public class IndexService {

    public static void indexOneByOne(Document doc, Hashtable<String, VocabularyEntry> vocabulary,
            HashMap<String, Post> docPost, DBManager db) {
        String delim = "[\\.\\n\\s*,;]+";
        try (BufferedReader buffer = new BufferedReader(new FileReader(doc.getPath()))) {
            while (buffer.ready()) {
                String newLine = buffer.readLine().toLowerCase();
                String[] words = newLine.split(delim);
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].replaceAll("[^a-zA-Z0-9]", "");
                    if (!word.isEmpty()) {
                        if (!vocabulary.containsKey(word)) {
                            VocabularyEntry newEntry = new VocabularyEntry(1, 0);
                            vocabulary.put(word, newEntry);
                        }
                        if (!docPost.containsKey(word)) {
                            VocabularyEntry entry = vocabulary.get(word);
                            entry.incrementNr();
                            entry.setNeedUpdate(true);
                            Post newPost = new Post(1, doc.getDocId(), getNormalizeContext(newLine));
                            docPost.put(word, newPost);
                        } else {
                            docPost.get(word).IncrementTF();
                        }

                        VocabularyEntry wordVocabularyEntry = vocabulary.get(word);
                        int docTf = docPost.get(word).getTermfrecuency();
                        if (docTf > wordVocabularyEntry.getMaxTf()) {
                            wordVocabularyEntry.setNeedUpdate(true);
                            wordVocabularyEntry.setMaxTf(docTf);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DBPost.insertPost(docPost, db);
            docPost.clear();
        }
    }

    public static String getNormalizeContext(String line) {
        if (line.length() > 140) {
            return line.substring(0, 140);
        }
        return line;
    }


}
