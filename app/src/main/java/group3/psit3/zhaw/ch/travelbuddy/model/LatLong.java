package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.gson.annotations.SerializedName;

public class LatLong {
    @SerializedName("Latitude")
    private double lon;
    @SerializedName("Longitude")
    private double lat;

    public LatLong(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLong() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
