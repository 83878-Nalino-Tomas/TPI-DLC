package dlc.tpi.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

import dlc.tpi.dataAccess.DBDocument;
import dlc.tpi.dataAccess.DBIndex;
import dlc.tpi.dataAccess.DBPost;
import dlc.tpi.dataAccess.DBVocabulary;
import dlc.tpi.entity.*;
import dlc.tpi.util.DBManager;

public class IndexService {

    public static Index indexar(List<Document> docList, Hashtable<String, VocabularyEntry> vocabulary, DBManager db) {
        Index index = new Index();
        Long[] results = new Long[3];
        String pathDocs = System.getProperty("user.home");
        HashMap<String, Post> docPost = new HashMap<>();
        long doclListStartSize = docList.size();
        indexDocuments(pathDocs, db, docList, docPost, vocabulary);
        long docListEndSize = docList.size();
        long cantDocIndexados = docListEndSize - doclListStartSize;
        results[0] = cantDocIndexados;
        long[] wordsCount = DBVocabulary.insertVocabulary(vocabulary, db);
        index.finish();
        results[1] = wordsCount[0];
        results[2] = wordsCount[1];
        index.setData(results);
        if (cantDocIndexados > 0) {
            DBIndex.saveIndex(index, db);
        }
        return index;
    }

    public static void indexDocuments(String pathDocs, DBManager db, List<Document> doclList,
            HashMap<String, Post> docPost, Hashtable<String, VocabularyEntry> vocabulary) {
        try (DirectoryStream<Path> stream = Files
                .newDirectoryStream(Paths.get(pathDocs + "\\DocumentosTP1"))) {
            for (java.nio.file.Path file : stream) {
                Document newDoc = new Document(file.getFileName().toString());
                if (doclList.contains(newDoc))
                    continue;
                doclList.add(newDoc);
                DBDocument.insertDoc(newDoc, db);
                indexOneByOne(newDoc, vocabulary, docPost, db);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                            VocabularyEntry newEntry = new VocabularyEntry(word, 1, 0);
                            vocabulary.put(word, newEntry);
                        }
                        if (!docPost.containsKey(word)) {
                            VocabularyEntry entry = vocabulary.get(word);
                            entry.incrementNr();
                            if (entry.inDataBase()) {
                                entry.setNeedUpdate(true);
                            }
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

    public static List<Index> getIndexHistory(DBManager db) {
        return DBIndex.getAllIndex(db);
    }

}
