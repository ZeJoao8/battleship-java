package battleship.states;

import battleship.controller.Controller;
import battleship.model.battle.BattleModel;
import battleship.controller.battle.BattleController;
import battleship.viewer.Viewer;
import battleship.viewer.battle.BattleViewer;

public class BattleState extends State<BattleModel> {
    public BattleState(BattleModel model) { super(model); }
    @Override protected Viewer<BattleModel> getViewer() { return new BattleViewer(getModel()); }
    @Override protected Controller<BattleModel> getController() { return new BattleController(getModel()); }
}
