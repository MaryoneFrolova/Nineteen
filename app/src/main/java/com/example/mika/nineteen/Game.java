package com.example.mika.nineteen;

import android.util.Pair;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by Mika on 28.01.2018.
 */

public class Game {

    private ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
    private int sizeI = 9;

    public void startGame (ArrayList<Integer> startMatrix, int sizeI) {
        this.sizeI = sizeI;
        int i;
        ArrayList<Integer> thisRow = new ArrayList<Integer>();
        for ( i = 0; i < startMatrix.size(); i += 1) {
            if (i % sizeI == 0 && i != 0) {
                matrix.add(thisRow);
                thisRow = new ArrayList<Integer>();
            }
            thisRow.add(startMatrix.get(i));
        }
        matrix.add(thisRow);

    }

    public int stepGame (int i, int j, int ii, int jj) {


        if (j > jj) {
            int a = j;
            j = jj;
            jj = a;
            a = i;
            i = ii;
            ii = a;
        }
        if (j == jj && i > ii) {
            int a = i;
            i = ii;
            ii = a;
            a = j;
            j = jj;
            jj = a;
        }

        boolean a = isNeighbor(i, j, ii, jj);
        boolean b = isGood(i, j, ii, jj);
        if (!(a && b))
           {return 1;}

        matrix.get(j).set(i, 0);
        matrix.get(jj).set(ii, 0);

        if (isAllNull()) return 3;

        if (isNeedAdd()) return 2;

        return 4;
    }


    public boolean isNeighbor(int i, int j, int ii, int jj) {
        if (i == ii)
        {return case1(i, j, ii, jj);}
        if (j == jj)
        {return case2(i, j, ii, jj);}
        return case3(i, j, ii, jj);
    }

    public boolean case1(int i, int j, int ii, int jj) {
        for (int k = j + 1; k < jj; k += 1) {
            if (matrix.get(k).get(i) != 0 && k != jj) return false;
        }
        return true;
    }

    public boolean case2(int i, int j, int ii, int jj) {
            for (int k = i + 1; k <= ii; k += 1) {
                if (matrix.get(j).get(k) != 0 && k != ii) return false;
            }
            return true;
    }

    public boolean case3(int i, int j, int ii, int jj) {
        for (int l = j; l <= jj; l += 1) {
            for (int k = (l == j ? i + 1 : 0); (k < sizeI); k += 1) {
                if (l == jj && k == ii) return  true;
                if (matrix.get(l).get(k) != 0) {
                    return false;}
            }
        }
        return true;
    }

    public boolean isGood (int i, int j, int ii, int jj) {
        if (isEqual(i, j, ii, jj) || isSum(i, j, ii, jj)) {
            return true;
        }
        return false;
    }

    public boolean  isEqual (int i, int j, int ii, int jj) {
        if (matrix.get(j).get(i) == matrix.get(jj).get(ii)) return true;
        return false;
    }

    public boolean  isSum (int i, int j, int ii, int jj) {
        if (matrix.get(j).get(i) + matrix.get(jj).get(ii) == 10) return true;
        return false;
    }

    public boolean isAllNull () {
        for (int l = 0; l < matrix.size(); l += 1)
            for (int k = 0; k < sizeI; k += 1)
                if (matrix.get(l).get(k) != 0) return false;
        return true;
    }

    public boolean isNeedAdd () {
        for (int l = 0; l < matrix.size(); l += 1)
            for (int k = 0; k < sizeI; k += 1)
                if (matrix.get(l).get(k) != 0)
                    if (findGoodNeighbor(k, l)) return false;
        return true;
    }

    public boolean findGoodNeighbor (int i, int j) {
        if (findInLine(i, j) || findInRow(i, j) || findNext(i, j))  return true;
        else return false;
    }

    public boolean findInLine (int i, int j) {
        if (i == sizeI - 1) {return  false;}
        for (int l = i + 1; l < sizeI; l += 1)
        {
           if (matrix.get(j).get(l) != 0) {
               if (isGood(i, j, l, j)) return true;
           }

        }
        return false;
    }

    public boolean findInRow (int i, int j) {
        if (j == matrix.size() - 1) return  false;
        for (int l = j + 1; l < matrix.size(); l += 1)
        {
            if (matrix.get(l).get(i) != 0) {
                if (isGood(i, j, i, l)) return true;
            }

        }
        return false;
    }

    public boolean findNext (int i, int j) {
        for (int l = j; l < matrix.size(); l += 1)
        {
            if (i == sizeI - 1) continue;
            for (int k = (l == j ? i + 1 : 0); i < sizeI; i += 1) {
                if (matrix.get(l).get(k) != 0) {
                    if (isGood(i, j, l, k)) return true;
                }
            }
        }
        return false;
    }

}
