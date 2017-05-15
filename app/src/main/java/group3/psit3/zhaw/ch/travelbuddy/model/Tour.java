package group3.psit3.zhaw.ch.travelbuddy.model;


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
public class Tour implements Serializable {

    private static final String TAG = Tour.class.getSimpleName();

    private static final long serialVersionUID = 7526471155622776147L;

    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("DetailDescription")
    private String detailDescription;
    @SerializedName("Country")
    private String country;
    @SerializedName("City")
    private String city;
    @SerializedName("Image")
    private String thumbnailUrl;

    private List<Poi> pois;

    public Tour() {
        this.pois = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public Tour setName(String name) {
        this.name = name;
        return this;
    }

    Tour setDescription(String description) {
        this.description = description;
        return this;
    }

    Tour setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
        return this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public static List<Tour> listFromJson(String response) {
        Gson gson = new GsonBuilder().create();
        Type collectionType = new TypeToken<Collection<Tour>>(){}.getType();
        return gson.fromJson(response, collectionType);
    }

    public int getId() {
        return id;
    }

    public Tour setId(int id) {
        this.id = id;
        return this;
    }
}
