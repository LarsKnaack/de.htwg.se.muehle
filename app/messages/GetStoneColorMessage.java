package messages;

public class GetStoneColorMessage {
    private int vertex;
    private char answer;
    public GetStoneColorMessage(int vertex) {
        this.vertex = vertex;
    }

    public GetStoneColorMessage(char answer) {
        this.answer = answer;
    }

    public char getContent() {
        return answer;
    }

    public int getVertex() {
        return vertex;
    }
}
