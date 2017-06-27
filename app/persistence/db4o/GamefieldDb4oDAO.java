package persistence.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.google.inject.Singleton;
import models.IGamefieldGraph;
import persistence.GamefieldDTO;
import persistence.IGamefieldDAO;

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
    public void saveGameField(GamefieldDTO gamefieldGraph) {
        db.store(gamefieldGraph);
        db.commit();
    }

    @Override
    public GamefieldDTO getGamefieldById(String id) {
        List<GamefieldDTO> gamefieldGraphs = db.query(new Predicate<GamefieldDTO>() {
            public boolean match(GamefieldDTO gamefieldGraph) {
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
