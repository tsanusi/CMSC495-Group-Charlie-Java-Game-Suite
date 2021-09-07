
public class EnemyMove extends Thread {
    public boolean stopThread;

    EnemyMove () {
        //enemyList = Maze.mazeLevelData.enemyList;
    }

    @Override
    public void run() {
        while (!stopThread) {
            try {
                if (Maze.mazeLevelData.enemyList != null) {
                    Maze.mazeLevelData.enemyList.enemyMove();
                }
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class EnemyList {
    int rowNumber; int columnNumber;
    EnemyList next;

    EnemyList(int columnNumber, int rowNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    void addToList(int columnNumber, int rowNumber) {
        if (next == null) {
            next = new EnemyList(columnNumber,rowNumber);
        }
        else {
            System.out.println ("Adding new");
            next.addToList(columnNumber,rowNumber);
        }
    }

    boolean isPresent2 ( int Column, int Row) {
        if ( columnNumber == 0
                || rowNumber == 0) {
            return next.isPresent2(Column,Row);
        }
        if (Column == columnNumber
                && Row == rowNumber) {
            return true;
        }
        if (next != null) {
            System.out.println ("Line 65 Column :" + columnNumber + "Row: " + Row);
            return next.isPresent2(Column,Row);
        }
        return false;

    }
    public int absoluteValue (int i) {
        if (i < 0) {
            return (i + (i * 2));
        }
        return i;
    }
    public void enemyMove ( ) {
        int deviationX = columnNumber - Maze.mazeLevelData.playerX;
        int deviationY = rowNumber - Maze.mazeLevelData.playerY;
        if (absoluteValue(deviationX) > absoluteValue(deviationY)) {
            if (!moveUpDown()) {
                moveLeftRight();
            }
        }
        else  {
            if (!moveLeftRight()) {
                moveUpDown();
            }
        }
        if (next != null ) {
            next.enemyMove();
        }
    }
    public boolean moveUpDown () {
        if (Maze.mazeLevelData.playerX < columnNumber
                && !isPresent2(columnNumber -1,rowNumber)
                && !Maze.mazeLevelData.isPresent(columnNumber - 1,rowNumber,MazeItems.WALL)) {
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            columnNumber--;
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.ENEMY_POSITION;
        }
        else if (Maze.mazeLevelData.playerX > columnNumber
                && !isPresent2(columnNumber +1,rowNumber)
                && !Maze.mazeLevelData.isPresent(columnNumber + 1 ,rowNumber,MazeItems.WALL)) {
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            columnNumber++;
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.ENEMY_POSITION;
        }
        else {
            return false;
        }
        System.out.println ("Screen repaint");
        Maze.gameScreen.repaint();
        return true;
    }
    public boolean moveLeftRight () {
        if (Maze.mazeLevelData.playerY < rowNumber
                && !isPresent2(columnNumber ,rowNumber - 1)
                && !Maze.mazeLevelData.isPresent(columnNumber ,rowNumber - 1,MazeItems.WALL)) {
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            rowNumber--;
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber ] = MazeItems.ENEMY_POSITION;
        }
        else if (Maze.mazeLevelData.playerY > rowNumber
                && !isPresent2(columnNumber,rowNumber + 1)
                && !Maze.mazeLevelData.isPresent(columnNumber ,rowNumber + 1,MazeItems.WALL)) {
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber] = MazeItems.FLOOR;
            rowNumber++;
            Maze.mazeLevelData.mazeGrid[columnNumber][rowNumber ] = MazeItems.ENEMY_POSITION;
        }
        else {
            return false;
        }
        Maze.gameScreen.repaint();
        return true;
    }

}
