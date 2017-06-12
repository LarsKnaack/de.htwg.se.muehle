package messages;

public class GetMillsRequest {

    private int vertex;

    public GetMillsRequest(int vertex) {
        this.vertex = vertex;
    }

    public int getVertex() {
        return vertex;
    }
}
