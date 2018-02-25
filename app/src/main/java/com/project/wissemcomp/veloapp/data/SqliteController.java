package com.project.wissemcomp.veloapp.data;

import android.content.ContentValues;
import android.content.Context;

import com.project.wissemcomp.veloapp.mvp.model.Bike;

import java.util.List;

/**
 * Created by Wissem on 24/02/2018.
 */

public class SqliteController {

    private SqliteHelper sqliteHelper;
    Context context;

    public SqliteController(Context mContext) {
        this.context = mContext;
        sqliteHelper = SqliteHelper.getInstance(context);
    }

    public boolean saveUserData(Bike bike) {
        ContentValues values = new ContentValues();
        values.put(SqliteTable.COL_BIKE_NAME, bike.getName());
        values.put(SqliteTable.COL_BIKE_PRICE, bike.getPrice());
        values.put(SqliteTable.COL_BIKE_DATE, bike.getDate());
        values.put(SqliteTable.COL_BIKE_DESCRIPTION, bike.getDescription());
        values.put(SqliteTable.COL_BIKE_IMAGE, bike.getImage());
        return sqliteHelper.insertData(SqliteTable.TABLE_BIKE, values);
    }

    public int updateUserData(Bike bike, int bike_id) {
        ContentValues values = new ContentValues();
        values.put(SqliteTable.COL_BIKE_NAME, bike.getName());
        values.put(SqliteTable.COL_BIKE_PRICE, bike.getPrice());
        values.put(SqliteTable.COL_BIKE_DATE, bike.getDate());
        values.put(SqliteTable.COL_BIKE_DESCRIPTION, bike.getDescription());
        values.put(SqliteTable.COL_BIKE_IMAGE, bike.getImage());
        return sqliteHelper.updateData(SqliteTable.TABLE_BIKE, values, bike_id);
    }


    public Bike getBikeDetails(int bike_id) {
        return sqliteHelper.getBikeDetail(bike_id);
    }

    public List<Bike> getAllBikes() {
        return sqliteHelper.getAllBikes();
    }

    public boolean deleteAccount(int bike_id) {
        return sqliteHelper.deleteBike(bike_id);
    }
}
