/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import controllers.IController;
import controllers.IGamefieldGraphAdapter;
import models.IMills;
import models.impl.GamefieldGraph;
import models.impl.Mills;
import persistence.IGamefieldDAO;
import persistence.db4o.GamefieldDb4oDAO;


public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(IMills.class).to(models.impl.Mills.class);
        bind(IController.class).to(controllers.impl.Controller.class);
        bind(IGamefieldGraphAdapter.class).to(controllers.impl.GamefieldAdapter.class);
        bind(IGamefieldDAO.class).to(GamefieldDb4oDAO.class);
    }
}
