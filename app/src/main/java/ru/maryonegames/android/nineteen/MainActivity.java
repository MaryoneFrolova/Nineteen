package ru.maryonegames.android.nineteen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton btngame, btncont, btnrec, btnset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btngame = (ImageButton) findViewById(R.id.btngame);
        btncont = (ImageButton) findViewById(R.id.btncont);
        btnset = (ImageButton) findViewById(R.id.btnset);
        btnrec = (ImageButton) findViewById(R.id.btnrec);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/tangak.otf");

    }

    public void gameBtn (View view) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);

        startActivity(intent);
    }

    public  void contGame (View view) {

        SharedPreferences mPrefs2 = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        boolean hasSave = mPrefs2.getBoolean("hasSave", false);
        if (hasSave == false) {
            Toast.makeText(getApplicationContext(), "You haven't the save games!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("cont", (Integer) 1);
        startActivity(intent);
    }

    public void settAct (View view) {

            Intent intent = new Intent(MainActivity.this, Main3Activity.class);

            startActivity(intent);

    }

    public void infoBtn (View view) {

        Intent intent = new Intent(MainActivity.this, Main4Activity.class);

        startActivity(intent);

    }
}
