package com.ingwesley.www.easytouriste_true.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.ingwesley.www.easytouriste_true.All_Models.DbColumn;

import static android.provider.BaseColumns._ID;


public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "touriste.db";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + DbColumn.Endroits_Column.TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY ," +
                DbColumn.Endroits_Column.NOM_END + " TEXT " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + DbColumn.Endroits_Column.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DbColumn.Endroits_Column.TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, id);
        contentValues.put(DbColumn.Endroits_Column.NOM_END, "");
        //contentValues.put(NOM_END,endStr.getNom());
        //contentValues.put(ILLUSTRATION_END,endStr.getIllustration());
        // contentValues.put(DESCRIPTION_END,endStr.getDescription());
        //contentValues.put(ADRESSE_END,endStr.getAdresse());
        //contentValues.put(TELEPHONE,endStr.getTelephone());
        db.insert(DbColumn.Endroits_Column.TABLE_NAME, null, contentValues);
        db.close();
    }



    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DbColumn.Endroits_Column.TABLE_NAME, "_ID = ?",new String[] {id});
    }

    public boolean checkUser(String id) {

        // array of columns to fetch
        String[] columns = {
                _ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection =  _ID + " = ?";

        // selection argument
        String[] selectionArgs = {id};

        Cursor cursor = db.query(DbColumn.Endroits_Column.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;

    }


    public String[] getAllEndroitsId() {
        // array of columns to fetch
        String[] columns = {
                _ID

        };
        // sorting orders

        String sortOrder =
                _ID + " ASC";
        // List<GetAllID> allId = new ArrayList<GetAllID>();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(DbColumn.Endroits_Column.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        String [] allId=new String[cursor.getCount()];
        // Traversing through all rows and adding to list
        int i=0;
        if (cursor.moveToFirst()) {

            do {

                //GetAllID idEnds = new GetAllID();
                allId[i]=cursor.getString(cursor.getColumnIndex(_ID));
                i++;
                //allId.add(idEnds);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return allId;
    }

}
