package com.project.wissemcomp.veloapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.wissemcomp.veloapp.mvp.model.Bike;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wissem on 24/02/2018.
 */
public class SqliteHelper extends SQLiteOpenHelper {


    private static SqliteHelper mInstance = null;

    private static final String DATABASE_NAME = "veloapp_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = SqliteHelper.class.getSimpleName();

    public static SqliteHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new SqliteHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public SqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqliteTable.DB_BIKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SqliteTable.DB_BIKE);
        onCreate(db);
    }

    public boolean insertData(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, values);
        if (result == -1){
            Log.d(TAG, "failed to save data!");
            return false;
        }else{
            Log.d(TAG, "save data successful");
            return true;
        }
    }

    /**
     * This method edit the bike details and it return int value.
     * @param table this param provide table that you want to update.
     * @param values values param provide col value.
     * @param bike_id to identify the bike you use want to edit.
     * @return int value.
     **/

    public int updateData(String table, ContentValues values,
                          int bike_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(table, values,
                SqliteTable.COL_BIKE_ID + " =? ",
                new String[]{String.valueOf(bike_id)});
    }


    public Bike getBikeDetail(int bike_id){
        Bike bike;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns={
                SqliteTable.COL_BIKE_ID,
                SqliteTable.COL_BIKE_NAME,
                SqliteTable.COL_BIKE_DATE,
                SqliteTable.COL_BIKE_PRICE,
                SqliteTable.COL_BIKE_DESCRIPTION,
                SqliteTable.COL_BIKE_IMAGE
        };

        String selection = SqliteTable.COL_BIKE_ID + " =? " ;
        String[] args={String.valueOf(bike_id)};

        Cursor cursor = db.query(SqliteTable.TABLE_BIKE, columns,
                selection, args, null, null, null);

        if (cursor != null && cursor.moveToFirst()){

            bike = new Bike();
            bike.setId(cursor.getInt(0));
            bike.setName(cursor.getString(1));
            bike.setDate(cursor.getString(2));
            bike.setPrice(cursor.getString(3));
            bike.setDescription(cursor.getString(4));
            bike.setImage(cursor.getString(5));

            return bike;
        }

        return null;
    }

    /**
     * This method delete bike details permanently, and it returns boolean value.
     * @param bike_id param bike_id provide id of the bike.
     * @return true/false.
     **/
    public boolean deleteBike(int bike_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(SqliteTable.TABLE_BIKE, SqliteTable.COL_BIKE_ID + " =? ",
                new String[]{String.valueOf(bike_id)}) > 0;
    }

    // Getting All Velos
    public List<Bike> getAllBikes() {
        List<Bike> bikeList = new ArrayList<Bike>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+ SqliteTable.TABLE_BIKE +" v "; //where NOT EXISTS (SELECT  * FROM vente ve where v.id = ve.id_velo) ORDER BY date_achat DESC

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bike bike = new Bike();
                bike.setId(cursor.getInt(0));
                bike.setName(cursor.getString(1));
                bike.setDate(cursor.getString(2));
                bike.setPrice(cursor.getString(3));
                bike.setDescription(cursor.getString(4));
                bike.setImage(cursor.getString(5));
                // Adding contact to list
                bikeList.add(bike);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return bikeList;

    }
}