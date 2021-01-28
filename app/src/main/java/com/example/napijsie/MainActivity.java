package com.example.napijsie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();


        DBAdapter dbo = new DBAdapter(context);
        dbo.open();

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

        String weight = prefs.getString("weight", getString(R.string.prefs_def_value));
        String age = prefs.getString("age", getString(R.string.prefs_def_value));
        String sex = prefs.getString("sex", "sex_male");

        if (weight.equals(getString(R.string.prefs_def_value)) || age.equals(getString(R.string.prefs_def_value))){
            navController.navigate(R.id.navigation_profile);
            Toast.makeText(context, getString(R.string.warn_settings_incomplete), Toast.LENGTH_LONG).show();
        }


    }

}