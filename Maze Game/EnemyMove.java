
public class EnemyMove extends Thread {
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
