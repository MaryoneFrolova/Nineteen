package ru.maryonegames.android.nineteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    RadioButton rb_classic, rb_random;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        rb_classic = (RadioButton) findViewById(R.id.rb_classic);
        rb_random = (RadioButton) findViewById(R.id.rb_random);
        tv = (TextView) findViewById(R.id.textView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/tangak.otf");
        rb_random.setTypeface(custom_font);
        rb_classic.setTypeface(custom_font);
        tv.setTypeface(custom_font);

        SharedPreferences mPrefs2 = getSharedPreferences("Mode",Context.MODE_PRIVATE);
        int currentmode = mPrefs2.getInt("Mode", 0);
        if (currentmode != 0) rb_random.setChecked(true);

        rb_classic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rb_classic.isChecked())
                {
                    SharedPreferences mPrefs = getSharedPreferences("Mode",Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putInt("Mode", 0);
                    prefsEditor.commit();
                }
            }
        });

        rb_random.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rb_random.isChecked())
                {
                    SharedPreferences mPrefs = getSharedPreferences("Mode",Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putInt("Mode", 1);
                    prefsEditor.commit();
                }
            }
        });
    }
}
