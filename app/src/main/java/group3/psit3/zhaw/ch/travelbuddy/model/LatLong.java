package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.gson.annotations.SerializedName;

/**
 * This is a model class and gets parsed from JSON.
 */
public class LatLong {
    @SerializedName("Latitude")
    private double lat;
    @SerializedName("Longitude")
    private double lon;

    public LatLong(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    double getLong() {
        return lon;
    }

    double getLat() {
        return lat;
    }
}
