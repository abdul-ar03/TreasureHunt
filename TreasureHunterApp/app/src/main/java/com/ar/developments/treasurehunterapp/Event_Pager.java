package com.ar.developments.treasurehunterapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import com.ar.developments.treasurehunterapp.adapters.PageAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;/**
 * Created by Admin on 12/14/2018.
 */

public class Event_Pager extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_pager_view);
        ArrayList<Clue> clueList = new ArrayList<Clue>();
        clueList = (ArrayList<Clue>) getIntent().getSerializableExtra("Clues");
        final int position=getIntent().getIntExtra("position",0);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        //FragmentManager fm = getSupportFragmentManager();
        PageAdapter pagerAdapter = new PageAdapter(this,(ArrayList<Clue>) clueList);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(position);
            }
        });
        pager.setAdapter(pagerAdapter);
    }
}
