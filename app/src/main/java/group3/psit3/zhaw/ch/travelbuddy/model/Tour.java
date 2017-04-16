package group3.psit3.zhaw.ch.travelbuddy.model;


import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tour {
    private String name, description, detailDescription, thumbnailUrl;

    public String getName() {
        return name;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public static List<Tour> listFromJsonArray(JSONArray response) {
        List<Tour> result = new ArrayList<>();
        // Parsing json
        for (int i = 0; i < response.length(); i++) {
            try {

                JSONObject obj = response.getJSONObject(i);
                Tour tour = new Tour();
                tour.setName(obj.getString("Name"));
                tour.setDescription(obj.getString("Description"));
                tour.setDetailDescription(obj.getString("DetailDescription"));
                // tour.setThumbnailUrl(obj.getString("Image"));
                tour.setThumbnailUrl("http://api.androidhive.info/json/movies/1.jpg");

                // adding movie to movies array
                result.add(tour);

            } catch (JSONException ex) {
                Log.e("Tour.class", String.format("Failed to parse json: %s", ex.getMessage()));
            }

        }
        return result;
    }

}
