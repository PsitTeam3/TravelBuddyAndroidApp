package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Progress;
import group3.psit3.zhaw.ch.travelbuddy.model.Summary;

public class SummaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Progress progress = (Progress) getIntent().getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Progress");

        drawSummary(new Summary(progress));

    }

    private void drawSummary(Summary summary) {

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutChild);

        for (Bitmap cur : summary.getImages()) {

            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(400, 600));
            image.setMaxHeight(400);
            image.setMaxWidth(600);
            image.setImageBitmap(cur);

            layout.addView(image);
        }

        final Context context = this;
        Button button = (Button) findViewById(R.id.tourOverviewButton);

        button.setOnClickListener(arg0 -> {
            Intent tourIntent = new Intent(context, ListActivity.class);
            startActivity(tourIntent);
        });

        TextView totalTimeSpent = (TextView) findViewById(R.id.totalTimeSpent);
        totalTimeSpent.setText("Total time spent: " + summary.getTotalTimeSpent());

        TextView tourName = (TextView) findViewById(R.id.tourName);
        tourName.setText("Tour summary - " + summary.getTourName());

        TextView tourDescription = (TextView) findViewById(R.id.tourDescription);
        tourDescription.setText("\"" + summary.getTourDescription() + "\"");

    }
}
