package dlc.tpi;


import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;

import dlc.tpi.dataAccess.DBDocument;
import dlc.tpi.dataAccess.DBVocabulary;
import dlc.tpi.entity.Document;
import dlc.tpi.entity.VocabularyEntry;
import dlc.tpi.util.DBManager;

public class main {
    public static void main(String[] args) throws Exception {
        DBManager db = new DBManager();
        db.setConnectionMode(DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE);
        db.setDriverName(DBManager.MARIADB_DRIVER_NAME);
        db.setUrl("jdbc:mariadb://localhost:3306/dlc?rewriteBatchedStatements=true");
        db.setUserName("root");
        db.setPassword("dlc2022");
        db.connect();
        // System.out.println("Connected to DB");
        // Hashtable<String, VocabularyEntry> vocabulary =
        // DBVocabulary.loadVocabulary(db);
        // String query = "Quixote, QUIJOTE, SANCHO";

        List<Document> docList = DBDocument.getDocuments(db);
        // SearchService.search(query, vocabulary, docmuents, db);
        //

        Hashtable <String, VocabularyEntry> vocabulary = DBVocabulary.loadVocabulary(db);
        String query = "Quixote, QUIJOTE, SANCHO";
        String delim = "[\\.\\n\\s*,;]+";
        String[] terms = query.split(delim);
        TreeSet<VocabularyEntry> queryVocabulary = new TreeSet<VocabularyEntry>();
        for (String term : terms) {
            term = term.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            VocabularyEntry entry = vocabulary.get(term);
            if (entry != null) {
                queryVocabulary.add(entry);
            }
        }




        // TreeSet<SearchEntry> ld = SearchService.search(query, vocabulary, docList, db);
        // // for (SearchEntry se : ld.descendingSet()) {
        // //     System.out.println(se);
        // // }

        // System.out.println(ld.size());
      //  System.out.println(ld.size());
        // String pathDocs = System.getProperty("user.home");
        // try (DirectoryStream<java.nio.file.Path> stream = Files
        // .newDirectoryStream(Paths.get(pathDocs + "\\DocumentosTP1"))) {
        // for (java.nio.file.Path file : stream) {
        // Document newDoc = new Document(file.getFileName().toString());

        // if (docList.contains(newDoc)) {
        // System.out.println("Documento ya indexado");

        // }
        // // if (docListButArray.contains(newDoc)) {
        // // System.out.println("Documento ya indexado");
        // // }

        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // Document doc = new Document("Quijote.txt");
        // Document doc1 = new Document(2, "Quijote.txt");
        // System.out.println(doc.equals(doc1));
    }
}
