package com.example.m.inventory_app_2_final;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.m.inventory_app_2_final.providerclass.CONTENT_URI;
import static com.example.m.inventory_app_2_final.providerclass.PRICE;
import static com.example.m.inventory_app_2_final.providerclass.PRODUCTNAME;
import static com.example.m.inventory_app_2_final.providerclass.QUANTITY;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_NAME;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_PHONE_NUMBER;

public class UpdateData extends AppCompatActivity {

    public EditText update_product, update_price, update_quantity, update_supplier, update_supplier_phone;
    public String update_val_product, update_val_quantity, update_val_supplier, update_val_supplier_phone, update_val_price;
    public Button update_data_h, Sales, update_delete_data_h,decrease_q,increase_q;
    private Uri CurrentProductUri;

    TextView detail_product,detail_price,detail_quantity,detail_supplier,detail_supplier_phone;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        update_product = findViewById(R.id.update_eProduct);
        update_price = findViewById(R.id.update_ePrice);
        update_quantity = findViewById(R.id.update_eQuantity);
        update_supplier = findViewById(R.id.update_eQuantity);
        update_supplier_phone = findViewById(R.id.update_eSupplierPhone);
        update_data_h = findViewById(R.id.h_update_data);
        decrease_q=findViewById(R.id.decrease_quantity);
        increase_q=findViewById(R.id.increase_quantity);
        Sales = findViewById(R.id.Sales);
        update_delete_data_h = findViewById(R.id.update_delete_data);

        detail_product=findViewById(R.id.holder_update_eProduct);
        detail_price=findViewById(R.id.holder_update_ePrice);
        detail_quantity=findViewById(R.id.holder_update_eQuantity);
        detail_supplier=findViewById(R.id.holder_update_eSupplierName);
        detail_supplier_phone=findViewById(R.id.holder_update_eSupplierPhone);


        values = new ContentValues();

        Bundle bundle = getIntent().getExtras();
        final long user_value = bundle.getLong("current_id");
        final int activity_identifier = bundle.getInt("activity_identifier");

        Log.d("Checker", "update: current_id: " + user_value);
        CurrentProductUri = ContentUris.withAppendedId(CONTENT_URI, user_value);

        allDetails();

        if (activity_identifier == 101) {
            DecreaseSales();
        }
        update_data_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_val_product = update_product.getText().toString().trim();
                update_val_price = update_price.getText().toString().trim();
                update_val_quantity = update_quantity.getText().toString().trim();
                update_val_supplier = update_supplier.getText().toString().trim();
                update_val_supplier_phone = update_supplier_phone.getText().toString().trim();

                if (update_val_product.isEmpty() == true && update_val_price.isEmpty() == true && update_val_quantity.isEmpty() == true && update_val_supplier.isEmpty() == true &&  update_val_supplier_phone.isEmpty() == true )
                {
                    Toast.makeText(getApplicationContext(),"Cannot Update Blank Fields",Toast.LENGTH_SHORT).show();
                }
                else
                update();

            }
        });


        decrease_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecreaseSales();
            }
        });

        increase_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncreaseSales();
            }
        });
        Sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DecreaseSales();
                Cursor g = getContentResolver().query(CurrentProductUri, null, null, null, "product_name");
                if (g != null) {
                    g.moveToNext();
                    int phone_number = Integer.parseInt(g.getString(g.getColumnIndex(SUPPLIER_PHONE_NUMBER)));

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone_number));
                    startActivity(callIntent);
                }
                else
                    Log.d("Error", "Failed to get Phone");
            }
        });


        update_delete_data_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Alert

                AlertDialog.Builder altdialg=new AlertDialog.Builder(UpdateData.this);
                altdialg.setMessage("Are you sure you want to delete?");
                altdialg.setCancelable(true);
                altdialg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int deleted_result = getContentResolver().delete(CurrentProductUri, null, null);
                        if (deleted_result == -1)
                            Toast.makeText(getApplicationContext(), "Failure To Delete", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Deletion Success", Toast.LENGTH_SHORT).show();
                            finish();
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
        });
    }

    private void update() {

        update_val_product = update_product.getText().toString().trim();
        update_val_price = update_price.getText().toString().trim();
        update_val_quantity = update_quantity.getText().toString().trim();
        update_val_supplier = update_supplier.getText().toString().trim();
        update_val_supplier_phone = update_supplier_phone.getText().toString().trim();


        if (update_val_product.isEmpty() != true) {
            values.put(PRODUCTNAME, update_val_product);
        }
        if (update_val_price.isEmpty() != true) {
            values.put(PRICE, update_val_price);

        }
        if (update_val_quantity.isEmpty() != true) {
            values.put(QUANTITY, update_val_quantity);
        }
        if (update_val_supplier.isEmpty() != true) {
            values.put(SUPPLIER_NAME, update_val_supplier);
        }
        if (update_val_supplier_phone.isEmpty() != true) {
            values.put(SUPPLIER_PHONE_NUMBER, update_val_supplier_phone);
        }

        int result = getContentResolver().update(CurrentProductUri, values, null, null);
        if (result == -1)
            Toast.makeText(getApplicationContext(), "Failure To Insert", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "SuccessFully Inserted", Toast.LENGTH_SHORT).show();

        finish();

    }

    public void DecreaseSales() {
        Cursor g = getContentResolver().query(CurrentProductUri, null, null, null, "product_name");
        if (g != null) {
            g.moveToNext();
            int sale_value = Integer.parseInt(g.getString(g.getColumnIndex(QUANTITY)));
            if (sale_value>0){
                values.put(QUANTITY, sale_value - 1);
                int result = getContentResolver().update(CurrentProductUri, values, null, null);
                if (result == -1)
                    Log.d("Error", "Failed to Sale");
                else {
                    Log.d("Sucess", "Success Sale");
                    finish();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Cannot Decrease Empty Stock",Toast.LENGTH_SHORT).show();
            }


        }
    }


    public void IncreaseSales() {
        Cursor g = getContentResolver().query(CurrentProductUri, null, null, null, "product_name");
        if (g != null) {
            g.moveToNext();
            int sale_value = Integer.parseInt(g.getString(g.getColumnIndex(QUANTITY)));
             values.put(QUANTITY, sale_value + 1);
            int result = getContentResolver().update(CurrentProductUri, values, null, null);
            if (result == -1)
                Log.d("Error", "Failed to Sale");
            else {
                Log.d("Sucess", "Success Sale");
                finish();
            }

        }
    }

    public void allDetails(){
        Cursor g = getContentResolver().query(CurrentProductUri, null, null, null, "product_name");
        if (g != null) {
            g.moveToNext();
            detail_quantity.setText(String.valueOf(Integer.parseInt(g.getString(g.getColumnIndex(QUANTITY)))));
            detail_supplier_phone.setText(g.getString(g.getColumnIndex(SUPPLIER_PHONE_NUMBER)));
            detail_supplier.setText(g.getString(g.getColumnIndex(SUPPLIER_NAME)));
            detail_price.setText(g.getString(g.getColumnIndex(PRICE)));
            detail_product.setText(g.getString(g.getColumnIndex(PRODUCTNAME)));
           /* Toast.makeText(getApplicationContext(),
                    "All Details: " + g.getString(g.getColumnIndex(PRODUCTNAME)), Toast.LENGTH_SHORT).show();*/
        }
    }

}
