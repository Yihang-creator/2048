package persistence;

import model.Grid;
import model.OneRanking;
import model.RankingList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a rankinglist parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static RankingList readRankings(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseRankingList(fileContent);

    }

    // EFFECTS: returns a 4x4 matrix parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static Grid readMatrix(File file) throws IOException {
        Grid grid = new Grid();
        List<String> fileContent = readFile(file);
        grid.setMatrix(parseMatrix(fileContent));
        grid.addScores(parseScore(fileContent));
        return grid;
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    // quoted from TellerApp
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // REQUIRES: each string should have 2 elements delimited by ',', in which
    // the element 1 should be able to be altered to integer type. element 0 represents
    // the name of the player and element 1 represents the score of the player.
    // EFFECTS: return a rankingList parsed from a list of String
    //          containing all the names of players and their correponding scores
    public static RankingList parseRankingList(List<String> fileContent) {
        RankingList rankingList = new RankingList();

        for (int i = 0; i < fileContent.size(); i++) {
            String[] splits = fileContent.get(i).split(DELIMITER);
            ArrayList<String> line = new ArrayList<>(Arrays.asList(splits));
            String name = line.get(0);
            int score = parseInt(line.get(1));
            OneRanking oneRanking = new OneRanking();
            oneRanking.setName(name);
            oneRanking.setScore(score);
            rankingList.addRanking(oneRanking);
        }
        return rankingList;
    }


    //REQUIRES: there should be five strings in the list. The first four strings in the list can be each separated into
    // four parts with each part indicating the number on a certain location.
    //EFFECTS: return a matrix parsed from the file's content
    public static int[][] parseMatrix(List<String> fileContent) {
        int[][] matrix = new int[4][4];



        return matrix;
    }

    public static int parseScore(List<String> fileContent) {

        return 0;
    }


}
