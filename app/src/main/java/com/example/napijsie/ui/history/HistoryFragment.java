package com.example.napijsie.ui.history;

        import android.content.Context;
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

        import com.example.napijsie.DBAdapter;
        import com.example.napijsie.R;

        import java.util.List;

public class HistoryFragment extends Fragment {


    private ListView lvHistory;

    private String[] items = {
            "Brak wpisów! Wróć tutaj za jakiś czas!"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
        DBAdapter dbo = new DBAdapter(context);
        dbo.open();

        Integer cnt_records = dbo.selectReadingsCnt();

        List<String> ddd = dbo.getReadingsAggDaily();
        String[] items_hist = ddd.toArray(new String[0]);

        if (items_hist.length > 0){
            items = items_hist;
        }

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lvHistory = (ListView) view.findViewById(R.id.list_view_history);

        lvHistory.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1 , items));

        return view;
    }
}