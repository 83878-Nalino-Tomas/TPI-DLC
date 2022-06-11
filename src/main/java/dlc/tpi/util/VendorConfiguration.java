package dlc.tpi.util;

import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import dlc.tpi.dataAccess.DBDocument;
import dlc.tpi.dataAccess.DBVocabulary;
import dlc.tpi.entity.Document;
import dlc.tpi.entity.VocabularyEntry;

@Singleton
@Startup
public class VendorConfiguration {

    @Inject
    private DBManager db;
    private Hashtable<String, VocabularyEntry> vocabulary;
    private List<Document> docList;

    @PostConstruct
    public void loadConfiguration() {
        vocabulary = DBVocabulary.loadVocabulary(db);
        docList = DBDocument.getDocuments(db);
    }

    public Hashtable<String, VocabularyEntry> getVocabulary() {
        return this.vocabulary;
    }

    public List<Document> getDocList() {
        return this.docList;
    }

    public void setDocList(List<Document> docList) {
        this.docList = docList;
    }
}
