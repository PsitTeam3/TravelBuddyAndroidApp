package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;
import android.hardware.Sensor;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Raffaele on 02.05.2017.
 */

public class Summary implements Serializable{
    private double AVERAGE_STEP_DISTANCE = 0.65;
    @SerializedName("totalSteps")
    private int totalSteps;
    @SerializedName("totalDistance")
    private double totalDistance;
    @SerializedName("time")
    private int time;
    @SerializedName("startTime")
    private Date startTime;
    @SerializedName("totalTime")
    private String totalTime;
    @SerializedName("picturesTaken")
    private int picturesTaken;
    public transient List<Bitmap> images;


    public Summary(Progress progress){

        this.startTime = progress.getStartTime();
        this.totalSteps = Sensor.TYPE_STEP_COUNTER - progress.getStepsAtStart();
        this.totalDistance = calculateDistance(this.totalSteps);
        this.images = progress.images;
        this.totalTime = progress.getFormattedTime();
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
