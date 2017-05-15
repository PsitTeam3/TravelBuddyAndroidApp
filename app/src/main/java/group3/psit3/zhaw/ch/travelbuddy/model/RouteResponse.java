package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a model class and gets parsed from JSON.
 */
public class RouteResponse implements Serializable {

    private List<LatLng> route;
    private boolean isTourFinished;

    private RouteResponse(List<LatLng> route, boolean isTourFinished) {
        this.route = route;
        this.isTourFinished = isTourFinished;
    }

    /**
     * Parses a RouteResponse object from a string. It
     * @param response Response string to parse
     * @return
     */
    public static RouteResponse fromJson(String response) {
        if ("Tour doesn't have remaining POIs".equals(response)) {
            return new RouteResponse(new ArrayList<>(), true);
        }
        Gson gson = new GsonBuilder().create();
        Type collectionType = new TypeToken<Collection<LatLong>>(){}.getType();
        List<LatLong> latLongs = gson.fromJson(response, collectionType);
        return new RouteResponse(latLongs.stream().map(cur -> new LatLng(cur.getLat(), cur.getLong())).collect(Collectors.toList()), false);
    }

    public boolean isFinished() {
        return isTourFinished;
    }

    public List<LatLng> getRoute() {
        return route;
    }
}
