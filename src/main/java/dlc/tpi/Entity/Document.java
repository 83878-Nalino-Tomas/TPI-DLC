package dlc.tpi.entity;

import java.io.File;

import javax.persistence.Transient;

public class Document implements Comparable<Document> {
    private Integer docId;
    private String docName;
    private String path;
    private final String dir = "DocumentosTP1";
    private Float indiceRelevancia;
    private String contextWords;

    public Document(String docName) {
        this.docName = docName;
        this.path = this.generatePath(docName);
        this.setIr();
    }

    public Document(Integer docId, String docName) {
        this.docId = docId;
        this.docName = docName;
        this.path = this.generatePath(docName);
        this.setIr();
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
        this.indiceRelevancia = 0f;
    }

    public void incrementIr(float increment) {
        this.indiceRelevancia += (this.indiceRelevancia == null) ? increment : increment * 1.25f;
    }

    public void appendContext(String context, String word) {
        if (this.contextWords == null) {
            this.contextWords = "\n Word: " + word + "\n Context: " + context;
        } else {
            this.contextWords += "\n Word: " + word + "\n Context: " + context;
        }
    }

    public Float getIr() {
        return this.indiceRelevancia;
    }

    public String getContextWords() {
        return this.contextWords;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Documento: ").append(this.docName).append("\t");
        sb.append("Peso: ").append(this.indiceRelevancia);
        sb.append("\n Contextos: ").append(this.contextWords);
        return sb.toString();
    }

    @Override
    public int compareTo(Document o) {
        Document document = (Document) o;

        if (this.indiceRelevancia > document.getIr()) {
            return 1;
        } else if (this.indiceRelevancia < document.getIr()) {
            return -1;
        } else {
            return 0;
        }

    }

    @Override
    public Document clone() {
        Document clone = new Document(this.docId, this.docName);
        return clone;
    }

    @Transient
    public File obtenerFile() {
        try {
            return new File(this.path);
        } catch (Exception e) {
            return null;
        }
    }
}
