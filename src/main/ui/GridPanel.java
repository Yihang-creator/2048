package ui;

import model.Grid;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private static final int SPACE = 10; // the width of space between tiles
    private static final int SL = 150; // the side length of all tiles


    private Grid grid; // 4x4 grid with scores

    //REQUIRES: matrix is a 4x4 grid
    //EFFECTS: set this.matrix to matrix
    public GridPanel(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 4; i++) {
            for (int j = 0;j < 4; j++) {

                // draw the grey background
                g.setColor(new Color(200,200,200));
                g.fillRoundRect((j + 1) * SPACE + j * SL, (i + 1) * SPACE + i * SL,SL,SL,SL / 10,SL / 10);

                // draw the tiles
                if (grid.getRow(i)[j] != 0) {
                    int x = (j + 1) * SPACE + j * SL;
                    int y = (i + 1) * SPACE + i * SL;
                    String number = Integer.toString(grid.getRow(i)[j]);
                    g.setColor(colorSetter(grid.getRow(i)[j]));
                    g.fillRoundRect(x, y, SL, SL, SL / 10, SL / 10);
                    Font font = new Font("Arial", Font.BOLD,50);
                    g.setFont(font);
                    g.setColor(Color.white);
                    FontMetrics fontMetrics = getFontMetrics(font);
                    int width = fontMetrics.stringWidth(number);
                    int height = fontMetrics.getHeight();
                    int placeOfFontX = x + SL / 2 - width / 2;
                    int placeOfFontY = y + SL / 2;
                    g.drawString(number, placeOfFontX, placeOfFontY);
                }
            }
        }

    }

    private Color colorSetter(int number) {
        int changeDegree = 10;
        int exponentOfTwo = (int) (Math.log(number) / Math.log(2));
        return new Color(200 + changeDegree,200 - exponentOfTwo * changeDegree, 200 - exponentOfTwo * changeDegree);
    }


}
