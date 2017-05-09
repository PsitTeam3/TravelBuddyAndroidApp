package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;

import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Progress {
    List<Bitmap> images;

    private Timestamp startTime;

    public Progress() {
        this.images = new ArrayList<>();
    }

    public Summary getSummary(){
        return new Summary(0,startTime);
    }
    public void add(Bitmap imageBitmap) {
        images.add(imageBitmap);
    }
}
