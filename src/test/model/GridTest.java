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
            assertArrayEquals(new int[]{0, 0, 0, 0},grid.getCol(i));
            assertArrayEquals(new int[]{0, 0, 0, 0},grid.getRow(i));
        }
    }

    @Test
    public void ableToMoveTest() {
        grid.setRow(new int[]{64,0,0,0},0);
        grid.setRow(new int[]{128,64,8,4},1);
        grid.setRow(new int[]{256,128,64,8},2);
        grid.setRow(new int[]{512,256,128,64},3);
        assertFalse(grid.ableToMoveLeft());
        assertTrue(grid.ableToMoveRight());
        assertFalse(grid.ableToMoveDown());
        assertTrue(grid.ableToMoveUp());
        Grid grid2 = new Grid();
        grid2.setRow(new int[]{64,8,4,4},0);
        grid2.setRow(new int[]{128,64,8,4},1);
        grid2.setRow(new int[]{256,128,64,8},2);
        grid2.setRow(new int[]{512,256,128,64},3);
        assertTrue(grid2.ableToMoveLeft());

    }

    @Test
    public void isOverTest() {
        Grid grid2 = new Grid();
        grid.setRow(new int[]{64,8,4,2},0);
        grid.setRow(new int[]{128,64,8,4},1);
        grid.setRow(new int[]{256,128,64,8},2);
        grid.setRow(new int[]{512,256,128,64},3);
        assertTrue(grid.isOver());
        grid2.setRow(new int[]{64,8,4,4},0);
        grid2.setRow(new int[]{128,64,8,4},1);
        grid2.setRow(new int[]{256,128,64,8},2);
        grid2.setRow(new int[]{512,256,128,64},3);
        assertFalse(grid2.isOver());

    }

    @Test
    public void hasIdenticalTilesTest() {
        assertTrue(grid.hasIdenticalTiles(new int[]{64,4,4,4}));
        assertFalse(grid.hasIdenticalTiles(new int[]{64,4,0,0}));
        assertTrue(grid.hasIdenticalTiles(new int[]{0,4,4,0}));
        assertTrue(grid.hasIdenticalTiles(new int[]{64,64,0,0}));
        assertFalse(grid.hasIdenticalTiles(new int[]{128,64,4,8}));

    }


        @Test
    public void matrixRotationClockwise90DegreesTest() {
        grid.setRow(new int[]{64,8,4,2},0);
        grid.setRow(new int[]{128,64,8,4},1);
        grid.setRow(new int[]{256,128,64,8},2);
        grid.setRow(new int[]{512,256,128,64},3);
        grid.matrixRotationClockwise90Degrees();
        assertArrayEquals(new int[]{512,256,128,64},grid.getRow(0));
        assertArrayEquals(new int[]{256,128,64,8},grid.getRow(1));
        assertArrayEquals(new int[]{128,64,8,4},grid.getRow(2));
        assertArrayEquals(new int[]{64,8,4,2},grid.getRow(3));


    }


    @Test
    public void moveAndMergeUpTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMergeUp();
        assertArrayEquals(new int[]{4,4,4,0},grid.getRow(0));
        assertArrayEquals(new int[]{2,2,2,0},grid.getRow(1));
        assertArrayEquals(new int[]{0,0,0,0},grid.getRow(2));
        assertArrayEquals(new int[]{0,0,0,0},grid.getRow(3));


    }
    @Test
    public void moveAndMergeDownTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMergeDown();
        assertArrayEquals(new int[]{0,0,0,0},grid.getRow(0));
        assertArrayEquals(new int[]{0,0,0,0},grid.getRow(1));
        assertArrayEquals(new int[]{2,2,2,0},grid.getRow(2));
        assertArrayEquals(new int[]{4,4,4,0},grid.getRow(3));



    }
    @Test
    public void moveAndMergeRightTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMergeRight();
        assertArrayEquals(new int[]{0,0,2,4},grid.getRow(0));
        assertArrayEquals(new int[]{0,0,2,4},grid.getRow(1));
        assertArrayEquals(new int[]{0,0,2,4},grid.getRow(2));
        assertArrayEquals(new int[]{0,0,0,0},grid.getRow(3));




    }
    @Test
    public void moveAndMergeLeftTest() {
        for (int i = 0; i < 3; i++) {
            grid.setCol(new int[]{2,2,2,0},i);
        }
        grid.setCol(new int[]{0,0,0,0},3);
        grid.moveAndMergeLeft();
        assertArrayEquals(new int[]{4,2,0,0},grid.getRow(0));
        assertArrayEquals(new int[]{4,2,0,0},grid.getRow(1));
        assertArrayEquals(new int[]{4,2,0,0},grid.getRow(2));
        assertArrayEquals(new int[]{0,0,0,0},grid.getRow(3));




    }

    @Test
    public void moveAndMergeLeftByRowTest() {
        grid.setRow(new int[]{4,2,0,0},0);
        grid.setRow(new int[]{8,4,0,2},1);
        grid.setRow(new int[]{32,8,8,0},2);
        grid.setRow(new int[]{256,256,4,0},3);
        grid.moveAndMergeLeftByRow(0);
        grid.moveAndMergeLeftByRow(1);
        grid.moveAndMergeLeftByRow(2);
        grid.moveAndMergeLeftByRow(3);
        assertArrayEquals(new int[]{4,2,0,0},grid.getRow(0));
        assertArrayEquals(new int[]{8,4,2,0},grid.getRow(1));
        assertArrayEquals(new int[]{32,16,0,0},grid.getRow(2));
        assertArrayEquals(new int[]{512,4,0,0},grid.getRow(3));

    }

    @Test
    public void putAllZerosOnRightSideTest() {
        assertArrayEquals(new int[]{4,2,0,0} , grid.putAllZerosOnRightSide(new int[]{0,4,0,2}));
        assertArrayEquals(new int[]{8,4,2,0} , grid.putAllZerosOnRightSide(new int[]{8,4,0,2}));
        assertArrayEquals(new int[]{32,8,8,0} , grid.putAllZerosOnRightSide(new int[]{32,8,8,0}));
        assertArrayEquals(new int[]{256,256,4,0} , grid.putAllZerosOnRightSide(new int[]{256,256,0,4}));

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
            if (grid.getCol(3)[i] == 2 || grid.getCol(3)[i] == 4) {
                addedNum++;
            }
        }
        if (grid.getRow(1)[2] == 2 || grid.getRow(1)[2] == 4) {
            addedNum++;
        }
        if (grid.getRow(1)[3] == 2 || grid.getRow(1)[3] == 4) {
            addedNum++;
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


    @Test
    public void addScoresTest() {
        grid.addScores(12);
        assertEquals(12,grid.getScore());
        grid.addScores(0);
        assertEquals(12,grid.getScore());
        grid.addScores(512);
        assertEquals(524,grid.getScore());
    }

}