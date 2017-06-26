package persistence;

import controllers.IGamefieldGraphAdapter;

/**
 * Created by Lars on 04.04.2017.
 */
public interface IGamefieldDAO {
    void saveGameField(IGamefieldGraphAdapter gamefieldGraph);
    boolean containsGamefieldGraphByID(String id);
    IGamefieldGraphAdapter getGamefieldById(String id);
    void deleteGamefieldByID(String id);
}

