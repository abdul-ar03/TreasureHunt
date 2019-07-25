package com.ar.developments.treasurehunterapp.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.ar.developments.treasurehunterapp.Clue;
import com.ar.developments.treasurehunterapp.Contest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SQL_db extends SQLiteOpenHelper {

    public SQL_db(Context context) {
        super(context, "Treasure_Hunt", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Contest = "Create table Contest ( c_id INTEGER PRIMARY KEY DEFAULT 1 , c_name Varchar(10), c_file Varchar(50) )";
        db.execSQL(Contest);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Contest");
        onCreate(db);
    }

    public void create_contest(Contest obj){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "insert into Contest (c_name,c_file) values('"+obj.getContest_name()+"','"+obj.getFile_name()+"')";
        db.execSQL(sql);
    }

    public boolean create_table(Contest obj){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String sql3 = "create table "+obj.getFile_name()+"(clue_id INTEGER PRIMARY KEY DEFAULT 1,clue varchar(3000) )";
            db.execSQL(sql3);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public List<Contest> get_all_contest() {
        List<Contest> mItems = new ArrayList<Contest>();
        String  selectQuery = "select * from Contest";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contest obj=new Contest();
                obj.setContest_id(cursor.getInt(0));
                obj.setContest_name(cursor.getString(1));
                obj.setFile_name(cursor.getString(2));
                mItems.add(obj);
               }
            while (cursor.moveToNext());
        }
        return mItems;
    }

    public void update_contest(Contest obj1,String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE Contest set c_name ='"+obj1.getContest_name()+"', c_file ='"+obj1.getFile_name()+"' where c_id="+obj1.getContest_id();
        if (!oldName.equals(obj1.getFile_name())){
            db.execSQL("ALTER TABLE " + oldName + " RENAME TO " + obj1.getFile_name()+";");
            db.execSQL(sql);
        }

    }

    public void delete_contest(Contest obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE from Contest where c_id="+obj.getContest_id();
        db.execSQL(sql);
        db.execSQL("DROP TABLE IF EXISTS "+obj.getFile_name());
    }

    public List<Clue> get_all_clues(Contest obj){
        List<Clue> clues = new ArrayList<Clue>();
        String  selectQuery = "select * from "+obj.getFile_name();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Clue temp=new Clue();
                temp.setClue_id(cursor.getInt(0));
                temp.setClue_txt(cursor.getString(1));
                clues.add(temp);
            }
            while (cursor.moveToNext());
        }
        return clues;
    }

    public void update_clues(Contest obj,List<Clue> clues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+obj.getFile_name());
        for(int i=0;i<clues.size();i++){
            Clue obj_clue=clues.get(i);
            String sql = "insert into "+obj.getFile_name()+" (clue) values('"+obj_clue.getClue_txt()+"')";
            db.execSQL(sql);
        }
    }

    public void add_clue(String txt, Contest obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "insert into "+obj.getFile_name()+" (clue) values('"+txt+"')";
        db.execSQL(sql);
    }

    public void edit_clue(Contest contest, Clue obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE "+contest.getFile_name()+" set clue ='"+obj.getClue_txt()+"' where clue_id="+obj.getClue_id();
        db.execSQL(sql);
        
    }

    public void delete_clue(Contest contest, Clue obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE from "+contest.getFile_name()+" where clue_id="+obj.getClue_id();
        db.execSQL(sql);
    }


//    public void create_table(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS feedtable");
////        if (cont!="") {
////            String[] re_arry1 = cont.split("\\$");
////            int count=1;
////            for (String ans : re_arry1) {
////                String[] re_arry2=ans.split("\\|");
////                String opt=re_arry2[0];
////                String name=re_arry2[1];
////                String f2=re_arry2[2];
////                String f3=re_arry2[3];
////                String f4=re_arry2[4];
////                String f0="field"+count;
////                Log.d("ok", "obj= {'id':'" + f0 + "','opt':'" + opt + "','f1':'" + name + "','f2':'" + f2 + "','f3':'" + f3 + "','f4':'" + f4 + "'};");
////                count++;
////            }
////        }
////        String[] re_arry1 = cont.split("\\$");
////        String tab_column="No INTEGER PRIMARY KEY AUTOINCREMENT ,";
////        int count=1;
////        for (String ans : re_arry1) {
////            String[] re_arry2=ans.split("\\|");
////            if(!re_arry2[0].equals("5") && !re_arry2[0].equals("8"))
////            {
////                tab_column+="col"+count+" varchar(100),";
////                count++;
////            }
////
////
////        }
////        tab_column=tab_column.substring(0,tab_column.length()-1);
////        Log.d("found", tab_column + "");
//
//        String sql3 = "create table feedtable (cont varchar(2000))";
//        db.execSQL(sql3);
//    }
//
//    public void insert_into_table(String cont){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "insert into feedtable values ('"+cont+"')";
//        db.execSQL(sql);
//    }
//
//    public String get_offline_count(){
//        String ans = null;
//        String  selectQuery = "select count(*) from feedtable";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//
//            do {
//                ans=cursor.getString(0);
//            }
//            while (cursor.moveToNext());
//        }
//        return ans;
//    }
//
//    public ArrayList get_offline_entry() {
//
//        ArrayList arrayList=new ArrayList();
//        String  selectQuery = "select * from feedtable";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        Log.d("anssss", "up");
//
//        if (cursor.moveToFirst()) {
//            Log.d("anssss","if");
//            do {
//                //content[0]=cursor.getString(1);
//                arrayList.add(cursor.getString(0));
////                content[1]=cursor.getString(2);
////                content[2]=cursor.getString(3);
////                content[3]=cursor.getString(4);
////                content[4]=cursor.getString(5);
////                content[5]=cursor.getString(6);
////                content[6]=cursor.getString(7);
////                Log.d("anssss"," "+content[0]+"  "+content[1]+" "+content[2]+"  "+content[3]+"  "+content[4]+"  "+content[5]+"  "+content[6]+"  ");
//            }
//            while (cursor.moveToNext());
//        }
//        return arrayList;
//    }


}