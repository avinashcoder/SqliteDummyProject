package com.xcelcorp.sqlitedummyproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_PH_NO = "contact_number";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_ADDRESS="address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_AGE + " INTEGER," + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT,"+ KEY_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    public void addContact(String name,int age,String contact, String email, String address){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_AGE,age);
        values.put(KEY_PH_NO,contact);
        values.put(KEY_EMAIL,email);
        values.put(KEY_ADDRESS,address);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public JSONArray getAllData() throws JSONException {
        JSONArray contactArray = new JSONArray();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                JSONObject contact = new JSONObject();

                contact.put(KEY_ID,cursor.getString(0));
                contact.put(KEY_NAME,cursor.getString(1));
                contact.put(KEY_AGE,cursor.getString(2));
                contact.put(KEY_PH_NO,cursor.getString(3));
                contact.put(KEY_EMAIL,cursor.getString(4));
                contact.put(KEY_ADDRESS,cursor.getString(5));

                contactArray.put(contact);

            } while (cursor.moveToNext());
        }
        return  contactArray;
    }

    public void updateContact(int id,String name, int age, String email, String contact, String address ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_AGE, age);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PH_NO, contact);
        values.put(KEY_ADDRESS, address);

        // updating row
        db.update(TABLE_CONTACTS, values, KEY_ID+ " = "+id, null);
        db.close();
    }

    // Deleting single contact
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
