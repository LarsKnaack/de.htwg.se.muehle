package model;

import actors.ModelUpdate;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by Lars on 18.04.2017.
 */
public abstract class AbstractModel extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    public Class<String> type;

    protected abstract void handleMessage( ModelUpdate update);

    @Override
    public Receive createReceive() {
        // TODO: HandleMessage in Receive
        return receiveBuilder().match(ModelUpdate.class, update-> handleMessage(update))
                .matchAny( o -> log.info("Received unhandled message")).build();
    }
}
