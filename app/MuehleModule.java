/**
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
import persistence.IGamefieldDAO;
import persistence.couchdb.GamefieldGraphCouchdbDAO;

public class MuehleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IMills.class).to(models.impl.Mills.class);
        bind(IController.class).to(controllers.impl.Controller.class);
//      bind(IGamefieldDAO.class).to(persistence.couchdb.GamefieldGraphCouchdbDAO.class);
		//bind(IGamefieldDAO.class).to();
//      bind(IGamefieldDAO.class).to(persistence.hibernate.GamefieldHiberateDAO.class);
        bind(IGamefieldGraphAdapter.class).to(controllers.impl.GamefieldAdapter.class);
    }
}
