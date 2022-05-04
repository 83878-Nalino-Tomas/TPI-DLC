package dlc.tpi.Entity;

public class Document {
    private int docId;
    private String docName;
    private String path;


    public Document(int docId, String docName, String path) {
        this.docId = docId;
        this.docName = docName;
        this.path = path;
    }

    public int getDocId() {
        return this.docId;
    }

    public String getDocName() {
        return this.docName;
    }

    public String getPath() {
        return this.path;
    }
    
}
