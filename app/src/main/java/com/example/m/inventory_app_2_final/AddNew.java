package com.example.m.inventory_app_2_final;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.m.inventory_app_2_final.providerclass.CONTENT_URI;
import static com.example.m.inventory_app_2_final.providerclass.PRICE;
import static com.example.m.inventory_app_2_final.providerclass.PRODUCTNAME;
import static com.example.m.inventory_app_2_final.providerclass.QUANTITY;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_NAME;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_PHONE_NUMBER;
import static com.example.m.inventory_app_2_final.providerclass.TABLE_NAME;

public class AddNew extends AppCompatActivity {

    public EditText product, price, quantity, supplier, supplier_phone;
    public String val_product, val_quantity, val_supplier, val_supplier_phone, val_price;
    public Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        product = findViewById(R.id.eProduct);
        price = findViewById(R.id.ePrice);
        quantity = findViewById(R.id.eQuantity);
        supplier = findViewById(R.id.eSupplierName);
        supplier_phone = findViewById(R.id.eSupplierPhone);
        save=findViewById(R.id.save_data);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDATA();
            }
        });
    }

    private void insertDATA() {
        ContentValues values = new ContentValues();

        val_product = product.getText().toString().trim();
        val_price = price.getText().toString().trim();
        val_quantity = quantity.getText().toString().trim();
        val_supplier = supplier.getText().toString().trim();
        val_supplier_phone = supplier_phone.getText().toString().trim();

        if (val_product.isEmpty()==true || val_price.isEmpty()==true || val_quantity.isEmpty()==true || val_supplier.isEmpty()==true || val_supplier_phone.isEmpty()==true){
            Toast.makeText(getApplicationContext(),"Cannot Enter Blank Fields",Toast.LENGTH_SHORT).show();
        }

        else{
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
            finish();
        }

    }
}
