package group3.psit3.zhaw.ch.travelbuddy.model;

import android.hardware.Sensor;

import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Raffaele on 02.05.2017.
 */

public class Summary {
    private double AVERAGE_STEP_DISTANCE = 0.65;
    private int totalSteps;
    private double totalDistance;
    private int time;
    private Timestamp startTime;
    private Timestamp timeNow;
    private int picturesTaken;

    public Summary(int stepsAtBeginning, Timestamp startTime){
        this.startTime = startTime;
        this.totalSteps = Sensor.TYPE_STEP_COUNTER - stepsAtBeginning;
        this.totalDistance = calculateDistance(this.totalSteps);
    }

    public int getTotalSteps(){
        return totalSteps;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public int getTime() {
        return time;
    }

    private double calculateDistance(int steps) {
        return steps * AVERAGE_STEP_DISTANCE;
    }



}
