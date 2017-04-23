package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Poi;
import group3.psit3.zhaw.ch.travelbuddy.model.Route;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

public class DetailActivity extends Activity {
    // Log tag
    private static final String TAG = DetailActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Tour tour = (Tour) getIntent().getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading tour...");
        pDialog.show();

        RequestQueuer.aRequest().queuePoiList(TAG, tour, this::setPoiList);

        String name = tour.getName();
        String detailDescription = tour.getDetailDescription();

        setTitle(name);

        TextView nameView = (TextView) findViewById(R.id.name);
        TextView detailDescriptionView = (TextView) findViewById(R.id.detailDescription);

        final Context context = this;
        detailDescriptionView.setOnTouchListener((v, event) -> {
            Intent tourIntent = new Intent(context, TourActivity.class);

            tourIntent.putExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour", tour);

            startActivity(tourIntent);
            return true;
        });

        nameView.setText(name);
        detailDescriptionView.setText(detailDescription);
    }

    private void setPoiList(List<Poi> pois) {
        System.out.println(pois);
        hidePDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
