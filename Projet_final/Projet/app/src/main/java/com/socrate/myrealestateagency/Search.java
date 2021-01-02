package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    Button deconnec_search,deco;
    SearchView search;
    ListView Lview;

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        deconnec_search = findViewById(R.id.deconnec_search);
        search = findViewById(R.id.search);
        Lview = findViewById(R.id.Lview);
        deco = findViewById(R.id.deco);

        // Getting value from previous Intent
        final Intent x = getIntent();
        final String name = x.getStringExtra("Name");

        // Database Connection
        helper = new SQLiteOpenHelper(Search.this,"Rooms.db",null,1) {
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

        Cursor c = database.rawQuery("SELECT DISTINCT * FROM Appart;",null);
        final ArrayList<Advert> arrayList = new ArrayList<>();
        // while there is a line, create an Advert object
        while (c.moveToNext()){
            Advert a = new Advert(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4) ,c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10));
            arrayList.add(a);

        }
        // Put the advert objects into arrayadapter and pass it to listview
        ArrayAdapter arrayAdapter = new ArrayAdapter(Search.this,android.R.layout.simple_list_item_1,arrayList);
        Lview.setAdapter(arrayAdapter);

        // Back to the previous page
        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Search.this,Main_menu.class);
                x.putExtra("Name",name);
                startActivity(x);
            }
        });


        // Move to the default page

        deconnec_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Search.this,MainActivity.class);
                startActivity(x);
            }
        });


        // set action on the searchview
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {


                Cursor d = database.rawQuery("SELECT DISTINCT * FROM Appart WHERE Description = '"+search.getQuery()+"';" ,null);
                final ArrayList<Advert> look_for = new ArrayList<>();

                while (d.moveToNext()){
                    Advert f = new Advert(d.getInt(0),d.getString(1),d.getString(2),d.getString(3),d.getString(4) ,d.getString(5),d.getString(6),d.getString(7),d.getString(8),d.getString(9),d.getString(10));
                    look_for.add(f);

                }
                //Set the result the filter query to listview
                ArrayAdapter filterAdapter = new ArrayAdapter(Search.this,android.R.layout.simple_list_item_1,look_for);
                Lview.setAdapter(filterAdapter);

                return false;
            }
        });






    }
}