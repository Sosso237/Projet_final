package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_menu extends AppCompatActivity {

    TextView welcome_user;
    Button deconnected,add_property,display_properties,delete_property,edit_property,search,edit_profile,back_from_menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        welcome_user = findViewById(R.id.welcome_user);
        deconnected = findViewById(R.id.deconnected);
        add_property = findViewById(R.id.add_property);
        display_properties = findViewById(R.id.display_properties);
        delete_property = findViewById(R.id.delete_property);
        edit_property = findViewById(R.id.edit_property);
        search = findViewById(R.id.search);
        edit_profile = findViewById(R.id.edit_profile);
        back_from_menu = findViewById(R.id.back_from_menu);

        // Getting value from previous Intent
        final Intent x = getIntent();
        final String name = x.getStringExtra("Name");
        welcome_user.setText("Welcome "+x.getStringExtra("Name"));

        // Move to the Add Intent
        add_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,Add_property.class);
                i.putExtra("username",x.getStringExtra("Name"));
                startActivity(i);
            }
        });

        // Move to Display Intent
        display_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,Display_Properties.class);
                i.putExtra("username",x.getStringExtra("Name"));
                startActivity(i);
            }
        });

        // Move to Delete Intent
        delete_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,Delete_property1.class);
                i.putExtra("Name",x.getStringExtra("Name"));
                startActivity(i);
            }
        });
        // Move to Edit Intent
        edit_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,Edit_property1.class);
                i.putExtra("Name",x.getStringExtra("Name"));
                startActivity(i);
            }
        });

        // Move to the Search Intent
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Main_menu.this,Search.class);
                x.putExtra("Name",name);
                startActivity(x);

            }
        });

        // Move to Edit Profile Intent
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,Edit_profile.class);
                i.putExtra("user",x.getStringExtra("Name"));
                startActivity(i);
            }
        });

        // Back to the menu
        back_from_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,MainActivity.class);
                startActivity(i);

            }
        });

        // Move to the default Intent
        deconnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_menu.this,MainActivity.class);
                startActivity(i);

            }
        });

    }
}