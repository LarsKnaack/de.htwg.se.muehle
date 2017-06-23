package persistence.hibernate;

import sun.security.provider.certpath.Vertex;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by Thomas on 22.06.2017.
 */
public class PersistentGamefield implements Serializable {

    private static final long serialVersionUID = 1338704903825440126L;

    @Id
    @Column(name = "id")
    private String gamefieldId;

    @OneToMany(mappedBy = "gamefield")
    @Column(name = "vertex")
    private List<PersistentVertex> vertexs;

    private String name;

    public PersistentGamefield() {

    }

    public String getId() {
        return gamefieldId;
    }

    public void setId(String id) {
        this.gamefieldId = id;
    }

    public List<PersistentVertex> getVertexs() {
        return vertexs;
    }

    public void setVertexs(List<PersistentVertex> vertexs) {
        this.vertexs = vertexs;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }



}
