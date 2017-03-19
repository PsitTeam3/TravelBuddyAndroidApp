package group3.psit3.zhaw.ch.travelbuddy;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ToursOverview extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours_overview);

        String[] tours = {"Zurich", "Bern", "Genf", "Bla"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, tours);
        getListView().setAdapter(adapter);
    }
}
