import java.awt.*;

public class GameScreen extends Canvas {
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