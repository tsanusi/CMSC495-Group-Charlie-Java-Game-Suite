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
enum MazeItems {FLOOR, WALL, ENTRANCE, EXIT, PLAYER_POSITION}
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
    Maze () {
        levelName = LevelNames.LEVEL_TWO;
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
        scanner = new Scanner(System.in);
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

            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }
    public static void main (String [] args) {
        Maze maze = new Maze();
        Container contentPane = Frame.getContentPane();
        gameScreen.addKeyListener(kListener);
    }
}
class GameScreen extends Canvas {
    final int BLOCK_SIZE = 50;
    static int oldXposition,oldYposition,playerXPosition, playerYPosition;

    int xTotalDimensions;
    int yTotalDimensions;
    static MazeItems[][] mazeItems;
    boolean mazeIsDrawn;
    GameScreen ( MazeItems[][] mItems) {
        this.xTotalDimensions = BLOCK_SIZE * mItems.length;
        this.yTotalDimensions = BLOCK_SIZE * mItems[0].length;
        copyMazeItems(mItems);
        mazeIsDrawn = false;
    }
    public static void copyMazeItems(MazeItems[][] mItems, int newX, int newY) {
        oldXposition = playerXPosition;
        oldYposition = playerYPosition;
        playerYPosition = newX;
        playerYPosition = newY;
        copyMazeItems(mItems);
    }

    public static void copyMazeItems(MazeItems[][] mItems) {
        mazeItems = new MazeItems[mItems.length][mItems[0].length];
        for (int i = 0; i < mItems.length; i++) {
            for (int j = 0; j < mItems[0].length; j++) {
                mazeItems[i][j] = mItems[i][j];
            }
        }
    }
    public void paint(Graphics g) {
            g.setColor(Color.GRAY);
            g.drawRect(0, 0, xTotalDimensions, yTotalDimensions);
            g.fillRect(0, 0, xTotalDimensions, yTotalDimensions);
            fillColumn(g, 0);
            mazeIsDrawn = true;
    }
    private void fillColumn (Graphics g, int column) {
        fillRow (g,column,0);
        if (column < mazeItems.length - 1) {
            fillColumn(g,column + 1);
        }
    }
    private void fillRow (Graphics g, int column, int row) {
        int i = column, j = row;
        switch (mazeItems[column][row]) {
            case FLOOR: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
            case ENTRANCE: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.YELLOW);
                g.drawOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
            case EXIT: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.GREEN);
                g.drawOval((i * BLOCK_SIZE) , (j * BLOCK_SIZE) , BLOCK_SIZE, BLOCK_SIZE);
                g.fillOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
            case PLAYER_POSITION: {
                System.out.println("Draw Player at " + i + "," + j);
                playerXPosition = i;
                playerYPosition = j;
                repositionCharacter(g);
            }
        }
        if (row < mazeItems[column].length - 1) {
            fillRow(g,column,row + 1);
        }
    }
    private void repositionCharacter (Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect((playerXPosition * BLOCK_SIZE), (playerYPosition * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
        g.fillRect((playerXPosition * BLOCK_SIZE), (playerYPosition * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
        g.setColor(Color.BLUE);
        g.drawOval((playerXPosition * BLOCK_SIZE), (playerYPosition * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
        g.fillOval((playerXPosition * BLOCK_SIZE), (playerYPosition * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
    }

}
