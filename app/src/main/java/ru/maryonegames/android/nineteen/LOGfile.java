package ru.maryonegames.android.nineteen;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by vamar on 07.02.2018.
 */

public class LOGfile {

    private ArrayList<Integer> startArray = new ArrayList<Integer>();
    int mode = 0;
    private ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>> log = new ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>>();

    public LOGfile (int modecurrent, ArrayList<Integer> startcurrent, ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>> logcurrent){
        this.startArray.addAll(startcurrent);
        this.log = logcurrent;
        this.mode = modecurrent;
    }


    public  void addLog(Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> pair){

        this.log.add(pair);
    }

    public ArrayList<Integer> getStartArray() {
        return startArray;
    }
    public int getMode() {
        return mode;
    }

    public ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> getLog() {
        return this.log;
    }
}
