package group3.psit3.zhaw.ch.travelbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.widget.ListView;
import group3.psit3.zhaw.ch.travelbuddy.adapter.CustomListAdapter;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

import java.util.List;

public class MainActivity extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private CustomListAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //android.os.Debug.waitForDebugger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new CustomListAdapter(this);
        listView.setAdapter(mAdapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj && Adding request to request queue
        RequestQueuer.aRequest().queueTourList(TAG, this::setTourList);

        final Context context = this;
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tour tour = mAdapter.getTours().get(position);

            Intent detailIntent = new Intent(context, TourDetailActivity.class);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
