package com.example.napijsie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_today, R.id.navigation_history, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String str_weight = prefs.getString("weight", "0");
        String str_age = prefs.getString("age", "0");
        String sex = prefs.getString("sex", "sex_male");
        String phys_activity = prefs.getString("phys_actvity", "activity_moderate");

        Float weight = 0.0f;

        try {
            weight = Float.valueOf(str_weight);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Integer age = 0;

        try {
            age = Integer.valueOf(str_age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //Setup a shared preference listener for recalucate the water req
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                Toast.makeText(context, getString(R.string.warn_settings_changed), Toast.LENGTH_LONG).show();

            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listener);

        if (weight == 0.0f || age == 0) {
            navController.navigate(R.id.navigation_profile);
            Toast.makeText(context, getString(R.string.warn_settings_incomplete), Toast.LENGTH_LONG).show();
        }
        else {

            Integer first_10_kg_male = 110;
            Integer first_10_kg_female = 100;
            Integer second_10_kg_male = 60;
            Integer second_10_kg_female = 50;
            Integer next_10_kg = 20;
            Integer age_18_50 = 200;
            Integer age_51_65 = 100;
            Integer activity_low = 100;
            Integer activity_med_male = 350;
            Integer activity_med_female = 300;
            Integer activity_high_male = 550;
            Integer activity_high_female = 500;

            Integer water_demand_calc = 0;

            Float weigth_left = weight;

            // 1st 10 KGS
            if (weigth_left > 0){
                if (sex == "sex_male"){
                    water_demand_calc += first_10_kg_male * 10;
                }
                else {
                    water_demand_calc += first_10_kg_female * 10;
                }
                weigth_left -= 10.0f;
            }

            // 2nd 20 KGS
            if (weigth_left > 0){
                if (sex == "sex_male"){
                    water_demand_calc += second_10_kg_male * 10;
                }
                else {
                    water_demand_calc += second_10_kg_female * 10;
                }
                weigth_left -= 10.0f;
            }

            while (weigth_left > 0) {
                water_demand_calc += next_10_kg * 10;
                weigth_left -= 10.0f;
            }

            // age addition
            if (age >= 18 && age <= 50){
                water_demand_calc += age_18_50;
            }
            else if (age >= 51 && age <= 65){
                water_demand_calc += age_51_65;
            }

            // activity addition
            if(phys_activity == "activity_minimal"){
                water_demand_calc += activity_low;
            }
            else if (phys_activity == "activity_moderate"){
                if (sex == "sex_male"){
                    water_demand_calc += activity_med_male;
                }
                else {
                    water_demand_calc += activity_med_female;
                }
            }
            else if (phys_activity == "activity_high"){
                if (sex == "sex_male"){
                    water_demand_calc += activity_high_male;
                }
                else {
                    water_demand_calc += activity_high_female;
                }
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("water_demand_calc",water_demand_calc.toString());
            editor.commit();
        }

    }


}