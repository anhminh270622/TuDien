package sample;

public class Word {
    private String word_target;
    private String word_class;
    private String word_explain;
public Word(){

}
    public Word(Word a) {
        this.word_target = a.word_target;
        this.word_explain = a.word_explain;
        this.word_class = a.word_class;
    }

    public Word(String word_target, String word_class, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.word_class = word_class;
    }

    public String getWord_target() {
        return word_target;
    }
    public String getWord_explain() {
        return word_explain;
    }
    public String getWord_class() {
        return word_class;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
    public void setWord_class(String word_class) {
        this.word_class = word_class;
    }
}