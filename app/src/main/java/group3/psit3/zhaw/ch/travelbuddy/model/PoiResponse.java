package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a model class and gets parsed from JSON.
 */
public class PoiResponse implements Serializable {

    @SerializedName("NextPOI")
    private Poi poi;

    @SerializedName("RouteToNextPOI")
    private List<LatLong> route;

    public PoiResponse(Poi poi, List<LatLong> route) {
        this.poi = poi;
        this.route = route;
    }

    /**
     * Extracts a POI from a nested JSON response.
     * @param response Response as string.
     * @return POI object
     */
    public static Poi fromJson(String response) {
        Gson gson = new GsonBuilder().create();
        PoiResponse poiResponse = gson.fromJson(response, PoiResponse.class);
        return poiResponse.getPoi().setRoute(poiResponse.getRoute());
    }

    public Poi getPoi() {
        return poi;
    }

    private List<LatLng> getRoute() {
        return route.stream().map(cur -> new LatLng(cur.getLat(), cur.getLong())).collect(Collectors.toList());
    }
}
