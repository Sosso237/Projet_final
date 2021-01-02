package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sign_up extends AppCompatActivity {

    TextView register_form,error_sign_up;
    EditText editPerson_name,editPerson_username,editPerson_mailaddress,editPerson_password,editPerson_verification_password;
    Button back_from_register,validate_form;

    SQLiteOpenHelper helper;
    SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        register_form = findViewById(R.id.register_form);
        editPerson_name = findViewById(R.id.editPerson_name);
        editPerson_username = findViewById(R.id.editPerson_username);
        editPerson_mailaddress = findViewById(R.id.editPerson_mailaddress);
        editPerson_password = findViewById(R.id.editPerson_password);
        editPerson_verification_password = findViewById(R.id.editPerson_verification_password);
        back_from_register = findViewById(R.id.back_from_register);
        validate_form = findViewById(R.id.validate_form);
        error_sign_up = findViewById(R.id.error_sign_up);

        error_sign_up.setVisibility(View.INVISIBLE);

        // Database Connection

        helper = new SQLiteOpenHelper(Sign_up.this,"Rooms.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Users (Name TEXT NOT NULL PRIMARY KEY, Pseudo TEXT, Email TEXT NOT NULL ,Password TEXT NOT NULL);");


            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users ;" );
                onCreate(sqLiteDatabase);
            }
        };


        database = helper.getWritableDatabase();

        // Move to the default page
        back_from_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Sign_up.this,MainActivity.class);
                startActivity(i);
            }
        });

        // Set action on validate button
        validate_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editPerson_name.getText().toString().isEmpty()  || editPerson_mailaddress.getText().toString().isEmpty() || editPerson_password.getText().toString().isEmpty() || editPerson_verification_password.getText().toString().isEmpty() ){
                    error_sign_up.setText("Warning, all fields are required except username");
                    error_sign_up.setVisibility(View.VISIBLE);

                } else if (!(editPerson_password.getText().toString().equals(editPerson_verification_password.getText().toString()))){
                    error_sign_up.setText("Passwords are different, retry");
                    error_sign_up.setVisibility(View.VISIBLE);
                } else {
                    // Hash the password
                    String hashed = md5 (editPerson_password.getText().toString());

                    try {
                        database.execSQL("INSERT INTO Users (Name,Pseudo,Email,Password) VALUES ('" + editPerson_name.getText().toString() + "','" + editPerson_username.getText().toString() + "','" + editPerson_mailaddress.getText() + "','" + hashed + "');");
                        Toast.makeText(Sign_up.this, "Successfully added, we'll be redirected to the default page,thanks", Toast.LENGTH_LONG).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Sign_up.this,MainActivity.class);
                                intent.putExtra("Name", editPerson_name.getText().toString());
                                startActivity(intent);

                            }
                        }, 1000);

                    } catch (SQLException e){
                        e.printStackTrace();
                        Intent i = new Intent (Sign_up.this, MainActivity.class);
                        startActivity(i);
                    }
                }


            }
        });


    }

    // As i want to deal with login credentials, i don't want to store password as clear text
    // so i'm gonna
    // use this script from the link below to add some security
    // to my password storage method, noneless it's not the 'perfect' method
    // https://mobikul.com/converting-string-md5-hashes-android/

    public String md5(String toString) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(toString.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}