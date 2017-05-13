package group3.psit3.zhaw.ch.travelbuddy.model;

import android.graphics.Bitmap;
import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Progress {
    List<Bitmap> images;
    private int stepsAtStart;
    private Date startTime;

    public Progress() {
        this.stepsAtStart = Sensor.TYPE_STEP_COUNTER;
        this.images = new ArrayList<>();
        this.startTime = new Date();
    }

    public void add(Bitmap imageBitmap) {
        images.add(imageBitmap);
    }

    public int getStepsAtStart(){
        return this.stepsAtStart;
    }

    public Date getStartTime(){
        return this.startTime;
    }

    public List<Bitmap> getImages(){
        return images;
    }

    public String getFormattedTime(){
        String output = new String();
        Date timeNow = new Date();

        long diff = timeNow.getTime() - this.startTime.getTime();
        long diffSeconds = (diff / 1000 % 60);
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        String stringHours = ""+diffHours;
        if(diffHours < 10){
            stringHours = "0"+diffHours;
        }

        String stringMinutes = ""+diffMinutes;
        if(diffMinutes < 10){
            stringMinutes = "0"+diffMinutes;
        }

        String stringSeconds = ""+diffSeconds;
        if(diffSeconds < 10){
            stringSeconds = "0"+diffSeconds;
        }
        return ""+stringHours+" h "+stringMinutes+" m "+stringSeconds+" s";
    }
}
