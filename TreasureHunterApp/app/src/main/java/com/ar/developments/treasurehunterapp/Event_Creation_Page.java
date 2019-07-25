package com.ar.developments.treasurehunterapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.ar.developments.treasurehunterapp.adapters.RecyclerListAdapter;
import com.ar.developments.treasurehunterapp.database.SQL_db;
import com.ar.developments.treasurehunterapp.helper.OnStartDragListener;
import com.ar.developments.treasurehunterapp.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/6/2018.
 */

public class Event_Creation_Page extends AppCompatActivity implements OnStartDragListener {
    private List<Clue > clueList = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private FloatingActionButton fab=null;
    private Dialog dialog=null;
    private Dialog generate_dialog=null;
    private RecyclerListAdapter adapter;
    private Contest contest;
    private int flag=0;
    private Typeface font1;
    private Typeface font2;
    private SQL_db db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_creation_page);
        font1 = Typeface.createFromAsset(this.getAssets(), "markazitext-vf.ttf");
        font2 = Typeface.createFromAsset(this.getAssets(), "markazitext-semibold.ttf");
        fab=(FloatingActionButton)findViewById(R.id.fab);
        contest= (Contest) getIntent().getSerializableExtra("Contest_obj");
        db=new SQL_db(this);
        fetch_data(contest);
        initilization();



    }

    private void initilization() {
        TextView actionbar_title1=(TextView)findViewById(R.id.actionbar_title1);
        actionbar_title1.setTypeface(font2);
        actionbar_title1.setText(contest.getFile_name());


        adapter = new RecyclerListAdapter(clueList,Event_Creation_Page.this);
        RecyclerView recyclerView =  (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("setOnLongClickListener",v+" ");

                return true;
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick",v+" ");
            }
        });

        final ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


        // add event dialog ----
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_add_item_dialog);
        TextView dialog_title=(TextView)dialog.findViewById(R.id.dialog_title);
        final TextView clue_title=(TextView)dialog.findViewById(R.id.clue_title);
        Button add_button=(Button)dialog.findViewById(R.id.btn_dialog);
        final EditText editText=(EditText)dialog.findViewById(R.id.edittxt_dialog);
        add_button.setTypeface(font2);
        dialog_title.setTypeface(font2);
        editText.setTypeface(font1);
        clue_title.setTypeface(font1);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=editText.getText().toString();
                if(txt.length()==0){
                    editText.setError("Please Enter Some Clue");
                }
                else{
                    db.add_clue(txt,contest);
                    clueList=db.get_all_clues(contest);
                    adapter.add_all(clueList);
                    editText.setText("");
                    dialog.dismiss();
                }
            }
        });

        generate_dialog = new Dialog(this);
        generate_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        generate_dialog.setContentView(R.layout.custome_export_dialog);
        final TextView txt_clue=(TextView)generate_dialog.findViewById(R.id.txt_clue);
        final TextView txt_save=(TextView)generate_dialog.findViewById(R.id.txt_save);
        final TextView txt_encrypt=(TextView)generate_dialog.findViewById(R.id.txt_encrypt);
        final TextView dialog_title2=(TextView)generate_dialog.findViewById(R.id.dialog_title);
        final TextView file_spinner=(TextView)generate_dialog.findViewById(R.id.file_spinner);
        final TextView clue_spinner=(TextView)generate_dialog.findViewById(R.id.clue_spinner);
        final Button btn_dialog=(Button)generate_dialog.findViewById(R.id.btn_dialog);
        dialog_title2.setTypeface(font2);
        txt_encrypt.setTypeface(font2);
        txt_save.setTypeface(font2);
        txt_clue.setTypeface(font2);
        clue_spinner.setTypeface(font1);
        file_spinner.setTypeface(font1);
        btn_dialog.setTypeface(font2);

        final PopupMenu popupMenu = new PopupMenu(this, file_spinner);
        popupMenu.inflate(R.menu.file_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                file_spinner.setText(item.getTitle());
                return false;
            }
        });
        final PopupMenu popupMenu2 = new PopupMenu(this, clue_spinner);
        popupMenu2.inflate(R.menu.clue_menu);
        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clue_spinner.setText(item.getTitle());
                return false;
            }
        });




        file_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        clue_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu2.show();
            }
        });

    }

    private void fetch_data(Contest contest) {
        clueList=db.get_all_clues(contest);
    }

    public void add_item_funct(View view){
        flag=1;
        dialog.show();

    }

    public void back_funct(View view){
        LinearLayout actionbar1=(LinearLayout)findViewById(R.id.actionbar1);
        LinearLayout actionbar2=(LinearLayout)findViewById(R.id.actionbar2);
        RecyclerView recyclerView =  (RecyclerView) findViewById(R.id.recycler_view);
        for(int i=0;i<clueList.size();i++){
            View child = recyclerView.getChildAt(i);
            if(child!=null){
                child.setBackgroundColor(Color.TRANSPARENT);
                View list_child = child.findViewById(R.id.list_child);
                list_child.setBackgroundResource(R.drawable.grid_shadow);
            }

        }
        actionbar1.setVisibility(View.VISIBLE);
        actionbar2.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    public void generate_funct(View view){
        generate_dialog.show();
    }

    public void edit_funct(View view){
        Clue obj=adapter.return_string_edit();
        edit(obj);
    }

    public void edit(final Clue obj){
        final Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.custome_add_item_dialog);
        Button add_button=(Button)dialog2.findViewById(R.id.btn_dialog);
        TextView clue_title=(TextView)dialog2.findViewById(R.id.clue_title);
        TextView dialog_title=(TextView)dialog2.findViewById(R.id.dialog_title);
        final EditText editText=(EditText)dialog2.findViewById(R.id.edittxt_dialog);
        add_button.setTypeface(font2);
        dialog_title.setTypeface(font2);
        editText.setTypeface(font1);
        clue_title.setTypeface(font1);
        editText.setText(obj.getClue_txt());
        add_button.setText("Update");
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=editText.getText().toString();
                if(txt.length()==0){
                    editText.setError("Please Enter Some Clue");
                }
                else{
                    obj.setClue_txt(txt);
                    db.edit_clue(contest,obj);
                    editText.setText("");
                    adapter.edit(obj);
                    dialog2.dismiss();
                }
            }
        });
        dialog2.show();
    }

    public void delete_funct(View view){
        adapter.remove(contest);
    }

    public void update_clue_list(){
        int count = adapter.getItemCount();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        clueList.clear();
        for (int i = 0; i < count; i++) {
            String text = ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.text)).getText().toString();
            String no = ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.txt_no)).getText().toString();
            Clue obj=new Clue();
            obj.setClue_txt(text);
            obj.setClue_id(Integer.parseInt(no));
            clueList.add(obj);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        LinearLayout actionbar1=(LinearLayout)findViewById(R.id.actionbar1);
        LinearLayout actionbar2=(LinearLayout)findViewById(R.id.actionbar2);

        Log.d("flag",flag+" ");
        if(actionbar1.getVisibility()==View.GONE){
            RecyclerView recyclerView =  (RecyclerView) findViewById(R.id.recycler_view);
            for(int i=0;i<clueList.size();i++){
                View child = recyclerView.getChildAt(i);
                if(child!=null){
                    Log.d("child",child+" ");
                    child.setBackgroundColor(Color.TRANSPARENT);
                    View list_child = child.findViewById(R.id.list_child);
                    list_child.setBackgroundResource(R.drawable.grid_shadow);
                }

            }
            actionbar1.setVisibility(View.VISIBLE);
            actionbar2.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
        else {
            update_clue_list();
            db.update_clues(contest,clueList);
            finish();
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
