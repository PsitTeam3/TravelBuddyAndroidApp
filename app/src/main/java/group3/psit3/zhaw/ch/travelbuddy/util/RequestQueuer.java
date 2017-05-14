package group3.psit3.zhaw.ch.travelbuddy.util;


import android.location.Location;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import group3.psit3.zhaw.ch.travelbuddy.app.AppController;
import group3.psit3.zhaw.ch.travelbuddy.model.Poi;
import group3.psit3.zhaw.ch.travelbuddy.model.PoiResponse;
import group3.psit3.zhaw.ch.travelbuddy.model.RouteResponse;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import org.json.JSONArray;

import java.util.List;
import java.util.function.Consumer;


public class RequestQueuer {

    public static RequestQueuer aRequest() {
        return AppController.getInstance().getRequestBuilder();
    }

    public void queueCurrentRoute(String TAG, Location location, Consumer<List<LatLng>> poiSetter, Consumer tourFinisher) {
        String url = UrlBuilder.anUrl().currentRoute(new LatLng(location.getLatitude(), location.getLongitude())).build();
        Log.i(TAG, "Sending request to: " + url);
        StringRequest req = new StringRequest(url,
                response -> poiSetter.accept(RouteResponse.listFromJson(response)),
                error -> tourFinisher.accept(null));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueGetNextPoi(String TAG, Consumer<Poi> nextPoi) {
        String url = UrlBuilder.anUrl().nextPOI().build();
        Log.i(TAG, "Sending request to: " + url);
        JsonObjectRequest req = new JsonObjectRequest(url,
                null,
                response -> nextPoi.accept(Poi.fromJson(response.toString())),
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueTourList(String TAG, Consumer<List<Tour>> tourListSetter) {
        String url = UrlBuilder.anUrl().allTours().build();
        Log.i(TAG, "Sending request to: " + url);
        JsonArrayRequest req = new JsonArrayRequest(url,
                response -> tourListSetter.accept(Tour.listFromJson(response.toString())),
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queuePoiList(String TAG, Tour tour, Consumer<List<Poi>> poiListSetter) {
        String url = UrlBuilder.anUrl().poisOfTour(tour).build();
        Log.i(TAG, "Sending request to: " + url);
        JsonArrayRequest req = new JsonArrayRequest(url,
                response -> poiListSetter.accept(Poi.listFromJson(response.toString())),
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueIsPoiInReach(String TAG, Location location, Consumer<Boolean> poiInReachListener) {
        String url = UrlBuilder.anUrl().poiInReach(new LatLng(location.getLatitude(), location.getLongitude())).build();
        Log.i(TAG, "Sending request to: " + url);
        StringRequest req = new StringRequest(url,
                response -> poiInReachListener.accept(Boolean.valueOf(response)),
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueDistanceToNextPoi(String TAG, Location location, Consumer<Double> distanceToNextPoiListener) {
        String url = UrlBuilder.anUrl().distanceToNextPoi(new LatLng(location.getLatitude(), location.getLongitude())).build();
        Log.i(TAG, "Sending request to: " + url);
        StringRequest req = new StringRequest(url,
                response -> distanceToNextPoiListener.accept(Double.valueOf(response)),
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueStartTour(String TAG, Tour tour, Location location, Consumer<Poi> routeSetter) {
        String url = UrlBuilder.anUrl().startTour(new LatLng(location.getLatitude(), location.getLongitude()), tour).build();
        Log.i(TAG, "Sending request to: " + url);
        if (location == null) return;
        JSONArray reqBody = new JSONArray();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                url,
                reqBody,
                response -> routeSetter.accept(PoiResponse.fromJson(response.toString())),
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }


    public void queueEndTour(String TAG) {
        String url = UrlBuilder.anUrl().endTour().build();
        Log.i(TAG, "Sending request to: " + url);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                url,
                null,
                null,
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueSetCurrentPoiVisited(String TAG) {
        String url = UrlBuilder.anUrl().setCurrentPoiVisited().build();
        Log.i(TAG, "Sending request to: " + url);
        JSONArray reqBody = new JSONArray();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,
                url,
                reqBody,
                null,
                error -> onError(TAG, error, url));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    private void onError(String TAG, VolleyError error, String url) {
        if ("".equals(error.getMessage())) {
            Log.e(TAG, error.getMessage());
        } else if (error.networkResponse != null) {
            Log.e(TAG, "Server error contains no message, Status: " + error.networkResponse.statusCode + ", URL: " + url);
        } else {
            Log.e(TAG, "Server error contains no message, URL: " + url);
        }
        error.printStackTrace();
    }
}
