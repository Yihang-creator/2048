package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOneRanking {
    OneRanking oneRanking;

    @BeforeEach
    public void setUp() {
        oneRanking = new OneRanking();

    }

    @Test
    public void oneRankingTest() {
        assertEquals(0, oneRanking.getScore());
        assertEquals("", oneRanking.getName());
    }

    @Test
    public void setNameTest() {
        oneRanking.setName("thunderbird");
        assertEquals("thunderbird", oneRanking.getName());

    }

    @Test
    public void setScoreTest() {
        oneRanking.setScore(10384);
        assertEquals(10384,oneRanking.getScore());
    }

    @Test
    public void extractScoreTest() {
        Grid grid = new Grid();
        grid.addScores(10384);
        oneRanking.extractScore(grid);
        assertEquals(10384,oneRanking.getScore());

    }
}
