package dlc.tpi.entity;

public class Document {
    private Integer docId;
    private String docName;
    private String path;
    private final String dir = "DocumentosTP1";
    private int indiceRelevancia;

    public Document(String docName) {
        this.docName = docName;
        this.path = this.generatePath(docName);
    }

    public Document(Integer docId, String docName) {
        this.docId = docId;
        this.docName = docName;
        this.path = this.generatePath(docName);
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getDocId() {
        return this.docId;
    }

    public String getDocName() {
        return this.docName;
    }

    public String getPath() {
        return this.path;
    }

    public void setIr() {
        this.indiceRelevancia = 0;
    }

    public void incrementIr(int ir) {
        this.indiceRelevancia += ir;
    }

    public int getIr() {
        return this.indiceRelevancia;
    }

    public String generatePath(String fileName) {

        StringBuilder sb = new StringBuilder();
        String userPath = System.getProperty("user.home");
        sb.append(userPath).append("\\");
        sb.append(this.dir).append("\\");
        sb.append(fileName);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        Document d = (Document) o;
        if (!this.docName.equals(d.getDocName())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 30;
        hashCode = 31 * hashCode + this.docName.hashCode();
        return hashCode;
    }

}
