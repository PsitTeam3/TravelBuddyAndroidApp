package group3.psit3.zhaw.ch.travelbuddy.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.android.volley.toolbox.JsonArrayRequest;
import group3.psit3.zhaw.ch.travelbuddy.app.AppController;
import group3.psit3.zhaw.ch.travelbuddy.model.Route;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;

import java.util.List;
import java.util.function.Consumer;

public class RequestBuilder {

    public static final String BASE_URL = "http://travelbuddy5.azurewebsites.net/api/";
    private static final String CURRENT_ROUTE = "/route";
    public static final String TOURS = "/tours";

    public static RequestBuilder aRequest() {
        return AppController.getInstance().getRequestBuilder();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCurrentRoute(Consumer<Route> routeSetter) {
        JsonArrayRequest req = new JsonArrayRequest(BASE_URL + CURRENT_ROUTE,
                response -> routeSetter.accept(Route.fromJsonArray(response)),
                error -> Log.e(getClass().getSimpleName(), error.getMessage()));
        AppController.getInstance().addToRequestQueue(req);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTourList(Consumer<List<Tour>> tourListSetter) {
        JsonArrayRequest req = new JsonArrayRequest(BASE_URL + TOURS,
                response -> tourListSetter.accept(Tour.listFromJsonArray(response)),
                error -> Log.e(getClass().getSimpleName(), error.getMessage()));
        AppController.getInstance().addToRequestQueue(req);
    }


}
