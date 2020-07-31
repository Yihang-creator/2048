package ui;

import model.Grid;
import model.OneRanking;
import model.RankingList;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class GameApp {
    private static final String GRID_FILE = "./data/grid.txt";
    private static final String RANKINGLIST_FILE = "./data/rankinglist.txt";
    private Grid grid;
    private OneRanking oneRanking = new OneRanking();
    private RankingList rankingList = new RankingList();
    private Scanner command;

    //EFFECTS ; run the game application
    public GameApp() {
        runGame();
    }

    //MODIFIES: this
    //EFFECTS: run the game application
    private void runGame() {
        initializeGame();
        continueGame();


    }

    //MODIFIES: this
    //EFFECTS; initialize the grid and allow users to access rankinglist from the very beginning
    private void initializeGame() {
        grid = new Grid();
        grid.addNewTile();
        grid.addNewTile();
        printGrid();
        command = new Scanner(System.in);

        //first input: start a new game or access rankingList
        System.out.println("enter 0 to start a new game, enter 1 to access rankinglist");
        String choice1 = command.nextLine();
        if (!choice1.equals("0") && !choice1.equals("1")) {
            while (true) {
                System.out.println("invalid input! enter 0 to start a new game, enter 1 to access rankinglist");
                Scanner input = new Scanner(System.in);
                choice1 = input.nextLine();
                if (choice1.equals("1") || choice1.equals("0")) {
                    break;
                }
            }
        }
        if (choice1.equals("1")) {
            printRankingListOption();
        }

    }


    //MODIFIES:this
    //EFFECTS: continue playing 2048 game after 2 tiles are added to an empty 4x4 grid
    private void continueGame() {
        while (!grid.isOver()) {
            System.out.println("enter next move: a for left, d for right, w for up, s for down; enter q to quit");
            Scanner command2 = new Scanner(System.in);
            String nextMove = command2.nextLine();
            boolean notMove1 = !nextMove.equals("a") && !nextMove.equals("d");
            boolean notMove2 = !nextMove.equals("w") && !nextMove.equals("s");
            while (notMove1 && notMove2 && !nextMove.equals("q")) {
                System.out.print("invalid input!");
                System.out.println("enter next move: a for left, d for right, w for up, s for down; enter q to quit");
                Scanner input = new Scanner(System.in);
                nextMove = input.nextLine();
                notMove1 = !nextMove.equals("a") && !nextMove.equals("d");
                notMove2 = !nextMove.equals("w") && !nextMove.equals("s");
            }
            processCommand(nextMove);
            printGrid();

        }
        safeExit();
    }


    //REQUIRES: nextMove must be one of "a" "d" "w" "s" "q"
    //MODIFIES: this
    //EFFECTS: move grid according to nextMove
    private void processCommand(String nextMove) {
        if (nextMove.equals("a")) {
            if (grid.ableToMoveLeft()) {
                grid.moveAndMergeLeft();
                grid.addNewTile();
            }
        } else if (nextMove.equals("d")) {
            if (grid.ableToMoveRight()) {
                grid.moveAndMergeRight();
                grid.addNewTile();
            }
        } else if (nextMove.equals("w")) {
            if (grid.ableToMoveUp()) {
                grid.moveAndMergeUp();
                grid.addNewTile();
            }
        } else if (nextMove.equals("s")) {
            if (grid.ableToMoveDown()) {
                grid.moveAndMergeDown();
                grid.addNewTile();
            }
        } else if (nextMove.equals("q")) {
            safeExit();
        }


    }


    //MODIFIES: this
    //EFFECTS: saves the score obtained in the grid and lets the user enter the name of the ranking.
    // And saves oneRanking to rankingList. and ask whether to restart or exit the game
    private void safeExit() {
        Scanner command3 = new Scanner(System.in);
        oneRanking.extractScore(grid);
        System.out.println("enter the player's name (name length cannot exceed 10 characters)");
        String name = command3.nextLine();
        oneRanking.setName(name.substring(0,min(10,name.length())));
        rankingList.addRanking(oneRanking);
        System.out.println("enter r to restart, quit otherwise");
        String ifRestart = command3.nextLine();
        if (ifRestart.equals("r")) {
            restart();
        } else {
            System.exit(0);
        }
    }


    //MODIFIES: this
    //EFFECTS: clear the grid and oneRanking. And start a new game
    // and restart makes adding multiple oneRanking to rankinglist possible
    private void restart() {
        oneRanking = new OneRanking();
        runGame();
    }


    //EFFECTS: print the 4x4 grid with numbered tiles, current score and highest history record
    private void printGrid() {
        int highestScore = 0;
        if (rankingList.getListOfScores().size() != 0) {
            highestScore = rankingList.getHighestScore();
        }
        System.out.println("Highest record : " + highestScore);
        System.out.println("Current Score : " + grid.getScore());
        System.out.println("----------------------");
        for (int i = 0; i < 4; i++) {
            System.out.print("|");
            int[] row = grid.getRow(i);

            for (int j = 0; j < 4; j++) {
                if (row[j] == 0) {
                    System.out.printf("%s|", "    ");
                } else {
                    String space = " ";
                    int numberOfSpace = 4 - String.valueOf(row[j]).length();
                    String manySpace = new String(new char[numberOfSpace]).replace("\0", space);
                    System.out.printf("%s|", manySpace + row[j]);
                }
            }
            System.out.println("");
            System.out.println("----------------------");
        }
    }

    //MODIFIES: this
    //EFFECTS: sort the RankingList before the ranking is printed, print the ranking list and ask for next instruction
    private void printRankingListOption() {
        rankingList.sortRankingList();
        printRankingList();
        System.out.println("enter 0 to start a new game, enter 2 to quit");
        command = new Scanner(System.in);
        String choice2 = command.nextLine();
        if (!choice2.equals("0") && !choice2.equals("2")) {
            while (true) {
                System.out.println("invalid input! enter 0 to start a new game, enter 2 to quit");
                Scanner input = new Scanner(System.in);
                choice2 = input.nextLine();
                if (choice2.equals("0") || choice2.equals("2")) {
                    break;
                }
            }
        }
        if (choice2.equals("2")) {
            System.exit(0);
        }
    }

    //EFFECTS: print the ranking list
    private void printRankingList() {
        int widthOfRank = 4;
        int widthOfPLayer = 10;
        int widthOfScore = max(rankingList.getHighestScore().toString().length(),5);
        System.out.println("RankingList");
        System.out.println("-------------------------------------");
        System.out.printf("|%s|%s|%s", "rank", "player    ", "score");
        String spaceInScoreAbove = new String(new char[widthOfScore - 5]).replace("\0", " ");
        System.out.println("|");
        if (rankingList.getListOfScores().size() == 0) {
            System.out.println("                                     ");
            System.out.println("no scores recorded for now");
        }
        for (int i = 0; i < rankingList.getListOfScores().size(); i++) {
            int numberOfSpaceinRank = widthOfRank - String.valueOf(i + 1).length();
            int numberOfSpaceinPlayer = widthOfPLayer - rankingList.getListOfPlayerNames().get(i).length();
            int numberOfSpaceinScore = widthOfScore - rankingList.getListOfScores().get(i).toString().length();
            String spaceInRank = new String(new char[numberOfSpaceinRank]).replace("\0", " ");
            String spaceInPlayer = new String(new char[numberOfSpaceinPlayer]).replace("\0", " ");
            String spaceInScoreBelow = new String(new char[numberOfSpaceinScore]).replace("\0", " ");
            System.out.print("|" + (i + 1) + spaceInRank + "|" + rankingList.getListOfPlayerNames().get(i));
            System.out.println(spaceInPlayer + "|" + rankingList.getListOfScores().get(i) + spaceInScoreBelow + "|");
        }
    }
}
