package BattleShip.Controller;

import BattleShip.Model.Board;
import BattleShip.Model.Boat;
import BattleShip.Model.PlacementModel;
import BattleShip.View.PlacementView;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;


public class PlacementController {
    private final Screen screen;
    private final PlacementModel model;
    private final PlacementView view;

    public PlacementController(Screen screen, Board board) {
        this.screen = screen;
        this.model = new PlacementModel(board);
        this.view = new PlacementView(screen);
    }

    public boolean run() throws Exception {
        while (true) {
            view.draw(model);
            Board board=model.getBoard();
            Boat current=board.getCurrentBoat();

            if (board.allPlaced()) return true;

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            KeyType kt = key.getKeyType();
            if (kt == KeyType.ArrowUp) {
                if (model.getCursorY() > 0) model.moveUp();
            } else if (kt == KeyType.ArrowDown) {
                if ((model.getCursorY() < model.getBoard().getHeigth()- current.getSize() && !current.isHorizontal()) || (model.getCursorY() < model.getBoard().getHeigth()-1 && current.isHorizontal())) model.moveDown();
            } else if (kt == KeyType.ArrowLeft) {
                if (model.getCursorX() > 0) model.moveLeft();
            } else if (kt == KeyType.ArrowRight) {
                if ((model.getCursorX() < model.getBoard().getWidth()-current.getSize() && current.isHorizontal()) || (model.getCursorX() < model.getBoard().getWidth()-1 && !current.isHorizontal()) ) model.moveRight();
            } else if (kt == KeyType.Character) {
                char c = key.getCharacter();
                if (c == 'q' || c == 'Q') return false;
                if (c == 'r' || c == 'R') {
                    int x = model.getCursorX();
                    int y = model.getCursorY();
                    int boardWidth = model.getBoard().getWidth();
                    int boardHeight = model.getBoard().getHeigth();
                    int size = current.getSize();
                    current.rotate();
                    if (current.isHorizontal()) {
                        if (x + size > boardWidth) {
                            for(int i=0;i<x+size-boardWidth;i++) model.moveLeft();
                        }
                    } else {
                        if (y + size > boardHeight) {
                            for(int i=0;i<y+size-boardHeight;i++) model.moveUp();
                        }
                    }
                }
            } else if (kt == KeyType.Enter) {
                int x = model.getCursorX();
                int y = model.getCursorY();
                boolean horiz = current.isHorizontal();
                if (board.canPlaceBoat(current,x, y, horiz)) {
                    model.getBoard().placeBoat(current,x, y, horiz);
                } else {
                    System.out.println("Posição inválida para " + current.getName());
                }
            } else if (kt == KeyType.Escape || kt == KeyType.EOF) {
                return false;
            }
        }
    }
}

