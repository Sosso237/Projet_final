package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_property1 extends AppCompatActivity {

    TextView Lid,error;
    Button deco,id,bback;
    EditText editTextNumber;
    SQLiteOpenHelper helper;
    SQLiteDatabase database;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property1);
        Lid = findViewById(R.id.Lid);
        deco = findViewById(R.id.deco);
        id = findViewById(R.id.id);
        editTextNumber = findViewById(R.id.editTextNumber);
        error = findViewById(R.id.error);
        error.setVisibility(View.INVISIBLE);
        bback = findViewById(R.id.bback);

        // Getting value from the previous Intent
        final Intent x = getIntent();
        final String name = x.getStringExtra("Name");

        // Database Connection
        helper = new SQLiteOpenHelper(Edit_property1.this,"Rooms.db",null,1) {
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

        database = helper.getReadableDatabase();

        // Back to the menu
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Edit_property1.this,Main_menu.class);
                x.putExtra("Name",name);
                startActivity(x);
            }
        });

        // Set action to the send button

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the field is empty, display a message
                if (editTextNumber.getText().toString().isEmpty()){

                    Toast.makeText(Edit_property1.this, "Warning!! This field can't be blank", Toast.LENGTH_LONG).show();
                    error.setText("Warning!! Set the id");
                    error.setVisibility(View.VISIBLE);

                } else {
                    Cursor c = database.rawQuery("SELECT DISTINCT * FROM Appart WHERE _id ='"+editTextNumber.getText().toString() +"';",null);
                    // If there is a line, passing values to the other Intent
                    if (c.moveToNext()){

                        Intent intent = new Intent(Edit_property1.this,Edit_property2.class);
                        intent.putExtra("ID",c.getString(0));
                        intent.putExtra("username",name);
                        intent.putExtra("type",c.getString(1));
                        intent.putExtra("price",c.getString(2));
                        intent.putExtra("surface",c.getString(3));
                        intent.putExtra("rooms",c.getString(4));
                        intent.putExtra("description",c.getString(5));
                        intent.putExtra("address",c.getString(6));

                        startActivity(intent);

                    } else {
                        // Bad input
                        Toast.makeText(Edit_property1.this, "Wrong input or id not present in database", Toast.LENGTH_LONG).show();
                    }
                }




            }
        });


    }
}