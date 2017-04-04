package group3.psit3.zhaw.ch.travelbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group3.psit3.zhaw.ch.travelbuddy.adapter.CustomListAdapter;
import group3.psit3.zhaw.ch.travelbuddy.app.AppController;

public class MainActivity extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json TEMPORARY_HARDCODED_URL
    private static final String TEMPORARY_HARDCODED_URL = "http://travelbuddy5.azurewebsites.net/api/tours";
    private ProgressDialog pDialog;
    private List<Tour> tours = new ArrayList<>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, tours);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj && Adding request to request queue
        AppController.getInstance().addToRequestQueue(getJsonRequest(TEMPORARY_HARDCODED_URL));

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tour tour = tours.get(position);

                Intent detailIntent = new Intent(context, TourDetailActivity.class);

                detailIntent.putExtra("name", tour.getName());
                detailIntent.putExtra("detailDescription", tour.getDetailDescription());

                startActivity(detailIntent);
            }

        });
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

    // TODO(async) hide http requests in CompletableFuture
    @NonNull
    private JsonArrayRequest getJsonRequest(String url) {
        return new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                        public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Tour tour = new Tour();
                                tour.setName(obj.getString("Name"));
                                tour.setDescription(obj.getString("Description"));
                                tour.setDetailDescription(obj.getString("DetailDescription"));
                                // tour.setThumbnailUrl(obj.getString("Image"));
                                tour.setThumbnailUrl("http://api.androidhive.info/json/movies/1.jpg");

                                // adding movie to movies array
                                tours.add(tour);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hidePDialog();
                    }
                }
        );
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
