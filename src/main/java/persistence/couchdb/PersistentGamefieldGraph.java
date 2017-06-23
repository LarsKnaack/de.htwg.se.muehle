package persistence.couchdb;


import java.util.Set;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;
import persistence.couchdb.PersistentVertex;

public class PersistentGamefieldGraph extends CouchDbDocument {

    private static final long serialVersionUID = 1538704903825440126L;

    /**
     * @TypeDiscriminator is used to mark properties that makes this class's
     *                    documents unique in the database.
     */
    @TypeDiscriminator
    private String id;

    private Set<PersistentVertex> vertexs;


    public PersistentGamefieldGraph() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<PersistentVertex> getVertexs() {
        return vertexs;
    }

    public void setVertexs(Set<PersistentVertex> vertexs) {
        this.vertexs = vertexs;
    }


}