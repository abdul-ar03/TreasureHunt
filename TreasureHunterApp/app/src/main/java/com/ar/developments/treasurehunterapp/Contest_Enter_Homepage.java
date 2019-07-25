package com.ar.developments.treasurehunterapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

/**
 * Created by Admin on 12/5/2018.
 */

public class Contest_Enter_Homepage extends Activity {
    public int intent_homepage=1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_enter_homepage);


        Typeface custom_font = Typeface.createFromAsset(this.getAssets(), "markazitext-vf.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(this.getAssets(), "markazitext-semibold.ttf");
        TextView event_name=(TextView)findViewById(R.id.event_name);
        event_name.setTypeface(custom_font2);

        LinearLayout scroll_container = (LinearLayout) findViewById(R.id.scroll_container);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View top_child = inflator.inflate(R.layout.hint_item_top, null);
        TextView txt1 = (TextView) top_child.findViewById(R.id.hint_txt);
        txt1.setTypeface(custom_font);
        scroll_container.addView(top_child);
        for (int i = 0; i < 8; i++) {

            View child = inflator.inflate(R.layout.hint_item_middle, null);
            TextView txt2 = (TextView) child.findViewById(R.id.hint_txt);
            txt2.setTypeface(custom_font);
            child.setId(i);
            child.setTag(i);
            scroll_container.addView(child);
        }

        View bottom_child = inflator.inflate(R.layout.hint_item_bottom, null);
        TextView txt3 = (TextView) bottom_child.findViewById(R.id.hint_txt);
        txt3.setTypeface(custom_font);
        scroll_container.addView(bottom_child);


        final TextView tx1 = (TextView) findViewById(R.id.st_time_txt);
        tx1.setTypeface(custom_font);

        TextView tx3 = (TextView) findViewById(R.id.scan_txt);
        tx3.setTypeface(custom_font2);
        Chronometer chronometer1 = (Chronometer)findViewById(R.id.chronometer2);
        chronometer1.setTypeface(custom_font);
        CircularFillableLoaders circularFillableLoaders = (CircularFillableLoaders) findViewById(R.id.circularFillableLoaders);
        circularFillableLoaders.setProgress(50);
// Set Wave and Border Color
        //circularFillableLoaders.setColor(R.color.orange2);
// Set Border Width
        circularFillableLoaders.setBorderWidth(5);
// Set Wave Amplitude (between 0.00f and 0.10f)
        circularFillableLoaders.setAmplitudeRatio(0.05f);


// https://android-arsenal.com/details/1/6347

        long startTime;
        SharedPreferences prefs = getSharedPreferences("stop_watch", MODE_PRIVATE);
        Long preTime = prefs.getLong("start_time", 0);
        if (preTime != 0) {
            Long temp = preTime- SystemClock.elapsedRealtime();
            startTime=SystemClock.elapsedRealtime()+temp;
        }
        else{
            startTime = SystemClock.elapsedRealtime();
        }

        Chronometer chronometer = (Chronometer)findViewById(R.id.chronometer2);
        chronometer.setBase(startTime);
        chronometer.start();
        Log.d("chronometer",preTime+" -- "+ startTime+" -- "+SystemClock.elapsedRealtime()+" "+chronometer.getBase());



        SharedPreferences.Editor editor = getSharedPreferences("stop_watch", MODE_PRIVATE).edit();
        editor.putLong("start_time", startTime);
        editor.apply();
    }
}
