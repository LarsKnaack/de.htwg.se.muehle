package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class RestController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private IController morrisController;

    @Inject
    public RestController(IController morrisController) {
        this.morrisController = morrisController;
    }

    public Result index() {
        return ok(); //views.html.morrisindex.render());
    }

    public Result update() {
        return ok(createJsonBody());
    }

    public Result jsInput(){
        return ok(createJsonBody());
    }

    public Result handleInput(int vertex) {
        System.out.println("HandleInput");
        boolean success;
        if(morrisController.getCurrentStonesToDelete() > 0) {
            success = morrisController.millDeleteStone(vertex);
        } else if(morrisController.requireInitial()) {
            success = morrisController.setStone(vertex);
        } else {
            success = morrisController.moveStone(vertex);
        }
        if(success) {
            return ok(createJsonBody());
        } else {
            return badRequest();
        }
    }

    public Result getVertexColor(int vertex) {
        return ok(String.valueOf(morrisController.getVertexColor(vertex)));
    }

    public Result setVertexColor(int vertex, String color) {
        boolean success = morrisController.setStone(vertex, color.charAt(0));
        if(success) {
            return ok(createJsonBody());
        } else {
            return badRequest();
        }
    }

    public Result moveStone(int start, int end) {
        boolean success = morrisController.moveStone(start, end);
        if(success) {
            return ok(createJsonBody());
        } else {
            return badRequest();
        }
    }

    private JsonNode createJsonBody() {
        ObjectNode responseBody = MAPPER.createObjectNode();
        responseBody.put("gamefield", morrisController.getGamefieldString());
        responseBody.put("stones",
                MAPPER.createObjectNode()
                        .put("Player1", 9 - morrisController.getConsumedStonesPlayer1())
                        .put("Player2", 9 - morrisController.getConsumedStonesPlayer2())
        );
        return responseBody;
    }

}