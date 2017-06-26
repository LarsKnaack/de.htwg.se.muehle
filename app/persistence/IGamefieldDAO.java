package persistence;

import controllers.IGamefieldGraphAdapter;
import models.IGamefieldGraph;

/**
 * Created by Lars on 04.04.2017.
 */
public interface IGamefieldDAO {
    void saveGameField(IGamefieldGraph gamefieldGraph);
    boolean containsGamefieldGraphByID(String id);
    IGamefieldGraph getGamefieldById(String id);
    void deleteGamefieldByID(String id);
}

