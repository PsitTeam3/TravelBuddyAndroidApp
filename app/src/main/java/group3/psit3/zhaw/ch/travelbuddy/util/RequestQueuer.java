package group3.psit3.zhaw.ch.travelbuddy.util;

import android.location.Location;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;
import group3.psit3.zhaw.ch.travelbuddy.app.AppController;
import group3.psit3.zhaw.ch.travelbuddy.model.*;
import org.json.JSONArray;

import java.util.List;
import java.util.function.Consumer;

public class RequestQueuer {

    public static RequestQueuer aRequest() {
        return AppController.getInstance().getRequestBuilder();
    }

    public void queueCurrentRoute(String TAG, Location location, Consumer<List<LatLng>> poiSetter) {
        JsonArrayRequest req = new JsonArrayRequest(UrlBuilder.anUrl().currentRoute(new LatLng(location.getLatitude(), location.getLongitude())).build(),
                response -> poiSetter.accept(RouteResponse.listFromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueTourList(String TAG, Consumer<List<Tour>> tourListSetter) {
        JsonArrayRequest req = new JsonArrayRequest(UrlBuilder.anUrl().allTours().build(),
                response -> tourListSetter.accept(Tour.listFromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queuePoiList(String TAG, Tour tour, Consumer<List<Poi>> poiListSetter) {
        JsonArrayRequest req = new JsonArrayRequest(UrlBuilder.anUrl().poisOfTour(tour).build(),
                response -> poiListSetter.accept(Poi.listFromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueStartTour(String TAG, Tour tour, Location location, Consumer<Poi> routeSetter) {
        if (location == null) return;
        JSONArray reqBody = new JSONArray();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                UrlBuilder.anUrl().startTour(new LatLng(location.getLatitude(), location.getLongitude()), tour).build(),
                reqBody,
                response -> routeSetter.accept(PoiResponse.fromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueIsPhotoValid(String TAG, Location location, Consumer<PhotoStatus> photoStatusSetter) {
        JSONArray body = new JSONArray();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, UrlBuilder.anUrl().validatePhoto(new LatLng(location.getLatitude(), location.getLongitude())).build(),
                body,
                response -> photoStatusSetter.accept(PhotoStatus.fromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }
}
