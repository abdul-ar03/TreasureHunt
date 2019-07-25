package com.ar.developments.treasurehunterapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

public class MainActivity extends Activity {
    public int intent_homepage=1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Typeface heading_font = Typeface.createFromAsset(this.getAssets(), "CYBERTOOTH.ttf");
        Typeface custom_font = Typeface.createFromAsset(this.getAssets(), "markazitext-vf.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(this.getAssets(), "markazitext-semibold.ttf");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Homepage.class);
                startActivityForResult(intent,intent_homepage);
            }
        }, 300);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == intent_homepage) {
            if(resultCode == Activity.RESULT_OK){
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }
}
