package persistence.hibernate;

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
public class PersistentVertex implements Serializable {

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
    private PersistentGamefield gamefield;

    public PersistentVertex(Integer vertex) {
        this.vertex = vertex;
    }

    public PersistentVertex() {

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

    public PersistentGamefield getGamefield() {
        return gamefield;
    }

    public void setGamefield(PersistentGamefield gamefield) {
        this.gamefield = gamefield;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
