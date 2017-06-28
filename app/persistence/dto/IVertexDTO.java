package persistence.dto;

/**
 * Created by Lars on 27.06.2017.
 */
public interface IVertexDTO {

    Integer getVertex();
    void setVertex(Integer vertex);
    char getColor();
    void setColor(char color);
    IGamefieldDTO getGamefield();
    void setGamefield(IGamefieldDTO gamefield);
    Object getId();
    void setId(Object id);
}
