package com.example.napijsie.ui.today;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.napijsie.AddReading;
import com.example.napijsie.DBAdapter;
import com.example.napijsie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TodayFragment extends Fragment {


    private TextView tvToday;

    private String[] items = {
            "Brak wpisów! Dodaj swoją pierwszą szklankę wody dzisiaj!"
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        DBAdapter dbo = new DBAdapter(context);
        dbo.open();

        List<String> ddd = dbo.getReadingsAggToday();
        String[] items_today = ddd.toArray(new String[0]);

        if (items_today.length > 0){
            items = items_today;
        }

        View view = inflater.inflate(R.layout.fragment_today, container, false);

        tvToday = (TextView) view.findViewById(R.id.text_today);

        tvToday.setText(items[0]);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddReading.class);
                startActivity(intent);
            }
        });

        return view;
    }
}