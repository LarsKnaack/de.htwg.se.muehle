package service;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.google.inject.Guice;
import com.google.inject.Inject;
import controller.IController;
import game.MuehleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;

public class RestService extends AllDirectives {

    private static volatile Object shutdownSwitch;
    private static Logger LOGGER;
    private IController controller;

    @Inject
    public RestService(IController controller) {
        this.controller = controller;
        LOGGER = LoggerFactory.getLogger(this.getClass());
        shutdownSwitch = new Object();
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("routes");
        Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        RestService app = Guice.createInjector(new MuehleModule()).getInstance(RestService.class);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);
        LOGGER.info("Server online at http://localhost:8080/\n");
        Thread runner = new Thread(() -> {
            try {
                synchronized (shutdownSwitch) {
                    shutdownSwitch.wait();
                }
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }

            binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> actorSystem.terminate());
            LOGGER.info("REST Server shutdown");
        });
        runner.start();
    }

    private Route createRoute() {
        return route(
                pathPrefix("stone", () ->
                        route(
                                pathPrefix(integerSegment(), vertex ->
                                        route(
                                                pathEndOrSingleSlash(() -> getVertexColor(vertex)),
                                                path(integerSegment(), end -> moveStone(vertex, end)),
                                                path(segment(), color -> setVertexColor(vertex, color))
                                        ))
                        )),
                path("q", () -> get(() -> {
                    synchronized (shutdownSwitch) {
                        shutdownSwitch.notify();
                    }
                    return complete("<h1>quitGame</h1>");
                }))
        );
    }

    private Route getVertexColor(int vertex) {
        return complete(String.valueOf(controller.getVertexColor(vertex)));
    }

    private Route setVertexColor(int vertex, String color) {
        boolean result = controller.setStone(vertex, color.charAt(0));
        return createHttpResponse(result);
    }

    private Route moveStone(Integer vertex, Integer end) {
        boolean result = controller.moveStone(vertex, end);
        return createHttpResponse(result);
    }


    private Route createHttpResponse(boolean result) {
        if (result) {
            return complete(StatusCodes.OK, controller.getGamefieldString());
        } else {
            return complete(StatusCodes.METHOD_NOT_ALLOWED);
        }
    }
}
