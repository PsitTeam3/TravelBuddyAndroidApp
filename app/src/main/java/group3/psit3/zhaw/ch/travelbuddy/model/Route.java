package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;

public class Route {
    private Iterable<LatLng> latLngs;

    public Iterable<LatLng> getLatLngs() {
        return latLngs;
    }

    public static Route fromJson(String response) {
        return new Route();
    }
}
