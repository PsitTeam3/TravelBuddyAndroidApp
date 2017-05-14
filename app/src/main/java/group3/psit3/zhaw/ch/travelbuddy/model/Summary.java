package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

public class Summary {
    private double AVERAGE_STEP_DISTANCE = 0.65;
    private int totalSteps;
    private double totalDistance;
    private int time;
    private Date startTime;
    private String totalTime;
    private int picturesTaken;
    public transient List<Bitmap> images;


    public Summary(Progress progress){
        this.startTime = progress.getStartTime();
        this.totalDistance = calculateDistance(this.totalSteps);
    }

    public int getTotalSteps(){
        return totalSteps;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getTime() {
        return time;
    }

    public Bitmap getPic(int id){
        return Bitmap.createScaledBitmap(images.get(id), 256, 256, false);
    }

    public int getPicturesTaken(){
        return images.size();
    }

    public void setImages(List<Bitmap> images){
        this.images = images;
    }
    public String totalTime(){
        return this.totalTime;
    }

    private double calculateDistance(int steps) {
        return steps * AVERAGE_STEP_DISTANCE;
    }



}
