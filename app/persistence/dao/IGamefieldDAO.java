package persistence.dao;

import models.IGamefieldGraph;
import persistence.dto.IGamefieldDTO;

/**
 * Created by Lars on 04.04.2017.
 */
public interface IGamefieldDAO {
    void saveGameField(IGamefieldGraph gamefieldGraph);
    boolean containsGamefieldGraphByID(String id);
    IGamefieldDTO getGamefieldById(String id);
    void deleteGamefieldByID(String id);
}

