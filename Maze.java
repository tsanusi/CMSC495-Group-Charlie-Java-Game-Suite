/////////////////////////////////////////////////////////////
// Maze Game                                               //
// CMSC 495 FALL 2021 Section                              //
// Professor: Mark Munoz                                   //
// Programmers: Wayne Mack                                 //
/////////////////////////////////////////////////////////////
package Maze;
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
    static MazeLevelData mazeLevelData;
    static LevelNames levelName;
    static Scanner scanner;
    static KeyListener kListener;
    Maze () {
        levelName = LevelNames.LEVEL_ONE;
        Frame = new JFrame("Maze Game");
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mazeLevelData = new MazeLevelData(levelName);
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
                System.out.println("Working");
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
    final int BLOCK_SIZE = 35;
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
        g.drawRect(0,0,xTotalDimensions,yTotalDimensions);
        g.fillRect(0,0,xTotalDimensions,yTotalDimensions);
        for (int i = 0; i < mazeItems.length; i++) {
            for (int j = 0; j <mazeItems[0].length; j++) {
                switch (mazeItems[i][j]) {
                    case WALL: {
                        /*g.setColor(Color.GRAY);
                        g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                        g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);*/
                        break;
                    }
                    case FLOOR: {
                        g.setColor(Color.BLACK);
                        g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                        g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
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
                        g.setColor(Color.BLACK);
                        g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                        g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                        g.setColor(Color.BLUE);
                        g.drawOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                        g.fillOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }
    }
}
class MazeLevelData {
    String levelName;
    MazeItems [][] mazeGrid;
    int xDimensions, yDimensions,
            playerX, playerY,
            startX,startY,
            finishX,FinishY;
    MazeLevelData (LevelNames levelName) {
        switch (levelName) {
            case LEVEL_ONE: {
                setDimensions(10,10);
                makeVerticalPath(1,1,7);
                makeHorizontalPath(1,3,1);
                makeVerticalPath(3,1,3);
                makeHorizontalPath(1,8,5);
                makeHorizontalPath(3,5,3);
                makeVerticalPath(4,4,6);
                makeHorizontalPath(1,7,7);
                makeVerticalPath(1,1,10);
                makeHorizontalPath(1,10,1);
                makeExit(3,5);
                makePlayer(2,1);
                break;
            }
            case LEVEL_TWO: {

            }
            case LEVEL_THREE: {

            }
            case LEVEL_FOUR: {

            }
            case LEVEL_FIVE: {

            }
            case LEVEL_SIX: {

            }
            case LEVEL_SEVEN: {

            }
            case LEVEL_EIGHT: {

            }
            case LEVEL_NINE: {

            }
            case LEVEL_TEN: {

            }
            case LEVEL_ELEVEN: {

            }
            case LEVEL_TWELVE: {

            }
        }
    }
    private int getxDimensions() {
        return xDimensions;
    }
    private int getyDimensions() {
        return yDimensions;
    }
    private void setDimensions (int xRows, int yRows) {
        mazeGrid = new MazeItems[xRows + 2][yRows + 2];
        this.xDimensions = 20 * (xRows + 2);
        this.yDimensions = 20 * (yRows + 2);
        for (int i = 0; i <= xRows + 1; i++) {
            for (int j = 0; j <= yRows+1; j++ ) {
                mazeGrid[i][j] = MazeItems.WALL;
            }
        }
    }
    private void makePlayer(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        repositionPlayer();
    }
    private void makeExit(int x, int y) {
        mazeGrid[x][y] = MazeItems.EXIT;
    }
    private void makeFloor(int x, int y) {
        mazeGrid[x][y] = MazeItems.FLOOR;
    }
    private void makeVerticalPath (int columnPoint, int firstRowPoint, int secondRowPoint) {
        for (int yRow = firstRowPoint; yRow <= secondRowPoint; yRow++ ) {
            makeFloor(columnPoint,yRow);
        }
    }
    private void makeHorizontalPath (int x1, int x2,int y) {
        System.out.println (x1 + " " + x2);
        for (int xColumn = x1; xColumn <= x2; xColumn++) {
            makeFloor(xColumn,y);
            System.out.println(xColumn);
        }
    }
    private Boolean canMoveto (int x, int y) {
        return mazeGrid[x][y] != MazeItems.WALL;
    }
    private void repositionPlayer () {
        mazeGrid[playerX][playerY] = MazeItems.PLAYER_POSITION;
    }
    public void move(Directions direction) {
        int newPositionX = this.playerX;
        int newPositionY = this.playerY;
        switch (direction) {
            case LEFT: {
                newPositionX--;
                break;
            }
            case RIGHT: {
                newPositionX++;
                break;
            }
            case UP: {
                newPositionY--;
                break;
            }
            case DOWN: {
                newPositionY++;
                break;
            }
        }
        System.out.println (canMoveto(newPositionX,newPositionY) + " " + direction);
        if (canMoveto(newPositionX,newPositionY)) {
            makeFloor(playerX,playerY);
            playerX = newPositionX;
            playerY = newPositionY;
            System.out.println ("Player Position: " + playerX + "," + playerY);
            mazeGrid[playerX][playerY] = MazeItems.PLAYER_POSITION;
            GameScreen.copyMazeItems(mazeGrid);
        }
    }
}