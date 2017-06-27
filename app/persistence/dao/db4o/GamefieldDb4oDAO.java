package persistence.dao.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.google.inject.Singleton;
import models.IGamefieldGraph;
import persistence.dto.db4o.Db4oGamefieldDTO;
import persistence.dao.IGamefieldDAO;

import java.util.List;

/**
 * Created by Thomas on 16.06.2017.
 */
@Singleton
public class GamefieldDb4oDAO implements IGamefieldDAO {

    private ObjectContainer db;

    //private static GamefieldDb4oDAO dao;
    public GamefieldDb4oDAO() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
                "gamefieldGraph.data");
    }

    @Override
    public void saveGameField(IGamefieldGraph gamefieldGraph) {
        System.out.println("Saving gamefield");
        Db4oGamefieldDTO dto = new Db4oGamefieldDTO(gamefieldGraph);
        db.store(dto);
        db.commit();
    }

    @Override
    public Db4oGamefieldDTO getGamefieldById(String id) {
        List<Db4oGamefieldDTO> gamefieldGraphs = db.query(new Predicate<Db4oGamefieldDTO>() {
            public boolean match(Db4oGamefieldDTO gamefieldGraph) {
                System.out.println("Database Id: " + gamefieldGraph.getId());
                return (id.equals(gamefieldGraph.getId()));
            }
        });
        System.out.println("Size of DatabaseQuery: " + gamefieldGraphs.size());
        if(gamefieldGraphs.size() > 0)
            return gamefieldGraphs.get(0);

        return null;
    }

    @Override
    public boolean containsGamefieldGraphByID(String id) {
        List<IGamefieldGraph> gamefields = db.query(new Predicate<IGamefieldGraph>() {
            private static final long serialVersionUID = 1L;

            public boolean match(IGamefieldGraph gamefieldGraph) {
                return (gamefieldGraph.getId().equals(id));
            }

        });

        if (gamefields.size() > 0)
            return  true;

        return false;
    }

    @Override
    public void deleteGamefieldByID(String id) {
        db.delete(getGamefieldById(id));
        db.commit();
    }
}
