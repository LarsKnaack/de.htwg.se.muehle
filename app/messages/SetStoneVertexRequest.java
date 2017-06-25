package messages;

public class SetStoneVertexRequest {

    private int vertex;
    private char color;
    private boolean answer;
    public SetStoneVertexRequest(int vertex, char color) {
        this.vertex = vertex;
        this.color = color;
    }
    public SetStoneVertexRequest(boolean answer) {
        this.answer = answer;
    }

    public boolean getAnswer() {
        return answer;
    }

    public char getColor() {
        return color;
    }

    public int getVertex() {
        return vertex;
    }
}
