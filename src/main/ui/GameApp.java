package ui;

import model.Grid;
import model.OneRanking;
import model.RankingList;

import java.util.Scanner;

public class GameApp {
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
        int choice1 = command.nextInt();
        while (choice1 != 0 && choice1 != 1) {
            System.out.println("invalid input! enter 0 to start a new game, enter 1 to access rankinglist");
            choice1 = command.nextInt();
        }
        if (choice1 == 1) {
            printRankingList(rankingList);
            System.out.println("enter 0 to start a new game, enter 2 to quit");
            int choice2 = command.nextInt();
            while (choice2 != 0 && choice2 != 2) {
                System.out.println("invalid input! enter 0 to start a new game, enter 2 to quit");
                choice2 = command.nextInt();
            }
            if (choice2 == 2) {
                System.exit(0);
            }
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
                nextMove = command2.nextLine();
            }
            processCommand(nextMove);
            grid.addNewTile();
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
            }
        } else if (nextMove.equals("d")) {
            if (grid.ableToMoveRight()) {
                grid.moveAndMergeRight();
            }
        } else if (nextMove.equals("w")) {
            if (grid.ableToMoveUp()) {
                grid.moveAndMergeUp();
            }
        } else if (nextMove.equals("s")) {
            if (grid.ableToMoveDown()) {
                grid.moveAndMergeDown();
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
        System.out.println("enter the player's name");
        String name = command3.nextLine();
        oneRanking.setName(name);
        rankingList.addRanking(oneRanking);
        System.out.println("enter r to restart, enter q to quit");
        String ifRestart = command3.nextLine();
        if (ifRestart.equals("r")) {
            restart();
        } else {
            System.exit(0);
        }
    }



    //MODIFIES: this
    //EFFECTS: clear the grid and oneRanking. And start a new game
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
            System.out.println("|                    |");
            System.out.print("|");
            int[] row = grid.getRow(i);
            for (int j = 0; j < 4; j++) {
                if (row[j] == 0) {
                    System.out.print("    |");
                } else {
                    System.out.print("  " + row[j] + "  |");
                }
            }
            System.out.println("");
            System.out.println("|                    |");
            System.out.println("----------------------");
        }
    }

    private void printRankingList(RankingList rankingList) {
        rankingList.sortRankingList();
        System.out.println("RankingList");
        System.out.println("-------------------------------------");
        System.out.println("|   rank   |   player   |   score   |");
        if (rankingList.getListOfScores().size() == 0) {
            System.out.println("                                     ");
            System.out.println("no scores recorded for now");
        }
        for (int i = 0; i < rankingList.getListOfScores().size(); i++) {
            System.out.print("|   " + (i + 1) + "      ");
            System.out.print("|   " + rankingList.getListOfPlayerNames().get(i) + "      ");
            System.out.print("|   " + rankingList.getListOfScores().get(i) + "      ");
            System.out.println("|");
        }


    }
}
