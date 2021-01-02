package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Specific_property extends AppCompatActivity {

    Button button2,button_back;
    TextView Txt_id,Txt_rooms,Txt_type,Txt_address,Txt_price,Txt_status,Txt_surface,Txt_creation_date,Txt_update_date,Txt_agent,Txt_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_property);
        button2= findViewById(R.id.button2);
        button_back = findViewById(R.id.button_back);
        Txt_id = findViewById(R.id.Txt_id);
        Txt_rooms = findViewById(R.id.Txt_rooms);
        Txt_type = findViewById(R.id.Txt_type);
        Txt_address = findViewById(R.id.Txt_address);
        Txt_price = findViewById(R.id.Txt_price);
        Txt_status = findViewById(R.id.Txt_status);
        Txt_surface = findViewById(R.id.Txt_surface);
        Txt_creation_date = findViewById(R.id.Txt_creation_date);
        Txt_update_date = findViewById(R.id.Txt_update_date);
        Txt_agent = findViewById(R.id.Txt_agent);
        Txt_description = findViewById(R.id.Txt_description);

        // Getting value from previous Intent
        final Intent x = getIntent();

        Txt_id.setText("ID : "+x.getStringExtra("ID"));
        Txt_rooms.setText("Rooms : "+x.getStringExtra("Rooms"));
        Txt_type.setText("Type : "+x.getStringExtra("Type"));
        Txt_address.setText("Address : "+x.getStringExtra("address"));
        Txt_price.setText("Price : "+x.getStringExtra("price"));
        Txt_status.setText("Status : "+x.getStringExtra("status"));
        Txt_surface.setText("Surface : "+x.getStringExtra("surface"));
        Txt_creation_date.setText("Date of Creation : "+x.getStringExtra("creation_date"));
        Txt_update_date.setText(" Update : "+x.getStringExtra("update"));
        Txt_agent.setText(" Agent name  :  "+x.getStringExtra("agent"));
        Txt_description.setText("Description :  "+x.getStringExtra("description"));

        // Move to the default page

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Specific_property.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Back to the previous page

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Specific_property.this,Main_menu.class);
                intent.putExtra("Name",x.getStringExtra("agent"));
                startActivity(intent);
            }
        });




    }
}