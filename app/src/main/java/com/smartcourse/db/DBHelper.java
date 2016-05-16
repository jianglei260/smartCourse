package com.smartcourse.db;

import java.io.File;

import com.example.smartcourse.AppManager;
import com.example.smartcourse.MyApplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_Name = "course";
    public static String COURSE_TBL_NAME = "coursetb";
    public static String USER_TBL_NAME = "usertb";
    private static final String CREATE_TBL = "create table if not exists"
            + " coursetb(_id integer primary key autoincrement,hour integer,weekday integer,startweek integer,endweek integer,name text,classRoom text,teacher text,flag integer)";
    private static final String USER_TBL = "create table if not exists " + "usertb(_id integer primary key autoincrement,depart varchar,pro varchar,grade varchar,class varchar)";
    private SQLiteDatabase db;
    private static DBHelper dbHelper;

    private DBHelper(Context context, String name) {
        super(context, name, null, 2);
        // TODO Auto-generated constructor stub
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context, DB_Name);
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        this.db = db;
        db.execSQL(CREATE_TBL);
        db.execSQL(USER_TBL);
    }

    public void insert(ContentValues values, String tbName) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(tbName, null, values);
        db.close();

    }

    public Cursor query(String tbName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(tbName, null, null, null, null, null, null);
        return c;
    }

    public void del(int id, String tbName) {
        if (db == null)
            db = getWritableDatabase();
        db.delete(tbName, "_id=?", new String[]{String.valueOf(id)});
    }

    public void close() {
        if (db != null)
            db.close();
    }

    @SuppressLint("NewApi")
    public void delAll(String tbName) {
        db = getWritableDatabase();
        if (tbName.equals(COURSE_TBL_NAME)) {
            db.execSQL("drop table " + tbName);
            db.execSQL(CREATE_TBL);
        } else if (tbName.equals(USER_TBL_NAME)) {
            db.execSQL("drop table " + USER_TBL_NAME);
            db.execSQL(USER_TBL);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
