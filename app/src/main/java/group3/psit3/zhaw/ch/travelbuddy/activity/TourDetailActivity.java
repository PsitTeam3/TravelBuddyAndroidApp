package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;

public class TourDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);

        Tour tour = (Tour) getIntent().getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour");
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
}
