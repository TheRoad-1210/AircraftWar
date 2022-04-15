package edu.hitsz.player;

import java.io.Serializable;

/**
 * @author 200111013
 */
public class Player implements Serializable {
    private int score;
    private String name;
    private String time;
    public Player(int score, String time){
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
