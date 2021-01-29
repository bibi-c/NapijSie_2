package com.example.napijsie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class AddReading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading);



        final Button button = (Button) findViewById(R.id.save_reading);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Context context = getApplicationContext();

                EditText et = findViewById(R.id.editTextWaterReading);
                String reading_str = et.getText().toString();

                Float reading = 0.0f;
                try {
                    reading = Float.valueOf(reading_str);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

                String demand_str = prefs.getString("water_demand_calc", "0");

                Float demand = 0.0f;

                try {
                    demand = Float.valueOf(demand_str);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                Date currentDate = new Date();
                Long unix_time = currentDate.getTime() / 1000;
                Integer datetime = Integer.valueOf(unix_time.intValue());

                DBAdapter dbo = new DBAdapter(context);
                dbo.open();
                dbo.insertReading(datetime, reading, demand);

                Intent intent = new Intent(context, MainActivity.class);

                startActivity(intent);
            }
        });

    }
}