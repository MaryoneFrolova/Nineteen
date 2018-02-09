package ru.maryonegames.android.nineteen;

import android.util.Pair;

import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mika on 28.01.2018.
 */

public class Game {

    private ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
    private int sizeI = 9;
    private ArrayList<Integer> whatAdd = new ArrayList<>();
    private ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>> hints;

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

        if (isNeedAdd())
        {
            addNumbers();
            return 2;}

        return 4;
    }


    private boolean isNeighbor(int i, int j, int ii, int jj) {
        if (i == ii)
        {return case1(i, j, ii, jj);}
        if (j == jj)
        {return case2(i, j, ii, jj);}
        return case3(i, j, ii, jj);
    }

    private boolean case1(int i, int j, int ii, int jj) {
        for (int k = j + 1; k < jj; k += 1) {
            if (matrix.get(k).get(i) != 0 && k != jj) return false;
        }
        return true;
    }

    private boolean case2(int i, int j, int ii, int jj) {
            for (int k = i + 1; k <= ii; k += 1) {
                if (matrix.get(j).get(k) != 0 && k != ii) return false;
            }
            return true;
    }

    private boolean case3(int i, int j, int ii, int jj) {
        for (int l = j; l <= jj; l += 1) {
            for (int k = (l == j ? i + 1 : 0); (k < sizeI); k += 1) {
                if (l == jj && k == ii) return  true;
                if (matrix.get(l).get(k) != 0) {
                    return false;}
            }
        }
        return true;
    }

    private boolean isGood (int i, int j, int ii, int jj) {
        if (isEqual(i, j, ii, jj) || isSum(i, j, ii, jj)) {
            return true;
        }
        return false;
    }

    private boolean  isEqual (int i, int j, int ii, int jj) {
        if (matrix.get(j).get(i) == matrix.get(jj).get(ii)) return true;
        return false;
    }

    private boolean  isSum (int i, int j, int ii, int jj) {
        if (matrix.get(j).get(i) + matrix.get(jj).get(ii) == 10) return true;
        return false;
    }

    private boolean isAllNull () {
        for (int l = 0; l < matrix.size(); l += 1)
            for (int k = 0; k < matrix.get(l).size(); k += 1)
                if (matrix.get(l).get(k) != 0) return false;
        return true;
    }

    public boolean isNeedAdd () {
        for (int l = 0; l < matrix.size(); l += 1)
            for (int k = 0; k < matrix.get(l).size(); k += 1)
                if (matrix.get(l).get(k) != 0)
                    if (findGoodNeighbor(k, l)) return false;
        return true;
    }

    private boolean findGoodNeighbor (int i, int j) {
        if (findInLine(i, j) || findInRow(i, j) || findNext(i, j))  return true;
        else return false;
    }

    private boolean findInLine (int i, int j) {
        if (i == sizeI - 1) {return  false;}
        for (int l = i + 1; l < matrix.get(j).size(); l += 1)
        {
           if (matrix.get(j).get(l) != 0) {
               if (isGood(i, j, l, j)) return true;
               else return false;
           }

        }
        return false;
    }

    private boolean findInRow (int i, int j) {
        if (j == matrix.size() - 1) return  false;
        for (int l = j + 1; l < matrix.size(); l += 1)
        {
            if (matrix.get(l).size() - 1 < i) return false;
            if (matrix.get(l).get(i) != 0) {
                if (isGood(i, j, i, l)) return true;
                else return false;
            }

        }
        return false;
    }

    private boolean findNext (int i, int j) {
        for (int l = j; l < matrix.size(); l += 1)
        {
            //if (i == sizeI - 1) continue;
            for (int k = (l == j ? i + 1 : 0); k < matrix.get(l).size(); k += 1) {
                if (matrix.get(l).get(k) != 0) {
                    if (isGood(i, j, k, l)) return true;
                    else return false;
                }
            }
        }
        return false;
    }

    public void addNumbers () {
        whatAdd.removeAll(whatAdd);

        for (int j = 0; j < matrix.size(); j += 1) {
            for (int i = 0; i < matrix.get(j).size(); i += 1) {
                if (matrix.get(j).get(i) != 0)
                    whatAdd.add(matrix.get(j).get(i));
            }
        }

        int indexY = matrix.size();
        int indexX = matrix.get(indexY - 1).size();
        int i;

        ArrayList<Integer> thisRow = new ArrayList<Integer>();;
        for ( i = 0; i < whatAdd.size(); i += 1) {


            if ((indexX + i) % sizeI == 0) {
                if (indexX + i == sizeI){

                }
                else {
                    matrix.add(thisRow);
                    thisRow = new ArrayList<Integer>();
                }
            }
            if (indexX + i < sizeI) matrix.get(indexY-1).add(whatAdd.get(i));
            else thisRow.add(whatAdd.get(i));
        }
        if (thisRow.size() != 0)        matrix.add(thisRow);

    }

    public ArrayList<Integer> howMuch() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < matrix.size(); i += 1) {
            if (matrix.get(i).size() != sizeI) continue;
            int sum = 0;
            for (int j = 0; j < matrix.get(i).size(); j += 1){
                sum += matrix.get(i).get(j);
            }
            if (sum == 0) res.add(i);
        }
        return res;
    }

    public int leftNumbers() {
        int res = 0;
        for (int i = 0; i < matrix.size(); i += 1) {
            for (int j = 0; j < matrix.get(i).size(); j += 1){
                if (matrix.get(i).get(j) != 0) res += 1;
            }
        }
        return res;
    }

    public ArrayList<Integer> getWhatAdd() {
        return whatAdd;
    }

    public Pair<Pair<Integer,Integer>, Pair<Integer,Integer>> getHints() {
        hints = new ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>>();
        for (int l = 0; l < matrix.size(); l += 1)
            for (int k = 0; k < matrix.get(l).size(); k += 1)
                if (matrix.get(l).get(k) != 0)
                {findInLine2(k,l);
                findInRow2(k,l);
                findNext2(k,l);}



        Random random = new Random();
        Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> hint = hints.get(random.nextInt(hints.size()));
        return hint;
    }

    private boolean findInLine2 (int i, int j) {
        if (i == sizeI - 1) {return  false;}
        for (int l = i + 1; l < matrix.get(j).size(); l += 1)
        {
            if (matrix.get(j).get(l) != 0) {
                if (isGood(i, j, l, j))
                {hints.add(Pair.create(Pair.create(i,j),Pair.create(l,j)));
                return true;}

                else return false;
            }

        }
        return false;
    }

    private boolean findInRow2 (int i, int j) {
        if (j == matrix.size() - 1) return  false;
        for (int l = j + 1; l < matrix.size(); l += 1)
        {
            if (matrix.get(l).size() - 1 < i) return false;
            if (matrix.get(l).get(i) != 0) {
                if (isGood(i, j, i, l))
                {hints.add(Pair.create(Pair.create(i,j),Pair.create(i,l)));
                    return true;}
                else return false;
            }

        }
        return false;
    }

    private boolean findNext2 (int i, int j) {
        for (int l = j; l < matrix.size(); l += 1)
        {
            //if (i == sizeI - 1);

            for (int k = (l == j ? i + 1 : 0); k < matrix.get(l).size(); k += 1) {
                if (matrix.get(l).get(k) != 0) {
                    if (isGood(i, j, k, l)) {
                        hints.add(Pair.create(Pair.create(i, j), Pair.create(k, l)));
                        return true;
                    } else return false;
                }
            }

        }
        return false;
    }
}
