package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class Search extends AppCompatActivity {

    Button deconnec_search,back_from_search, searchButton;
    Spinner spinnerSearchType,spinnerSearchStatus;
    TextView searchPriceMinNumber, searchPriceMaxNumber, searchSurfaceMinNumber, searchSurfaceMaxNumber, searchRoomsMinNumber, searchRoomsMaxNumber;

    String[] status={"not sold","sold","either"};
    String[] type={"House","Flat","Office","Any"};


    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        deconnec_search = findViewById(R.id.deconnec_search);
        back_from_search = findViewById(R.id.back_from_search);
        spinnerSearchType = findViewById(R.id.spinnerSearchType);
        spinnerSearchStatus = findViewById(R.id.spinnerSearchStatus);

        searchButton = findViewById(R.id.searchButton);

        searchPriceMinNumber = findViewById(R.id.searchPriceMinNumber);
        searchPriceMaxNumber = findViewById(R.id.searchPriceMaxNumber);
        searchSurfaceMinNumber = findViewById(R.id.searchSurfaceMinNumber);
        searchSurfaceMaxNumber = findViewById(R.id.searchSurfaceMaxNumber);
        searchRoomsMinNumber = findViewById(R.id.searchRoomsMinNumber);
        searchRoomsMaxNumber = findViewById(R.id.searchRoomsMaxNumber);

        // Getting value from previous Intent
        final Intent x = getIntent();
        final String name = x.getStringExtra("Name");

        //Populate type
        ArrayAdapter populate_type = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        populate_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinnerSearchType.setAdapter(populate_type);

        //Populate rooms
        ArrayAdapter polulate_status = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        polulate_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinnerSearchStatus.setAdapter(polulate_status);

        // Move to the default page

        deconnec_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Search.this,MainActivity.class);
                startActivity(x);
            }
        });


        // Back to menu

        back_from_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Search.this,Main_menu.class);
                i.putExtra("Name",name);
                startActivity(i);
            }
        });

        // Go to search results

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Search.this, "Warning all fields are required", Toast.LENGTH_LONG).show();

                if (searchPriceMaxNumber.getText().toString().isEmpty() ||  searchPriceMinNumber.getText().toString().isEmpty() || searchRoomsMaxNumber.getText().toString().isEmpty() || searchRoomsMinNumber.getText().toString().isEmpty() || searchSurfaceMaxNumber.getText().toString().isEmpty() || searchSurfaceMinNumber.getText().toString().isEmpty()){
                    Toast.makeText(Search.this, "Warning all fields are required", Toast.LENGTH_LONG).show();
                } else {
                    Intent a = new Intent(Search.this,Search_Results.class);
                    a.putExtra("Name",name);

                    a.putExtra("populate_type", spinnerSearchType.getSelectedItem().toString());
                    a.putExtra("polulate_status", (CharSequence) spinnerSearchStatus.getSelectedItem().toString());

                    a.putExtra("searchPriceMaxNumber", searchPriceMaxNumber.getText().toString());
                    a.putExtra("searchPriceMinNumber", searchPriceMinNumber.getText().toString());
                    a.putExtra("searchRoomsMaxNumber", searchRoomsMaxNumber.getText().toString());
                    a.putExtra("searchRoomsMinNumber", searchRoomsMinNumber.getText().toString());
                    a.putExtra("searchSurfaceMaxNumber", searchSurfaceMaxNumber.getText().toString());
                    a.putExtra("searchSurfaceMinNumber", searchSurfaceMinNumber.getText().toString());


                    startActivity(a);
                }

            }
        });
    }
}
