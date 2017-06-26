package persistence.hibernate;

import models.IGamefieldGraph;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.IGamefieldDAO;
import persistence.db4o.GamefieldDTO;
import play.api.Play;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 04.04.2017.
 */
public class GamefieldHiberateDAO implements IGamefieldDAO{

    private IGamefieldGraph copyGamefieldGraph(PersistentGamefield pgamefieldGraph) {
        if(pgamefieldGraph == null) {
            return null;
        }

        IGamefieldGraph gamefieldGraph = Play.current().injector().instanceOf(IGamefieldGraph.class);
        gamefieldGraph.setId(pgamefieldGraph.getId());

        for (PersistentVertex vertex : pgamefieldGraph.getVertexs()) {
            gamefieldGraph.setStoneVertex(vertex.getVertex(), vertex.getColor());
        }

        return gamefieldGraph;
    }

    private PersistentGamefield copyGamefieldGraph(GamefieldDTO gamefieldGraph) {
        if (gamefieldGraph == null) {
            return null;
        }

        String gamefieldId = gamefieldGraph.getId();
        PersistentGamefield pgamefieldGraph;
        if (containsGamefieldGraphByID(gamefieldId)) {
            Session session = HibernateUtil.getInstance().getCurrentSession();
            pgamefieldGraph = (PersistentGamefield) session.get(PersistentGamefield.class, gamefieldId);

            List<PersistentVertex> vertexs = pgamefieldGraph.getVertexs();
            for(PersistentVertex vertex : vertexs) {
                Integer v = vertex.getVertex();

                vertex.setColor(gamefieldGraph.getStoneColorVertex(v));
            }
        } else {
            pgamefieldGraph = new PersistentGamefield();

            List<PersistentVertex> vertexs = new ArrayList<PersistentVertex>();

            for(int i = 0; i < 24; i++) {
                char color = gamefieldGraph.getStoneColorVertex(i);

                PersistentVertex vertex = new PersistentVertex();
                vertex.setVertex(i);
                vertex.setColor(color);

                vertexs.add(vertex);
            }
            pgamefieldGraph.setVertexs(vertexs);
        }

        pgamefieldGraph.setId(gamefieldId);

        return pgamefieldGraph;
    }


    @Override
    public void saveGameField(GamefieldDTO gamefieldGraph) {
        Transaction tx = null;
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();

            PersistentGamefield pgrid = copyGamefieldGraph(gamefieldGraph);

            session.saveOrUpdate(pgrid);

            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public GamefieldDTO getGamefieldById(String id) {
        Session session = HibernateUtil.getInstance().getCurrentSession();
        session.beginTransaction();
        return null;
        //TODO: return copyGamefieldGraph((PersistentGamefield) session.get(PersistentGamefield.class, id));
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

            PersistentGamefield pgrid = (PersistentGamefield) session.get(PersistentGamefield.class, id);

            session.delete(pgrid);

            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
