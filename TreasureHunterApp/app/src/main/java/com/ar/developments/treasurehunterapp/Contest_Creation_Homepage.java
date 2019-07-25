package com.ar.developments.treasurehunterapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.ar.developments.treasurehunterapp.adapters.RecyclerGridAdapter;
import com.ar.developments.treasurehunterapp.database.SQL_db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/21/2018.
 */

public class Contest_Creation_Homepage extends Activity implements RecyclerGridAdapter.ItemClickListener,RecyclerGridAdapter.ItemLongClickListener{

    RecyclerGridAdapter adapter;
    private LinearLayout actionbar1=null;
    private  LinearLayout actionbar2=null;
    private FloatingActionButton fab=null;
    private RecyclerView recyclerView=null;
    private int prev_pos=0;
    private Dialog dialog=null;
    private Dialog menu_dialog=null;
    private int flag=0;
    private Typeface font1;
    private Typeface font2;
    private List<Contest> data=null;
    private View menupopup=null;
    private int intent_event_creation_page=4422;
    private SQL_db db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_creation_homepage);
        font1 = Typeface.createFromAsset(this.getAssets(), "markazitext-vf.ttf");
        font2 = Typeface.createFromAsset(this.getAssets(), "markazitext-semibold.ttf");
        db=new SQL_db(this);
        initilization();








        // set up the RecyclerView

    }

    private void initilization() {
        TextView actionbar_title=(TextView)findViewById(R.id.actionbar_title);
        actionbar1=(LinearLayout)findViewById(R.id.actionbar1);
        actionbar2=(LinearLayout)findViewById(R.id.actionbar2);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        actionbar_title.setTypeface(font2);
        data = db.get_all_contest();


        // recyler view
        int numberOfColumns = 2;
        recyclerView =  (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecyclerGridAdapter(this, data);
        adapter.setClickListener(this);
        adapter.setLongClickListener(this);
        recyclerView.setAdapter(adapter);


        // add file dialog box ......
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_add_file_dialog);
        Button add_button=(Button)dialog.findViewById(R.id.btn_dialog);
        final EditText edittxt_dialog_filename=(EditText)dialog.findViewById(R.id.edittxt_dialog_filename);
        final EditText edittxt_dialog_contestname=(EditText)dialog.findViewById(R.id.edittxt_dialog_contestname);
        TextView dialog_title=(TextView)dialog.findViewById(R.id.dialog_title);
        TextView file_name_txt=(TextView)dialog.findViewById(R.id.file_name_txt);
        TextView contest_name_txt=(TextView)dialog.findViewById(R.id.contest_name_txt);
        contest_name_txt.setTypeface(font1);
        file_name_txt.setTypeface(font1);
        dialog_title.setTypeface(font2);
        edittxt_dialog_filename.setTypeface(font1);
        edittxt_dialog_contestname.setTypeface(font1);
        add_button.setTypeface(font2);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        menu_dialog = new Dialog(this);
        menu_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu_dialog.setContentView(R.layout.menu_popup);
        TextView txt_qr=(TextView)menu_dialog.findViewById(R.id.txt_qr);
        TextView txt_file=(TextView)menu_dialog.findViewById(R.id.txt_file);
        final LinearLayout qr_div=(LinearLayout)menu_dialog.findViewById(R.id.qr_div);
        final LinearLayout contest_file_div=(LinearLayout)menu_dialog.findViewById(R.id.contest_file_div);
        txt_file.setTypeface(font1);
        txt_qr.setTypeface(font1);
        qr_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dialog.dismiss();
            }
        });
        contest_file_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dialog.dismiss();
            }
        });



        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file=edittxt_dialog_filename.getText().toString();
                String contest=edittxt_dialog_contestname.getText().toString();
                if(file.length()==0){
                    edittxt_dialog_filename.setError("Enter File Name");
                }
                if(contest.length()==0){
                    edittxt_dialog_contestname.setError("Enter Contest Name");
                }
                if(file.length()>0 && contest.length()>0){
                    Contest a=new Contest();
                    a.setContest_name(contest);
                    a.setFile_name(file);

                    if(db.create_table(a)){
                        db.create_contest(a);
                        data=db.get_all_contest();
                        adapter.add_all(data);
                        edittxt_dialog_filename.setText("");
                        edittxt_dialog_contestname.setText("");
                        dialog.dismiss();
                    }
                    else{
                        edittxt_dialog_filename.setError("Enter Different Name");

                    }


                }
            }
        });

    }


    public void add_item_funct(View view){
        flag=0;
        final EditText edittxt_dialog_filename=(EditText)dialog.findViewById(R.id.edittxt_dialog_filename);
        final EditText edittxt_dialog_contestname=(EditText)dialog.findViewById(R.id.edittxt_dialog_contestname);
        edittxt_dialog_filename.setText("");
        edittxt_dialog_contestname.setText("");
        edittxt_dialog_filename.setFocusable(true);
        dialog.show();
    }

    public void menu_click(View view){
        menu_dialog.show();
    }



    public void edit_funct(View view){
        final Contest obj=adapter.return_string_edit(prev_pos);
        Log.d("edit",obj.getContest_id()+"  "+obj.getContest_name());
        final Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.custome_add_file_dialog);
        Button add_button=(Button)dialog2.findViewById(R.id.btn_dialog);
        final EditText edittxt_dialog_filename=(EditText)dialog2.findViewById(R.id.edittxt_dialog_filename);
        final EditText edittxt_dialog_contestname=(EditText)dialog2.findViewById(R.id.edittxt_dialog_contestname);
        TextView dialog_title=(TextView)dialog2.findViewById(R.id.dialog_title);
        TextView file_name_txt=(TextView)dialog2.findViewById(R.id.file_name_txt);
        TextView contest_name_txt=(TextView)dialog2.findViewById(R.id.contest_name_txt);
        contest_name_txt.setTypeface(font1);
        file_name_txt.setTypeface(font1);
        dialog_title.setTypeface(font2);
        edittxt_dialog_filename.setTypeface(font1);
        edittxt_dialog_contestname.setTypeface(font1);
        edittxt_dialog_filename.setText(obj.getFile_name());
        edittxt_dialog_contestname.setText(obj.getContest_name());
        final Contest oldObj=obj;
        add_button.setTypeface(font2);
        add_button.setText("Rename");
        dialog2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file=edittxt_dialog_filename.getText().toString();
                String contest=edittxt_dialog_contestname.getText().toString();
                if(file.length()==0){
                    edittxt_dialog_filename.setError("Enter File Name");
                }
                if(contest.length()==0){
                    edittxt_dialog_contestname.setError("Enter Contest Name");
                }
                if(file.length()>0 && contest.length()>0 ){
                    edittxt_dialog_filename.setText("");
                    edittxt_dialog_contestname.setText("");
                    obj.setContest_name(contest);
                    obj.setFile_name(file);
                    adapter.edit(obj,prev_pos);
                    dialog2.dismiss();
                    flag=0;
                    if (!oldObj.getFile_name().equals(file) || !oldObj.getContest_name().equals(contest)){
                        db.update_contest(obj,oldObj.getFile_name());
                    }
                    for(int i=0;i<data.size();i++){
                        View child = recyclerView.getChildAt(i);
                        if(child!=null){
                            child.setBackgroundColor(Color.TRANSPARENT);
                            View grid_child = child.findViewById(R.id.grid_child);
                            grid_child.setBackgroundResource(R.drawable.grid_shadow);
                        }

                    }
                }
            }
        });
        dialog2.show();
    }

    public void delete_funct(View view){
        final Contest obj=adapter.return_string_edit(prev_pos);
        Log.d("delete",obj.getContest_id()+"  "+obj.getContest_name());
        db.delete_contest(obj);
        adapter.remove(prev_pos);
        data.remove(obj);
        flag=0;
        for(int i=0;i<data.size();i++){
            View child = recyclerView.getChildAt(i);
            if(child!=null){
                child.setBackgroundColor(Color.TRANSPARENT);
            }

        }
    }


    @Override
    public void onItemClick(View view, int position) {
        if (flag==0) {
            Log.d("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + (data.get(position)).getFile_name());
            Intent intent = new Intent(Contest_Creation_Homepage.this, Event_Creation_Page.class);
            intent.putExtra("Contest_obj", data.get(position));
            startActivityForResult(intent, intent_event_creation_page);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if(actionbar1.getVisibility()==View.VISIBLE && flag==0) {
                    flag=1;
                    prev_pos=position;
                    actionbar1.setVisibility(View.GONE);
                    actionbar2.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                    view.setBackgroundResource(R.drawable.grid_outer_border);
                    View grid_child = view.findViewById(R.id.grid_child);
                    grid_child.setBackgroundResource(R.drawable.grid_shadow2);
        }
        else {
            if (prev_pos==position){
                flag=0;
                actionbar1.setVisibility(View.VISIBLE);
                actionbar2.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                view.setBackgroundColor(Color.TRANSPARENT);
                View grid_child = view.findViewById(R.id.grid_child);
                grid_child.setBackgroundResource(R.drawable.grid_shadow);
            }


        }

    }

    public void back_funct(View view){
        flag=0;
        for(int i=0;i<data.size();i++){
            View child = recyclerView.getChildAt(i);
            if(child!=null){
                child.setBackgroundColor(Color.TRANSPARENT);
                View grid_child = child.findViewById(R.id.grid_child);
                grid_child.setBackgroundResource(R.drawable.grid_shadow);
            }

        }
        actionbar1.setVisibility(View.VISIBLE);
        actionbar2.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.d("flag",flag+" ");
        if(actionbar1.getVisibility()==View.GONE){
            flag=0;
            for(int i=0;i<data.size();i++){
                View child = recyclerView.getChildAt(i);
                if(child!=null){
                    child.setBackgroundColor(Color.TRANSPARENT);
                    View grid_child = child.findViewById(R.id.grid_child);
                    grid_child.setBackgroundResource(R.drawable.grid_shadow);
                }

            }
            actionbar1.setVisibility(View.VISIBLE);
            actionbar2.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
        else {
            flag=0;
            finish();
        }
    }
}

