//////////////////////////////////////////////
// Wayne Mack Jr.                           //
// 6425 Union Court                         //
// Glen Burnie, MD 21061                    //
// (443) 627-1117                           //
//------------------------------------------//
// CMSC 495 - Fall 2021                     //
// Professor Mark Munoz                     //
//------------------------------------------//
// Maze Game                                //
// Written in Java:                         //
//------------------------------------------//
// Class: GameScreen.java                   //
// The class displays the game screen for   //
// the maze game.                           //
//////////////////////////////////////////////
import javax.swing.*;
import java.awt.*;

public class GameScreen extends Canvas {
    final int BLOCK_SIZE = 50;
    static int playerXPosition, playerYPosition;
    int xTotalDimensions;
    int yTotalDimensions;
    //static MazeItems[][] mazeItems;
    boolean mazeIsDrawn;
    GameScreen ( MazeItems[][] mItems) {
        this.xTotalDimensions = BLOCK_SIZE * mItems.length;
        this.yTotalDimensions = BLOCK_SIZE * mItems[0].length;
        mazeIsDrawn = false;
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
        if (column < Maze.mazeLevelData.mazeGrid.length - 1) {
            fillColumn(g,column + 1);
        }
    }
    private void fillRow (Graphics g, int column, int row) {
        int i = column, j = row;
        switch (Maze.mazeLevelData.mazeGrid[column][row]) {
            case FLOOR: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
            case ENEMY_POSITION: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.RED);
                g.drawOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
            case EXIT: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.GREEN);
                g.drawRect((i * BLOCK_SIZE) , (j * BLOCK_SIZE) , BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }
            case PLAYER_POSITION: {
                playerXPosition = i;
                playerYPosition = j;
                repositionCharacter(g);
            }
        }
        if (row < Maze.mazeLevelData.mazeGrid[column].length - 1) {
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
class TitleScreen extends JFrame {
    // Coming soon . . .
}