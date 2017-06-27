package persistence;

import models.IGamefieldGraph;

import java.util.ArrayList;
import java.util.List;

public class GamefieldDTO {
    private List<List<Integer>> adjacencyList = new ArrayList<>(24);
    private vertex vertexes[] = new vertex[24];
    private String id;

    public GamefieldDTO(IGamefieldGraph gamefieldGraph) {
        this.id = gamefieldGraph.getId();
        for (int i = 0; i < 24; i++) {
            adjacencyList.add(gamefieldGraph.getAdjacencyList(i));
            vertexes[i] = new vertex();
            vertexes[i].color = gamefieldGraph.getStoneColorVertex(i);
        }
    }

    public String getId() {
        return id;
    }

    public char getStoneColorVertex(int v) {
        final int vertex = v - 1;
        if ((vertex < 0) || (vertex >= 24)) {
            return 'n';
        }
        return vertexes[vertex].color;
    }

    public List<Integer> getAdjacencyList(int vertex) {
        final int v = (vertex - 1);
        if ((v < 0) || (v >= 24)) {
            return new ArrayList<>();
        }
        return adjacencyList.get(v);
    }
    private class vertex {
        char color;
    }
}
