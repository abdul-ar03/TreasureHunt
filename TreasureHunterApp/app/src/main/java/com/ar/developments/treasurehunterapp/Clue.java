package com.ar.developments.treasurehunterapp;

import java.io.Serializable;

/**
 * Created by Admin on 12/10/2018.
 */

public class Clue implements Serializable {
    private int clue_id;
    private String clue_txt;

    public int getClue_id() {
        return clue_id;
    }

    public void setClue_id(int clue_id) {
        this.clue_id = clue_id;
    }

    public String getClue_txt() {
        return clue_txt;
    }

    public void setClue_txt(String clue_txt) {
        this.clue_txt = clue_txt;
    }
}
