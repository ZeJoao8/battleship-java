import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class EnemyBoardView extends BoardView {

    public EnemyBoardView(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Object m, int offsetX, int offsetY) throws Exception {
        BattleModel model = (BattleModel) m;
        Board enemy = model.getEnemyBoard();

        // Desenha a grade básica
        drawBoard(enemy, offsetX, offsetY, true);

        // Agora desenhar barcos atingidos ou afundados
        for (Boat b : enemy.getFleet()) {

            if (b.isSunk()) {
                BoatView.drawSunkBoat(b, offsetX, offsetY, g);
                continue;
            }

            for (int i = 0; i < b.getPositions().size(); i++) {
                if (b.isPartHit(i)) {
                    Position p = b.getPositions().get(i);
                    g.setForegroundColor(TextColor.Factory.fromString("#FF5555")); // vermelho
                    g.putString(offsetX + p.getX() * 2, offsetY + p.getY(), "X");
                }
            }
        }

        screen.refresh();
    }



}