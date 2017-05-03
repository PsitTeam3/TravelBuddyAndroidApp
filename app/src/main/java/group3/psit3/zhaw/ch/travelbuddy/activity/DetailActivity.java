package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Poi;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import group3.psit3.zhaw.ch.travelbuddy.model.TourOverview;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

import java.util.List;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {
    // Log tag
    private static final String TAG = DetailActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private Button button;
    private TourOverview mTourOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mTourOverview = new TourOverview();
        setContentView(R.layout.activity_detail);

        Tour tour = (Tour) getIntent().getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading tour...");
        pDialog.show();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        RequestQueuer.aRequest().queuePoiList(TAG, tour, this::setPoiList);

        String name = tour.getName();
        String detailDescription = tour.getDetailDescription();

        setTitle(name);

        TextView detailDescriptionView = (TextView) findViewById(R.id.detailDescription);

        detailDescriptionView.setText(detailDescription);

        final Context context = this;
        button = (Button) findViewById(R.id.startTourButton);
        button.setOnClickListener(arg0 -> {
            Intent tourIntent = new Intent(context, TourActivity.class);
            tourIntent.putExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour", tour);
            startActivity(tourIntent);
        });
    }

    private void setPoiList(List<Poi> pois) {
        mTourOverview.setPois(pois).draw();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mTourOverview.initMap(googleMap).draw();
    }
}
