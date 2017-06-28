package persistence.dto.hibernate;

import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Thomas on 23.06.2017.
 */
@Entity
@Table(name = "vertex")
public class HibernateVertexDTO implements Serializable, IVertexDTO {

    private static final long serialVersionUID = 3384225396653913648L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer vertex = 0;

    @Column
    private char color = 'n';

    @ManyToOne
    @JoinColumn(name = "gamefieldid")
    private HibernateGamefieldDTO gamefield;

    public HibernateVertexDTO(Integer vertex) {
        this.vertex = vertex;
    }

    public HibernateVertexDTO(Integer vertex, char color) {
        this.vertex = vertex;
        this.color = color;

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

    public IGamefieldDTO getGamefield() {
        return gamefield;
    }

    public void setGamefield(IGamefieldDTO gamefield) {
        this.gamefield = (HibernateGamefieldDTO) gamefield;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
