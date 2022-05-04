package dlc.tpi.Entity;

public class VocabularyEntry {
    private int maxTf;
    private int nr;

    public void IncrementTF(){
        ++ this.nr;
    }


    public int getMaxTf() {
        return this.maxTf;
    }

    public int getNr() {
        return this.nr;
    }


}
