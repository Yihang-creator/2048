package persistence;

import model.Grid;
import model.OneRanking;
import model.RankingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestWriter {
    private static final String TEST_MATRIX_FILE = "./data/testMatrix.txt";
    private static final String TEST_RANkINGLIST_FILE = "./data/testRankingList.txt";
    private Writer testWriter1;
    private Writer testWriter2;
    private Grid grid;
    private RankingList rankingList;

    @BeforeEach
    void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter1 = new Writer(new File(TEST_MATRIX_FILE));
        testWriter2 = new Writer(new File(TEST_RANkINGLIST_FILE));
        grid = new Grid();
        rankingList = new RankingList();
        grid.setMatrix(new int[][] {{8,4,0,0},
                {32,16,8,4},
                {128,8,4,8},
                {256,128,64,4}});

        grid.addScores(1024);
        OneRanking ranking1 = new OneRanking();
        OneRanking ranking2 = new OneRanking();
        OneRanking ranking3 = new OneRanking();
        ranking1.setScore(9998);
        ranking2.setScore(5000);
        ranking3.setScore(2000);
        ranking1.setName("Player1");
        ranking2.setName("Player2");
        ranking3.setName("Player3");
        rankingList.addRanking(ranking1);
        rankingList.addRanking(ranking2);
        rankingList.addRanking(ranking3);
    }

    @Test
    void testWriteMatrix() {
        // save the matrix to file
        testWriter1.write(grid);
        testWriter1.close();

        //parse matrix from the file and check if matrix is written correctly
        try {
            Grid grid1 = Reader.readMatrix(new File(TEST_MATRIX_FILE));
            assertArrayEquals(new int[]{8,4,0,0}, grid1.getRow(0));
            assertArrayEquals(new int[]{32,16,8,4}, grid1.getRow(1));
            assertArrayEquals(new int[]{128,8,4,8}, grid1.getRow(2));
            assertArrayEquals(new int[]{256,128,64,4}, grid1.getRow(3));
            assertEquals(1024, grid1.getScore());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteRankingList() {
        //save the ranking list to file
        testWriter2.write(rankingList);
        testWriter2.close();

        //parse the ranking list from the file
        try {
            RankingList rankingList1 = Reader.readRankings(new File(TEST_RANkINGLIST_FILE));
            List<Integer> listOfScores = rankingList1.getListOfScores();
            List<String> listOfPlayers = rankingList1.getListOfPlayerNames();
            assertEquals(3,listOfPlayers.size());
            assertEquals(3,listOfScores.size());
            assertEquals("Player1",listOfPlayers.get(0));
            assertEquals("Player2",listOfPlayers.get(1));
            assertEquals("Player3",listOfPlayers.get(2));
            assertEquals(9998,listOfScores.get(0));
            assertEquals(5000,listOfScores.get(1));
            assertEquals(2000,listOfScores.get(2));


        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }
}
