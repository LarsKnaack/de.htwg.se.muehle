package messages;

public class MsgMoveStone {

    private int startVertex, endVertex;
    private char color;
    public MsgMoveStone(int startVertex, int endVertex, char color) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.color = color;
    }

    public int getStartVertex() {
        return startVertex;
    }

    public int getEndVertex() {
        return endVertex;
    }

    public char getColor() {
        return color;
    }
}
