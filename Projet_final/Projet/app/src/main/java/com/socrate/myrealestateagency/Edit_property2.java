package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_property2 extends AppCompatActivity {

    TextView text_id,text_type,text_price,text_surface,text_rooms,text_description,text_address,text_printid,text_edit;
    Button deco,back_edit,next_page_edit;
    Spinner spinner_edit_type,spinner_edit_rooms;
    EditText edit_price,edit_surface,edit_description,edit_address;

    String[] type={"House","Flat","Office"};
    String[] rooms = {"1","2","3","4","5","6","7","8","9"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property2);
        text_id = findViewById(R.id.text_id);
        text_type = findViewById(R.id.text_type);
        text_price = findViewById(R.id.text_price);
        text_surface = findViewById(R.id.text_surface);
        text_rooms = findViewById(R.id.text_rooms);
        text_description = findViewById(R.id.text_description);
        text_address = findViewById(R.id.text_address);
        text_printid = findViewById(R.id.text_printid);
        text_edit = findViewById(R.id.text_edit);
        deco = findViewById(R.id.deco);
        back_edit = findViewById(R.id.back_edit);
        next_page_edit = findViewById(R.id.next_page_edit);
        spinner_edit_type = findViewById(R.id.spinner_edit_type);
        spinner_edit_rooms = findViewById(R.id.spinner_edit_rooms);
        edit_price = findViewById(R.id.edit_price);
        edit_surface = findViewById(R.id.edit_surface);
        edit_description = findViewById(R.id.edit_description);
        edit_address = findViewById(R.id.edit_address);

        final Intent x = getIntent();
        //final String take_id = x.getStringExtra("ID");
        final String agent = x.getStringExtra("username");
        text_printid.setText(x.getStringExtra("ID"));
        edit_price.setText(x.getStringExtra("price"));
        edit_surface.setText(x.getStringExtra("surface"));
        edit_description.setText(x.getStringExtra("description"));
        edit_address.setText(x.getStringExtra("address"));

        //Populate type

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinner_edit_type.setAdapter(aa);

        //Populate rooms

        ArrayAdapter polulate_rooms = new ArrayAdapter(Edit_property2.this,android.R.layout.simple_spinner_item,rooms);
        polulate_rooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner Type
        spinner_edit_rooms.setAdapter(polulate_rooms);

        // Move to the default page

        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_property2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // back to the menu
        back_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_property2.this,Main_menu.class);
                intent.putExtra("Name",agent);
                startActivity(intent);
            }
        });

        // Move on the next page
        next_page_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spinner_edit_type.getSelectedItem().toString().isEmpty() ||  edit_price.getText().toString().isEmpty() || edit_surface.getText().toString().isEmpty() || spinner_edit_rooms.getSelectedItem().toString().isEmpty() || edit_description.getText().toString().isEmpty() || edit_address.getText().toString().isEmpty()){
                    Toast.makeText(Edit_property2.this, "Warning all fiels are required", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Edit_property2.this, Edit_property3.class);
                    intent.putExtra("ID",x.getStringExtra("ID"));
                    intent.putExtra("type",spinner_edit_type.getSelectedItem().toString());
                    intent.putExtra("price",edit_price.getText().toString());
                    intent.putExtra("surface",edit_surface.getText().toString());
                    intent.putExtra("rooms",spinner_edit_rooms.getSelectedItem().toString());
                    intent.putExtra("description",edit_description.getText().toString());
                    intent.putExtra("postal",edit_address.getText().toString());
                    intent.putExtra("Agent",agent);

                    startActivity(intent);
                }
            }
        });

    }
}