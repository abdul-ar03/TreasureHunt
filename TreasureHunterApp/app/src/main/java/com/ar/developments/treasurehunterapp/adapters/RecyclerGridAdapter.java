package com.ar.developments.treasurehunterapp.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.developments.treasurehunterapp.Contest;
import com.ar.developments.treasurehunterapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 11/21/2018.
 */

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {

    private List<Contest> mItems = new ArrayList<Contest>();
    private  LinearLayout actionbar1=null;
    private  LinearLayout actionbar2=null;
    private FloatingActionButton fab=null;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    private Typeface custom_font;
    private Typeface custom_font2;

    public RecyclerGridAdapter(Context context, List<Contest> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = data;
        custom_font = Typeface.createFromAsset(context.getAssets(), "markazitext-vf.ttf");
        custom_font2 = Typeface.createFromAsset(context.getAssets(), "markazitext-semibold.ttf");

    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_item, parent, false);
        View view1 = (View) parent.getParent();
        View view2 = (View) view1.getParent();
        actionbar1=(LinearLayout)view2.findViewById(R.id.actionbar1);
        actionbar2=(LinearLayout)view2.findViewById(R.id.actionbar2);
        fab=(FloatingActionButton)view2.findViewById(R.id.fab);
        return new ViewHolder(view);
    }

    public void add_all(List<Contest> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        Log.d("pos",pos+" ");
        mItems.remove(pos);
        notifyItemRemoved(pos);
        actionbar1.setVisibility(View.VISIBLE);
        actionbar2.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    public Contest return_string_edit(int pos) {
        return mItems.get(pos);
    }

    public void edit(Contest item,int pos) {
        mItems.set(pos, item);
        notifyItemChanged(pos);
        actionbar1.setVisibility(View.VISIBLE);
        actionbar2.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_file_name.setText( (mItems.get(position).getFile_name()) );
        holder.txt_contest_name.setText( (mItems.get(position).getContest_name()) );
        holder.holder_item.setBackgroundColor(Color.TRANSPARENT);
        holder.grid_child.setBackgroundResource(R.drawable.grid_shadow);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mItems.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView txt_file_name;
        TextView txt_contest_name;
        View holder_item;
        View grid_child;
        ViewHolder(final View itemView) {
            super(itemView);
            txt_file_name = itemView.findViewById(R.id.txt_file_name);
            txt_contest_name = itemView.findViewById(R.id.txt_contest_name);
            grid_child = itemView.findViewById(R.id.grid_child);
            holder_item = itemView;
            txt_contest_name.setTypeface(custom_font2);
            txt_file_name.setTypeface(custom_font);

            itemView.setOnClickListener(this);
            final ViewPropertyAnimator animator3 = itemView.animate();
            animator3.setDuration(150);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
                    itemView.setEnabled(false);
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
                                                    itemView.setEnabled(true);
                                                }
                                            });
                                }
                            });
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mLongClickListener != null) mLongClickListener.onItemLongClick(v, getAdapterPosition());
                    itemView.setEnabled(false);
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
                                                    itemView.setEnabled(true);
                                                }
                                            });
                                }
                            });
                    return true;
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongClickListener != null) mLongClickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        Log.d("getitem",id+" ");
        return "1";
        //return mItems.get();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setLongClickListener(ItemLongClickListener itemClickListener) {
        this.mLongClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}