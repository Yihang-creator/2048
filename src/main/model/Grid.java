package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.Random;

// class-level comment: Grid is used to represent the 4x4 grid and the behaviour of the grids.
// Class grid have two most important fields , matrix is used to represent 4x4 grid and numbers in the grid using arrays
// score is used to represent the score obtained when merging tiles.

public class Grid implements Saveable {
    private static final int SIDE_LENGTH = 4;
    private int[][] matrix = new int[SIDE_LENGTH][SIDE_LENGTH];
    private int score;
    // after each move, only tiles numbered 2 or 4 will be added to the grid
    private int[] arrayOfNumberAppear = {2, 4};
    private int arraySize = arrayOfNumberAppear.length;

    //EFFECTS: construct an empty 4x4 grid
    public Grid() {
    }


    //EFFECTS: return true when the tiles in the grid can move in the left direction and false otherwise
    public boolean ableToMoveLeft() {
        //determine whether there are neighbouring tiles with identical numbers
        for (int i = 0; i < SIDE_LENGTH; i++) {
            int[] row = getRow(i);
            if (hasIdenticalTiles(row)) {
                return true;
            }
        }

        //determine whether there are empty grids in the leftmost three columnns
        // that is between non-empty grids on the same row
        return isZeroBetweenNumbers();
    }

    //EFFECTS: return true there is empty grid in the leftmost three columnns
    //        that is between non-empty grids on the same row and false otherwise
    private boolean isZeroBetweenNumbers() {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            int[] row = getRow(i);
            for (int j = SIDE_LENGTH - 1; j >= 0; j--) {
                if (row[j] != 0) {                        // find a first non-zero tile from right to left
                    for (int k = j; k >= 0; k--) {        // find zero that is to the left of non-zero tile
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
        for (int i = 0; i < SIDE_LENGTH; i++) {
            moveAndMergeLeftByRow(i);
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
        for (int i = 0; i < SIDE_LENGTH; i++) {
            if (row[i] == lastElement) {
                row[i - 1] = 2 * row[i];
                addScores(2 * row[i]);
                row[i] = 0;
                lastElement = 0;
            } else {
                lastElement = row[i];
            }
        }
        // put all the zeros on the right
        row = putAllZerosOnRightSide(row);
        setRow(row, rowNum);
    }

    //MODIFIES: row
    //EFFECTS: move all the zeros to rightmost columns
    public int[] putAllZerosOnRightSide(int[] row) {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            if (row[i] == 0) {
                for (int j = i; j < SIDE_LENGTH; j++) {
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
    //EFFECTS: rotate the matrix by 90 degrees (clockwise)
    public void matrixRotationClockwise90Degrees() {
        int[][] newMatrix = new int[4][4];
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                newMatrix[i][j] = matrix[SIDE_LENGTH - 1 - j][i];
            }
        }
        matrix = newMatrix;

    }

    //REQUIRES: getEmptyNum() > 0
    //MODIFIES: this
    //EFFECTS: after each move, a new tile numbered 2 or 4 will be added to an empty grid.
    //         No existing tiles will be changed.
    public void addNewTile() {
        Random random = new Random();
        int tileNum = 1 + random.nextInt(getEmptyNum());
        int currentEmptyNum = 0;
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                if (matrix[i][j] == 0) {
                    currentEmptyNum++;
                }
                if (currentEmptyNum == tileNum) {
                    matrix[i][j] = arrayOfNumberAppear[random.nextInt(arraySize)];
                    return;
                }
            }
        }
    }

    //EFFECTS: return the number of empty grid in the matrix
    private int getEmptyNum() {
        int emptyNum = 0;
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
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
        int[] column = new int[SIDE_LENGTH];
        for (int i = 0; i < SIDE_LENGTH; i++) {
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

    //REQUIRES: col is an array with four elements, 0 < colNum <=3
    //MODIFIES: this
    //EFFECTS: set the designated column to the given column
    public void setCol(int[] col, int colNum) {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            matrix[i][colNum] = col[i];
        }
    }

    //REQUIRES: matrix should be a 4x4 in scale
    //EFFECTS: set the current matrix to be the given matrix
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }


    //EFFECTS: save the grid and score to a file
    @Override
    public void save(PrintWriter printWriter) {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            printWriter.print(matrix[i][0] + Reader.DELIMITER);
            printWriter.print(matrix[i][1] + Reader.DELIMITER);
            printWriter.print(matrix[i][2] + Reader.DELIMITER);
            printWriter.print(matrix[i][3]);
            printWriter.println();
        }
        printWriter.print(score);

    }
}
