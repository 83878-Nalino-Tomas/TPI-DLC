package dlc.tpi.Entity;

import java.util.Objects;

public class Post {

    private int termfrecuency;
    private int docId;
    private String context;

    public Post(int termfrecuency, int docId, String context) {
        this.termfrecuency = termfrecuency;
        this.docId = docId;
        this.context = context;
    }

    public void IncrementTF() {
        this.termfrecuency++;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Post)) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(termfrecuency, post.getTermfrecuency()) && Objects.equals(docId, post.getDocId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(termfrecuency, docId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id Del documento: ").append(this.docId);
        sb.append("\t Frecuencia en el documento: ").append(this.termfrecuency);
        sb.append("\n Contexto de la palabra: ").append(context);
        return sb.toString();
    }

    public int getTermfrecuency() {
        return this.termfrecuency;
    }

    public int getDocId() {
        return this.docId;
    }

    public String getContext() {
        return this.context;
    }

}