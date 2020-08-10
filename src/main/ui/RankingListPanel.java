package ui;

import model.RankingList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// a ranking list panel used to paint panel
public class RankingListPanel extends JPanel {
    private RankingList rankingList;
    int size = rankingList.getListOfScores().size();
    private Font font = new Font("Times New Roman", Font.BOLD, 30);
    FontMetrics fontMetrics = getFontMetrics(font);
    // height of rectangles
    private int height = fontMetrics.getHeight() * 2;
    // the maximum length of player name is 10
    private int widthOfPlayer = fontMetrics.stringWidth("characters") * 14 / 10;
    // assume the number of rank doesn't exceed 99
    private int widthOfRank   = fontMetrics.stringWidth("99") * 3;
    //assume the score of this game doesn't exceed 99999.
    private int widthOfScore  = fontMetrics.stringWidth("99999") * 9 / 5;


    //MODIFIES: this
    //EFFECTS: constructs a rankinglist panel
    public RankingListPanel(RankingList rankingList) {
        this.rankingList = rankingList;
    }

    @Override
    //EFFECTS: paint the rankingList
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<Integer> listOfScores = rankingList.getListOfScores();
        List<String> listOfNames = rankingList.getListOfPlayerNames();
        g.setFont(font);
        g.setColor(Color.BLACK);


        for (int i = 0; i < size; i++) {
            // print the header for the grid
            if (i == 0) {
                g.drawString("Rank", 0, 0);
                g.drawString("Name", widthOfRank, 0);
                g.drawString("score",widthOfPlayer + widthOfRank, 0);
            }

            String rank = Integer.toString(i + 1);
            String playerName = listOfNames.get(i).trim();
            String score = Integer.toString(listOfScores.get(i));
            int y = height * (i + 1);

            // draw grids
            g.drawRect(0,y,widthOfRank,height);
            g.drawRect(widthOfRank,y,widthOfPlayer,height);
            g.drawRect(widthOfPlayer + widthOfRank, y, widthOfScore,height);

            // draw strings
            int placeOfFontY = y + height / 4 + fontMetrics.getAscent();
            g.drawString(rank, 0, placeOfFontY);
            g.drawString(playerName, widthOfRank, placeOfFontY);
            g.drawString(score,widthOfPlayer + widthOfRank, placeOfFontY);
        }


    }

}
