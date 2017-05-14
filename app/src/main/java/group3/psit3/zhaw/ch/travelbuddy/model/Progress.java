package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Progress implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;
    public static final int PHOTO_TAKE_THRESHOLD = 10000;

    private Double currentDistance;
    private Double startDistance;

    private List<SerializableBitmap> images;
    private Date startTime;
    private Date lastTimePhotoTaken;

    public Progress() {
        this.startDistance = null;
        this.currentDistance = null;
        this.images = new ArrayList<>();
        this.startTime = new Date();
        this.lastTimePhotoTaken = new Date();
    }

    public void add(Bitmap imageBitmap) {
        this.lastTimePhotoTaken = new Date();
        images.add(new SerializableBitmap(imageBitmap));
    }

    public boolean canTakePhoto() {
        return new Date().getTime() - lastTimePhotoTaken.getTime() > PHOTO_TAKE_THRESHOLD;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public String formatTime(Date startTime, Date endTime) {

        long diff = endTime.getTime() - startTime.getTime();
        long diffSeconds = (diff / 1000 % 60);
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        String stringHours = "" + diffHours;
        if (diffHours < 10) {
            stringHours = "0" + diffHours;
        }

        String stringMinutes = "" + diffMinutes;
        if (diffMinutes < 10) {
            stringMinutes = "0" + diffMinutes;
        }

        String stringSeconds = "" + diffSeconds;
        if (diffSeconds < 10) {
            stringSeconds = "0" + diffSeconds;
        }
        return "" + stringHours + " h " + stringMinutes + " m " + stringSeconds + " s";
    }

    public void updateDistance(Double distance) {
        this.startDistance = this.startDistance == null ? distance : startDistance;
        this.currentDistance = distance;
    }

    public String getTimeSpent() {
        return formatTime(startTime, new Date());
    }

    public Integer getCurrentDistance() {
        return currentDistance == null ? 0 : Math.toIntExact(Math.round(currentDistance));
    }

    public int getProgress() {
        return currentDistance == null
                || startDistance == null
                ? 0 : currentDistance == 0.0
                    ? 100 : (int) (100 * (startDistance/currentDistance));
    }

    public List<Bitmap> getBitmaps() {
        return images.stream().map(SerializableBitmap::getImage).collect(Collectors.toList());
    }

    public void finish() {
        this.currentDistance = 0.0;
        this.startDistance = 0.0;
    }
}
