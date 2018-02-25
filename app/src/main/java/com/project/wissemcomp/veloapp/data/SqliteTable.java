package com.project.wissemcomp.veloapp.data;

/**
 * Created by Wissem on 24/02/2018.
 */

public class SqliteTable {

    /**
     * This class provide tables
     **/

    public static final String TABLE_BIKE = "table_bike";
    public static final String COL_BIKE_ID = " col_bike_id";
    public static final String COL_BIKE_NAME = " col_bike_name";
    public static final String COL_BIKE_DATE = " col_bike_date";
    public static final String COL_BIKE_PRICE = " col_bike_price";
    public static final String COL_BIKE_DESCRIPTION = " col_bike_description";
    public static final String COL_BIKE_IMAGE = " col_bike_image";

    public static final String DB_BIKE = "CREATE TABLE " + TABLE_BIKE + "(" + COL_BIKE_ID + " INTEGER PRIMARY KEY, "
            + COL_BIKE_NAME + " TEXT, " + COL_BIKE_DATE + " TEXT, " + COL_BIKE_PRICE + " TEXT, " + COL_BIKE_DESCRIPTION + " TEXT, " + COL_BIKE_IMAGE + " TEXT " + ")";

}