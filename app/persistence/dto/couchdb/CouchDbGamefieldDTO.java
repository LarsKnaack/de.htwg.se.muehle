package persistence.dto.couchdb;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.IGamefieldGraph;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

public class CouchDbGamefieldDTO extends CouchDbDocument implements IGamefieldDTO {

    private static final long serialVersionUID = 1538704903825440126L;

    /**
     * @TypeDiscriminator is used to mark properties that makes this class's
     *                    documents unique in the database.
     */
    @TypeDiscriminator
    private String id;

    private List<CouchDbVertexDTO> vertexs;

    public CouchDbGamefieldDTO(IGamefieldGraph gamefieldGraph) {
        vertexs = new LinkedList<>();
        for(int i = 0; i < 24; i++) {
            vertexs.add(new CouchDbVertexDTO(i, gamefieldGraph.getStoneColorVertex(i)));
        }
    }

    public CouchDbGamefieldDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IVertexDTO> getVertexs() {
        List<IVertexDTO> result = new LinkedList<>();
        for(CouchDbVertexDTO vertex : vertexs) {
            result.add((IVertexDTO) vertex);
        }
        return result;
    }

    public void setVertexs(List<IVertexDTO> vertexs) {
        this.vertexs.clear();
        for (IVertexDTO vertex: vertexs) {
            this.vertexs.add((CouchDbVertexDTO) vertex);
        }
    }


}