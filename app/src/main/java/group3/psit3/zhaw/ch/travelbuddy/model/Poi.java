package group3.psit3.zhaw.ch.travelbuddy.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getVisitDuration() {
        return visitDuration;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static Poi fromJson (String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, Poi.class);
    }

    public static List<Poi> listFromJson(String response) {
        Gson gson = new GsonBuilder().create();
        Type collectionType = new TypeToken<Collection<Poi>>(){}.getType();
        return gson.fromJson(response, collectionType);
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }


}
