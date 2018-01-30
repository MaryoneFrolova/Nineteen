package com.example.mika.nineteen;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {

    TableLayout tb;
    ArrayList<ArrayList<ToggleButton>> matrixButton;
    int sizeI = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tb = (TableLayout) findViewById(R.id.table_container);

        matrixButton = new ArrayList<ArrayList<ToggleButton>>();

        ArrayList<Integer> startArray = new ArrayList<Integer>();
        startArray.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,1,1,1,2,1,3,1,4,1,5,1,6,1,7,1,8,1,9));
        initGame(startArray);


    }

    protected void addRow () {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

        tb = (TableLayout) findViewById(R.id.table_container);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(tableParams);

        ArrayList<ToggleButton> rowButton = new ArrayList<ToggleButton>();
        for (int i = 0; i < sizeI; i += 1) {
            ToggleButton oneButton = new ToggleButton(this);
            oneButton.setLayoutParams(rowParams);
            oneButton.setChecked(true);
            rowButton.add(oneButton);
            tr.addView(oneButton);
        }
        tb.addView(tr);
        matrixButton.add(rowButton);
    }


    protected void initGame (ArrayList<Integer> arr) {
        int j = -1;
        int l = 0;
        int i = 0;
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
}
