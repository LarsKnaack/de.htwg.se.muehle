package persistence.dto.ebean;

import io.ebean.Model;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

import javax.persistence.*;

/**
 * Created by Lars on 27.06.2017.
 */
@Entity
public class EbeanVertexDTO extends Model implements IVertexDTO {

    @Id
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="gamefield_id", referencedColumnName = "gamefield_id")
    private EbeanGamefieldDTO gamefield;

    private int vertex;

    private char color;

    public EbeanVertexDTO(int vertex, char color, EbeanGamefieldDTO ebeanGamefieldDTO) {
        this.vertex = vertex;
        this.color = color;
        gamefield = ebeanGamefieldDTO;
    }

    @Override
    public Integer getVertex() {
        return vertex;
    }

    @Override
    public void setVertex(Integer vertex) {
        this.vertex = vertex;
    }

    @Override
    public char getColor() {
        return color;
    }

    @Override
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
    public Object getId() {
        return null;
    }

    @Override
    public void setId(Object id) {

    }
}
