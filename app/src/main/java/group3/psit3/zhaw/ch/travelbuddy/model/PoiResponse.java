package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PoiResponse implements Serializable {

    @SerializedName("NextPOI")
    private Poi poi;

    @SerializedName("RouteToNextPOI")
    private Route route;

    public PoiResponse(Poi poi, Route route) {
        this.poi = poi;
        this.route = route;
    }

    public static PoiResponse fromJson(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, PoiResponse.class);
    }

    public Poi getPoi() {
        return poi;
    }

    public Route getRoute() {
        return route;
    }
}
