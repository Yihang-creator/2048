package ui;

import model.Grid;

import javax.swing.*;
import java.awt.*;

// a grid panel used to paint grid
public class GridPanel extends JPanel {
    private static final int SPACE = 10; // the width of space between tiles
    private static final int SL = 150; // the side length of all tiles


    private Grid grid; // 4x4 grid with scores

    //EFFECTS: set this.matrix to matrix and set size of the grid panel
    public GridPanel(Grid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(650,650));

    }

    @Override
    //EFFECTS: paint the grid with numbers
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 4; i++) {
            for (int j = 0;j < 4; j++) {
                int x = (j + 1) * SPACE + j * SL;
                int y = (i + 1) * SPACE + i * SL;

                // draw the grey background
                g.setColor(new Color(200,200,200));
                g.fillRoundRect(x, y, SL, SL,SL / 10,SL / 10);

                // draw the tiles
                if (grid.getRow(i)[j] != 0) {
                    String number = Integer.toString(grid.getRow(i)[j]);
                    g.setColor(colorSetter(grid.getRow(i)[j]));
                    g.fillRoundRect(x, y, SL, SL, SL / 10, SL / 10);
                    Font font = new Font("Arial", Font.BOLD,70);
                    g.setFont(font);
                    g.setColor(Color.white);
                    FontMetrics fontMetrics = getFontMetrics(font);
                    int width = fontMetrics.stringWidth(number);
                    int height = fontMetrics.getHeight();
                    int placeOfFontX = x + SL / 2 - width / 2;
                    int placeOfFontY = y + SL / 2 - height / 2 + fontMetrics.getAscent();
                    g.drawString(number, placeOfFontX, placeOfFontY);
                }
            }
        }

    }

    //REQUIRE: number <= 20;
    //EFFECTS: return the color of tiles numbered number over 2.
    private Color colorSetter(int number) {
        int changeDegree = 10;
        int exponentOfTwo = (int) (Math.log(number) / Math.log(2));
        return new Color(200 + changeDegree,200 - exponentOfTwo * changeDegree, 200 - exponentOfTwo * changeDegree);
    }


}
