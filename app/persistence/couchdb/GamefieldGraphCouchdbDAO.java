package persistence.couchdb;

import models.impl.GamefieldGraph;
import models.IGamefieldGraph;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.IGamefieldDAO;
import play.api.Play;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

public class GamefieldGraphCouchdbDAO implements IGamefieldDAO {

    private CouchDbConnector db = null;
    private Logger logger = LoggerFactory.getLogger("persistence.couchdb");

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

    private IGamefieldGraph copyGamefieldGraph(PersistentGamefieldGraph pgamefield) {
        if (pgamefield == null) {
            return null;
        }

        IGamefieldGraph gamefieldGraph = new GamefieldGraph();

        gamefieldGraph.setId(pgamefield.getId());

        for (PersistentVertex vertex : pgamefield.getVertexs()) {
            int v = vertex.getVertex();
            char color = vertex.getColor();

            gamefieldGraph.setStoneVertex(v, color);
        }

        return gamefieldGraph;
    }

    private PersistentGamefieldGraph copyGamefieldGraph(IGamefieldGraph gamefieldGraph) {
        if (gamefieldGraph == null) {
            return null;
        }

        String gamefieldGraphId = gamefieldGraph.getId();
        PersistentGamefieldGraph pGamefieldGraph;
        if (containsGamefieldGraphByID(gamefieldGraphId)) {
            // The Object already exists within the session
            pGamefieldGraph = (PersistentGamefieldGraph) db.find(PersistentGamefieldGraph.class, gamefieldGraphId);

            // Synchronize values
            for (PersistentVertex vertex : pGamefieldGraph.getVertexs()) {
                Integer v = vertex.getVertex();

                vertex.setColor(gamefieldGraph.getStoneColorVertex(v));
            }

        } else {
            // A new database entry
            pGamefieldGraph = new PersistentGamefieldGraph();

            Set<PersistentVertex> vertexs = new HashSet<PersistentVertex>();

            for(int i = 0; i < 24; i++) {
                char color = gamefieldGraph.getStoneColorVertex(i);

                PersistentVertex vertex = new PersistentVertex();
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
        if (containsGamefieldGraphByID(gamefieldGraph.getId())) {
            db.update(copyGamefieldGraph(gamefieldGraph));
        } else {
            db.create(gamefieldGraph.getId(), copyGamefieldGraph(gamefieldGraph));
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
    public IGamefieldGraph getGamefieldById(String id) {
        PersistentGamefieldGraph g = db.find(PersistentGamefieldGraph.class, id);
        if (g == null) {
            return null;
        }
        return copyGamefieldGraph(g);
    }

    @Override
    public void deleteGamefieldByID(String id) {
        db.delete(copyGamefieldGraph(getGamefieldById((id))));
    }


}