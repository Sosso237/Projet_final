package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Delete_property1 extends AppCompatActivity {

    Button deconn_delete, back, delete;
    TextView id_delete;
    EditText edit_id;


    SQLiteDatabase database,delete_data;
    SQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_property1);
        deconn_delete = findViewById(R.id.deconn_delete);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        id_delete = findViewById(R.id.id_delete);
        edit_id = findViewById(R.id.edit_id);

        // Get Value from the previous Intent
        final Intent p = getIntent();
        final String name = p.getStringExtra("Name");

        // Database Connection

        helper = new SQLiteOpenHelper(Delete_property1.this,"Rooms.db",null,1) {
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
        // Ability to read data from the database
        database = helper.getReadableDatabase();
        // Ability to modify data in the database
        delete_data = helper.getWritableDatabase();

        // Move to the default page

        deconn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Delete_property1.this, MainActivity.class);
                x.putExtra("Name",name);
                startActivity(x);
            }
        });

        // Back to the previous screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Delete_property1.this, Main_menu.class);
                x.putExtra("Name", p.getStringExtra("Name"));
                startActivity(x);
            }
        });

        // delete action
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the field is empty

                if (edit_id.getText().toString().isEmpty()) {
                    Toast.makeText(Delete_property1.this, "This fiels can't be blank", Toast.LENGTH_LONG).show();

                } else {
                    // Result of the query
                    Cursor c = database.rawQuery("SELECT DISTINCT * FROM Appart WHERE _id ='" + edit_id.getText().toString() + "';", null);
                    // If there is a line
                    if (c.moveToNext()) {

                        final String id = c.getString(0);
                        final String publisher = c.getString(10);
                        if (id.equals(edit_id.getText().toString())){

                            //Check if the user can delete the advert
                            if (name.equals(publisher)) {

                                final String[] s = {"Successfull Operation"};
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Delete_property1.this);
                                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this advert");
                                alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            try {
                                                // If yes is selected, the query is executed, a notification is pop up and wait a few milliseconds
                                                delete_data.execSQL("DELETE FROM Appart WHERE _id ='" + id + "' AND Agent = '" + name + "';");
                                                Toast.makeText(Delete_property1.this, s[0], Toast.LENGTH_LONG).show();

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = new Intent(Delete_property1.this,Main_menu.class);

                                                        intent.putExtra("Name", name);
                                                        startActivity(intent);

                                                    }
                                                }, 1000);



                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                                //If the answer is no
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(Delete_property1.this, "You'll be taken to the back screen", Toast.LENGTH_LONG).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(Delete_property1.this,Main_menu.class);

                                            intent.putExtra("Name", name);
                                            startActivity(intent);

                                        }
                                    }, 2000);

                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            // The id exist but the user is not allowed to delete the advert, he's not not the creator of the advert
                            Toast.makeText(Delete_property1.this, "You're not allowed to do this action", Toast.LENGTH_LONG).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Delete_property1.this,Main_menu.class);

                                    intent.putExtra("Name", name);
                                    startActivity(intent);

                                }
                            }, 2000);

                        }

                    }
                        }else {
                        //Id not present in database
                        Toast.makeText(Delete_property1.this, "Invalid id or not present in the database", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}