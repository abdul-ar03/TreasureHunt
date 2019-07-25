package com.ar.developments.treasurehunterapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.button;

/**
 * Created by Admin on 11/19/2018.
 */

public class Homepage extends Activity {
    private int intent_contest_creation=1212;
    private int intent_contest_enter=1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Typeface font1 = Typeface.createFromAsset(this.getAssets(), "markazitext-vf.ttf");
        Typeface heading_font = Typeface.createFromAsset(this.getAssets(), "CYBERTOOTH.ttf");
        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "markazitext-semibold.ttf");
        final Button button_enter_contest = (Button) findViewById(R.id.button_enter_contest);
        final Button button_create_contest = (Button) findViewById(R.id.button_create_contest);
        final ImageView img_about= (ImageView)findViewById(R.id.img_about);
        final ImageView img_rating= (ImageView)findViewById(R.id.img_rating);
        final ImageView img_sharing= (ImageView)findViewById(R.id.img_sharing);
        TextView txt_about = (TextView) findViewById(R.id.txt_about);
        TextView txt_rating = (TextView) findViewById(R.id.txt_rating);
        TextView txt_share = (TextView) findViewById(R.id.txt_share);
        TextView txt_app_name1 = (TextView) findViewById(R.id.txt_app_name1);
        TextView txt_app_name2 = (TextView) findViewById(R.id.txt_app_name2);
        button_enter_contest.setTypeface(font2);
        button_create_contest.setTypeface(font2);
        txt_about.setTypeface(font1);
        txt_rating.setTypeface(font1);
        txt_share.setTypeface(font1);
        txt_app_name1.setTypeface(heading_font);
        txt_app_name2.setTypeface(heading_font);

//        button_enter_contest.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
//                        v.invalidate();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP: {
//                        v.getBackground().clearColorFilter();
//                        v.invalidate();
//                        break;
//                    }
//                }
//                return false;
//            }
//        });


        final ViewPropertyAnimator animator3 = img_about.animate();
        animator3.setDuration(150);
        img_about.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                img_about.setEnabled(false);
                animator3.scaleXBy(-0.09f)
                        .scaleYBy(-0.09f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animator3.scaleXBy(0.09f)
                                        .scaleYBy(0.09f)
                                        .setListener(null)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                img_about.setEnabled(true);
                                            }
                                        });
                            }
                        });
                return false;

            }
        });

        final ViewPropertyAnimator animator4 = img_sharing.animate();
        animator4.setDuration(150);
        img_sharing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                img_sharing.setEnabled(false);
                animator4.scaleXBy(-0.09f)
                        .scaleYBy(-0.09f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animator4.scaleXBy(0.09f)
                                        .scaleYBy(0.09f)
                                        .setListener(null)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                img_sharing.setEnabled(true);
                                            }
                                        });
                            }
                        });
                return false;
            }
        });

        final ViewPropertyAnimator animator5 = img_rating.animate();
        animator5.setDuration(150);
        img_rating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                img_rating.setEnabled(false);
                animator5.scaleXBy(-0.09f)
                        .scaleYBy(-0.09f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animator5.scaleXBy(0.09f)
                                        .scaleYBy(0.09f)
                                        .setListener(null)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                img_rating.setEnabled(true);
                                            }
                                        });
                            }
                        });
                return false;

            }
        });



    }

    public void create_contest_funt(View view){
        Intent intent = new Intent(Homepage.this, Contest_Creation_Homepage.class);
        startActivityForResult(intent,intent_contest_creation);
        Log.d("onclick","ok");
    }

    public void enter_contest_funt(View view){
        Intent intent = new Intent(Homepage.this, Contest_Enter_Homepage.class);
        startActivityForResult(intent,intent_contest_enter);
        Log.d("onclick","ok");
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
