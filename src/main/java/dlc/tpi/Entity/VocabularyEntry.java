package dlc.tpi.Entity;

public class VocabularyEntry {
    private int maxTf;
    private int nr;
    private Boolean dataBase;
    private Boolean needUpdate;

    public VocabularyEntry(int maxTf, int nr) {
        this.maxTf = maxTf;
        this.nr = nr;
        this.dataBase = false;
        this.needUpdate = false;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MaxTF: ").append(this.maxTf);
        sb.append("\nNr: ").append(this.nr);
        return sb.toString();
    }
}
