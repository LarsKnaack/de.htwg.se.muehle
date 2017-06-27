package game; /**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

import com.google.inject.AbstractModule;
import controllers.IController;
import controllers.IGamefieldGraphAdapter;
import models.IMills;
import models.impl.GamefieldGraph;
import models.impl.Mills;
import persistence.IGamefieldDAO;
import play.libs.akka.AkkaGuiceSupport;

public class MuehleModule extends AbstractModule implements AkkaGuiceSupport {

    @Override
    protected void configure() {
        bindActor(GamefieldGraph.class, "gamefieldActor");
        bindActor(Mills.class, "millsActor");
        bind(IMills.class).to(models.impl.Mills.class);
        bind(IController.class).to(controllers.impl.Controller.class);
//      bind(IGamefieldDAO.class).to(persistence.couchdb.GamefieldGraphCouchdbDAO.class);
		bind(IGamefieldDAO.class).to(persistence.db4o.GamefieldDb4oDAO.class);
//      bind(IGamefieldDAO.class).to(persistence.hibernate.GamefieldHiberateDAO.class);
        bind(IGamefieldGraphAdapter.class).to(controllers.impl.GamefieldAdapter.class);
    }
}
