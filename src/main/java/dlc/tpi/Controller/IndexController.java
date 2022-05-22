package dlc.tpi.Controller;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dlc.tpi.DataAccess.DBDocument;
import dlc.tpi.DataAccess.DBPost;
import dlc.tpi.DataAccess.DBVocabulary;
import dlc.tpi.Entity.*;

public class IndexController {

    public static void main(String[] args) {
        HashMap<String, VocabularyEntry> vocabulary = new HashMap<>();
        HashMap<String, Post> docPost = new HashMap<>();
        String pathDocs = System.getProperty("user.home");
        Long init = System.currentTimeMillis();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathDocs + "\\DocumentosTP1"))) {
            for (Path file : stream) {
                Document newDoc = new Document(file.getFileName().toString());
                DBDocument.insertDoc(newDoc);
                indexOneByOne(newDoc, vocabulary, docPost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBVocabulary.insertVocabulary(vocabulary);
        System.out.println((System.currentTimeMillis() - init) / 1000 / 60 + " Minutos");
    }

    public static void indexOneByOne(Document doc, HashMap<String, VocabularyEntry> vocabulary,
            HashMap<String, Post> docPost) {
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
                            vocabulary.get(word).incrementNr();
                            Post newPost = new Post(1, doc.getDocId(), getContext(newLine));
                            docPost.put(word, newPost);
                        } else {
                            docPost.get(word).IncrementTF();
                        }

                        VocabularyEntry wordVocabularyEntry = vocabulary.get(word);
                        int docTf = docPost.get(word).getTermfrecuency();
                        if (docTf > wordVocabularyEntry.getMaxTf()) {
                            wordVocabularyEntry.setMaxTf(docTf);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DBPost.insertPost(docPost);
            docPost.clear();
        }
    }

    public static String getContext(String line) {
        if (line.length() > 140) {
            return line.substring(0, 140);
        }
        return line;
    }
}
