package com.shariarunix.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAB_CONTACT = "contact";
    public static final String CONT_ID = "id";
    public static final String CONT_NAME = "name";
    public static final String CONT_TITLE = "title";
    public static final String CONT_PHONE = "phone";
    public static final String CONT_EMAIL = "email";

    private Context context;
    public DBHelper(Context context) {
        super(context, "contact_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS contact(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, title TEXT, phone TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
