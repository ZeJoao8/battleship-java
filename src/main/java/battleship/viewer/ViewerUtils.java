package battleship.viewer;

import battleship.gui.GUI;

public final class ViewerUtils {

    private ViewerUtils() {}

    public static int centerX(GUI gui, String text) {
        return (gui.getWidth() - text.length()) / 2;
    }

    public static int centerY(GUI gui, int height) {
        return (gui.getHeight() - height) / 2;
    }
}
