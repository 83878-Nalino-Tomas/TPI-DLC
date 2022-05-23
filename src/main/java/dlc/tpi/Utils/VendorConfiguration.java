package dlc.tpi.Utils;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import dlc.tpi.DataAccess.DBVocabulary;
import dlc.tpi.Entity.VocabularyEntry;

@Singleton
@Startup
public class VendorConfiguration {

    @Inject
    private DBManager db;
    private Hashtable<String, VocabularyEntry> vocabulary;

    @PostConstruct
    public void loadConfiguration() {
        vocabulary = DBVocabulary.loadVocabulary(db);
    }

    public Hashtable<String,VocabularyEntry> getVocabulary(){
        return this.vocabulary;
    }
}
