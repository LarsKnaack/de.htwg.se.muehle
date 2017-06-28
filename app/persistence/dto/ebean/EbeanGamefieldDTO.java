package persistence.dto.ebean;

import io.ebean.Model;
import models.IGamefieldGraph;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;
import persistence.dto.hibernate.HibernateVertexDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lars on 27.06.2017.
 */

@Entity
public class EbeanGamefieldDTO extends Model implements IGamefieldDTO {

    @Id
    @Column(name="gamefield_id")
    private String id;

    @OneToMany(mappedBy = "gamefield")
    private List<EbeanVertexDTO> vertices;

    public EbeanGamefieldDTO(IGamefieldGraph graph) {
        this.id = graph.getId();
        vertices = new ArrayList<>();
        for(int i = 0; i < 24; i++) {
            vertices.add(new EbeanVertexDTO(i, graph.getStoneColorVertex(i), this));
        }
    }

    @Override
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
        for(EbeanVertexDTO dto: vertices) {
            result.add(dto);
        }
        return result;
    }

    @Override
    public void setVertexs(List<IVertexDTO> vertexs) {
        this.vertices.clear();
        for(IVertexDTO dto : vertexs) {
            this.vertices.add((EbeanVertexDTO) dto);
        }
    }
}
