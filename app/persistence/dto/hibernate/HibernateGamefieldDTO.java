package persistence.dto.hibernate;

import models.IGamefieldGraph;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by Thomas on 22.06.2017.
 */
public class HibernateGamefieldDTO implements Serializable, IGamefieldDTO {

    private static final long serialVersionUID = 1338704903825440126L;

    @Id
    @Column(name = "id")
    private String gamefieldId;

    @OneToMany(mappedBy = "gamefield")
    @Column(name = "vertex")
    private List<HibernateVertexDTO> vertexs;

    public HibernateGamefieldDTO() {

    }

    public HibernateGamefieldDTO(IGamefieldGraph gamefieldGraph) {
        vertexs = new LinkedList<>();
        for(int i = 0; i < 24; i++) {
            vertexs.add(new HibernateVertexDTO(i, gamefieldGraph.getStoneColorVertex(i)));
        }
    }

    public String getId() {
        return gamefieldId;
    }

    public void setId(String id) {
        this.gamefieldId = id;
    }

    public List<IVertexDTO> getVertexs() {
        List<IVertexDTO> result = new LinkedList<>();
        for(HibernateVertexDTO dto : vertexs) {
            result.add(dto);
        }
        return result;
    }

    public void setVertexs(List<IVertexDTO> vertexs) {
        this.vertexs.clear();
        for(IVertexDTO dto : vertexs) {
            this.vertexs.add((HibernateVertexDTO) dto);
        }
    }

}
