//////////////////////////////////////////////
// CMSC 495 - Fall 2021                     //
// Professor Mark Munoz                     //
//------------------------------------------//
// Maze Game                                //
// Written in Java:                         //
//------------------------------------------//
// Class: Maze.java                         //
// This class is the main Class for the     //
// Maze game                                //
//////////////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
/**********************************************
 * This is the main class for the Maze game.  *
 * This holds all class variables.            *
 **********************************************/
public class Maze {
    JButton restartButton;
    JTextField timeDisplay;
    static JFrame Frame;
    static GameScreen gameScreen;
    static TileMap mazeLevelData;
    static LevelNames levelName;
    static Scanner scanner;
    static EnemyMove em;
    static KeyListener kListener;
    boolean threadStarted;
    /*****************************
     * Maze Class Constructor    *
     * @param levelName          *
     *****************************/
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
        threadStarted = false;
        Frame.setVisible(true);
        em = new EnemyMove();
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
                gameScreen.repaint();
                if (!threadStarted) {
                    em.start();
                    threadStarted = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }
    public static void main (String [] args) {
        TitleScreen title = new TitleScreen();
        /*
        Maze maze = new Maze(LevelNames.LEVEL_TWO);
        Container contentPane = Frame.getContentPane();
        gameScreen.addKeyListener(kListener);
        */
    }
    public static void winMazeSequence (int winLocationX, int winLocationY) {
        JFrame wMs = new JFrame();
        em.setStopThread();
        Maze.mazeLevelData.mazeGrid[winLocationX][winLocationY] = MazeItems.PLAYER_WIN;
        Maze.gameScreen.repaint();
        JOptionPane.showMessageDialog(wMs,"Good Job, You made it to the Exit!.");
        Maze.main(null);
    }
    public static void deathSequence(int deathLocationX, int deathLocationY) {
        JFrame f = new JFrame();
        em.setStopThread();
        Maze.mazeLevelData.mazeGrid[deathLocationX][deathLocationY] = MazeItems.PLAYER_DEAD;
        Maze.gameScreen.repaint();
        JOptionPane.showMessageDialog(f,"Uh oh, You were caught, Try again!.");
        Maze.main(null);
    }
}