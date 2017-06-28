package persistence.dao.ebean;

import io.ebean.Ebean;
import models.IGamefieldGraph;
import persistence.dto.IGamefieldDTO;
import persistence.dao.IGamefieldDAO;
import persistence.dto.ebean.EbeanGamefieldDTO;

/**
 * Created by Lars on 27.06.2017.
 */
public class GamefieldEbeanDAO implements IGamefieldDAO {

    @Override
    public void saveGameField(IGamefieldGraph gamefieldGraph) {
        EbeanGamefieldDTO dto = new EbeanGamefieldDTO(gamefieldGraph);
        dto.save();
    }

    @Override
    public boolean containsGamefieldGraphByID(String id) {
        EbeanGamefieldDTO dto = Ebean.find(EbeanGamefieldDTO.class, id);
        return dto != null;
    }

    @Override
    public IGamefieldDTO getGamefieldById(String id) {
        return Ebean.find(EbeanGamefieldDTO.class, id);
    }

    @Override
    public void deleteGamefieldByID(String id) {
        if(containsGamefieldGraphByID(id)) {
            Ebean.delete(EbeanGamefieldDTO.class, id);
        }

    }
}
