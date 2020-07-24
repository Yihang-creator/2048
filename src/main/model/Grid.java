package model;

import java.util.Random;

public class Grid {
    private int[][] matrix = new int[4][4];
    private int score;
    // after each move, only tiles numbered 2 or 4 will be added to the grid
    private int[] arrayOfNumberAppear = {2, 4};

    //EFFECTS: construct an empty 4x4 grid
    public Grid() {
    }


    //EFFECTS: return true when the tiles in the grid can move in the left direction and false otherwise
    public boolean ableToMoveLeft() {
        for (int i = 0; i < 4; i++) {         //determine whether there are neighbouring tiles with identical numbers
            int[] row = getRow(i);
            if (hasIdenticalTiles(row)) {
                return true;
            }
        }
        for (int i = 0; i < 4; i++) {         //determine whether there are empty grids in the leftmost three columnns
            int[] row = getRow(i);            // that is between non-empty grids on the same row
            for (int j = 3; j >= 0; j--) {
                if (row[j] != 0) {
                    for (int k = j; k >= 0; k--) {
                        if (row[k] == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //EFFECTS: return true when the tiles in the grid can move in the right direction and false otherwise
    public boolean ableToMoveRight() {
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        boolean able = ableToMoveLeft();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        return able;
    }

    //EFFECTS: return true when the tiles in the grid can move in the up direction and false otherwise
    public boolean ableToMoveUp() {
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        boolean able = ableToMoveLeft();
        matrixRotationClockwise90Degrees();
        return able;
    }

    //EFFECTS: return true when the tiles in the grid can move in the down direction and false otherwise
    public boolean ableToMoveDown() {
        matrixRotationClockwise90Degrees();
        boolean able = ableToMoveLeft();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        return able;

    }

    //EFFECTS: return true if the given array has neighbouring tiles with identical numbers (excluding 0)
    public boolean hasIdenticalTiles(int[] array) {
        int lastElement = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == lastElement && array[i] != 0) {
                return true;
            } else {
                lastElement = array[i];
            }
        }
        return false;
    }


    //EFFECTS: determine whether the game is over.
    //         The game is over if no further move can be done
    public boolean isOver() {
        return !ableToMoveLeft() && !ableToMoveUp() && !ableToMoveRight() && !ableToMoveDown();
    }


    //MODIFIES: this
    //EFFECTS: move all the tiles in the left direction. Merge neighbouring tiles which have the same number.
    public void moveAndMergeLeft() {
        if (ableToMoveLeft()) {
            for (int i = 0; i < 4; i++) {
                moveAndMergeLeftByRow(i);

            }
        }
    }


    //MODIFIES: this
    //EFFECTS: move all the tiles in the right direction. Merge neighbouring tiles which have the same number.
    public void moveAndMergeRight() {
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        moveAndMergeLeft();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();



    }

    //MODIFIES: this
    //EFFECTS: move all the tiles in the up direction. Merge neighbouring tiles which have the same number.
    public void moveAndMergeUp() {
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        moveAndMergeLeft();
        matrixRotationClockwise90Degrees();



    }

    //MODIFIES: this
    //EFFECTS: move all the tiles in the down direction. Merge neighbouring tiles which have the same number.
    public void moveAndMergeDown() {
        matrixRotationClockwise90Degrees();
        moveAndMergeLeft();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();
        matrixRotationClockwise90Degrees();


    }

    //MODIFIES: this
    //EFFECTS: return the merged and moved row
    public void moveAndMergeLeftByRow(int rowNum) {
        int[] row = getRow(rowNum);
        // put all the zeros on the right
        row = putAllZerosOnRightSide(row);
        // merge the same number
        int lastElement = -1;
        for (int i = 0; i < 4; i++) {
            if (row[i] == lastElement) {
                row[i - 1] = 2 * row[i];
                row[i] = 0;
                lastElement = 0;
            } else {
                lastElement = row[i];
            }
        }
        // put all the zeros on the right
        row = putAllZerosOnRightSide(row);
        setRow(row,rowNum);
    }

    public int[] putAllZerosOnRightSide(int[] row) {
        for (int i = 0; i < 4; i++) {
            if (row[i] == 0) {
                for (int j = i; j < 4; j++) {
                    if (row[j] != 0) {
                        row[i] = row[j];
                        row[j] = 0;
                        break;
                    }
                }
            }
        }
        return row;
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
        Random random = new Random();
        int tileNum = 1 + random.nextInt(getEmptyNum());
        int currentEmptyNum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    currentEmptyNum++;
                    if (currentEmptyNum == tileNum) {
                        matrix[i][j] = arrayOfNumberAppear[random.nextInt(2)];
                        break;
                    }
                }
            }
        }
    }

    //EFFECTS: return the number of empty grid in the matrix
    public int getEmptyNum() {
        int emptyNum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    emptyNum++;
                }
            }
        }
        return emptyNum;
    }

    //MODIFIES: this
    //EFFECTS: addScore will be added to the total score
    public void addScores(int addScore) {
        score = score + addScore;

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
