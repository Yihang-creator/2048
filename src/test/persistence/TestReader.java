package persistence;

import model.Grid;
import model.RankingList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

public class TestReader {
    @Test
    void testParseMatrixFile1() {
        try {
            Grid grid1 = Reader.readMatrix(new File("./data/testMatrixFile1.txt"));
            assertArrayEquals(new int[]{4,2,0,0},grid1.getRow(0));
            assertArrayEquals(new int[]{2,0,0,0},grid1.getRow(1));
            assertArrayEquals(new int[]{16,8,4,4},grid1.getRow(2));
            assertArrayEquals(new int[]{32,16,8,4},grid1.getRow(3));
            assertEquals(152,grid1.getScore());

        } catch (IOException e) {
            fail("IOException should noe be thrown");
        }

    }

    @Test
    void testParseMatrixFile2() {
        try {
            Grid grid2 = Reader.readMatrix(new File("./data/testMatrixFile1.txt"));
            assertArrayEquals(new int[]{8,4,0,0}, grid2.getRow(0));
            assertArrayEquals(new int[]{32,16,8,4}, grid2.getRow(1));
            assertArrayEquals(new int[]{128,8,4,8}, grid2.getRow(2));
            assertArrayEquals(new int[]{256,128,64,4}, grid2.getRow(3));
            assertEquals(152, grid2.getScore());
        } catch (IOException e) {
            fail("IOException should noe be thrown");
        }


    }

    @Test
    void testMatrixIOEException() {
        try {
            Reader.readMatrix(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }

    }

    @Test
    void testParseRankingListFile1() {
        try {
            RankingList rankingList1 = Reader.readRankings(new File("./data/testRankingListFile1.txt"));
            List<Integer> listOfScores = rankingList1.getListOfScores();
            List<String> listOfPlayers = rankingList1.getListOfPlayerNames();
            assertEquals(5,listOfPlayers.size());
            assertEquals(5,listOfScores.size());
            assertEquals("PineTree",listOfPlayers.get(0));
            assertEquals("PalmTree",listOfPlayers.get(1));
            assertEquals("banyan",listOfPlayers.get(2));
            assertEquals("evergreen",listOfPlayers.get(3));
            assertEquals("poplar",listOfPlayers.get(4));
            assertEquals(9888,listOfScores.get(0));
            assertEquals(9886,listOfScores.get(1));
            assertEquals(8674,listOfScores.get(2));
            assertEquals(7078,listOfScores.get(3));
            assertEquals(888,listOfScores.get(4));

        } catch (IOException e) {
            fail("should noe throw IOException");
        }


    }

    @Test
    void testParseRankingListFile2() {
        try {
            RankingList rankingList2 = Reader.readRankings(new File("./data/testRankingListFile2.txt"));
            List<Integer> listOfScores = rankingList2.getListOfScores();
            List<String> listOfPlayers = rankingList2.getListOfPlayerNames();
            assertEquals(3,listOfPlayers.size());
            assertEquals(3,listOfScores.size());
            assertEquals("TorchTree",listOfPlayers.get(0));
            assertEquals("gingko",listOfPlayers.get(1));
            assertEquals("willow",listOfPlayers.get(2));
            assertEquals(20844,listOfScores.get(0));
            assertEquals(19888,listOfScores.get(1));
            assertEquals(15582,listOfScores.get(2));

        } catch (IOException e) {
            fail("should noe throw IOException");
        }

    }

    @Test
    void testRankingListIOEException() {
        try {
            Reader.readRankings(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }

    }


}
