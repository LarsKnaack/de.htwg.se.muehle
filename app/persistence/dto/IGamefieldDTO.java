package persistence.dto;

import persistence.dto.couchdb.CouchDbVertexDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by Lars on 27.06.2017.
 */
public interface IGamefieldDTO {

    String getId();
    void setId(String id);

    List<IVertexDTO> getVertexs();
    void setVertexs(List<IVertexDTO> vertexs);

}
