package Maze;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
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
        title.startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                levelSelector(1);
            }
        });
        title.instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        title.highScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public static void displayInstructions() {

    }

    public static void gameLevelSequence() {


    }
    public static void gameLevelSequence (int levelNumber) {

    }
    public static void levelSelector (int  levelNumbers) {
        Maze maze;
        switch (levelNumbers) {
            case 1: { maze = new Maze(LevelNames.LEVEL_ONE); break; }
            case 2: { maze = new Maze(LevelNames.LEVEL_TWO); break; }
            case 3: { maze = new Maze(LevelNames.LEVEL_THREE); break; }
            case 4: { maze = new Maze(LevelNames.LEVEL_FOUR); break; }
            case 5: { maze = new Maze(LevelNames.LEVEL_FIVE); break; }
            case 6: { maze = new Maze(LevelNames.LEVEL_SIX);break; }
            case 7: { maze = new Maze(LevelNames.LEVEL_SEVEN); break; }
            case 8: { maze = new Maze(LevelNames.LEVEL_EIGHT); break; }
            case 9: { maze = new Maze(LevelNames.LEVEL_NINE); break; }
            case 10: { maze = new Maze(LevelNames.LEVEL_TEN); break; }
            case 11: { maze = new Maze(LevelNames.LEVEL_ELEVEN); break; }
            case 12: { maze = new Maze(LevelNames.LEVEL_TWELVE); break; }
        }
        Container contentPane = Frame.getContentPane();
        gameScreen.addKeyListener(kListener);

    }
    public static void winMazeSequence (int winLocationX, int winLocationY) {
        JFrame wMs = new JFrame();
        em.setStopThread();
        Maze.mazeLevelData.mazeGrid[winLocationX][winLocationY] = MazeItems.PLAYER_WIN;
        Maze.gameScreen.repaint();
        JOptionPane.showMessageDialog(wMs,"Good Job, You made it to the Exit!.");
        levelSelector(2);
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