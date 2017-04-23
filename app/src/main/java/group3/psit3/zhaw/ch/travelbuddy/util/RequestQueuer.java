package group3.psit3.zhaw.ch.travelbuddy.util;

import android.location.Location;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import group3.psit3.zhaw.ch.travelbuddy.app.AppController;
import group3.psit3.zhaw.ch.travelbuddy.model.PhotoStatus;
import group3.psit3.zhaw.ch.travelbuddy.model.Poi;
import group3.psit3.zhaw.ch.travelbuddy.model.Route;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.function.Consumer;

public class RequestQueuer {

    private static final String BASE_URL = "http://travelbuddy5.azurewebsites.net/api";
    private static final String CURRENT_ROUTE = "/route";
    private static final String TOURS = "/tours/gettours";
    private static final String START_TOUR = "/usertour/startusertour";
    private static final String VALIDATE_PHOTO = "/isPhotoValid";
    private static final String ROUTE_POIS = "/poi/getpoisbytour?id=";

    public static RequestQueuer aRequest() {
        return AppController.getInstance().getRequestBuilder();
    }

    public void queueCurrentRoute(String TAG, Location location, Consumer<Route> routeSetter) {
        JSONArray body = new SimpleJsonBuilder().setProperty("lat", location.getLatitude()).setProperty("long", location.getLongitude()).toJsonArray();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, BASE_URL + CURRENT_ROUTE,
                body,
                response -> routeSetter.accept(Route.fromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueTourList(String TAG, Consumer<List<Tour>> tourListSetter) {
        JsonArrayRequest req = new JsonArrayRequest(BASE_URL + TOURS,
                response -> tourListSetter.accept(Tour.listFromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queuePoiList(String TAG, Tour tour, Consumer<List<Poi>> poiListSetter) {
        JsonArrayRequest req = new JsonArrayRequest(BASE_URL + ROUTE_POIS + tour.getId(),
                response -> poiListSetter.accept(Poi.listFromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueStartTour(String TAG, Tour tour, Location location, Consumer<Route> routeSetter) {
        JSONArray reqBody = new JSONArray();
        try {
            reqBody = new JSONArray(String.format("{ id: %s, lat: %s, long: %s }", tour.getId(), location.getLatitude(), location.getLongitude()));
        } catch (JSONException e) {
            // falls through
        }
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, BASE_URL + START_TOUR,
                reqBody,
                response -> routeSetter.accept(Route.fromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }

    public void queueIsPhotoValid(String TAG, Location location, Consumer<PhotoStatus> photoStatusSetter) {
        JSONArray body = new SimpleJsonBuilder().setProperty("lat", location.getLatitude()).setProperty("long", location.getLongitude()).toJsonArray();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, BASE_URL + VALIDATE_PHOTO,
                body,
                response -> photoStatusSetter.accept(PhotoStatus.fromJson(response.toString())),
                error -> Log.e(TAG, error.getMessage()));
        AppController.getInstance().addToRequestQueue(req, TAG);
    }
}
