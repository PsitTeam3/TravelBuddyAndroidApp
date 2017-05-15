package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Summary wraps Progress and provides convenient methods
 * to get finalized information.
 */
public class Summary {
    private final Progress progress;

    public Summary(Progress progress) {
        this.progress = progress;
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
