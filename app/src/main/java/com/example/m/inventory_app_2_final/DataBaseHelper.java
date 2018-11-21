package com.example.m.inventory_app_2_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.m.inventory_app_2_final.providerclass.PRICE;
import static com.example.m.inventory_app_2_final.providerclass.PRODUCTNAME;
import static com.example.m.inventory_app_2_final.providerclass.QUANTITY;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_NAME;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_PHONE_NUMBER;
import static com.example.m.inventory_app_2_final.providerclass.TABLE_NAME;
import static com.example.m.inventory_app_2_final.providerclass._ID;

public class DataBaseHelper extends SQLiteOpenHelper {
    static String DATABASENAME="inventory.db";
    public DataBaseHelper(Context context) {
        super(context,DATABASENAME , null, 1);
    }

    static  final String create_database="CREATE TABLE "+TABLE_NAME+"("+ _ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            PRODUCTNAME+" TEXT NOT NULL, "+PRICE+" TEXT NOT NULL, "+ QUANTITY + " INTEGER NOT NULL DEFAULT 0 ,"
            +SUPPLIER_NAME +" TEXT NOT NULL, "+ SUPPLIER_PHONE_NUMBER +" TEXT );";


    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }
}
