package dlc.tpi.Entity;

public class Document {
    private int docId;
    private String docName;
    private String path;
    private final String dir = "DocumentosTP1";

    public Document(String docName) {
        this.docName = docName;
        this.path = this.generatePath(docName);
    }

    public void setDocId(int docId) {
        this.docId = docId;
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

    public String generatePath(String fileName) {

        StringBuilder sb = new StringBuilder();
        String userPath = System.getProperty("user.home");
        sb.append(userPath).append("\\");
        sb.append(this.dir).append("\\");
        sb.append(fileName);

        return sb.toString();
    }

}
