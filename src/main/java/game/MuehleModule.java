/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package game;

import com.google.inject.AbstractModule;
import controller.IController;
import controller.IGamefieldGraphAdapter;
import model.IGamefieldGraph;
import model.IMills;


public class MuehleModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IGamefieldGraph.class).to(model.impl.GamefieldGraph.class);
        bind(IMills.class).to(model.impl.Mills.class);
        bind(IController.class).to(controller.impl.Controller.class);
        bind(IGamefieldGraphAdapter.class).to(controller.impl.GamefieldAdapter.class);

    }

}
