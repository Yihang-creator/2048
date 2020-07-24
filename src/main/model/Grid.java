package model;

public class Grid {
    private int[][] matrix = new int[4][4];
    private int score;
    // after each move, only tiles numbered 2 or 4 will be added to the grid
    private int[] arrayOfNumberAppear = {2,4};

    //EFFECTS: construct an empty 4x4 grid
    public Grid() {}

    //REQUIRES: command is one of "up","down","left" or "right".
    //EFFECTS: determine whether the tiles in the grid can move in the command direction
    public boolean ableToMove(String command) {
        return true; //stub
    }

    //EFFECTS: determine whether the game is over.
    //         The game is over when grid is full and no further move can be done
    public boolean isOver() {
        return false; //stub
    }

    //REQUIRES: command is one of "up","down","left" or "right".
    //MODIFIES: this
    //EFFECTS: move all the tiles in the given direction. Merge neighbouring tiles which have the same number.
    public void moveAndMerge(String command) {


    }

    //MODIFIES: this
    //EFFECTS: rotate the matrix by 90 degrees ()
    public void matrixRotationClockwise90Degrees() {
        int[][] newMatrix = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newMatrix[i][j] = matrix[3 - j][i];
            }
        }
        matrix = newMatrix;

    }

    //MODIFIES: this
    //EFFECTS: after each move, a new tile numbered 2 or 4 will be added to an empty grid.
    //         No existing tiles will be changed.
    public void addNewTile() {

    }

    //MODIFIES: this
    //EFFECTS: addScore will be added to the total score
    public void addScores(int addScore) {

    }


    //REQUIRES: 0 <= rowNum <=3
    //EFFECTS: return the row of the given index
    public int[] getRow(int rowNum) {
        return matrix[rowNum];
    }

    //REQUIRES: 0 <= colNum <=3
    //EFFECTS: return the column of the given index
    public int[] getCol(int colNum) {
        int[] column = new int[4];
        for (int i = 0; i < 4; i++) {
            column[i] = matrix[i][colNum];
        }
        return column;
    }

    //EFFECTS: return the current score
    public int getScore() {
        return score;
    }

    //REQUIRES: row is an array with four elements, 0 < rowNum <=3
    //MODIFIES: this
    //EFFECTS: set the designated row to the given row
    public void setRow(int[] row, int rowNum) {
        matrix[rowNum] = row;

    }

    public void setCol(int[] col, int colNum) {
        for (int i = 0; i < 4; i++) {
            matrix[i][colNum] = col[i];
        }
    }




}
