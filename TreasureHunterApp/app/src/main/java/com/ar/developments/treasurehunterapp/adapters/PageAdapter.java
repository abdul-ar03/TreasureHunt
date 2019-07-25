package com.ar.developments.treasurehunterapp.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.developments.treasurehunterapp.Clue;
import com.ar.developments.treasurehunterapp.R;

import java.util.ArrayList;


public class PageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<Clue> clueList ;
    private Typeface font1;

    public PageAdapter(Context context, ArrayList<Clue> list) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        font1 = Typeface.createFromAsset(context.getAssets(), "markazitext-vf.ttf");
        clueList= list;
    }

    @Override
    public int getCount() {
        return clueList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);
        TextView textView=(TextView)itemView.findViewById(R.id.pager_text);
        textView.setTypeface(font1);
        Clue obj=clueList.get(position);
        int pos=position+1;
        textView.setText(pos+". "+obj.getClue_txt());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}