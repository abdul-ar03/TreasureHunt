package com.ar.developments.treasurehunterapp.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ar.developments.treasurehunterapp.Clue;
import com.ar.developments.treasurehunterapp.Contest;
import com.ar.developments.treasurehunterapp.Contest_Creation_Homepage;
import com.ar.developments.treasurehunterapp.Event_Creation_Page;
import com.ar.developments.treasurehunterapp.Event_Pager;
import com.ar.developments.treasurehunterapp.R;
import com.ar.developments.treasurehunterapp.database.SQL_db;
import com.ar.developments.treasurehunterapp.helper.ItemClickListner;
import com.ar.developments.treasurehunterapp.helper.ItemTouchHelperAdapter;
import com.ar.developments.treasurehunterapp.helper.ItemTouchHelperViewHolder;
import com.ar.developments.treasurehunterapp.helper.OnStartDragListener;
import com.ar.developments.treasurehunterapp.helper.ItemLongClickListner;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final List<Clue> mItems = new ArrayList<>();
    private  LinearLayout actionbar1=null;
    private  LinearLayout actionbar2=null;
    private FloatingActionButton fab=null;
    public int item_press=0;
    private Typeface font1=null;
    public int current_position=0;
    private final OnStartDragListener mDragStartListener;
    private SQL_db db;

    public RecyclerListAdapter(List<Clue> list, Event_Creation_Page context) {
        mDragStartListener = context;
        mItems.addAll(list);
        font1 = Typeface.createFromAsset(context.getAssets(), "markazitext-vf.ttf");
        db=new SQL_db(context);
    }


    public void add_all(List<Clue> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(Contest contest) {
        Clue obj=mItems.get(current_position);
        mItems.remove(current_position);
        db.delete_clue(contest,obj);
        Log.d("delete",obj.getClue_txt()+" "+obj.getClue_id());
        actionbar1.setVisibility(View.VISIBLE);
        actionbar2.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        notifyDataSetChanged();
    }

    public Clue return_string_edit() {
        return mItems.get(current_position);
    }

    public void edit(Clue item) {
        mItems.set(current_position, item);
        notifyItemChanged(current_position);
        actionbar1.setVisibility(View.VISIBLE);
        actionbar2.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        View view1 = (View) parent.getParent();
        View view2 = (View) view1.getParent();
        actionbar1=(LinearLayout)view2.findViewById(R.id.actionbar1);
        actionbar2=(LinearLayout)view2.findViewById(R.id.actionbar2);
        fab=(FloatingActionButton)view2.findViewById(R.id.fab);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        Clue obj=mItems.get(position);
        holder.text_space.setText(obj.getClue_txt());
        int pos=position+1;
        Log.d("pos",""+pos);
        holder.number_space.setText(""+pos);
        holder.parentView.setBackgroundColor(Color.TRANSPARENT);
        holder.list_child.setBackgroundResource(R.drawable.grid_shadow);

        holder.setItemLongClickListener(new ItemLongClickListner() {
            @Override
            public void onItemLongClick(final View v, int pos) {
                final ViewPropertyAnimator animator3 = v.animate();
                animator3.setDuration(150);
                v.setEnabled(false);
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
                                                v.setEnabled(true);
                                            }
                                        });
                            }
                        });
                if(actionbar1.getVisibility()==View.VISIBLE) {
                    Log.d("pos",""+pos);
                    current_position=pos;
                    actionbar1.setVisibility(View.GONE);
                    actionbar2.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                    v.setBackgroundResource(R.drawable.grid_outer_border);
                    holder.list_child.setBackgroundResource(R.drawable.grid_shadow2);
                    item_press = 1;

                }

            }
        });


        holder.setItemClickListener(new ItemClickListner() {
            @Override
            public void onItemClick(View v, int pos) {
                Log.d("onClick",v+" ");
                Intent intent = new Intent((Context) mDragStartListener, Event_Pager.class);
                intent.putExtra("Clues", (Serializable) mItems);
                intent.putExtra("position",pos);
                ((Context) mDragStartListener).startActivity(intent);
            }
        });

                holder.move_icon_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d("touch", "touch" + item_press);
                        if (item_press == 0 || actionbar1.getVisibility() == View.VISIBLE) {
                            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                                mDragStartListener.onStartDrag(holder);
                            }
                        }
                        return false;
                    }
                });
    }


    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }




    public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder,View.OnLongClickListener, View.OnClickListener {
        public final TextView text_space;
        public final TextView number_space;
        public final ImageView handleView;
        public final LinearLayout move_icon_view;
        public final LinearLayout parentView;
        public final LinearLayout list_child;
        ItemLongClickListner itemLongClickListener;
        ItemClickListner itemClickListener;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            text_space = (TextView) itemView.findViewById(R.id.text);
            number_space = (TextView) itemView.findViewById(R.id.txt_no);
            text_space.setTypeface(font1);
            number_space.setTypeface(font1);
            handleView = (ImageView) itemView.findViewById(R.id.icon);
            move_icon_view=(LinearLayout)itemView.findViewById(R.id.move_icon_view);
            parentView=(LinearLayout)itemView.findViewById(R.id.parentView);
            list_child = itemView.findViewById(R.id.list_child);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundResource(R.drawable.grid_outer_border);
            list_child.setBackgroundResource(R.drawable.grid_shadow2);
        }

        @Override
        public void onItemClear() {
           itemView.setBackgroundColor(Color.TRANSPARENT);
            notifyDataSetChanged();
        }

        public void setItemLongClickListener(ItemLongClickListner ic) {
            this.itemLongClickListener=ic;
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onItemLongClick(v,getLayoutPosition());
            return true;
        }



        public void setItemClickListener(ItemClickListner ic) {
            this.itemClickListener=ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());

        }
    }

}