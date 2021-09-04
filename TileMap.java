

public class TileMap {
    String levelName;
    MazeItems [][] mazeGrid;
    int xDimensions, yDimensions,
            playerX, playerY,
            lastPlayerX, lastPlayerY;
    TileMap (LevelNames levelName) {
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
            setDimensions(17,8);
            makeHorizontalPath(1,3,1);
            makeVerticalPath(1,1,7);
            makeHorizontalPath(1,5,8);
            makeVerticalPath(5,6,8);
            makeHorizontalPath(3,5,6);
            makeVerticalPath(3,1,6);
            makeHorizontalPath(3,7,4);
            makeVerticalPath(5,1,4);
            makeVerticalPath(7,1,8);
            makeHorizontalPath(5,9,1);
            makeVerticalPath(9,1,6);
            makeHorizontalPath(7,17,8);
            makeHorizontalPath(8,14,6);
            makeVerticalPath(12,6,8);
            makeVerticalPath(10,1,7);
            makeHorizontalPath(10,14,1);
            makeVerticalPath(14,1,5);
            makePlayer(1,1);





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

    private void setDimensions (int xRows, int yRows) {
        mazeGrid = new MazeItems[xRows + 2][yRows + 2];
        this.xDimensions = 20 * (xRows + 2);
        this.yDimensions = 20 * (yRows + 2);
        for (int i = 0; i <= xRows + 1; i++) {
            for (int j = 0; j <= yRows+1; j++ ) {
                mazeGrid[i][j] = MazeItems.WALL;
                System.out.println ("Set: "+ i + " and " + j);
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
        System.out.println (x + " and " + y);
        mazeGrid[x][y] = MazeItems.FLOOR;
    }
    private void makeVerticalPath (int columnPoint, int firstRowPoint, int secondRowPoint) {
        for (int yRow = firstRowPoint; yRow <= secondRowPoint; yRow++ ) {
            makeFloor(columnPoint,yRow);
        }
    }
    private void makeHorizontalPath (int x1, int x2,int y) {
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
        if (canMoveto(newPositionX,newPositionY)) {
            mazeGrid[playerX][playerY] = MazeItems.FLOOR;
            playerX = newPositionX;
            playerY = newPositionY;
            mazeGrid[playerX][playerY] = MazeItems.PLAYER_POSITION;
            GameScreen.copyMazeItems(mazeGrid,playerX,playerY);


        }
    }
}
