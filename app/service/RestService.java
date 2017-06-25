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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.IController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.integerSegment;
import static akka.http.javadsl.server.PathMatchers.segment;


@Singleton
public class RestService extends AllDirectives {

    private static volatile Object shutdownSwitch;
    private static Logger LOGGER;
    private IController controller;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Inject
    public RestService(IController controller) {
        LOGGER = LoggerFactory.getLogger(this.getClass());
        this.controller = controller;
        shutdownSwitch = new Object();

        Thread thread = new Thread(this::startRestService);
        thread.start();
    }

    private synchronized void startRestService() {
        ActorSystem actorSystem = ActorSystem.create("morris.routes");
        Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = this.createRoute().flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost(Endpoints.BASE_HOST, Endpoints.BASE_PORT), materializer);
        LOGGER.info("Server online at " + Endpoints.BASE_URL + "\n");

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
                path(segment("update"), () -> createHttpResponse(true)),
                path(segment("handleInput").slash(integerSegment()), this::handleInput),
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

    private Route handleInput(Integer input) {
        boolean result;
        if (controller.getCurrentStonesToDelete() > 0) {
            result = controller.millDeleteStone(input);
        } else if (controller.requireInitial()) {
            result = controller.setStone(input);
        } else {
            result = controller.moveStone(input);
        }
        return createHttpResponse(result);
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
            ObjectNode responseBody = mapper.createObjectNode();
            responseBody.put("gamefield", controller.getGamefieldString());
            responseBody.set("stones",
                    mapper.createObjectNode()
                            .put("Player1", 9 - controller.getConsumedStonesPlayer1())
                            .put("Player2", 9 - controller.getConsumedStonesPlayer2())
            );
            LOGGER.info("Sending Message: " + responseBody.toString());
            return complete(StatusCodes.OK, responseBody.toString());
        } else {
            return complete(StatusCodes.METHOD_NOT_ALLOWED);
        }
    }
}
