package persistence.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import controllers.IGamefieldGraphAdapter;
import persistence.IGamefieldDAO;
import models.IGamefieldGraph;
import java.util.List;

/**
 * Created by Thomas on 16.06.2017.
 */
public class GamefieldDb4oDAO implements IGamefieldDAO {

    private ObjectContainer db;

    public GamefieldDb4oDAO() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
                "gamefieldGraph.data");
    }

    @Override
    public void saveGameField(IGamefieldGraphAdapter gamefieldGraph) {
        db.store(gamefieldGraph);
    }

    @Override
    public IGamefieldGraphAdapter getGamefieldById(String id) {
        List<IGamefieldGraphAdapter> gamefieldGraphs = db.query(new Predicate<IGamefieldGraphAdapter>() {
            public boolean match(IGamefieldGraphAdapter gamefieldGraph) {
                return (id.equals(gamefieldGraph.getId()));
            }
        });

        if(gamefieldGraphs.size() > 0)
            return gamefieldGraphs.get(0);

        return null;
    }

    @Override
    public boolean containsGamefieldGraphByID(String id) {
        List<IGamefieldGraphAdapter> gamefields = db.query(new Predicate<IGamefieldGraphAdapter>() {
            private static final long serialVersionUID = 1L;

            public boolean match(IGamefieldGraphAdapter gamefieldGraph) {
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
    }
}
