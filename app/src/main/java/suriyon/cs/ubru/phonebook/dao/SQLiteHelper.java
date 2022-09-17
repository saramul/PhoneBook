package suriyon.cs.ubru.phonebook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import suriyon.cs.ubru.phonebook.model.PhoneBook;

public class SQLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private static String DATABASE_NAME = "phonebookdb";
    private static int DATABASE_VERSION = 2;
    private static String TABLE_NAME = "phonebook";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_PHONE = "phone";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_PHONE + " TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }

    public boolean insert(PhoneBook phoneBook) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, phoneBook.getName());
        values.put(COLUMN_PHONE, phoneBook.getPhone());
        long row = db.insert(TABLE_NAME, null, values);
        if(row!=-1)
            result = true;
        db.close();
        return result;
    }
    public boolean update(PhoneBook phoneBook) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, phoneBook.getName());
        values.put(COLUMN_PHONE, phoneBook.getPhone());
        int row = db.update(TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(phoneBook.getId())});
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean delete(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }
    public List<PhoneBook> select() {
        List<PhoneBook> phoneBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                PhoneBook phoneBook = new PhoneBook(id, name, phone);
                phoneBooks.add(phoneBook);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return phoneBooks;
    }
    public List<PhoneBook> select(String sname) {
        List<PhoneBook> phoneBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE '%" + sname + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                PhoneBook phoneBook = new PhoneBook(id, name, phone);
                phoneBooks.add(phoneBook);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return phoneBooks;
    }
    public PhoneBook select(int id) {
        PhoneBook phoneBook = new PhoneBook();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            phoneBook.setId(cursor.getInt(0));
            phoneBook.setName(cursor.getString(1));
            phoneBook.setPhone(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return phoneBook;
    }
}
