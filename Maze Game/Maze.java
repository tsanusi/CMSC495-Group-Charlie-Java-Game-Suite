/////////////////////////////////////////////////////////////
// Maze Game                                               //
// CMSC 495 FALL 2021 Section                              //
// Professor: Mark Munoz                                   //
// Programmers: Wayne Mack                                 //
/////////////////////////////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
enum Directions {UP, DOWN, LEFT, RIGHT}
enum MazeItems {FLOOR, WALL, EXIT, PLAYER_POSITION, ENEMY_POSITION}
enum LevelNames {LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, LEVEL_FOUR, LEVEL_FIVE, LEVEL_SIX, LEVEL_SEVEN,
    LEVEL_EIGHT, LEVEL_NINE, LEVEL_TEN, LEVEL_ELEVEN, LEVEL_TWELVE}
public class Maze {
    JButton restartButton;
    JTextField timeDisplay;
    static JFrame Frame;
    static GameScreen gameScreen;
    static TileMap mazeLevelData;
    static LevelNames levelName;
    static Scanner scanner;
    static KeyListener kListener;
    Maze (LevelNames levelName) {
        this.levelName = levelName;
        Frame = new JFrame("Maze Game");
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mazeLevelData = new TileMap(levelName);
        gameScreen = new GameScreen(mazeLevelData.mazeGrid);
        int xAt20Percent = (int)(((double)gameScreen.xTotalDimensions)* .2);
        int yAt20Percent = (int)(((double)gameScreen.xTotalDimensions) * .2);
        Frame.setSize(gameScreen.xTotalDimensions  , gameScreen.yTotalDimensions + yAt20Percent);
        Frame.add(gameScreen);
        Frame.setVisible(true);
        EnemyMove em = new EnemyMove();
        RepaintScreen rs = new RepaintScreen();
        rs.start();
        em.start();
        kListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP: {
                        mazeLevelData.move(Directions.UP);
                        break;
                    }
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN: {
                        mazeLevelData.move(Directions.DOWN);
                        break;
                    }
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT: {
                        mazeLevelData.move(Directions.LEFT);
                        break;
                    }
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT: {
                        mazeLevelData.move(Directions.RIGHT);
                        break;
                    }
                }


            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }
    public static void main (String [] args) {
        Maze maze = new Maze(LevelNames.LEVEL_TWO);
        Container contentPane = Frame.getContentPane();
        gameScreen.addKeyListener(kListener);
    }
}

class RepaintScreen extends Thread {

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
class EnemyMove extends Thread {
    boolean stopThread;
    @Override
    public void run() {
        stopThread = false;
        while (!stopThread) {
            try {
                System.out.println("Enemy Move Thread running");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            enemyMove(0);
            Maze.gameScreen.repaint();
        }
    }
    public void stopEnemyMovement () {
        stopThread = true;
    }
    private void enemyMove (int columnNumber) {
        enemyMove(columnNumber,0);
        if (columnNumber < Maze.mazeLevelData.mazeGrid.length - 1) {
            enemyMove(columnNumber + 1);
        }
    }
    private void enemyMove (int columnNumber, int rowNumber) {
        boolean moved;
        if (Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] == MazeItems.ENEMY_POSITION) {
            int xDeviation = Maze.mazeLevelData.playerX - columnNumber;
            int yDeviation = Maze.mazeLevelData.playerY - rowNumber;
            if (absoluteValue(xDeviation) > absoluteValue(yDeviation)) {
                if (!moveLeftRight(columnNumber, rowNumber)) {
                    moveUpDown(columnNumber, rowNumber);
                }
            } else if (!moveUpDown(columnNumber, rowNumber)) {
                moveLeftRight(columnNumber, rowNumber);
            }
        }
        if (rowNumber < Maze.mazeLevelData.mazeGrid[columnNumber].length - 1) {
            enemyMove(columnNumber, rowNumber + 1);
        }
        Maze.gameScreen.repaint();
    }
    private int absoluteValue(int i) {
        if (i > 0) {
            return i;
        }
        else {
            return i + (-2 * i);
        }
    }
    private boolean canMove (int x, int y, Directions direction) {
        switch (direction) {
            case UP: {
                y = y -1;
                break;
            }
            case DOWN: {
                y = y + 1;
                break;
            }
            case LEFT: {
                x = x - 1;
                break;
            }
            case RIGHT: {
                x = x + 1;
                break;
            }
        }
        return (!Maze.mazeLevelData.isPresent(x,y ,MazeItems.WALL)) &&
                (!Maze.mazeLevelData.isPresent(x,y ,MazeItems.ENEMY_POSITION));
    }
    private boolean moveLeftRight(int columnNumber, int rowNumber) {
        System.out.println("MOVE_LEFT_RIGHT");
        if (Maze.mazeLevelData.playerX < columnNumber && canMove(columnNumber,rowNumber,Directions.LEFT) ) {
            System.out.println("LEFT true");
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            Maze.mazeLevelData.mazeGrid[columnNumber - 1][rowNumber] = MazeItems.ENEMY_POSITION;
            return true;
        }
        else if (Maze.mazeLevelData.playerX > columnNumber && canMove(columnNumber,rowNumber,Directions.RIGHT) ) {
            System.out.println("RIGHT true");
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            Maze.mazeLevelData.mazeGrid[columnNumber + 1 ][rowNumber] = MazeItems.ENEMY_POSITION;
            return true;
        }
        return false;
    }
    private boolean moveUpDown(int columnNumber, int rowNumber) {
        System.out.println("MOVE_UP_DOWN");
        if (Maze.mazeLevelData.playerY < rowNumber && canMove(columnNumber,rowNumber,Directions.UP) ) {
            System.out.println("UP True");
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber - 1] = MazeItems.ENEMY_POSITION;
            return true;
        }
        else if (Maze.mazeLevelData.playerX > rowNumber && canMove(columnNumber,rowNumber,Directions.DOWN) ) {
            System.out.println("DOWN True");
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber + 1] = MazeItems.ENEMY_POSITION;
            return true;
        }
        return false;
    }
}
