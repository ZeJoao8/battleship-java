package battleship.model.game;

public class GameTimer implements Runnable {

    private volatile boolean running = true;
    private int seconds = 0;
    private Thread thread;

    public GameTimer() {
        thread = new Thread(this, "GameTimerThread");
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                seconds++;
            } catch (InterruptedException ignored) {}
        }
    }

    public void stop() {
        running = false;
    }

    public int getSeconds() {
        return seconds;
    }
}
