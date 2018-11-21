package com.example.m.inventory_app_2_final;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.HashMap;

public class providerclass extends ContentProvider {
    static final String CONTENT_AUTHORITY = "com.example.m.inventory_app_2_final.providerclass";  //authority
    static final String URL = "content://" + CONTENT_AUTHORITY + "/details";
    static final Uri CONTENT_URI = Uri.parse(URL);
    
    public static final String TABLE_NAME="details";
    public static final String _ID= BaseColumns._ID;
    public static final String PRODUCTNAME="product_name";
    public static final String PRICE="Price";
    public static final String QUANTITY="Quantity";
    public static final String SUPPLIER_NAME="Supplier_Name";
    public static final String SUPPLIER_PHONE_NUMBER="supplier_Phone";

    SQLiteDatabase db;

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int INVENTORY = 1;
    static final int INVENTORY_ID = 2;

    static final UriMatcher uriMatcher;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, "details", INVENTORY);
        uriMatcher.addURI(CONTENT_AUTHORITY, "details/#", INVENTORY_ID);

    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataBaseHelper dbHelper = new DataBaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(	TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case INVENTORY:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case INVENTORY_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on product names
             */
            sortOrder = PRODUCTNAME;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case INVENTORY:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;

            case INVENTORY_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case INVENTORY:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;

            case INVENTORY_ID:
                count = db.update(TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? "AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all  records
             */
            case INVENTORY:
                return "vnd.android.cursor.dir/vnd.example.details";
            /**
             * Get a particular detail
             */
            case INVENTORY_ID:
                return "vnd.android.cursor.item/vnd.example.details";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}