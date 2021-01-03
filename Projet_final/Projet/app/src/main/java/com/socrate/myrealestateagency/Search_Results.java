
package com.socrate.myrealestateagency;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class Search_Results extends AppCompatActivity {


    Button deco,Display_back;
    TextView Txt_consul;
    ListView display;

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        deco = findViewById(R.id.deco);
        Display_back = findViewById(R.id.Display_back);
        Txt_consul = findViewById(R.id.Txt_consul);
        display = findViewById(R.id.display);

        //Get values from the previous Intent

        final Intent x = getIntent();
        final String name = x.getStringExtra("username");
        final String populate_type = x.getStringExtra("populate_type");
        final String polulate_status = x.getStringExtra("polulate_status");
        final Integer searchPriceMaxNumber = Integer.valueOf(x.getStringExtra("searchPriceMaxNumber"));
        final Integer searchPriceMinNumber = Integer.valueOf(x.getStringExtra("searchPriceMinNumber"));
        final Integer searchRoomsMaxNumber = Integer.valueOf(x.getStringExtra("searchRoomsMaxNumber"));
        final Integer searchRoomsMinNumber = Integer.valueOf(x.getStringExtra("searchRoomsMinNumber"));
        final Integer searchSurfaceMaxNumber = Integer.valueOf(x.getStringExtra("searchSurfaceMaxNumber"));
        final Integer searchSurfaceMinNumber = Integer.valueOf(x.getStringExtra("searchSurfaceMinNumber"));

        // Database Connection
        helper = new SQLiteOpenHelper(Search_Results.this,"Rooms.db",null,1) {
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

        // Ability to read data in the database
        database = helper.getReadableDatabase();

        //Getting value from the query
        Cursor c = database.rawQuery("SELECT DISTINCT * FROM Appart WHERE CAST (Price AS INT) < 'searchPriceMaxNumber' ", null);
        final ArrayList<Advert> arrayList = new ArrayList<>();
        // While there is a line , add it to the arrayList
        while (c.moveToNext()){
            Advert a = new Advert(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4) ,c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10));
            arrayList.add(a);

        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(Search_Results.this,android.R.layout.simple_list_item_1,arrayList);
        display.setAdapter(arrayAdapter);

        // Set Action on the listview and passing values to another Intent
        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Search_Results.this,Specific_property.class);

                //if (Integer.valueOf(arrayList.get(i).getPrice()) <= searchPriceMaxNumber & Integer.valueOf(arrayList.get(i).getPrice()) >= searchPriceMinNumber) {
                    intent.putExtra("ID",String.valueOf(arrayList.get(i).getId()));
                    intent.putExtra("Type",arrayList.get(i).getType());
                    intent.putExtra("price",arrayList.get(i).getPrice());
                    intent.putExtra("surface",arrayList.get(i).getSurface());
                    intent.putExtra("Rooms",arrayList.get(i).getRooms());
                    intent.putExtra("description",arrayList.get(i).getDescription());
                    intent.putExtra("address",arrayList.get(i).getAddress());
                    intent.putExtra("status",arrayList.get(i).getStatus());
                    intent.putExtra("creation_date",arrayList.get(i).getCreation_date());
                    intent.putExtra("update",arrayList.get(i).getUpdate_date());
                    intent.putExtra("agent",arrayList.get(i).getAgent());
                    startActivity(intent);
                //}


            }
        });

        // Move to the default page

        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_Results.this,MainActivity.class);

                startActivity(intent);
            }
        });

        // Back to the menu

        Display_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_Results.this,Main_menu.class);
                intent.putExtra("Name",name);
                startActivity(intent);
            }
        });


    }
}