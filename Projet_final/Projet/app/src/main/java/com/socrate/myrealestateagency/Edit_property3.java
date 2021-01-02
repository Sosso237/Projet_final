package com.socrate.myrealestateagency;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class Edit_property3 extends AppCompatActivity {

    Button deco_edit,previous_edit,save_edit;
    Spinner spinner2;
    CalendarView calendarView;
    TextView property_status_edit,edit_date;

    String[] status={"not sold","sold"};
    String get_date,update_date;

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property3);
        deco_edit = findViewById(R.id.deco_edit);
        previous_edit= findViewById(R.id.previous_edit);
        save_edit = findViewById(R.id.save_edit);
        spinner2 = findViewById(R.id.spinner2);
        calendarView = findViewById(R.id.calendarView);
        property_status_edit = findViewById(R.id.property_status_edit);
        edit_date = findViewById(R.id.edit_date);

        final Intent x = getIntent();
        final String id = x.getStringExtra("ID");
        final String property_type = x.getStringExtra("type");
        final String property_price = x.getStringExtra("price");
        final String property_surface = x.getStringExtra("surface");
        final String property_rooms = x.getStringExtra("rooms");
        final String property_description = x.getStringExtra("description");
        final String property_postal = x.getStringExtra("postal");
        final String agent = x.getStringExtra("Agent");


        helper = new SQLiteOpenHelper(Edit_property3.this,"Rooms.db",null,1) {
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

        database = helper.getWritableDatabase();

        // Get the current Date for the last date of the update
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            System.out.println(formatter.format(date));
            update_date = formatter.format(date);
        }

        // Set action on selected date on the calendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                //Log.v("#####", "Date Change: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                get_date = dayOfMonth+"-"+(month+1)+"-"+year;

            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinner2.setAdapter(aa);


        // Move to the default page

        deco_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_property3.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Back to the previous screen
        previous_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_property3.this,Edit_property2.class);
                startActivity(intent);
            }
        });

        // Set action on edit button, update the data if all fields are set
        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (spinner2.getSelectedItem().toString().isEmpty() || get_date.isEmpty()){
                    Toast.makeText(Edit_property3.this, "Warning all fiels are required", Toast.LENGTH_LONG).show();
                } else {

                    database.execSQL("UPDATE Appart SET Type ='" + property_type +"'," + "Price ='" +property_price+ "', Surface ='" + property_surface + "'," + "Rooms ='" + property_rooms +   "'," + "Description ='" +property_description+ "', Address ='" + property_postal + "',"   + "Status ='" + spinner2.getSelectedItem().toString() +   "'," + "Creation_Date ='" +get_date+ "', Update_date ='" + update_date + "',"+ "Agent ='" + agent + "' "  + "WHERE _id ='" + id + "';" );

                    Toast.makeText(Edit_property3.this, "Successfully edit added", Toast.LENGTH_LONG).show();
                    Intent x = new Intent(Edit_property3.this,Main_menu.class);
                    x.putExtra("Name",agent);
                    startActivity(x);

                }



            }
        });


    }
}