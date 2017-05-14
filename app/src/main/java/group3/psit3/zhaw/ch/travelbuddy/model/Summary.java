package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

public class Summary {
    private final Progress progress;

    public Summary(Progress progress) {
        this.progress = progress;
    }

    public Date getStartTime() {
        return progress.getStartTime();
    }

    public List<Bitmap> getImages() {
        return progress.getBitmaps();
    }

    public String getTotalTimeSpent() {
        return progress.getTimeSpent();
    }

    public String getTourName() {
        return progress.getTour().getName();
    }

    public String getTourDescription() {
        return progress.getTour().getDescription();
    }
}
