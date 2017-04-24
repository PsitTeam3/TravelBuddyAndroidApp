package group3.psit3.zhaw.ch.travelbuddy.util;

import com.google.android.gms.maps.model.LatLng;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;

public class UrlBuilder {

    public static final String BASE_URL = "http://travelbuddy5.azurewebsites.net/api";
    private static final String CURRENT_ROUTE = "/route";
    private static final String TOURS = "/tours/gettours";
    private static final String START_TOUR = "/usertour/startusertour";
    private static final String VALIDATE_PHOTO = "/poi/isnextpoiinrange";
    private static final String ROUTE_POIS = "/poi/getpoisbytour?id=";

    public static UrlBuilder anUrl() {
        return new UrlBuilder();
    }

    public String currentRoute() {
        return BASE_URL + CURRENT_ROUTE;
    }

    public String allTours() {
        return BASE_URL + TOURS;
    }

    public String validatePhoto(LatLng position) {
        return String.format(UrlBuilder.BASE_URL + VALIDATE_PHOTO + "?latitude=%s&longitude=%s",
                position.latitude, position.longitude);
    }

    public String startTour(LatLng location, Tour tour) {
        return String.format(BASE_URL + START_TOUR + "?userID=%s&tourdID=%s&currentLatitude=%s&currentLongitude=%s",
                5, tour.getId(), location.latitude, location.longitude);
    }

    public String poisOfTour(Tour tour) {
        return BASE_URL + ROUTE_POIS + tour.getId();
    }
}
