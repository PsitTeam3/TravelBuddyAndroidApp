package group3.psit3.zhaw.ch.travelbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TourDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);

        String name = this.getIntent().getExtras().getString("name");
        String detailDescription = this.getIntent().getExtras().getString("detailDescription");

        setTitle(name);

        TextView nameView = (TextView) findViewById(R.id.name);
        TextView detailDescriptionView = (TextView) findViewById(R.id.detailDescription);

        nameView.setText(name);
        detailDescriptionView.setText(detailDescription);
    }
}
