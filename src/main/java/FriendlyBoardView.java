import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class FriendlyBoardView extends BoardView {

    public FriendlyBoardView(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Object m,int x,int y) throws Exception {
        BattleModel model = (BattleModel) m;
        Board board = model.getPlayerBoard();

        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        drawBoard(board, x, y,false);

        for (Boat b : board.getFleet()) {
            BoatView.drawBoat(b, x, y, g);
        }

        screen.refresh();
    }

}
