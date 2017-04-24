package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Route {

    @SerializedName("RouteToNextPOI")
    private List<LatLong> points;

    public List<LatLng> getPoints() {
        return points.stream().map(cur -> new LatLng(cur.getLat(), cur.getLong())).collect(Collectors.toList());
    }

    public Route() {
        this.points = new ArrayList<>();
    }

    public static Route fromJson(String response) {
        return new Route();
    }

    public boolean isInPoiReach() {
        // TODO implement
        throw new UnsupportedOperationException();
    }
}
