package model;

import java.util.ArrayList;

// OneRanking is used to represent the player's name and his/her score in this single game
public class OneRanking {

    private String name;
    private int score;


    //EFFECTS: constructs a ranking with default name set to "" and default score set to 0
    public OneRanking() {
        this.name = "";
        this.score = 0;
    }

    //MODIFIES: this
    //EFFECTS: set the name of the player which the ranking belongs to to the given string
    public void setName(String name) {
        this.name = name;
    }


    //MODIFIES: this
    //EFFECTS; set the score of the ranking to the given integer.
    public void setScore(int score) {
        this.score = score;
    }

    //MODIFIES: this
    //EFFECTS; set the score of the ranking to the score obtained in the given Grid
    public void extractScore(Grid grid) {
        this.score = grid.getScore();
    }

    //EFFECTS; return name of the ranking
    public String getName() {
        return this.name;
    }

    //EFFECTS: return the score of the ranking
    public int getScore() {
        return this.score;
    }

    
}

