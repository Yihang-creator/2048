package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    Grid grid;

    @BeforeEach
    public void setUp() {
        grid = new Grid();


    }

    @Test
    public void gridTest() {
        assertEquals(0,grid.getScore());
        for (int i = 0; i < 4; i++) {
            assertEquals(new int[]{0, 0, 0, 0},grid.getCol(i));
            assertEquals(new int[]{0, 0, 0, 0},grid.getRow(i));
        }
    }

    @Test
    public void moveAndMergeUpTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMerge("up");
        assertEquals(new int[]{4,4,4,0},grid.getRow(0));
        assertEquals(new int[]{2,2,2,0},grid.getRow(1));
        assertEquals(new int[]{0,0,0,0},grid.getRow(2));
        assertEquals(new int[]{0,0,0,0},grid.getRow(3));


    }
    @Test
    public void moveAndMergeDownTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMerge("down");
        assertEquals(new int[]{0,0,0,0},grid.getRow(0));
        assertEquals(new int[]{0,0,0,0},grid.getRow(1));
        assertEquals(new int[]{2,2,2,0},grid.getRow(2));
        assertEquals(new int[]{4,4,4,0},grid.getRow(3));



    }
    @Test
    public void moveAndMergeRightTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMerge("right");
        assertEquals(new int[]{4,2,0,0},grid.getRow(0));
        assertEquals(new int[]{4,2,0,0},grid.getRow(1));
        assertEquals(new int[]{4,2,0,0},grid.getRow(2));
        assertEquals(new int[]{0,0,0,0},grid.getRow(3));



    }
    @Test
    public void moveAndMergeLeftTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMerge("left");
        assertEquals(new int[]{0,0,2,4},grid.getRow(0));
        assertEquals(new int[]{0,0,2,4},grid.getRow(1));
        assertEquals(new int[]{0,0,2,4},grid.getRow(2));
        assertEquals(new int[]{0,0,0,0},grid.getRow(3));



    }

    @Test
    public void addNewTileTest() {
        grid.setRow(new int[]{0,0,0,0},0);
        grid.setRow(new int[]{8,4,0,0},1);
        grid.setRow(new int[]{32,8,0,0},2);
        grid.setRow(new int[]{256,64,4,0},3);
        grid.addNewTile();

        //check whether there is only one tile added
        int addedNum = 0;
        for (int i = 0; i < 4; i++) {
            if (grid.getRow(0)[i] == 2 || grid.getRow(0)[i] == 4) {
                addedNum++;
            }
            if (grid.getCol(3)[i] == 2 || grid.getRow(3)[i] == 4) {
                addedNum++;
            }
            if (grid.getRow(1)[2] == 2 || grid.getRow(1)[2] == 4) {
                addedNum++;
            }
            if (grid.getRow(1)[3] == 2 || grid.getRow(1)[3] == 4) {
                addedNum++;
            }
        }
        assertEquals(1,addedNum);
        //check whether original tiles are changed
        boolean ifChanged = false;
        if (grid.getRow(3)[0] != 256 || grid.getRow(3)[1] != 64 ||
                grid.getRow(3)[2] != 4) {
            ifChanged = true;

        }
        if (grid.getRow(2)[0] != 32 || grid.getRow(2)[1] != 8) {
            ifChanged = true;

        }
        if (grid.getRow(1)[0] != 8 || grid.getRow(1)[1] != 4 ) {
            ifChanged = true;

        }
        assertFalse(ifChanged);


    }

    //MODIFIES: this
    //EFFECTS: the number on newly merged tiles will be added to the score
    @Test
    public void addScoresTest() {

    }

}