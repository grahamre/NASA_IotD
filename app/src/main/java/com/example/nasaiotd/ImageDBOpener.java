package com.example.nasaiotd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ImageDBOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "nasa_iotd";
    protected final static int VERSION_NUM = 2;
    public final static String TABLE_NAME = "image";
    public final static String COL_ID = "id";
    public final static String COL_TITLE = "title";
    public final static String COL_DATE = "date";
    public final static String COL_DESCRIPTION = "description";
    public final static String COL_URL = "url";
    public final static String COL_HDURL = "hdurl";

    public ImageDBOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT,"
                + COL_DATE  + " TEXT,"
                + COL_DESCRIPTION + " TEXT,"
                + COL_URL  + " TEXT,"
                + COL_HDURL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
