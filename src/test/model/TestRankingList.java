package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestRankingList {
    RankingList rankingList;
    OneRanking oneRanking1;
    OneRanking oneRanking2;
    OneRanking oneRanking3;
    OneRanking oneRanking4;
    OneRanking oneRanking5;

    @BeforeEach
    public void setUp() {
        rankingList = new RankingList();
        oneRanking1 = new OneRanking();
        oneRanking1.setScore(9998);
        oneRanking1.setName("Player1");
        oneRanking2 = new OneRanking();
        oneRanking2.setScore(20000);
        oneRanking2.setName("Player2");
        oneRanking3 = new OneRanking();
        oneRanking3.setScore(2000);
        oneRanking3.setName("Player3");
        oneRanking4 = new OneRanking();
        oneRanking4.setScore(5004);
        oneRanking4.setName("Player4");
        oneRanking5 = new OneRanking();
        oneRanking5.setScore(30488);
        oneRanking5.setName("Player5");
    }


    @Test
    public void RankingListTest() {
        assertEquals(0,rankingList.getListOfPlayerNames().size());
        assertEquals(0,rankingList.getListOfScores().size());
    }

    @Test
    public void sortRankingListTest() {
        rankingList.addRanking(oneRanking1);
        rankingList.sortRankingList();
        assertEquals("Player1",rankingList.getListOfPlayerNames().get(0));
        assertEquals(9998,rankingList.getListOfScores().get(0));
        rankingList.addRanking(oneRanking2);
        rankingList.addRanking(oneRanking3);
        rankingList.addRanking(oneRanking4);
        rankingList.addRanking(oneRanking5);
        rankingList.sortRankingList();
        assertEquals("Player5",rankingList.getListOfPlayerNames().get(0));
        assertEquals(30488,rankingList.getListOfScores().get(0));
        assertEquals("Player2",rankingList.getListOfPlayerNames().get(1));
        assertEquals(20000,rankingList.getListOfScores().get(1));
        assertEquals("Player1",rankingList.getListOfPlayerNames().get(2));
        assertEquals(9998,rankingList.getListOfScores().get(2));
        assertEquals("Player4",rankingList.getListOfPlayerNames().get(3));
        assertEquals(5004,rankingList.getListOfScores().get(3));
        assertEquals("Player3",rankingList.getListOfPlayerNames().get(4));
        assertEquals(2000,rankingList.getListOfScores().get(4));
    }



    @Test
    public void getHighestNameTest() {
        rankingList.addRanking(oneRanking1);
        rankingList.addRanking(oneRanking2);
        rankingList.addRanking(oneRanking3);
        rankingList.addRanking(oneRanking4);
        rankingList.addRanking(oneRanking5);
        assertEquals("Player5",rankingList.getHighestName());

    }

    @Test
    public void getHighestScoreTest() {
        rankingList.addRanking(oneRanking1);
        rankingList.addRanking(oneRanking2);
        rankingList.addRanking(oneRanking3);
        rankingList.addRanking(oneRanking4);
        rankingList.addRanking(oneRanking5);
        assertEquals(30488,rankingList.getHighestScore());

    }

}
