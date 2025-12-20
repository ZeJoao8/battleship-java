package battleship.states.game;

import battleship.controller.Controller;
import battleship.controller.game.BattleController;
import battleship.model.game.BattleModel;
import battleship.states.State;
import battleship.viewer.Viewer;
import battleship.viewer.game.battle.BattleViewer;

public class BattleState extends State<BattleModel> {
    public BattleState(BattleModel model) { super(model); }
    @Override protected Viewer<BattleModel> getViewer() { return new BattleViewer(getModel()); }
    @Override protected Controller<BattleModel> getController() { return new BattleController(getModel()); }
}
