package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;

public class Route {
    private Iterable<LatLng> latLngs;

    public Iterable<LatLng> getLatLngs() {
        return latLngs;
    }

    public static Route fromJsonArray(JSONArray response) {
        return new Route();
    }
}
