package Maze;

//////////////////////////////////////////////
// CMSC 495 - Fall 2021                     //
// Professor Mark Munoz                     //
//------------------------------------------//
// Maze.Maze Game                                //
// Written in Java:                         //
//------------------------------------------//
// Class: Maze.TileMap.java                      //
// This class holds a Tile Map and holds    //
// the data for the level layouts of the    //
// maze game.                               //
//////////////////////////////////////////////
enum Directions {UP, DOWN, LEFT, RIGHT}
enum LevelNames {LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, LEVEL_FOUR, LEVEL_FIVE, LEVEL_SIX, LEVEL_SEVEN,
                 LEVEL_EIGHT, LEVEL_NINE, LEVEL_TEN, LEVEL_ELEVEN, LEVEL_TWELVE}
enum MazeItems {FLOOR, WALL, EXIT, PLAYER_POSITION, ENEMY_POSITION,PLAYER_POSITION_UP,PLAYER_POSITION_DOWN,PLAYER_POSITION_LEFT,
                PLAYER_POSITION_RIGHT,ENEMY_POSITION_UP,ENEMY_POSITION_DOWN,ENEMY_POSITION_LEFT,ENEMY_POSITION_RIGHT, PLAYER_DEAD, PLAYER_WIN}
public class TileMap {
    String levelName;
    MazeItems [][] mazeGrid;
    int xDimensions, yDimensions,
            playerX, playerY;
    EnemyList enemyList;
    TileMap (LevelNames levelName) {
    switch (levelName) {
        case LEVEL_ONE: {
            setDimensions(10,10);
            makeHorizontalPath(1,4,1);
            makeVerticalPath(1,1,5);
            makeHorizontalPath(1,6,5);
            makeVerticalPath(4,1,7);
            makeVerticalPath(6,1,7);
            makeHorizontalPath(6,10,1);
            makeVerticalPath(10,1,3);
            makeHorizontalPath(8,10,3);
            makeVerticalPath(8,3,5);
            makeHorizontalPath(8,10,5);
            makeVerticalPath(10,5,10);
            makeHorizontalPath(1,10,7);
            makeVerticalPath(1,7,10);
            makeVerticalPath(3,7,10);
            makeHorizontalPath(3,4,10);
            makeHorizontalPath(3,8,9);
            makeVerticalPath(8,7,10);
            makePlayer(1,1);
            makeExit(10,10);
            makeEnemy(1,10);
            makeEnemy(10,1);
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
            makeExit(17,8);
            makeEnemy(16,8);
            makeEnemy(14,1);
            break;
        }
        case LEVEL_THREE: {

        }
        case LEVEL_FOUR: {
            setDimensions(13,15);
            makeVerticalPath(1,1,2);
            makeHorizontalPath(1,13,2);
            makeVerticalPath(11,2,4);
            makeHorizontalPath(4,1,3);
            makeHorizontalPath(4,5,11);
            makeVerticalPath(13,2,7);
            makeVerticalPath(1,4,14);
            makeHorizontalPath(1,3,4);
            makeHorizontalPath(1,3,6);
            makeHorizontalPath(1,5,8);
            makeVerticalPath(5,6,10);
            makeHorizontalPath(3,7,10);
            makeHorizontalPath(6,5,9);
            makeVerticalPath(7,6,8);
            makeVerticalPath(9,6,10);
            makeHorizontalPath(11,13,6);
            makeHorizontalPath(11,13,8);
            break;



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
            }
        }
    }

    private void makeEnemy (int x, int y) {
        if (enemyList == null) {
            enemyList = new EnemyList(x,y);
        }
        else {
            enemyList.addToList(x,y);
        }
        //mazeGrid[x][y] = Maze.MazeItems.ENEMY_POSITION;

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
    private void makeVerticalPath (int x, int y1, int y2) {
        for (int yRow = y1; yRow <= y2; yRow++ ) {
            makeFloor(x,yRow);
        }
    }
    private void makeHorizontalPath (int x1, int x2,int y) {
        for (int xColumn = x1; xColumn <= x2; xColumn++) {
            makeFloor(xColumn,y);
        }
    }
    public Boolean isPresent (int x, int y, MazeItems item) {
        return mazeGrid[x][y] == item;
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
        if (!isPresent(newPositionX,newPositionY, MazeItems.WALL)) {
            mazeGrid[playerX][playerY] = MazeItems.FLOOR;

            playerX = newPositionX;
            playerY = newPositionY;
            MazeItems viewThisWay;
            switch (direction) {
                case LEFT: {
                    viewThisWay = MazeItems.PLAYER_POSITION_LEFT;
                    break;
                }
                case RIGHT: {
                    viewThisWay = MazeItems.PLAYER_POSITION_RIGHT;
                    break;
                }
                default: {
                    viewThisWay = MazeItems.PLAYER_POSITION_UP;
                }
            }


            if (enemyGotYou()) {
                Maze.deathSequence(newPositionX,newPositionY);
            }
            else if (isPresent(newPositionX,newPositionY,MazeItems.EXIT)) {
                Maze.winMazeSequence(newPositionX,newPositionY);
            }
            mazeGrid[playerX][playerY] = viewThisWay;

        }
    }
    public  boolean enemyGotYou () {
        return enemyList.isPresent2(playerX,playerY);
    }
}