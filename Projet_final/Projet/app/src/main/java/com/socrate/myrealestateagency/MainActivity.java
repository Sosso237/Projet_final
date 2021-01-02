package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    TextView welcome_message,message,sign_up;
    ImageView image;
    Button join;
    SQLiteDatabase database;
    SQLiteOpenHelper helper;
    ListView list_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome_message = findViewById(R.id.welcome_message);
        message = findViewById(R.id.message);
        sign_up = findViewById(R.id.sign_up);
        image = findViewById(R.id.image);
        join = findViewById(R.id.join);
        list_users = findViewById(R.id.list_users);

        // Database Connection

        helper = new SQLiteOpenHelper(MainActivity.this,"Rooms.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Users (Name TEXT NOT NULL PRIMARY KEY, Pseudo TEXT, Email TEXT NOT NULL ,Password TEXT NOT NULL);");
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Appart (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,Type TEXT NOT NULL, Price TEXT NOT NULL, Surface TEXT NOT NULL ,Rooms TEXT NOT NULL,Description TEXT NOT NULL,Address TEXT NOT NULL,Status TEXT NOT NULL,Creation_Date TEXT NOT NULL, Update_date TEXT NOT NULL, Agent TEXT NOT NULL);");

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users ;" );
                onCreate(sqLiteDatabase);
            }
        };

        database = helper.getReadableDatabase();

        //Getting the result of the query

        Cursor c = database.rawQuery("SELECT DISTINCT Name FROM Users ;",null);
        final ArrayList<String> arrayList = new ArrayList<>();

        // While there is a line, i'll add it to my listview
        while (c.moveToNext()){
            arrayList.add(c.getString(0));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        list_users.setAdapter(arrayAdapter);

        //Set Action to Listview to get the selected user

        list_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.putExtra("Name", arrayList.get(i));
                startActivity(intent);


            }
        });

        // Subscribe

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Sign_up.class);
                startActivity(i);
            }
        });



    }


}


