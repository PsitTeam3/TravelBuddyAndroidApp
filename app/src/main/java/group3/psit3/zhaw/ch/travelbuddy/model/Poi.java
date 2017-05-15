package group3.psit3.zhaw.ch.travelbuddy.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a model class and gets parsed from JSON.
 */
public class Poi implements Serializable {
    @SerializedName("Id")
    private int id;
    @SerializedName("Description")
    private String description;
    @SerializedName("VisitDuration")
    private int visitDuration;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    private List<LatLng> route = new ArrayList<>();

    /**
     * Parses POI from JSON string.
     * @param response Response as string
     * @return Poi object
     */
    public static Poi fromJson(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, Poi.class);
    }

    /**
     * Parses a list of POIs from JSON String.
     * @param response Response as string
     * @return List of POIs
     */
    public static List<Poi> listFromJson(String response) {
        Gson gson = new GsonBuilder().create();
        Type collectionType = new TypeToken<Collection<Poi>>(){}.getType();
        return gson.fromJson(response, collectionType);
    }

    public int getId() {
        return id;
    }

    String getDescription() {
        return description;
    }

    int getVisitDuration() {
        return visitDuration;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }


    Poi setRoute(List<LatLng> route) {
        this.route = route;
        return this;
    }

    List<LatLng> getRoute() {
        return route;
    }
}
