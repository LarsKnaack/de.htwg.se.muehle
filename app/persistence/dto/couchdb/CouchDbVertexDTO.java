package persistence.dto.couchdb;

import org.ektorp.support.CouchDbDocument;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

public class CouchDbVertexDTO extends CouchDbDocument implements IVertexDTO {

    private static final long serialVersionUID = 2462372913947261930L;

    private String id;

    private Integer vertex = 0;
    private char color = 'n';

    public CouchDbVertexDTO(Integer vertex, char color) {
        this.vertex = vertex;
        this.color = color;
    }

    public CouchDbVertexDTO() {
    }

    public Integer getVertex() {
        return vertex;
    }

    public void setVertex(Integer vertex) {
        this.vertex = vertex;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    @Override
    public IGamefieldDTO getGamefield() {
        return null;
    }

    @Override
    public void setGamefield(IGamefieldDTO gamefield) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id.toString();
    }
}
