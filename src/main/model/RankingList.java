package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//RankingList records the names of players and scores of rankings.
// corresponding name and score have the same index in two lists.
public class RankingList implements Saveable {

    private List<String> listOfPlayerNames;
    private List<Integer> listOfScores;

    public RankingList() {
        listOfPlayerNames = new ArrayList<>();
        listOfScores      = new ArrayList<>();
    }

    //REQUIRES: listOfPlayerNames and listOfScores are non-empty and have equal numbers of elements in two lists
    //MODIFIES: this
    //EFFECTS: sort listOfScores from high to low, change listOfPlayerNames accordingly
    public void sortRankingList() throws InvalidRankingListException {
        if (listOfScores.size() != listOfPlayerNames.size()) {
            throw new InvalidRankingListException();
        } else if (listOfScores.size() == 0) {
            throw new IndexOutOfBoundsException();
        } else {
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

    }

    //REQUIRES: listOfPlayerNames and listOfScores are not empty
    //EFFECTS: return the name of the highest ranking
    public String getHighestName() {
        try {
            sortRankingList();
            return listOfPlayerNames.get(0);
        } catch (Exception e) {
            return "no highest score";
        }
    }

    //EFFECTS: return the score of the highest ranking
    public Integer getHighestScore() {
        try {
            sortRankingList();
            return listOfScores.get(0);
        } catch (Exception e) {
            return 0;
        }
    }

    //MODIFIES: this
    //EFFECTS: add one ranking to the rankinglist
    public void addRanking(OneRanking oneRanking) {
        listOfPlayerNames.add(oneRanking.getName());
        listOfScores.add(oneRanking.getScore());

    }


    //EFFECTS; return the listOfScores
    public List<Integer> getListOfScores() {
        return listOfScores;

    }

    //EFFECTS; return the listOfScores
    public List<String> getListOfPlayerNames() {
        return listOfPlayerNames;

    }

    //MODIFIES: this
    //EFFECTS: set listOfPLayerNames
    public void setListOfPlayerNames(List<String> listOfPlayerNames) {
        this.listOfPlayerNames = listOfPlayerNames;
    }

    //MODIFIES: this
    //EFFECTS: set listOfScores
    public void setListOfScores(List<Integer> listOfScores) {
        this.listOfScores = listOfScores;
    }

    //EFFECTS: save rankingList to file
    @Override
    public void save(PrintWriter printWriter) {
        for (int i = 0; i < listOfScores.size(); i++) {
            printWriter.print(listOfPlayerNames.get(i));
            printWriter.print(Reader.DELIMITER);
            printWriter.print(listOfScores.get(i));
            printWriter.println();
        }
    }
}
