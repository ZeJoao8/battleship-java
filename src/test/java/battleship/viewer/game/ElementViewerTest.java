package battleship.viewer.game;

import battleship.model.Position;
import battleship.gui.GUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementViewerTest {

    record Fake(int x){}

    @Test
    void elementViewerStoresModel() {
        Fake f = new Fake(8);
        ElementViewer<Fake> v = new ElementViewer<>(f) {
            @Override public void draw(GUI gui, Position p){}
        };
        assertEquals(8, v.getModel().x());
    }
}
