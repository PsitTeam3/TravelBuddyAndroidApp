package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Route {
    private Iterable<LatLng> points;
    private boolean inPoiReach;

    public Route() {
        this.points = new ArrayList<>();
    }

    public Iterable<LatLng> getPoints() {
        return points;
    }

    public static Route fromJson(String response) {
        return new Route();
    }

    public boolean isInPoiReach() {
        return inPoiReach;
    }
}
