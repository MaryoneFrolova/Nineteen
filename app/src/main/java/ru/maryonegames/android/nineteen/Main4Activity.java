package ru.maryonegames.android.nineteen;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        TextView tv = (TextView) findViewById(R.id.textView2);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/tangak.otf");
        tv.setTypeface(custom_font);
    }

    public void go_site(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maryone.ru/"));
        startActivity(browserIntent);
    }

    public void write_msg(View view){
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setType("plain/text");
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL,new String[]{"maryonegames@gmail.com"});

        email.putExtra(Intent.EXTRA_SUBJECT, "AliasGame mail");
        email.putExtra(Intent.EXTRA_TEXT, "Write here");
        if (email.resolveActivity(getPackageManager()) != null)
            startActivity(email);
    }
}
