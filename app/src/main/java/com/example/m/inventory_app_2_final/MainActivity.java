package com.example.m.inventory_app_2_final;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.m.inventory_app_2_final.providerclass.CONTENT_URI;
import static com.example.m.inventory_app_2_final.providerclass.PRICE;
import static com.example.m.inventory_app_2_final.providerclass.PRODUCTNAME;
import static com.example.m.inventory_app_2_final.providerclass.QUANTITY;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_NAME;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_PHONE_NUMBER;
import static com.example.m.inventory_app_2_final.providerclass.TABLE_NAME;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public ListView listView;
    Cursor c;
    Uri CurrentProductUri;

    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddNew.class);
                startActivity(intent);
            }
        });


        listView=findViewById(R.id.lis);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CurrentProductUri= ContentUris.withAppendedId(CONTENT_URI,id);

               Intent intent=new Intent(getApplicationContext(),UpdateData.class);
                Bundle bundle=new Bundle();
                bundle.putLong("current_id",id);
                bundle.putInt("activity_identifier",100);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0,null,this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.insert_Dummy_data) {
            insertDATA();
            return true;
        }

        if (id == R.id.delete) {
            deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {

        //Alert

        AlertDialog.Builder altdialg=new AlertDialog.Builder(MainActivity.this);
        altdialg.setMessage("Are you sure you want to delete?");
        altdialg.setCancelable(true);
        altdialg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int deleted_result = getContentResolver().delete(CONTENT_URI,null,null);
                if (deleted_result == -1)
                    Toast.makeText(getApplicationContext(), "Failure To Delete", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Deletion Success", Toast.LENGTH_SHORT).show();

            }
        });

        altdialg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        altdialg.show();

        //Alert Dialog

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader=
                new CursorLoader(getApplicationContext(),CONTENT_URI,null,null,null,"product_name");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        adapter=new CustomAdapter(getApplicationContext(),cursor);
        listView.setAdapter(adapter);
        onClickRetrieveStudents();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void onClickRetrieveStudents() {
        // Retrieve student records
        c = managedQuery(CONTENT_URI, null, null, null, "product_name");
        if (c.moveToFirst()) {
            do{

            } while (c.moveToNext());
        }
    }

    private void insertDATA() {

        String val_product = "Test Product";
        String val_price = "1200";
        String val_quantity = "5";
        String val_supplier = "Flipkart";
        String val_supplier_phone = "123456789";

        ContentValues values = new ContentValues();
        values.put(PRODUCTNAME, val_product);
        values.put(PRICE, val_price);
        values.put(QUANTITY, val_quantity);
        values.put(SUPPLIER_NAME, val_supplier);
        values.put(SUPPLIER_PHONE_NUMBER, val_supplier_phone);
        Uri result = getContentResolver().insert(Uri.withAppendedPath(CONTENT_URI,TABLE_NAME), values);

        if (result!=null)
            Toast.makeText(getApplicationContext(),"SuccessFully Inserted",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"Failure To Insert",Toast.LENGTH_SHORT).show();

        
    }

}
