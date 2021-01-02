package com.socrate.myrealestateagency;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;



public class Add_property2 extends AppCompatActivity {

    TextView property_status,creation;
    Button button3,previous,save_quit;
    CalendarView date_;
    Spinner spinner;

    String[] status={"not sold","sold"};
    String get_date,update_date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property2);
        property_status = findViewById(R.id.property_status);
        creation = findViewById(R.id.creation);
        button3 = findViewById(R.id.button3);
        previous = findViewById(R.id.previous);
        save_quit = findViewById(R.id.save_quit);
        date_ = findViewById(R.id.date_);
        spinner = findViewById(R.id.spinner);


        SQLiteOpenHelper helper;
        final SQLiteDatabase database;


        final Intent x = getIntent();
        final String name = x.getStringExtra("username");
        final String property_type = x.getStringExtra("type");
        final String property_price = x.getStringExtra("price");
        final String property_surface = x.getStringExtra("surface");
        final String property_rooms = x.getStringExtra("rooms");
        final String property_description = x.getStringExtra("description");
        final String property_postal = x.getStringExtra("postal");

        // Database Connection

        helper = new SQLiteOpenHelper(Add_property2.this,"Rooms.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Appart (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,Type TEXT NOT NULL, Price TEXT NOT NULL, Surface TEXT NOT NULL ,Rooms TEXT NOT NULL,Description TEXT NOT NULL,Address TEXT NOT NULL,Status TEXT NOT NULL,Creation_Date TEXT NOT NULL, Update_date TEXT NOT NULL, Agent TEXT NOT NULL);");


            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

       date_.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
               get_date = dayOfMonth+"-"+(month+1)+"-"+year;

                }
           });

        // Populate status Spinner

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinner.setAdapter(aa);

        //Move to default page
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_property2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Back to the previous screen
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_property2.this,Add_property.class);
                startActivity(intent);
            }
        });

        // Save and insert data in database
        save_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if all fields are not empty

                if (spinner.getSelectedItem().toString().isEmpty() || get_date.isEmpty()){
                    //If any of those fields is empty
                    Toast.makeText(Add_property2.this, "Warning all fiels are required", Toast.LENGTH_LONG).show();
                } else {
                    // If not, insert data into the database, display a notification and move to the menu page
                    database.execSQL("INSERT INTO Appart (Type,Price,Surface,Rooms,Description,Address,Status,Creation_Date,Update_date,Agent) VALUES ('" + property_type + "','" + property_price  + "','" + property_surface + "','" + property_rooms +"','"+ property_description +"','" + property_postal+"','" +spinner.getSelectedItem().toString() +"','" + get_date +"','"+ update_date+"','" +  name  + "');");
                    Toast.makeText(Add_property2.this, "Successfully added", Toast.LENGTH_LONG).show();
                    Intent x = new Intent(Add_property2.this,Main_menu.class);
                    x.putExtra("Name",name);
                    startActivity(x);

                }


            }
        });

    }
}