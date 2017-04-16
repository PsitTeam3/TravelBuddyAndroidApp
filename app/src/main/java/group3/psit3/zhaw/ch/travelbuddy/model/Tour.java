package group3.psit3.zhaw.ch.travelbuddy.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class Tour {

    private static final String TAG = Tour.class.getSimpleName();

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
    private String thumbnailUrl;

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

    public Tour setDescription(String description) {
        this.description = description;
        return this;
    }

    public Tour setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
        return this;
    }

    public Tour setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

}
