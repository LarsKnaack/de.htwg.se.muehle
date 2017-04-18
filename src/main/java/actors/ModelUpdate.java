package actors;

import controller.IGamefieldGraphAdapter;
import model.IGamefieldGraph;
import model.IMills;
import model.IPlayer;

/**
 * Created by Lars on 18.04.2017.
 */
public class ModelUpdate {

    private IGamefieldGraph gamefieldGraph;
    private IGamefieldGraphAdapter gamefieldGraphAdapter;
    private IMills mills;
    private IPlayer player;

    public IPlayer getPlayer() {
        return player;
    }

    public void setPlayer(IPlayer player) {
        this.player = player;
    }

    public IMills getMills() {
        return mills;
    }

    public void setMills(IMills mills) {
        this.mills = mills;
    }

    public IGamefieldGraphAdapter getGamefieldGraphAdapter() {
        return gamefieldGraphAdapter;
    }

    public void setGamefieldGraphAdapter(IGamefieldGraphAdapter gamefieldGraphAdapter) {
        this.gamefieldGraphAdapter = gamefieldGraphAdapter;
    }

    public IGamefieldGraph getGamefieldGraph() {
        return gamefieldGraph;
    }

    public void setGamefieldGraph(IGamefieldGraph gamefieldGraph) {
        this.gamefieldGraph = gamefieldGraph;
    }
}
