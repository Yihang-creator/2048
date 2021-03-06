package ui;

import model.Grid;
import model.InvalidRankingListException;
import model.OneRanking;
import model.RankingList;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static java.lang.Integer.min;

public class GameApp extends JFrame {
    private static final String GRID_FILE = "./data/grid.txt";
    private static final String RANKINGLIST_FILE = "./data/rankinglist.txt";
    private Grid grid;
    private OneRanking oneRanking = new OneRanking();
    private RankingList rankingList = new RankingList();
    private static final int WIDTHOFRANK = 4;
    private static final int WIDTHOFPLAYER = 10;
    private GridPanel gridPanel;
    private ScorePanel scorePanel;
    private JButton rankingListButton;
    private JButton saveButton;
    private JButton restartButton;

    //EFFECTS ; run the game application
    public GameApp() {
        super("2048");
        initializeGrid();
    }

    //MODIFIES: this
    //EFFECTS: load the game progress from files if files exist. create a new grid if the files don't exist.
    private void initializeGrid() {
        File matrixFile = new File(GRID_FILE);
        if (matrixFile.exists()) {
            askWhetherToLoadGame(matrixFile);
        } else {
            newGame();
            runGame();
        }

    }

    //MODIFIES: this
    //EFFECTS: initializes the grid and add two tiles to the empty grids
    private void newGame() {
        grid = new Grid();
        grid.addNewTile();
        grid.addNewTile();
    }

    //MODIFIES: this
    //EFFECTS: run the game application
    private void runGame() {
        initializeRanking();
        initializeGraphics();
        accessRankingList();
        continueGame();
    }

    //MODIFIES: this
    //EFFECTS: load the ranking list from files if files exist. create a new ranking if the files don't exist.
    private void initializeRanking() {
        File matrixFile = new File(GRID_FILE);
        try {
            rankingList = Reader.readRankings(new File(RANKINGLIST_FILE));
        } catch (Exception e) {
            rankingList = new RankingList();
        }

    }

