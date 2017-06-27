package persistence;

/**
 * Created by Lars on 04.04.2017.
 */
public interface IGamefieldDAO {
    void saveGameField(GamefieldDTO gamefieldGraph);
    boolean containsGamefieldGraphByID(String id);
    GamefieldDTO getGamefieldById(String id);
    void deleteGamefieldByID(String id);
}

