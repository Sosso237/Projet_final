package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add_property extends AppCompatActivity {

    TextView Txtproperty,txtproperty_type,txtproperty_price,txtproperty_surface,txtproperty_rooms,txtproperty_description,txtproperty_address;
    Spinner spinnerProperty_type,spinnerProperty_rooms;
    Button back_from_add_property,valider_add_property,deconnected2;
    EditText editProperty_price,editProperty_surface,editPropertyDescription,editTextTextPostalAddress;

    String[] type={"House","Flat","Office"};
    String[] rooms = {"1","2","3","4","5","6","7","8","9"};


    SQLiteOpenHelper helper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        Txtproperty = findViewById(R.id.Txtproperty);
        txtproperty_type = findViewById(R.id.txtproperty_type);
        txtproperty_price = findViewById(R.id.txtproperty_price);
        txtproperty_surface = findViewById(R.id.txtproperty_surface);
        txtproperty_rooms = findViewById(R.id.txtproperty_rooms);
        txtproperty_description = findViewById(R.id.txtproperty_description);
        txtproperty_address = findViewById(R.id.txtproperty_address);
        spinnerProperty_type = findViewById(R.id.spinnerProperty_type);
        spinnerProperty_rooms = findViewById(R.id.spinnerProperty_rooms);
        back_from_add_property = findViewById(R.id.back_from_add_property);
        valider_add_property = findViewById(R.id.valider_add_property);
        deconnected2 = findViewById(R.id.deconnected2);
        editProperty_price = findViewById(R.id.editProperty_price);
        editProperty_surface = findViewById(R.id.editProperty_surface);
        editPropertyDescription = findViewById(R.id.editPropertyDescription);
        editTextTextPostalAddress = findViewById(R.id.editTextTextPostalAddress);

        //Get Value from the previous Intent
        final Intent x = getIntent();
        final String username = x.getStringExtra("username");


        // Database Connection

        helper = new SQLiteOpenHelper(Add_property.this,"Rooms.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Appart (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,Type TEXT NOT NULL, Price TEXT NOT NULL, Surface TEXT NOT NULL ,Rooms TEXT NOT NULL,Description TEXT NOT NULL,Address TEXT NOT NULL,Status TEXT NOT NULL,Creation_Date TEXT NOT NULL, Update_date TEXT NOT NULL, Agent TEXT NOT NULL);");

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Appart ;" );
                onCreate(sqLiteDatabase);
            }
        };

        //Populate type
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinnerProperty_type.setAdapter(aa);

        //Populate rooms

        ArrayAdapter polulate_rooms = new ArrayAdapter(Add_property.this,android.R.layout.simple_spinner_item,rooms);
        polulate_rooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinnerProperty_rooms.setAdapter(polulate_rooms);


        // Back to menu

        back_from_add_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Add_property.this,Main_menu.class);
                i.putExtra("Name",username);
                startActivity(i);
            }
        });

        // Action on Next Page button

        valider_add_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if all values are not empty
                if (spinnerProperty_type.getSelectedItem().toString().isEmpty() || editProperty_price.getText().toString().isEmpty() ||  editProperty_surface.getText().toString().isEmpty() || spinnerProperty_rooms.getSelectedItem().toString().isEmpty() || editPropertyDescription.getText().toString().isEmpty() || editTextTextPostalAddress.getText().toString().isEmpty() ){
                    Toast.makeText(Add_property.this, "Warning all fiels are required", Toast.LENGTH_LONG).show();
                } else {
                // Passing values to Add_property2 interface

                Intent i = new Intent(Add_property.this,Add_property2.class);
                i.putExtra("type",spinnerProperty_type.getSelectedItem().toString());
                i.putExtra("price",editProperty_price.getText().toString());
                i.putExtra("surface",editProperty_surface.getText().toString());
                i.putExtra("rooms",spinnerProperty_rooms.getSelectedItem().toString());
                i.putExtra("description",editPropertyDescription.getText().toString());
                i.putExtra("postal",editTextTextPostalAddress.getText().toString());
                i.putExtra("username",username);
                startActivity(i);
                }

            }
        });

        // Move to the default page

        deconnected2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Add_property.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}