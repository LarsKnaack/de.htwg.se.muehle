package persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

public class PersistentVertex extends CouchDbDocument {

    private static final long serialVersionUID = 2462372913947261930L;

    private String id;

    private Integer vertex = 0;
    private char color = 'n';

    public PersistentCell(Integer vertex, char color) {
        this.vertex = vertex;
        this.color = color;
    }

    public PersistentCell() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
