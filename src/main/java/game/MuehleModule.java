/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package game;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import controller.IController;
import controller.IGamefieldGraphAdapter;
import model.IMills;
import model.impl.GamefieldGraph;
import model.impl.Mills;
import persistence.IGamefieldDAO;
import persistence.db4o.GamefieldDb4oDAO;


public class MuehleModule extends AbstractModule {

    ActorSystem system = ActorSystem.create("MuehleSystem");

    @Override
    protected void configure() {
        bind(IMills.class).to(model.impl.Mills.class);
        bind(IController.class).to(controller.impl.Controller.class);
        bind(IGamefieldGraphAdapter.class).to(controller.impl.GamefieldAdapter.class);
        bind(IGamefieldDAO.class).to(GamefieldDb4oDAO.class);
    }

    @Provides
    @Named("gamefieldActor")
    public ActorRef getGamefieldActor() {
        return system.actorOf(Props.create(GamefieldGraph.class));
    }

    @Provides
    @Named("millsActor")
    public ActorRef getMillsActor() {
        return system.actorOf(Props.create(Mills.class));
    }
}
