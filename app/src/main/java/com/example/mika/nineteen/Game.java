package com.example.mika.nineteen;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Mika on 28.01.2018.
 */

public class Game {

    private ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
    private int sizeI = 9;

    public void startGame (ArrayList<Integer> startMatrix) {
        for (int i = 0; i < startMatrix.size();) {
            if (matrix.get(matrix.size() - 1).size() - 1 == sizeI) matrix.add(null);
            matrix.get(matrix.size() - 1).add(startMatrix.get(i));
            startMatrix.remove(0);

            if (startMatrix.size() == 0) break;
        }
    }

    public int stepGame (int i, int j, int ii, int jj) {
        int res = 0;

        if (j < jj) {
            int a = i;
            i = ii;
            ii = a;
            a = j;
            j = jj;
            jj = a;
        }
        if (j == jj && i < ii) {
            int a = i;
            i = ii;
            ii = a;
            a = j;
            j = jj;
            jj = a;
        }

        if (!isNeighbor(i, j, ii, jj) || !isGood(i, j, ii, jj)) return res = 1;

        if (isAllNull()) return res = 2;

        if (isNeedAdd()) return res = 3;

        return res = 4;
    }


    public boolean isNeighbor(int i, int j, int ii, int jj) {
        if (case1(i, j, ii, jj) || case1(i, j, ii, jj) || case3(i, j, ii, jj))
            return true;
        return true;
    }

    public boolean case1(int i, int j, int ii, int jj) {
    if (i == ii) {
        for (int k = j + 1; k < jj; k += 1) {
            if (matrix.get(k).get(i) != 0 && k != jj) return false;
        }
        return true;
    }
    return false;
    }

    public boolean case2(int i, int j, int ii, int jj) {
        if (j == jj) {
            for (int k = i + 1; k <= ii; k += 1) {
                if (matrix.get(j).get(k) != 0 && k != ii) return false;
            }
            return true;
        }
        return false;
    }

    public boolean case3(int i, int j, int ii, int jj) {
        for (int l = j; l <= jj; l += 1) {
            for (int k = (l == j) ? i + 1 : 0; k <= sizeI || (k == ii && l == jj); k += 1) {
                if (matrix.get(l).get(k) != 0 && l != jj && k != ii)
                    return false;
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
        if (matrix.get(i).get(j) + matrix.get(ii).get(jj) == 10) return true;
        return false;
    }

    public boolean isAllNull () {
        for (int l = 0; l <= matrix.size(); l += 1)
            for (int k = 0; k <= sizeI; k += 1)
                if (matrix.get(l).get(k) != 0) return false;
        return true;
    }

    public boolean isNeedAdd () {
        for (int l = 0; l <= matrix.size(); l += 1)
            for (int k = 0; k <= sizeI; k += 1)
                if (matrix.get(l).get(k) != 0)
                    if (findGoodNeighbor(l, k)) return false;
        return true;
    }

    public boolean findGoodNeighbor (int i, int j) {
        if (findInLine(i, j) || findInRow(i, j) || findNext(i, j))  return true;
        else return false;
    }

    public boolean findInLine (int i, int j) {
        for (int l = i + 1; l <= sizeI; l += 1)
        {
           if (matrix.get(j).get(l) != 0) {
               if (isGood(i, j, l, j)) return true;
           }

        }
        return false;
    }

    public boolean findInRow (int i, int j) {
        for (int l = j + 1; l <= matrix.size(); l += 1)
        {
            if (matrix.get(l).get(i) != 0) {
                if (isGood(i, j, i, l)) return true;
            }

        }
        return false;
    }

    public boolean findNext (int i, int j) {
        for (int l = j; l <= matrix.size(); l += 1)
        {
            for (int k = (l == j ? i + 1 : 0); i <= sizeI; i += 1) {
                if (matrix.get(l).get(k) != 0) {
                    if (isGood(i, j, l, k)) return true;
                }
            }
        }
        return false;
    }

}
