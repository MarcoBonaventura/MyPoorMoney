package com.example.mypoormoney;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DBNAME = "MoneyDB";
    public DBhelper(Context context) {
        super(context, DBNAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String q="CREATE TABLE "+DatabaseStrings.TBL_NAME+
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseStrings.FIELD_DATE +" TEXT," +
                DatabaseStrings.FIELD_IN + "TEXT," +
                DatabaseStrings.FIELD_OUT + " TEXT," +
                DatabaseStrings.FIELD_OPER + " TEXT)";
        db.execSQL(q);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { }
}

