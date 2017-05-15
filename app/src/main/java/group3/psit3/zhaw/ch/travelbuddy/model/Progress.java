package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;
import group3.psit3.zhaw.ch.travelbuddy.util.SerializableBitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Progress implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;
    private static final int PHOTO_TAKE_THRESHOLD = 10000;

    private Double currentDistance;
    private Double startDistance;

    private List<SerializableBitmap> images;
    private Date startTime;
    private Date lastTimePhotoTaken;
    private Tour tour;

    public Progress() {
        this.startDistance = null;
        this.currentDistance = null;
        this.images = new ArrayList<>();
        this.startTime = new Date();
        this.lastTimePhotoTaken = new Date();
    }

    /**
     * Adds a photo and updates last time added.
     * @param imageBitmap Photo to store.
     */
    public void add(Bitmap imageBitmap) {
        this.lastTimePhotoTaken = new Date();
        images.add(new SerializableBitmap(imageBitmap));
    }

    /**
     * Checks whether the user can save on more photo.
     * This prevents spamming the camera and limit the amount of
     * photos per POI to one.
     * @return Whether the user can take another photo.
     */
    public boolean canTakePhoto() {
        return new Date().getTime() - lastTimePhotoTaken.getTime() > PHOTO_TAKE_THRESHOLD;
    }

    /**
     * Updates distance. This method is idempotent, since it checks whether
     * it is the first time it the distance got set or not.
     * @param distance The current distance to the next POI.
     */
    public void updateDistance(Double distance) {
        this.startDistance = this.startDistance == null ? distance : startDistance;
        this.currentDistance = distance;
    }

    /**
     * Calculates the width of the progress bar. It handles all edge
     * cases with sane defaults (If the user starts at POI, progress is
     * 100%).
     * @return Progress ranging from 0 to 100.
     */
    public int getProgress() {
        return currentDistance == null
                || startDistance == null
                ? 0 : startDistance == 0.0
                ? 100 : (int) (100 * ((startDistance - currentDistance)/startDistance));
    }

    /**
     * Returns current distance to POI rounded to meters.
     * @return Distance in meters.
     */
    public Integer getCurrentDistance() {
        return currentDistance == null ? 0 : Math.toIntExact(Math.round(currentDistance));
    }

    public void setStartDistance(Double distance) {
        this.startDistance = distance;
    }

    public String getTimeSpent() {
        return formatTime(startTime, new Date());
    }

    public List<Bitmap> getBitmaps() {
        return images.stream().map(SerializableBitmap::getImage).collect(Collectors.toList());
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Tour getTour() {
        return tour;
    }

    private String formatTime(Date startTime, Date endTime) {

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
}
