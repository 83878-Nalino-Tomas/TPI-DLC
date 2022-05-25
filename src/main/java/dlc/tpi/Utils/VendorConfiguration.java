package dlc.tpi.Utils;

import java.util.HashSet;
import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import dlc.tpi.DataAccess.DBDocument;
import dlc.tpi.DataAccess.DBVocabulary;
import dlc.tpi.Entity.VocabularyEntry;

@Singleton
@Startup
public class VendorConfiguration {

    @Inject
    private DBManager db;
    private Hashtable<String, VocabularyEntry> vocabulary;
    private HashSet<String> docList;

    @PostConstruct
    public void loadConfiguration() {
        vocabulary = DBVocabulary.loadVocabulary(db);
        docList = DBDocument.getDocuments(db);
    }

    public Hashtable<String,VocabularyEntry> getVocabulary(){
        return this.vocabulary;
    }

    public HashSet<String> getDocList(){
        return this.docList;
    }
}
