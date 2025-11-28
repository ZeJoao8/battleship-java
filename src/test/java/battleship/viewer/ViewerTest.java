package battleship.viewer;

import battleship.gui.GUI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ViewerTest {

    static class TestViewer extends Viewer<Object> {
        boolean drawCalled = false;

        public TestViewer(Object model) {
            super(model);
        }

        @Override
        protected void drawElements(GUI gui) {
            drawCalled = true;
        }
    }

    @Test
    void draw_callsGuiMethodsAndDrawElements() throws IOException {
        GUI gui = mock(GUI.class);
        TestViewer viewer = new TestViewer(new Object());

        viewer.draw(gui);

        verify(gui).clear();
        verify(gui).fillBackground("#010326");
        verify(gui).refresh();
        assert(viewer.drawCalled);
    }
}

