package dlc.tpi.Controller;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.print.Doc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dlc.tpi.Entity.*;

public class IndexController {
    public static void main(String[] args) {
        // For tests
        HashMap<String, VocabularyEntry> vocabulary = new HashMap<>();
        HashMap<String, HashMap<Integer, Post>> post = new HashMap<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("C:\\Users\\54351\\DocumentosTP1"))) {
            int docId = 0;
            for(Path file: stream) {
                ++ docId;
                Document newDoc = new Document(docId, file.getFileName().toString(), file.toString()); 
                index(newDoc, vocabulary, post);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Tamaño del vocabulario: " + vocabulary.size());
        System.out.println("Don Quixote \n" + vocabulary.get("Quixote"));
        for (Post p : post.get("Quixote").values()) {
            System.out.println(p);
        }
    }

    public static void index( Document doc, HashMap<String, VocabularyEntry> vocabulary,
            HashMap<String, HashMap<Integer, Post>> posteo) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(doc.getPath()))) {
            while (buffer.ready()) {
                String newLine = buffer.readLine();
                StringTokenizer words = new StringTokenizer(newLine, "[.,;- ]");
                while (words.hasMoreTokens()) {
                    String word = words.nextToken();
                    // To do word cleaning

                    if (!vocabulary.containsKey(word)) {
                        VocabularyEntry newEntry = new VocabularyEntry(1, 0);
                        vocabulary.put(word, newEntry);
                    }

                    if (!posteo.containsKey(word)) {
                        vocabulary.get(word).incrementNr();
                        HashMap<Integer, Post> newWordPost = newHashPost(doc.getDocId(), newLine );
                        posteo.put(word, newWordPost);
                    } else {
                        HashMap<Integer, Post> wordPost = posteo.get(word);
                        if (wordPost.containsKey(doc.getDocId())) {
                            wordPost.get(doc.getDocId()).IncrementTF();
                        } else {
                            vocabulary.get(word).incrementNr();
                            Post newPost = new Post(1, doc.getDocId(), newLine );
                            wordPost.put(doc.getDocId(), newPost);
                        }
                    }

                    VocabularyEntry vEntry = vocabulary.get(word);
                    int docTf = posteo.get(word).get(doc.getDocId()).getTermfrecuency();
                    if(docTf > vEntry.getMaxTf()) {
                        vEntry.setMaxTf(docTf);
                    }
                }

            }
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, Post> newHashPost(int docId, String context) {
        Post newPost = new Post(1, docId, context);
        HashMap<Integer, Post> newWordPost = new HashMap<>();
        newWordPost.put(docId, newPost);
        return newWordPost;
    }


}