    //MODIFIES: this
    //EFFECTS: initialize the panel showing scores and grids
    private void initializeGraphics() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveProgress();
                super.windowClosing(e);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,950);
        setLocationRelativeTo(null);
        gridPanel = new GridPanel(grid);
        scorePanel = new ScorePanel(grid,rankingList);
        add(gridPanel);
        add(scorePanel,BorderLayout.NORTH);
        setFocusable(true);
        pack();
        printGrid();
    }

    //MODIFIES: this
    //EFFECTS: add a button allow users to access rankingList
    private void accessRankingList() {
        printGrid();
        JButton rankingListButton = new JButton("Ranking");
        rankingListButton.setFocusable(false);
        rankingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printRankingListOption();
            }
        });
        add(rankingListButton,BorderLayout.EAST);

    }


    // MODIFIES: this
    // EFFECTS: create a window asking the user whether to load the last game(if there is a last game)
    private void askWhetherToLoadGame(File matrixFile) {
        JFrame load = new JFrame("2048: whether to load the last game?");
        load.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel text = new JLabel("whether to load the last game?");
        load.add(text,BorderLayout.NORTH);
        addYesButton(matrixFile, load);
        addNoButton(load);
        load.pack();
        load.setLocationRelativeTo(null);
        load.setVisible(true);
    }

    //MODIFIES: load
    //EFFECTS: create a button. pressing this button means the user wants to load the last game
    private void addYesButton(File matrixFile, JFrame load) {
        JButton yesButton = new JButton("Yes!");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    grid = Reader.readMatrix(matrixFile);
                } catch (IOException e) {
                    newGame();
                } finally {
                    load.dispatchEvent(new WindowEvent(load,WindowEvent.WINDOW_CLOSING));
                    runGame();
                }
            }
        });
        load.add(yesButton,BorderLayout.WEST);
    }

    //MODIFIES: load
    //EFFECTS: create a button. pressing this button means the user doesn't want to load the last game
    private void addNoButton(JFrame load) {
        JButton noButton = new JButton("No!");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
                load.dispatchEvent(new WindowEvent(load,WindowEvent.WINDOW_CLOSING));
                runGame();
            }
        });
        load.add(noButton, BorderLayout.EAST);
    }





    //MODIFIES:this
    //EFFECTS: continue playing 2048 game after 2 tiles are added to an empty 4x4 grid; allow users to use keyevent to
    // move the tiles. add a button allowing users to save the current progress
    // and another button allowing users to restart the game
    private void continueGame() {
        addKeyListener(new KeyHandler());
        saveButton = new JButton("save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameToRankingList();
            }
        });
        add(saveButton,BorderLayout.SOUTH);
        restartButton = new JButton("restart");
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        add(restartButton,BorderLayout.WEST);
        pack();

    }

    //MODIFIES: this
    //EFFECTS: when the game is over , end the game and save the current ranking to ranking list.
    private void endGame() {
//        System.out.println("Game Over!");
        JFrame gameOver = new JFrame("GameOver");
        gameOver.setFocusable(false);
        JLabel label = new JLabel("Game Over");
        label.setFont(new Font("Arial",Font.BOLD,100));
        gameOver.add(label,BorderLayout.CENTER);
        gameOver.pack();
        gameOver.setLocationRelativeTo(null);
        gameOver.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameOver.setVisible(true);
        saveGameToRankingList();
        new File(GRID_FILE).delete();
    }




    //REQUIRES: nextMove must be one of "a" "d" "w" "s" "q"
    //MODIFIES: this
    //EFFECTS: move grid according to nextMove. If the grid is movable in that direction, add a new tile, refresh the
    // grid and determines whether to end the game
    private void processCommand(String nextMove) {
        if (nextMove.equals("a")) {
            if (grid.ableToMoveLeft()) {
                grid.moveAndMergeLeft();
                afterMovingTiles();
            }
        } else if (nextMove.equals("d")) {
            if (grid.ableToMoveRight()) {
                grid.moveAndMergeRight();
                afterMovingTiles();
            }
        } else if (nextMove.equals("w")) {
            if (grid.ableToMoveUp()) {
                grid.moveAndMergeUp();
                afterMovingTiles();
            }
        } else if (nextMove.equals("s")) {
            if (grid.ableToMoveDown()) {
                grid.moveAndMergeDown();
                afterMovingTiles();
            }
        }



    }

    //MODIFIES: this
    //EFFECTS: add a new tile, refresh the grid and determines whether to end the game
    private void afterMovingTiles() {
        grid.addNewTile();
        printGrid();
        if (grid.isOver()) {
            endGame();
        }

    }


    //MODIFIES: this
    //EFFECTS: end the game and save the score obtained in the grid with the name
    private void saveGameToRankingList() {
        JFrame newWindow = new JFrame("save the current score");
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
        JLabel text = new JLabel("Enter the player's name");
        JTextField nameText = new JTextField("",10);
        JButton confirmButton = new JButton("confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                oneRanking.setName(name.substring(0, min(10, name.length())));
                oneRanking.setScore(grid.getScore());

                rankingList.addRanking(oneRanking);
                newWindow.dispatchEvent(new WindowEvent(newWindow,WindowEvent.WINDOW_CLOSING));
                newWindow.dispose();
            }
        });
        nameText.requestFocusInWindow();
        newWindow.add(text,BorderLayout.NORTH);
        newWindow.add(nameText,BorderLayout.CENTER);
        newWindow.add(confirmButton,BorderLayout.SOUTH);
        newWindow.pack();
        newWindow.setVisible(true);
    }

    //EFFECTS: save the game to GRID_FILE. save the ranking list no matter what.
    private void saveProgress() {
        if (!grid.isOver()) {
            try {
                Writer writer = new Writer(new File(GRID_FILE));
                writer.write(grid);
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("unable to save the game progress");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            Writer writer = new Writer(new File(RANKINGLIST_FILE));
            writer.write(rankingList);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("unable to save the ranking list");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    //MODIFIES: this
    //EFFECTS: clear the grid and oneRanking. And start a new game
    // and restart makes adding multiple oneRanking to rankinglist possible
    private void restart() {
        remove(gridPanel);
        remove(scorePanel);
        oneRanking = new OneRanking();
        newGame();
        gridPanel = new GridPanel(grid);
        add(gridPanel);
        scorePanel = new ScorePanel(grid,rankingList);
        add(scorePanel,BorderLayout.NORTH);
        printGrid();
    }


    //EFFECTS: print the 4x4 grid with numbered tiles, current score and highest history record
    private void printGrid() {
        scorePanel.update();
        gridPanel.repaint();
        setVisible(true);


    }

    //MODIFIES: this
    //EFFECTS: sort the RankingList before the ranking is printed, print the ranking list and ask for next instruction
    private void printRankingListOption() {
        try {
            rankingList.sortRankingList();
        } catch (InvalidRankingListException e) {
            rankingList = new RankingList(); // reset the rankingList
        } catch (IndexOutOfBoundsException e) {
            // does nothing
        }
        printRankingList();
    }

    //EFFECTS: print the ranking list
    private void printRankingList() {
        JFrame rankingWindow = new JFrame("Ranking");
        rankingWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rankingWindow.setSize(400,800);
        RankingListPanel rankingListPanel = new RankingListPanel(rankingList);
        rankingWindow.add(rankingListPanel);
        rankingWindow.pack();
        rankingWindow.setLocationRelativeTo(null);
        rankingWindow.setVisible(true);
        rankingWindow.repaint();
    }

    //Represents a key handler that responds to keyboard events
    private class KeyHandler extends KeyAdapter {

        @Override
        //MODIFIES: this
        //EFFECTS: updates grid in response to keyboard event
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_KP_LEFT:
                    processCommand("a");
                    break;
                case KeyEvent.VK_KP_RIGHT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    processCommand("d");
                    break;
                case KeyEvent.VK_KP_UP:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    processCommand("w");
                    break;
                case KeyEvent.VK_KP_DOWN:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    processCommand("s");
                    break;
            }
        }

    }


}
