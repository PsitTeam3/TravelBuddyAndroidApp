package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Progress {
    List<Bitmap> images;

    public Progress() {
        this.images = new ArrayList<>();
    }

    public void add(Bitmap imageBitmap) {
        images.add(imageBitmap);
    }
}
