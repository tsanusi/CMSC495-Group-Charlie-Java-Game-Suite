public class RepaintScreen extends Thread {

    public void run() {
        try {
            System.out.println ("Repaint Thread Running");
            Thread.sleep(50);
            Maze.gameScreen.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        run();
    }
}