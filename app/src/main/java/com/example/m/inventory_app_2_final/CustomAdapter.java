package com.example.m.inventory_app_2_final;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.m.inventory_app_2_final.providerclass.PRICE;
import static com.example.m.inventory_app_2_final.providerclass.PRODUCTNAME;
import static com.example.m.inventory_app_2_final.providerclass.QUANTITY;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_NAME;
import static com.example.m.inventory_app_2_final.providerclass.SUPPLIER_PHONE_NUMBER;
import static com.example.m.inventory_app_2_final.providerclass._ID;

public class CustomAdapter extends CursorAdapter {

    TextView pname,ptitle,pPrice,pQuantity,pPhone;
    Button saleProduct;
    Cursor cursor;
    int sale_value;
    public CustomAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.cursor=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.my_custom_layout,parent,false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        pname=view.findViewById(R.id.ProductName);
        ptitle=view.findViewById(R.id.Supplier);
        pPhone=view.findViewById(R.id.supp_phone);
        pPrice=view.findViewById(R.id.price);
        pQuantity=view.findViewById(R.id.quan);

        final int id=cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        String product=cursor.getString(cursor.getColumnIndexOrThrow(PRODUCTNAME));
        String supplier=cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_NAME));
        String supplier_phone=cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PHONE_NUMBER));
        String price=cursor.getString(cursor.getColumnIndexOrThrow(PRICE));
        final String quantity=cursor.getString(cursor.getColumnIndexOrThrow(QUANTITY));

        pname.setText(product);
        ptitle.setText(supplier);
        pPhone.setText(supplier_phone);
        pPrice.setText(price);
        pQuantity.setText(quantity);


        saleProduct=view.findViewById(R.id.sale_quan);
        saleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sale_value= Integer.parseInt(quantity)-1;
                //Toast.makeText(context,"Current Quantity: "+sale_value,Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(context,UpdateData.class);
                Bundle bundle=new Bundle();
                bundle.putLong("current_id",id);
                bundle.putInt("activity_identifier",101);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                /*UpdateData obj=new UpdateData();
                obj.DecreaseSales();*/
            }
        });
    }


}
