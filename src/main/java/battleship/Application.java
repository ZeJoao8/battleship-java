package battleship;

public class Application {
    public static void main(String[] args) {
        try {
            new Game().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

