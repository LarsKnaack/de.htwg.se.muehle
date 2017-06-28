package persistence.dto.db4o;

import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

/**
 * Created by Lars on 27.06.2017.
 */
public class Db4oVertexDTO implements IVertexDTO {

    private Integer vertex;
    private char color;

    public Db4oVertexDTO(int vertex, char color) {
        this.vertex = vertex;
        this.color = color;
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
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Object id) {

    }
}
