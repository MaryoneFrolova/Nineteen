package com.example.mika.nineteen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {

    TableLayout tb;
    ArrayList<ArrayList<ToggleButton>> matrixButton;
    ArrayList<Integer> whatToAdd;
    int sizeI = 9;
    Game a;
    int j = -1;
    int l = 0;
    int last = sizeI;
    int  check = 999;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tb = (TableLayout) findViewById(R.id.table_container);

        matrixButton = new ArrayList<ArrayList<ToggleButton>>();

        ArrayList<Integer> startArray = new ArrayList<Integer>();
        //startArray.addAll((Arrays.asList(1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,1)));
        //startArray.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,1,1,1,2,1,3,1,4,1,5,1,6,1,7,1,8,1,9));
        startArray.addAll(Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1));
        a = new Game();
        a.startGame(startArray, sizeI);
        initGame(startArray);


    }

    protected void addRow () {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, height/15, 1.0f);

        tb = (TableLayout) findViewById(R.id.table_container);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(tableParams);
        j = matrixButton.size() - 1;
        ArrayList<ToggleButton> rowButton = new ArrayList<ToggleButton>();
        for (int i = 0; i < sizeI; i += 1) {
            final ToggleButton oneButton = new ToggleButton(this);
            oneButton.setLayoutParams(rowParams);
            oneButton.setChecked(false);
            oneButton.setMinLines(1);

            oneButton.setId((j + 1) * 1000 + i);
            try {
                oneButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()) {
                            if (check == 999) {
                                check = compoundButton.getId();
                            } else if (check != 999) {
                                if (check == compoundButton.getId()) check = 999;
                                else {
                                    int current = compoundButton.getId();
                                    int res = a.stepGame(check % 1000, check / 1000, current % 1000, current / 1000);

                                    int result = res;
                                    switch (res) {
                                        case 1: {
                                            matrixButton.get(check / 1000).get(check % 1000).setChecked(false);
                                            check = compoundButton.getId();
                                            break;
                                        }
                                        case 2: {
                                            matrixButton.get(check / 1000).get(check % 1000).setBackgroundResource(R.drawable.cros);
                                            matrixButton.get(check / 1000).get(check % 1000).setClickable(false);
                                            matrixButton.get(check / 1000).get(check % 1000).setChecked(false);
                                            compoundButton.setBackgroundResource(R.drawable.cros);
                                            compoundButton.setChecked(false);
                                            compoundButton.setClickable(false);
                                            check = 999;
                                            whatToAdd = a.getWhatAdd();
                                            addElements();


                                            int iterCount = 0;
                                            while (a.isNeedAdd()) {
                                                a.addNumbers();
                                                whatToAdd = a.getWhatAdd();
                                                addElements();
                                                iterCount = whatToAdd.size();
                                                if (iterCount > 500) {
                                                    Toast.makeText(getApplicationContext(), "So hard game. I think you have not any chance to win", Toast.LENGTH_SHORT).show();
                                                    break;
                                                }
                                            }

                                            ArrayList<Integer> hideRow = a.howMuch();
                                            if (hideRow.size() != 0)
                                                for (int r = 0; r < hideRow.size(); r += 1 )
                                                {
                                                    TableRow trr = (TableRow) findViewById(1000000*(hideRow.get(r)+1));
                                                    trr.setVisibility(View.GONE);
                                                }
                                            break;

                                        }
                                        case 3: {
                                            matrixButton.get(check / 1000).get(check % 1000).setBackgroundResource(R.drawable.cros);
                                            matrixButton.get(check / 1000).get(check % 1000).setClickable(false);
                                            matrixButton.get(check / 1000).get(check % 1000).setChecked(false);
                                            compoundButton.setBackgroundResource(R.drawable.cros);
                                            compoundButton.setChecked(false);
                                            compoundButton.setClickable(false);
                                            check = 999;
                                            Toast.makeText(getApplicationContext(), "YOU WIN!!!", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        case 4: {
                                            matrixButton.get(check / 1000).get(check % 1000).setBackgroundResource(R.drawable.cros);
                                            matrixButton.get(check / 1000).get(check % 1000).setClickable(false);
                                            matrixButton.get(check / 1000).get(check % 1000).setChecked(false);
                                            compoundButton.setBackgroundResource(R.drawable.cros);
                                            compoundButton.setChecked(false);
                                            compoundButton.setClickable(false);
                                            check = 999;

                                            ArrayList<Integer> hideRow = a.howMuch();
                                            if (hideRow.size() != 0)
                                                for (int r = 0; r < hideRow.size(); r += 1 )
                                                {

                                                    TableRow trr = (TableRow) findViewById(1000000*(hideRow.get(r)+1));
                                                    trr.setVisibility(View.GONE);
                                                }
                                            break;
                                        }
                                    }

                                }
                            }
                        } else {
                            if (check != 999) {
                                if (check == compoundButton.getId()) check = 999;
                            }
                        }
                    }
                });
            }
            catch (RuntimeException ecs) {
                Toast.makeText(getApplicationContext(), "So hard game! I stop it!", Toast.LENGTH_SHORT).show();
                break;
            }
            rowButton.add(oneButton);
            tr.addView(oneButton);
        }
        tr.setId(( j + 2 ) * 1000000);
        tb.addView(tr);
        matrixButton.add(rowButton);
    }



    protected void initGame (ArrayList<Integer> arr) {

        int i;
        for (i = 0; i < arr.size(); i += 1, l+=1) {
            if (i % sizeI == 0 || i == 0) {
                addRow();
                j += 1;
                l = 0;
            }
            ToggleButton oneBtn = matrixButton.get(j).get(l);
            oneBtn.setText(String.valueOf(arr.get(i)));
            oneBtn.setTextOn(String.valueOf(arr.get(i)));
            oneBtn.setTextOff(String.valueOf(arr.get(i)));

        }
        int o = i % sizeI;
        if (o == 0) last = sizeI;
        else last = o;
        if (i % sizeI != 0) {
            while (o < sizeI) {
                ToggleButton oneBtn = matrixButton.get(j).get(o);
                oneBtn.setClickable(false);
                oneBtn.setText("");
                oneBtn.setTextOn("");
                oneBtn.setTextOff("");
                o += 1;
            }
        }
    }

    private void addElements () {
        int indexY = matrixButton.size();
        int indexX = last;

        int i;
        int index = 0;

        for ( i = 0; i < whatToAdd.size(); i += 1, index += 1) {


            if ((indexX + i) % sizeI == 0) {
                index = 0;
                if (indexX + i == sizeI){
                    if (indexX + whatToAdd.size() > sizeI - 1) addRow();
                }
                else {
                    addRow();
                }
            }
            if (indexX + i < sizeI) {
                matrixButton.get(indexY-1).get(indexX + i).setClickable(true);
                matrixButton.get(indexY-1).get(indexX + i).setText(String.valueOf(whatToAdd.get(i)));
                matrixButton.get(indexY-1).get(indexX + i).setTextOn(String.valueOf(whatToAdd.get(i)));
                matrixButton.get(indexY-1).get(indexX + i).setTextOff(String.valueOf(whatToAdd.get(i)));
            }
            else {
                ToggleButton oneBtn = matrixButton.get(matrixButton.size()-1).get(index);
                oneBtn.setText(String.valueOf(whatToAdd.get(i)));
                oneBtn.setTextOn(String.valueOf(whatToAdd.get(i)));
                oneBtn.setTextOff(String.valueOf(whatToAdd.get(i)));
            }
        }

        int o = (i + indexX) % sizeI;
        if (o == 0) last = sizeI;
        else last = o;
        if (i + indexX % sizeI != 0) {
            while (o < sizeI) {
                ToggleButton oneBtn = matrixButton.get(matrixButton.size()-1).get(o);
                oneBtn.setClickable(false);
                oneBtn.setText("");
                oneBtn.setTextOn("");
                oneBtn.setTextOff("");
                o += 1;
            }
        }


    }
}
