package com.socrate.myrealestateagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Edit_profile extends AppCompatActivity {

    TextView Text_name,Text_pseudo,Text_mail,Text_current_password,Text_new_password,Label_message,Text_Name_user , CurrentPassword;
    Button button,go_back,update;
    EditText editTextTextPersonName, editTextEmailAddress, editNewPassword;

    SQLiteOpenHelper helper;
    SQLiteDatabase database,update_queries;

    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Text_name = findViewById(R.id.Text_name);
        Text_pseudo = findViewById(R.id.Text_pseudo);
        Text_mail = findViewById(R.id.Text_mail);
        Text_current_password = findViewById(R.id.Text_current_password);
        Text_new_password = findViewById(R.id.Text_current_password);
        Label_message = findViewById(R.id.Label_message);
        Text_Name_user = findViewById(R.id.Text_Name_user);
        button = findViewById(R.id.button);
        go_back = findViewById(R.id.go_back);
        update = findViewById(R.id.update);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        CurrentPassword = findViewById(R.id.CurrentPassword);
        editNewPassword = findViewById(R.id.editNewPassword);

        // Getting values from the previous Intent
        final Intent x = getIntent();
        final String username = x.getStringExtra("user");

        // Database Connection
        helper = new SQLiteOpenHelper(Edit_profile.this,"Rooms.db",null,1) {
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

        // Read database
        database = helper.getReadableDatabase();
        update_queries = helper.getWritableDatabase();
        final Cursor c = database.rawQuery("SELECT DISTINCT * FROM Users WHERE Name ='" + username  + "';", null);
        if (c.moveToNext()) {

            Text_Name_user.setText(c.getString(0));
            editTextTextPersonName.setText(c.getString(1));
            editTextEmailAddress.setText(c.getString(2));
            pass = c.getString(3);
            StringBuilder sb = new StringBuilder();
            // Replace all char on pass variable with '*' to mask the original password
            for(int i = 0; i< c.getString(3).length(); i++){
                sb.append("*");
            }
            // Masked password
            CurrentPassword.setText(sb.toString());

            // Set action on update button

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ( Text_Name_user.getText().toString().isEmpty() || editTextEmailAddress.getText().toString().isEmpty() ||  CurrentPassword.getText().toString().isEmpty()){
                        Toast.makeText(Edit_profile.this, "Warning, fiedls required are marqued with a * ", Toast.LENGTH_LONG).show();

                    } else {
                        // Check is a new password is set
                        if (editNewPassword.getText().toString().isEmpty()){
                            update_queries.execSQL("UPDATE Users SET Name ='" + Text_Name_user.getText().toString() +"'," + "Pseudo ='" +editTextTextPersonName.getText().toString()+ "', Email ='" + editTextEmailAddress.getText().toString() + "'," + "Password ='" + pass + "' " + "WHERE Name ='" + username + "';" );
                            //System.out.println("UPDATE Users SET Name ='" + Text_Name_user.getText().toString() +"'," + "Pseudo ='" +editTextTextPersonName.getText().toString()+ "', Email ='" + editTextEmailAddress.getText().toString() + "'," + "Password ='" + pass + "' " + "WHERE Name ='" + username + "';" );
                            Toast.makeText(Edit_profile.this, "Updated successfully ", Toast.LENGTH_LONG).show();


                        } else {
                            // New password, set so we have to hash the password and store it
                            String Hashed = md5(editNewPassword.getText().toString());
                            update_queries.execSQL("UPDATE Users SET Name ='" + Text_Name_user.getText().toString() +"'," + "Pseudo ='" +editTextTextPersonName.getText().toString()+ "', Email ='" + editTextEmailAddress.getText().toString() + "'," + "Password ='" + Hashed + "' " + "WHERE Name ='" + username + "';" );
                            Toast.makeText(Edit_profile.this, "Password set", Toast.LENGTH_LONG).show();

                        }

                        Intent intent = new Intent(Edit_profile.this,MainActivity.class);
                        startActivity(intent);

                    }

                }
            });



        }

        // Move to the default page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_profile.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Back to previous screen
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_profile.this,Main_menu.class);
                intent.putExtra("Name",username);
                startActivity(intent);
            }
        });


    }





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