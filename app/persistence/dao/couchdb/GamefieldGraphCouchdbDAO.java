package persistence.dao.couchdb;

import models.IGamefieldGraph;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dao.IGamefieldDAO;
import persistence.dto.IGamefieldDTO;
import persistence.dto.IVertexDTO;
import persistence.dto.couchdb.CouchDbGamefieldDTO;
import persistence.dto.couchdb.CouchDbVertexDTO;
import play.api.Play;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

public class GamefieldGraphCouchdbDAO implements IGamefieldDAO {

    private CouchDbConnector db = null;
    private Logger logger = LoggerFactory.getLogger("persistence.dao.couchdb");

    public GamefieldGraphCouchdbDAO() {
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();

        } catch (MalformedURLException e) {
            logger.error("Malformed URL", e);
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("mills_db", true);
        db.createDatabaseIfNotExists();
    }

    private IGamefieldGraph copyGamefieldGraph(CouchDbGamefieldDTO pgamefield) {
        if (pgamefield == null) {
            return null;
        }

        IGamefieldGraph gamefieldGraph = Play.current().injector().instanceOf(IGamefieldGraph.class);

        gamefieldGraph.setId(pgamefield.getId());

        for (IVertexDTO vertex : pgamefield.getVertexs()) {
            int v = vertex.getVertex();
            char color = vertex.getColor();

            gamefieldGraph.setStoneVertex(v, color);
        }

        return gamefieldGraph;
    }

    private CouchDbGamefieldDTO copyGamefieldGraph(IGamefieldDTO gamefieldGraph) {
        if (gamefieldGraph == null) {
            return null;
        }

        String gamefieldGraphId = gamefieldGraph.getId();
        CouchDbGamefieldDTO pGamefieldGraph;
        if (containsGamefieldGraphByID(gamefieldGraphId)) {
            // The Object already exists within the session
            pGamefieldGraph = db.find(CouchDbGamefieldDTO.class, gamefieldGraphId);

            // Synchronize values
            for (IVertexDTO vertex : pGamefieldGraph.getVertexs()) {
                Integer v = vertex.getVertex();

                vertex.setColor(gamefieldGraph.getVertexs().get(v).getColor());
            }

        } else {
            // A new database entry
            pGamefieldGraph = new CouchDbGamefieldDTO();

            Set<CouchDbVertexDTO> vertexs = new HashSet<CouchDbVertexDTO>();

            for(int i = 0; i < 24; i++) {
                char color = gamefieldGraph.getVertexs().get(i).getColor();

                CouchDbVertexDTO vertex = new CouchDbVertexDTO();
                vertex.setVertex(i);
                vertex.setColor(color);

                vertexs.add(vertex);
            }


        }

        pGamefieldGraph.setId(gamefieldGraphId);

        return pGamefieldGraph;
    }

    @Override
    public void saveGameField(IGamefieldGraph gamefieldGraph) {

        CouchDbGamefieldDTO dto = new CouchDbGamefieldDTO(gamefieldGraph);
        if (containsGamefieldGraphByID(gamefieldGraph.getId())) {
            db.update(copyGamefieldGraph(dto));
        } else {
            db.create(gamefieldGraph.getId(), copyGamefieldGraph(dto));
        }
    }

    @Override
    public boolean containsGamefieldGraphByID(String id) {
        if (getGamefieldById(id) == null) {
            return false;
        }
        return true;
    }


    @Override
    public IGamefieldDTO getGamefieldById(String id) {
        CouchDbGamefieldDTO g = db.find(CouchDbGamefieldDTO.class, id);
        if (g == null) {
            return null;
        }return new CouchDbGamefieldDTO(copyGamefieldGraph(g));
    }

    @Override
    public void deleteGamefieldByID(String id) {
        db.delete(copyGamefieldGraph(getGamefieldById((id))));
    }


}