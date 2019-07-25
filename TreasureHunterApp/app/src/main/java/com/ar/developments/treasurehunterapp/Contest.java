package com.ar.developments.treasurehunterapp;

/**
 * Created by Admin on 11/22/2018.
 */
import java.io.Serializable;
public class Contest implements Serializable{
    private int contest_id;
    private String file_name;
    private String contest_name;

    public String getContest_name() {
        return contest_name;
    }
    public void setContest_name(String contest_name) {
        this.contest_name = contest_name;
    }
    public String getFile_name() {
        return file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }


    public int getContest_id() {
        return contest_id;
    }

    public void setContest_id(int contest_id) {
        this.contest_id = contest_id;
    }
}
