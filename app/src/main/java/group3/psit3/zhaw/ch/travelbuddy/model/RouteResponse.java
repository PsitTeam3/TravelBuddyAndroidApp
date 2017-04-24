package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RouteResponse implements Serializable {

    public static List<LatLng> listFromJson(String response) {
        Gson gson = new GsonBuilder().create();
        Type collectionType = new TypeToken<Collection<LatLong>>(){}.getType();
        List<LatLong> latLongs = gson.fromJson(response, collectionType);
        return latLongs.stream().map(cur -> new LatLng(cur.getLat(), cur.getLong())).collect(Collectors.toList());
    }
}
