package com.shariarunix.contact;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactTableClass extends DBHelper{
    public ContactTableClass(Context context) {
        super(context);
    }

    public void insertContact(ContactModel contactModel) {

        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(CONT_NAME, contactModel.getName());
        cValues.put(CONT_TITLE, contactModel.getTitle());
        cValues.put(CONT_PHONE, contactModel.getPhone());
        cValues.put(CONT_EMAIL, contactModel.getEmail());

        sqlDB.insert(TAB_CONTACT, null, cValues);
        sqlDB.close();
    }

    public ContactModel readContact(int dataId) {

        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = sqlDB.query(TAB_CONTACT, null, CONT_ID + "= ?", new String[]{String.valueOf(dataId), }, null, null, null);
        ContactModel contactModel = new ContactModel();

        while (cursor.moveToNext()) {

            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(CONT_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CONT_NAME));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(CONT_TITLE));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(CONT_PHONE));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(CONT_EMAIL));

            contactModel = new ContactModel(id, name, title, phone, email);
        }

        sqlDB.close();
        return contactModel;
    }

    public List<ContactModel> readAllContact() {
        List<ContactModel> contactsAll = new ArrayList<>();

        SQLiteDatabase sqlDB = getReadableDatabase();
        Cursor cursor = sqlDB.query(TAB_CONTACT, null, null, null, null, null, null );

        while (cursor.moveToNext()){

            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(CONT_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CONT_NAME));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(CONT_TITLE));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(CONT_PHONE));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(CONT_EMAIL));

            ContactModel contactModel = new ContactModel(id, name, title, phone, email);
            contactsAll.add(contactModel);

        }

        sqlDB.close();
        return contactsAll;
    }

    public void updateContact(ContactModel contactModel) {

        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(CONT_NAME, contactModel.getName());
        cValues.put(CONT_TITLE, contactModel.getTitle());
        cValues.put(CONT_PHONE, contactModel.getPhone());
        cValues.put(CONT_EMAIL, contactModel.getEmail());

        sqlDB.update(TAB_CONTACT, cValues, CONT_ID + "= ?", new String[]{String.valueOf(contactModel.getId()), });
        sqlDB.close();
    }

    public void deleteContact(int dataId) {

        SQLiteDatabase sqlDB = getWritableDatabase();
        sqlDB.delete(TAB_CONTACT, CONT_ID + "= ?", new String[]{String.valueOf(dataId), });
        sqlDB.close();
    }
}
