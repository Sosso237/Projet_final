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
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {


    TextView one_last,error_password;
    ImageView login_image;
    EditText password_login;
    Button button_validate_login,button_back_from_login;
    SQLiteOpenHelper helper ;
    SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        one_last = findViewById(R.id.one_last);
        error_password = findViewById(R.id.error_password);
        login_image = findViewById(R.id.login_image);
        password_login = findViewById(R.id.password_login);
        button_validate_login = findViewById(R.id.button_validate_login);
        button_back_from_login = findViewById(R.id.button_back_from_login);

        final Intent a = getIntent();
        one_last.setText("One Last step "+a.getStringExtra("Name"));



        helper = new SQLiteOpenHelper(Login.this,"Rooms.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Users (Name TEXT NOT NULL PRIMARY KEY, Pseudo TEXT, Email TEXT NOT NULL,Password TEXT NOT NULL);");

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users ;" );
                onCreate(sqLiteDatabase);
            }
        };

        database = helper.getReadableDatabase();


        button_validate_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (password_login.getText().toString().isEmpty()){
                    error_password.setText("Warning this field can't be blank, retry ");
                    error_password.setVisibility(View.VISIBLE);
                } else {
                    // Hash the current Password
                    String Hashed =  md5(password_login.getText().toString());

                    //Getting the result of the query

                    Cursor c = database.rawQuery("SELECT DISTINCT * FROM Users WHERE Name ='"+a.getStringExtra("Name") +"' AND Password = '"+Hashed+"';",null);
                    // if there is a line, i store username and the hashed password
                    if (c.moveToNext()){
                        Intent i = new Intent(Login.this,Main_menu.class);

                        i.putExtra("Name", a.getStringExtra("Name"));
                        i.putExtra("pseudo",c.getString(1));

                        startActivity(i);
                    } else {

                        // Error message

                        error_password.setText("Wrong credentials");
                        error_password.setVisibility(View.VISIBLE);
                    }


                }

            }
        });


        // Back to default page

        button_back_from_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,MainActivity.class);
                startActivity(i);
            }
        });




    }

    // As i want to deal with password, i use hashed password
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