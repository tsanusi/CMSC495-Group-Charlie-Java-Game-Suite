package Maze;
//////////////////////////////////////////////
// CMSC 495 - Fall 2021                     //
// Professor Mark Munoz                     //
//------------------------------------------//
// Maze.Maze Game                           //
// Written in Java:                         //
//------------------------------------------//
// Class: Maze.GameScreen.java              //
// The class displays the game screen for   //
// the maze game.                           //
//////////////////////////////////////////////
import javax.swing.*;
import java.awt.*;

import java.io.IOException;
enum Mouth {SMILE,CAUTIOUS,MAD}

public class GameScreen extends Canvas {
    int BLOCK_SIZE = 50;
    int xTotalDimensions;
    int yTotalDimensions;
    boolean mazeIsDrawn;
    GameScreen ( int blockSize, MazeItems[][] mItems) {
        this.BLOCK_SIZE = blockSize;
        this.xTotalDimensions = BLOCK_SIZE * mItems.length;
        this.yTotalDimensions = BLOCK_SIZE * mItems[0].length;
        mazeIsDrawn = false;
        Toolkit t=Toolkit.getDefaultToolkit();
    }
    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, xTotalDimensions, yTotalDimensions);
        g.fillRect(0, 0, xTotalDimensions, yTotalDimensions);
        try {
            fillColumn(g, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mazeIsDrawn = true;
    }
    private void fillColumn (Graphics g, int column) throws IOException {
        fillRow (g,column,0);
        if (column < Maze.mazeLevelData.mazeGrid.length - 1) {
            fillColumn(g,column + 1);
        }
    }
    private void fillRow (Graphics g, int column, int row) throws IOException {
        Toolkit t=Toolkit.getDefaultToolkit();

        int i = column, j = row;
        switch (Maze.mazeLevelData.mazeGrid[column][row]) {
            case FLOOR: {
                g.setColor(Color.BLACK);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                break;
            }

            case EXIT: {
                g.setColor(Color.GREEN);
                g.drawRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
                g.drawImage(t.getImage("Exit.png"),(i * BLOCK_SIZE), (j * BLOCK_SIZE),this);
                break;
            }
            case PLAYER_POSITION:
            case PLAYER_POSITION_UP: {
                drawCharacter(g,i,j, Color.BLUE, Directions.UP,Mouth.CAUTIOUS);
                break;
            }
            case PLAYER_POSITION_DOWN: {
                drawCharacter(g,i,j, Color.BLUE, Directions.DOWN,Mouth.CAUTIOUS);
                break;
            }
            case PLAYER_POSITION_LEFT: {
                drawCharacter(g,i,j, Color.BLUE, Directions.LEFT,Mouth.CAUTIOUS);
                break;
            }
            case PLAYER_POSITION_RIGHT: {
                drawCharacter(g,i,j, Color.BLUE, Directions.RIGHT,Mouth.CAUTIOUS);
                break;
            }
            case ENEMY_POSITION_UP: {
                drawCharacter(g,i,j, Color.RED, Directions.UP,Mouth.MAD);
                break;
            }
            case ENEMY_POSITION:
            case ENEMY_POSITION_DOWN: {
                drawCharacter(g,i,j, Color.RED, Directions.DOWN,Mouth.MAD);
                break;
            }
            case ENEMY_POSITION_LEFT: {
                drawCharacter(g,i,j, Color.RED, Directions.LEFT,Mouth.MAD);
                break;
            }
            case ENEMY_POSITION_RIGHT: {
                drawCharacter(g,i,j, Color.RED, Directions.RIGHT,Mouth.MAD);
                break;
            }
            case PLAYER_WIN: {
                drawCharacter(g,i,j,Color.BLUE, Directions.UP,Mouth.SMILE);
                break;
            }
            case PLAYER_DEAD: {
                drawCharacter(g,i,j,Color.GRAY, Directions.UP,Mouth.MAD);
                break;
            }
        }

        if (row < Maze.mazeLevelData.mazeGrid[column].length - 1) {
            fillRow(g,column,row + 1);
        }
    }
    private void drawCharacter (Graphics g, int i, int j, Color characterColor, Directions directionFacing, Mouth mouthMode) {
        Color mouthTop,mouthBottom;
        if (mouthMode == Mouth.CAUTIOUS) {
            mouthTop = Color.BLACK;
            mouthBottom = characterColor;// This will not be used below. just to fix an initialization
        }
        else if (mouthMode == Mouth.SMILE) {
            mouthTop = characterColor;
            mouthBottom = Color.BLACK;
        }
        else {
            mouthBottom = characterColor;
            mouthTop = Color.BLACK;
        }
        // Background Color and Square
        g.setColor(Color.BLACK);
        g.fillRect((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
        // CharacterColor
        g.setColor(characterColor);
        g.fillOval((i * BLOCK_SIZE), (j * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
        // Eye justification
        int leftRightJustifierConstant;
        switch (directionFacing) {
            case UP:
            case DOWN: {
                leftRightJustifierConstant = BLOCK_SIZE / 10;
                break;
            }
            case LEFT: {
                leftRightJustifierConstant = 0;
                break;
            }
            default: {
                leftRightJustifierConstant = (BLOCK_SIZE / 10) * 2;
                break;
            }
        }
        g.setColor(Color.WHITE);
        g.fillOval(((i * BLOCK_SIZE) + ((BLOCK_SIZE / 10) * 2)) , (j * BLOCK_SIZE + BLOCK_SIZE/5), (BLOCK_SIZE/ 10) * 3, (BLOCK_SIZE / 10) * 3);
        g.fillOval(((i * BLOCK_SIZE) + ((BLOCK_SIZE / 10) * 5)) , (j * BLOCK_SIZE + BLOCK_SIZE/5), (BLOCK_SIZE/ 10) * 3, (BLOCK_SIZE / 10) * 3);
        g.setColor(Color.BLACK);
        g.fillOval(((i * BLOCK_SIZE) + ((BLOCK_SIZE / 10) * 2)+(leftRightJustifierConstant)), (j * BLOCK_SIZE + BLOCK_SIZE/3), BLOCK_SIZE/ 8, BLOCK_SIZE / 8);
        g.fillOval(((i * BLOCK_SIZE) + ((BLOCK_SIZE / 10) * 5)+(leftRightJustifierConstant)), (j * BLOCK_SIZE + BLOCK_SIZE/3), BLOCK_SIZE/ 8, BLOCK_SIZE / 8);
        g.setColor(mouthTop);
        g.fillOval((i * BLOCK_SIZE + (BLOCK_SIZE / 4))+ (BLOCK_SIZE/10  ), (j * BLOCK_SIZE+ (BLOCK_SIZE /2) + 10), BLOCK_SIZE / 3, BLOCK_SIZE/5);
        if (mouthMode != Mouth.CAUTIOUS) {
            g.setColor(mouthBottom);
            g.fillOval((i * BLOCK_SIZE + (BLOCK_SIZE / 4))+ (BLOCK_SIZE/10  ), (j * BLOCK_SIZE+ (BLOCK_SIZE /2) + 15), BLOCK_SIZE / 3, BLOCK_SIZE/5);
        }
        if (mouthMode == Mouth.SMILE) {
            g.setColor(mouthTop);
            g.fillOval((i * BLOCK_SIZE + (BLOCK_SIZE / 4))+ (BLOCK_SIZE/10  ), (j * BLOCK_SIZE+ (BLOCK_SIZE /2) + 10), BLOCK_SIZE / 3, BLOCK_SIZE/5);
        }

    }
}
class TitleScreen  {
    Frame titleFrame;
    JLabel Title;
    JButton startButton;
    JButton instructionsButton;
    JButton highScoreButton;
    TitleScreen () {
        titleFrame = new JFrame("The Maze Game!");
        titleFrame.setLayout(null);
        Title = new JLabel ("Maze Game");
        startButton = new JButton ("Start");
        instructionsButton = new JButton ("Instructions");
        highScoreButton = new JButton ("High Scores");
        titleFrame.setSize(300,300);
        Title.setBounds (5,5,190,60);
        Title.setFont( new Font("Comic Sans", Font.PLAIN, 24) );
        startButton.setBounds(50,70,200,40);
        instructionsButton.setBounds(50,120,200,40);
        highScoreButton.setBounds(50,170,200,40);
        titleFrame.add(Title);
        titleFrame.add(startButton);
        titleFrame.add(instructionsButton);
        titleFrame.add(highScoreButton);
        titleFrame.setVisible(true);





    }
}