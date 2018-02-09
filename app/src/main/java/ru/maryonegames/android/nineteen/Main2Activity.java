package ru.maryonegames.android.nineteen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    TextView tvscore, tvleft, tv1, tv2;
    LOGfile log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        int cont = intent.getIntExtra("cont", 0);


        tb = (TableLayout) findViewById(R.id.table_container);
        tvscore = (TextView) findViewById(R.id.tvscore);
        tvleft = (TextView) findViewById(R.id.tvleft);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        matrixButton = new ArrayList<ArrayList<ToggleButton>>();


        SharedPreferences mPrefs2 = getSharedPreferences("Mode",Context.MODE_PRIVATE);
        int currentmode = mPrefs2.getInt("Mode", 0);
        ArrayList<Integer> startArray = new ArrayList<Integer>();

        if (cont == 1)
        {
            SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("MyLog", "");
            LOGfile logo = gson.fromJson(json, LOGfile.class);
            startArray.addAll(logo.getStartArray());
        }
        else {


        if (currentmode != 0) {
                Random random = new Random();
                for (int y = 1; y <= sizeI * 3; y += 1) {
                    startArray.add(random.nextInt(9) + 1);
                }
            } else {
                startArray.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7, 1, 8, 1, 9));
            //startArray.addAll(Arrays.asList(1,1,1,1));
        }
        }
        log = new LOGfile(currentmode, startArray, new ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>>());


        a = new Game();
        a.startGame(startArray, sizeI);
        initGame(startArray);
        tvleft.setText(String.valueOf(a.leftNumbers()));

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/tangak.otf");
        tvleft.setTypeface(custom_font);
        tvscore.setTypeface(custom_font);
        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);

        if (cont == 1)
        {
            SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("MyLog", "");
            LOGfile logo = gson.fromJson(json, LOGfile.class);
            ArrayList<Pair<Pair<Integer,Integer>, Pair<Integer,Integer>>> logfile = logo.getLog();

            for (int i = 0; i < logfile.size(); i += 1) {
                matrixButton.get(logfile.get(i).first.second).get(logfile.get(i).first.first).setChecked(true);
                matrixButton.get(logfile.get(i).second.second).get(logfile.get(i).second.first).setChecked(true);            }
        }


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
            oneButton.setBackgroundResource(R.drawable.toggle_selector);
            oneButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

            Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/tangak.otf");
            oneButton.setTextColor(Color.parseColor("#ffffff"));
            oneButton.setTypeface(custom_font);
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
                                    if (res != 1) {
                                        tvscore.setText(String.valueOf(Integer.parseInt((String) tvscore.getText())+1));
                                        tvleft.setText(String.valueOf(a.leftNumbers()));
                                    }
                                    int result = res;
                                    switch (res) {
                                        case 1: {
                                            matrixButton.get(check / 1000).get(check % 1000).setChecked(false);
                                            check = compoundButton.getId();
                                            break;
                                        }
                                        case 2: {
                                            log.addLog(Pair.create(Pair.create(check % 1000, check / 1000), Pair.create(current % 1000, current / 1000)));

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
                                            log.addLog(Pair.create(Pair.create(check % 1000, check / 1000), Pair.create(current % 1000, current / 1000)));

                                            matrixButton.get(check / 1000).get(check % 1000).setBackgroundResource(R.drawable.cros);
                                            matrixButton.get(check / 1000).get(check % 1000).setClickable(false);
                                            matrixButton.get(check / 1000).get(check % 1000).setChecked(false);
                                            compoundButton.setBackgroundResource(R.drawable.cros);
                                            compoundButton.setChecked(false);
                                            compoundButton.setClickable(false);
                                            check = 999;
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                                            builder.setTitle("Congretulations!")
                                                    .setMessage("YOU WIN!!!")
                                                    .setIcon(R.drawable.good)
                                                    .setCancelable(false)
                                                    .setNegativeButton("Nice",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            });
                                            AlertDialog alert = builder.create();
                                            alert.show();


                                            break;
                                        }
                                        case 4: {
                                            log.addLog(Pair.create(Pair.create(check % 1000, check / 1000), Pair.create(current % 1000, current / 1000)));

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
        if ((i + indexX) % sizeI != 0) {
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

    public void showHint(View view) {
        Pair<Pair<Integer,Integer>, Pair<Integer,Integer>> currentHint = a.getHints();

        Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        matrixButton.get(currentHint.first.second).get(currentHint.first.first).startAnimation(animAlpha);
        matrixButton.get(currentHint.second.second).get(currentHint.second.first).startAnimation(animAlpha);


    }

    public void undoStep(View view)  {
        if (log.getLog().size() == 0)
        {
            Toast.makeText(getApplicationContext(), "You don't make first step", Toast.LENGTH_SHORT).show();
            return;

        }


        ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>> logo = log.getLog();
        logo.remove(logo.size()-1);
        int size = logo.size();
        int modo = log.getMode();
        ArrayList<Integer> startAr = log.getStartArray();


        tb.removeAllViews();
        a = new Game();
        a.startGame(startAr,sizeI);
        matrixButton.removeAll(matrixButton);
        initGame(startAr);
        tvscore.setText(String.valueOf(0));




        log = new LOGfile(modo, startAr, new ArrayList<Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>>());
        for (int i = 0; i < size; i += 1){
            matrixButton.get(logo.get(i).first.second).get(logo.get(i).first.first).setChecked(true);
            matrixButton.get(logo.get(i).second.second).get(logo.get(i).second.first).setChecked(true);
        }
        tvleft.setText(String.valueOf(a.leftNumbers()));


    }

    public void saveGame(View view)  {
        Pair<Pair<Integer,Integer>, Pair<Integer,Integer>> currentHint = a.getHints();

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(log); // myObject - instance of MyObject
        prefsEditor.putString("MyLog", json);
        prefsEditor.commit();

        SharedPreferences mPrefs3 = getSharedPreferences("Mode",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefs3Editor = mPrefs3.edit();
        prefs3Editor.putBoolean("hasSave", true);
        prefs3Editor.commit();

        AlertDialog.Builder ad;
        final Context context = Main2Activity.this;
        ad = new AlertDialog.Builder(context);
        ad.setTitle("Exit game?");  // заголовок
        ad.setIcon(R.drawable.exit);
        ad.setMessage("The game is save. Do you want exit now?"); // сообщение
        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, "The game was save", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "The game was save",
                        Toast.LENGTH_LONG).show();
            }
        });

        ad.show();
    }
}

