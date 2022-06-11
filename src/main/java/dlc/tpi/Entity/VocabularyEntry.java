package dlc.tpi.entity;

public class VocabularyEntry implements Comparable<VocabularyEntry> {
    private String word;
    private int maxTf;
    private int nr;
    private Boolean dataBase;
    private Boolean needUpdate;

    public VocabularyEntry(String word, int maxTf, int nr) {
        this.word = word;
        this.maxTf = maxTf;
        this.nr = nr;
        this.dataBase = false;
        this.needUpdate = false;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public Boolean inDataBase() {
        return this.dataBase;
    }

    public void setDataBase(Boolean dataBase) {
        this.dataBase = dataBase;
    }

    public Boolean needUpdate() {
        return this.needUpdate;
    }

    public void setNeedUpdate(Boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public float getIdf(int n) {
        return (float) Math.log10((float) n / (float) this.nr);
    }

    public boolean isStopWord(int n) {
        int porcentaje = (this.nr * 100) / n;
        if(porcentaje >= 35) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MaxTF: ").append(this.maxTf);
        sb.append("\nNr: ").append(this.nr);
        return sb.toString();
    }

    @Override
    public int compareTo(VocabularyEntry o) {
        VocabularyEntry v = (VocabularyEntry) o;

        if (this.nr > v.getNr()) {
            return -1;
        } else if (this.nr < v.getNr()) {
            return 1;
        } else {
            return 0;
        }
    }

}
