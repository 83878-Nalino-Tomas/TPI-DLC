package dlc.tpi.Entity;

public class VocabularyEntry {
    private int maxTf;
    private int nr;

    public VocabularyEntry(int maxTf, int nr) {
        this.maxTf = maxTf;
        this.nr = nr;
    }

    public void incrementNr() {
        this.nr++;
    }

    public int getMaxTf() {
        return this.maxTf;
    }

    public void setMaxTf(int maxTf) {
        this.maxTf = maxTf;
    }

    public int getNr() {
        return this.nr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MaxTF: ").append(this.maxTf);
        sb.append("\nNr: ").append(this.nr);
        return sb.toString();
    }
}
