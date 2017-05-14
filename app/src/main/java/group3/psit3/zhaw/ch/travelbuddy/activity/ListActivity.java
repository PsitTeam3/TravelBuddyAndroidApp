package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.adapter.CustomListAdapter;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

import java.util.List;

public class ListActivity extends Activity {
    // Log tag
    private static final String TAG = ListActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private CustomListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RequestQueuer.aRequest().queueEndTour(TAG);

        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new CustomListAdapter(this);
        listView.setAdapter(mAdapter);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading tours...");
        pDialog.show();

        RequestQueuer.aRequest().queueTourList(TAG, this::setTourList);

        final Context context = this;
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tour tour = mAdapter.getTours().get(position);

            Intent detailIntent = new Intent(context, DetailActivity.class);

            detailIntent.putExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour", tour);

            startActivity(detailIntent);
        });
    }

    public void setTourList(List<Tour> tourList) {
        hidePDialog();
        mAdapter.setTours(tourList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
