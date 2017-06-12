package messages;

public class BoolAnswer {

    private final boolean answer;

    public BoolAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean getContent() {
        return answer;
    }
}
