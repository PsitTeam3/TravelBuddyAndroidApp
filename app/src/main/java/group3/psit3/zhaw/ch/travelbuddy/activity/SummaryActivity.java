package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.Summary;

import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private Button button;
    public static List<Bitmap> gallery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent i = getIntent();
        Summary summary = (Summary)i.getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Summary");
        summary.setImages(this.gallery);



/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }

        //Fill images with test values
        String absPath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)).getAbsolutePath();
        summary.images.add(BitmapFactory.decodeFile(absPath + "/pic1.bmp"));
        summary.images.add(BitmapFactory.decodeFile(absPath + "/pic2.bmp"));
        summary.images.add(BitmapFactory.decodeFile(absPath + "/pic3.bmp"));

        //

*/



        TextView total_time = (TextView) findViewById(R.id.total_time);
        total_time.setText(summary.totalTime());

        TextView total_steps = (TextView) findViewById(R.id.total_steps);
        total_steps.setText(String.valueOf(summary.getTotalSteps()));

        TextView total_pictures = (TextView) findViewById(R.id.total_pictures);
        total_pictures.setText(String.valueOf(summary.getPicturesTaken()));

        ImageView image1  = (ImageView) findViewById(R.id.image_1);
        image1.setImageBitmap(summary.getPic(0));




    }





    private void checkPermissions(){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1052);

        }

    }

    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1052: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted.

                } else {


                    // Permission denied - Show a message to inform the user that this app only works
                    // with these permissions granted

                }
                return;
            }

        }
    }

}
