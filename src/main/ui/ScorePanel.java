package ui;

import model.Grid;
import model.RankingList;

import javax.swing.*;
import java.awt.*;

// a score panel used to pain scores

public class ScorePanel extends JPanel {


    private RankingList rankingList;
    private Grid grid;
    private JLabel bestScore;
    private JLabel currentScore;
    private static final String BEST = "Best Score: ";
    private static final String CURRENT = " Current score: ";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 50;


    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Grid grid, RankingList rankingList) {
        this.grid = grid;
        this.rankingList = rankingList;
        setBackground(new Color(200, 200, 200));
        Font font = new Font("Arila", Font.BOLD,25);
        bestScore = new JLabel(BEST + rankingList.getHighestScore());
        bestScore.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        currentScore = new JLabel(CURRENT + grid.getScore());
        currentScore.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        bestScore.setFont(font);
        currentScore.setFont(font);
        add(bestScore);
        add(Box.createHorizontalStrut(50));
        add(currentScore);
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        bestScore.setText(BEST + rankingList.getHighestScore());
        currentScore.setText(CURRENT + grid.getScore());
        repaint();
    }
}
