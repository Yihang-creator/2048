package model;

import java.util.ArrayList;

public class RankingList {

    private ArrayList<String> listOfPlayerNames;
    private ArrayList<Integer> listOfScores;

    public RankingList() {
        listOfPlayerNames = new ArrayList();
        listOfScores      = new ArrayList();
    }

    //REQUIRES: listOfPlayerNames and listOfScores are non-empty
    //MODIFIES: this
    //EFFECTS: sort listOfScores from high to low, change listOfPlayerNames accordingly
    public void sortRankingList() {
        for (int i = 0; i < listOfScores.size() - 1; i++) {
            for (int j = listOfScores.size() - 1; j > i; j--) {
                if (listOfScores.get(j) > listOfScores.get(j - 1)) {
                    int smallerNum = listOfScores.get(j - 1);
                    listOfScores.set(j - 1, listOfScores.get(j));
                    listOfScores.set(j, smallerNum);
                    String smallerNumName = listOfPlayerNames.get(j - 1);
                    listOfPlayerNames.set(j - 1, listOfPlayerNames.get(j));
                    listOfPlayerNames.set(j, smallerNumName);
                }
            }
        }

    }

    //EFFECTS: return the name of the highest ranking
    public String getHighestName() {
        sortRankingList();
        return listOfPlayerNames.get(0);
    }

    //EFFECTS: return the score of the highest ranking
    public Integer getHighestScore() {
        sortRankingList();
        return listOfScores.get(0);
    }

    //MODIFIES: this
    //EFFECTS: add one ranking to the rankinglist
    public void addRanking(OneRanking oneRanking) {
        listOfPlayerNames.add(oneRanking.getName());
        listOfScores.add(oneRanking.getScore());

    }


    //EFFECTS; return the listOfScores
    public ArrayList<Integer> getListOfScores() {
        return listOfScores;

    }

    //EFFECTS; return the listOfScores
    public ArrayList<String> getListOfPlayerNames() {
        return listOfPlayerNames;

    }



}
