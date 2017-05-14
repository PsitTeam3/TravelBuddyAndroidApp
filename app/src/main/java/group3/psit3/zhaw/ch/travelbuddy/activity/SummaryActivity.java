package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Progress;
import group3.psit3.zhaw.ch.travelbuddy.model.Summary;

public class SummaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Progress progress = (Progress) getIntent().getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Progress");
        Summary summary = new Summary(progress);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutChild);

        for (Bitmap cur : summary.getImages()) {

            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80, 60));
            image.setMaxHeight(200);
            image.setMaxWidth(200);
            image.setImageBitmap(cur);

            layout.addView(image);
        }
    }

}
