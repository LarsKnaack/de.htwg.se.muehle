package persistence.dto.db4o;

import models.IGamefieldGraph;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Db4oGamefieldDTO implements IGamefieldDTO {
    private List<List<Integer>> adjacencyList;
    private List<Db4oVertexDTO> vertexes;
    private String id;

    public Db4oGamefieldDTO(IGamefieldGraph gamefieldGraph) {
        adjacencyList = new ArrayList<>(24);
        vertexes = new ArrayList<>();
        this.id = gamefieldGraph.getId();
        for (int i = 0; i < 24; i++) {
            adjacencyList.add(gamefieldGraph.getAdjacencyList(i));
            vertexes.add(new Db4oVertexDTO(i, gamefieldGraph.getStoneColorVertex(i)));
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<IVertexDTO> getVertexs() {
        List<IVertexDTO> result = new LinkedList<>();
        for(Db4oVertexDTO vertex : vertexes) {
            result.add(vertex);
        }
        return result;
    }

    @Override
    public void setVertexs(List<IVertexDTO> vertexs) {
        this.vertexes.clear();
        for(IVertexDTO vertex : vertexs) {
            this.vertexes.add((Db4oVertexDTO) vertex);
        }
    }

    public char getStoneColorVertex(int v) {
        final int vertex = v - 1;
        if ((vertex < 0) || (vertex >= 24)) {
            return 'n';
        }
        return vertexes.get(vertex).getColor();
    }

    public List<Integer> getAdjacencyList(int vertex) {
        final int v = (vertex - 1);
        if ((v < 0) || (v >= 24)) {
            return new ArrayList<>();
        }
        return adjacencyList.get(v);
    }
}
