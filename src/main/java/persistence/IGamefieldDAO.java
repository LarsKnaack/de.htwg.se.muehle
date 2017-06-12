package persistence;

import model.IGamefieldGraph;

/**
 * Created by Lars on 04.04.2017.
 */
public interface IGamefieldDAO {
    void saveGameField(IGamefieldGraph gamefieldGraph);
    IGamefieldGraph getGamefieldById(String id);
}
