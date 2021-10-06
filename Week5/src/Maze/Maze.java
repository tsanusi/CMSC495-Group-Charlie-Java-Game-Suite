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
    //xJButton restartButton;
    static JLabel statusDisplay;
    static Maze maze;
    static JFrame Frame;
    static GameScreen gameScreen;
    static TileMap mazeLevelData;
    static LevelNames levelName;
    static Scanner scanner;
    static EnemyMove em;
    static KeyListener kListener;
    boolean threadStarted;
    static int levelNumber;
    static PlayerStanding playerStanding;
    /*****************************
     * Maze Class Constructor    *
     * @param levelName          *
     *****************************/
    Maze (LevelNames levelName, int dc, int h, int m, int s) {
        playerStanding = new PlayerStanding();
        this.levelName = levelName;
        playerStanding = new PlayerStanding(dc,h,m,s);
        Frame = new JFrame("Maze Game");
        Frame.setResizable(false);
        //Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mazeLevelData = new TileMap(levelName);
        gameScreen = new GameScreen(mazeLevelData.blockSize,mazeLevelData.mazeGrid);
        statusDisplay = new JLabel("Click on screen and press Arrows to Start . . .");
        //statusDisplay.setEditable(false);
        Frame.add(statusDisplay);
        int yAt20Percent = (int)(((double)gameScreen.xTotalDimensions) * .2);
        Frame.setSize(gameScreen.xTotalDimensions  , gameScreen.yTotalDimensions + 100);
        statusDisplay.setBounds(40,gameScreen.yTotalDimensions+20,265,30);

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
                    playerStanding.start();
                    threadStarted = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }

    /***********************************************
     * This is the main method called to start the *
     * entire game                                 *
     * @param args                                 *
     ***********************************************/
    public static void main (String [] args) {
        levelNumber = 1;
        TitleScreen title = new TitleScreen();
        title.startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                playerStanding = new PlayerStanding();
                gameLevelSequence(false);
            }
        });
        title.instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayInstructions();
            }
        });
        title.highScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    /******************************************************
     * This method holds the instructions used to display *
     * a window which will display the instructions upon  *
     * request                                            *
     ******************************************************/
    public static void displayInstructions() {
        JFrame frame = new JFrame();
        //custom title, warning icon
        JOptionPane.showMessageDialog(frame,
                "Instructions \n" +
                        "Get the blue guy to the exit in each maze.\n" +
                        "Avoid the Red Guys. \n" +
                        "Use W,A,S,D or the Arrow keys to move your character",
                "Instructions:",
                JOptionPane.WARNING_MESSAGE);
    }

    /*******************************************************
     * This method is used to start the maze levels and    *
     * advance the levels if the mazes were successfully   *
     * completed.                                          *
     * @param winLevel                                     *
     *******************************************************/
    public static void gameLevelSequence(boolean winLevel) {
        if (winLevel) {
            levelNumber++;
        }
        if (levelNumber == 0) {
            levelNumber++;
            levelSelector(levelNumber,0,0,0,0);
        }
        else if (levelNumber > 8) {
            //win Game Sequence
        }
        else {
            levelSelector(levelNumber, playerStanding.deathCount,
                    playerStanding.hours, playerStanding.minutes, playerStanding.seconds);
        }
    }

    /*******************************************************************
     * This method  starts the maze depending on what level was called *
     * @param levelNumbers - the level number                          *
     * @param dc - deathcount from last maze                           *
     * @param h  - hours from last maze                                *
     * @param m  - minutes from last maze                              *
     * @param s  - seconds from last maze                              *
     *******************************************************************/
    public static void levelSelector (int  levelNumbers, int dc, int h, int m, int s) {
         maze = null;
        switch (levelNumbers) {
            case 1: { maze = new Maze(LevelNames.LEVEL_ONE, dc,h,m,s); break; }
            case 2: { maze = new Maze(LevelNames.LEVEL_TWO,dc,h,m,s); break; }
            case 3: { maze = new Maze(LevelNames.LEVEL_THREE, dc,h,m,s); break; }
            case 4: { maze = new Maze(LevelNames.LEVEL_FOUR, dc,h,m,s); break; }
            case 5: { maze = new Maze(LevelNames.LEVEL_FIVE, dc,h,m,s); break; }
            case 6: { maze = new Maze(LevelNames.LEVEL_SIX, dc,h,m,s);break; }
            case 7: { maze = new Maze(LevelNames.LEVEL_SEVEN, dc,h,m,s); break; }
            case 8: { maze = new Maze(LevelNames.LEVEL_EIGHT, dc,h,m,s); break; }
        }
        Container contentPane = maze.Frame.getContentPane();
        gameScreen.addKeyListener(kListener);
    }

    /**********************************************************************
     * The sequence of instructions when a maze is won!                   *
     * @param winLocationX                                                *
     * @param winLocationY                                                *
     **********************************************************************/
    public static void winMazeSequence (int winLocationX, int winLocationY) {
        JFrame wMs = new JFrame();
        em.setStopThread();
        Maze.mazeLevelData.mazeGrid[winLocationX][winLocationY] = MazeItems.PLAYER_WIN;
        Maze.gameScreen.repaint();
        playerStanding.stopClock();
        JOptionPane.showMessageDialog(wMs,"Good Job, You made it to the Exit!.");
        maze.Frame.setVisible(false);
        gameLevelSequence(true);

    }

    /***********************************************************************************
     * The instruction when an enemy catch the player. it shows a dialogue and and     *
     * restarts the maze.                                                              *
     * @param deathLocationX                                                           *
     * @param deathLocationY                                                           *
     ***********************************************************************************/
    public static void deathSequence(int deathLocationX, int deathLocationY) {
        JFrame f = new JFrame();
        em.setStopThread();
        Maze.mazeLevelData.mazeGrid[deathLocationX][deathLocationY] = MazeItems.PLAYER_DEAD;
        playerStanding.stopClock();
        playerStanding.playerDied();
        Maze.gameScreen.repaint();
        JOptionPane.showMessageDialog(f,"Uh oh, You were caught, Try again!.");
        maze.Frame.setVisible(false);
        gameLevelSequence(false);
    }

    private static void gameWinSequence () {

    }
}