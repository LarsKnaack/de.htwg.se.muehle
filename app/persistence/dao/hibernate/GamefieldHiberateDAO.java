package persistence.dao.hibernate;

import models.IGamefieldGraph;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.dao.IGamefieldDAO;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;
import persistence.dto.hibernate.HibernateGamefieldDTO;
import persistence.dto.hibernate.HibernateVertexDTO;
import play.api.Play;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 04.04.2017.
 */
public class GamefieldHiberateDAO implements IGamefieldDAO{

    private IGamefieldDTO copyGamefieldGraph(HibernateGamefieldDTO pgamefieldGraph) {
        if(pgamefieldGraph == null) {
            return null;
        }
        IGamefieldGraph gamefieldGraph = Play.current().injector().instanceOf(IGamefieldGraph.class);
        gamefieldGraph.setId(pgamefieldGraph.getId());

        for (IVertexDTO vertex : pgamefieldGraph.getVertexs()) {
            gamefieldGraph.setStoneVertex(vertex.getVertex(), vertex.getColor());
        }

        return new HibernateGamefieldDTO(gamefieldGraph);
    }

    private HibernateGamefieldDTO copyGamefieldGraph(IGamefieldGraph gamefieldGraph) {
        if (gamefieldGraph == null) {
            return null;
        }

        String gamefieldId = gamefieldGraph.getId();
        HibernateGamefieldDTO pgamefieldGraph;
        if (containsGamefieldGraphByID(gamefieldId)) {
            Session session = HibernateUtil.getInstance().getCurrentSession();
            pgamefieldGraph = (HibernateGamefieldDTO) session.get(HibernateGamefieldDTO.class, gamefieldId);

            List<IVertexDTO> vertexs = pgamefieldGraph.getVertexs();
            for(IVertexDTO vertex : vertexs) {
                Integer v = vertex.getVertex();

                vertex.setColor(gamefieldGraph.getStoneColorVertex(v));
            }
        } else {
            pgamefieldGraph = new HibernateGamefieldDTO();

            List<IVertexDTO> vertexs = new ArrayList<IVertexDTO>();

            for(int i = 0; i < 24; i++) {
                char color = gamefieldGraph.getStoneColorVertex(i);

                HibernateVertexDTO vertex = new HibernateVertexDTO(i, color);

                vertexs.add(vertex);
            }
            pgamefieldGraph.setVertexs(vertexs);
        }

        pgamefieldGraph.setId(gamefieldId);

        return pgamefieldGraph;
    }


    @Override
    public void saveGameField(IGamefieldGraph gamefieldGraph) {
        Transaction tx = null;
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();

            HibernateGamefieldDTO pgrid = copyGamefieldGraph(gamefieldGraph);

            session.saveOrUpdate(pgrid);

            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public IGamefieldDTO getGamefieldById(String id) {
        Session session = HibernateUtil.getInstance().getCurrentSession();
        session.beginTransaction();
        return copyGamefieldGraph(session.get(HibernateGamefieldDTO.class, id));
    }

    @Override
    public boolean containsGamefieldGraphByID(String id) {
        if(getGamefieldById(id) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteGamefieldByID(String id) {
        Transaction tx = null;
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();

            HibernateGamefieldDTO pgrid = session.get(HibernateGamefieldDTO.class, id);

            session.delete(pgrid);

            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
