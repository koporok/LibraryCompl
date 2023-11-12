package com.example.Library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//Класс для доступа к БД
public class DataBaseAccessor extends SQLiteOpenHelper
{
    // Основные данные базы
    private static final String DATABASE_NAME = "listofbooks.db";
    private static final int DB_VERSION = 3;

    // таблицы
    private static final String TABLE_BOOKS = "Books";


    // столбцы таблицы Computers
    private static final String COLUMN_ID = "_id";//Обязательно с подчеркиванием
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_REVIEW = "preview";



    public DataBaseAccessor(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создать таблицу
        db.execSQL("CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_REVIEW + " TEXT);");

        db.execSQL("INSERT INTO " + TABLE_BOOKS + "(" + COLUMN_TITLE + ", " + COLUMN_GENRE + ", " + COLUMN_AUTHOR + ", " + COLUMN_REVIEW + ") values('comp1','вкл', 'Пенза', '04.11.2023 17:52')");
        db.execSQL("INSERT INTO " + TABLE_BOOKS + "(" + COLUMN_TITLE + ", " + COLUMN_GENRE + ", " + COLUMN_AUTHOR + ", " + COLUMN_REVIEW + ") values('comp2','выкл', 'Пенза', '04.11.2023 15:37')");


    }

    @SuppressLint("Range")
    public ArrayList<Book> getAllData() {
        ArrayList<Book> dataItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKS,null);

        if (cursor.moveToFirst()) {
            do {
                Book item = new Book();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                item.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                item.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                item.setReview(cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW)));
                dataItems.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dataItems;
    }


    public void updateBook(int id, String name, String status, String location, String online) {
        // выполнить запрос на обновление БД
        getReadableDatabase().execSQL("UPDATE "+ TABLE_BOOKS
                + " SET "
                + COLUMN_TITLE + "='" + name + "', "
                + COLUMN_AUTHOR + "='" + location + "', "
                + COLUMN_REVIEW + "='" + online + "', "
                + COLUMN_GENRE + "='" + status + "'"
                + " WHERE "
                + COLUMN_ID + "=" + id);
    }

    public void addBook(String name, String status, String location, String online) {
        getReadableDatabase().execSQL("INSERT INTO " + TABLE_BOOKS + "(" + COLUMN_TITLE + ", " + COLUMN_GENRE + ", " + COLUMN_AUTHOR + ", " + COLUMN_REVIEW + ") values('" + name + "','" + status + "', '" + location + "', '" + online + "')");
    }

    public void deleteBook(int id){
        getReadableDatabase().execSQL("DELETE FROM " + TABLE_BOOKS + " WHERE " +  COLUMN_ID + " = " + id + ";");
        System.out.println("DELETE FROM" + TABLE_BOOKS + " + WHERE " +  COLUMN_ID + " = " + id + ";");
    }

    public void editStatus(int id, String status){
        getReadableDatabase().execSQL("UPDATE "+ TABLE_BOOKS
                + " SET "
                + COLUMN_GENRE + "='" + status + "'"
                + " WHERE "
                + COLUMN_ID + "=" + id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try
        {
            //удалить старую таблицу
            db.execSQL("DROP TABLE " + TABLE_BOOKS);
        }
        catch (Exception exception)
        {

        }
        finally {
            //создать новую и заполнить ее
            onCreate(db);
        }
    }
}