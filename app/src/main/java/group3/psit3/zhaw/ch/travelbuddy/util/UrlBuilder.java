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
    private String url;

    public static UrlBuilder anUrl() {
        return new UrlBuilder();
    }

    public UrlBuilder currentRoute() {
        this.url = BASE_URL + CURRENT_ROUTE;
        return this;
    }

    public UrlBuilder allTours() {
        this.url = BASE_URL + TOURS;
        return this;
    }

    public UrlBuilder validatePhoto(LatLng position) {
        this.url = String.format(UrlBuilder.BASE_URL + VALIDATE_PHOTO + "?latitude=%s&longitude=%s",
                position.latitude, position.longitude);
        return this;
    }

    public UrlBuilder startTour(LatLng location, Tour tour) {
        this.url = String.format(BASE_URL + START_TOUR + "?userID=%s&tourID=%s&currentLatitude=%s&currentLongitude=%s",
                5, tour.getId(), location.latitude, location.longitude);
        return this;
    }

    public UrlBuilder poisOfTour(Tour tour) {
        this.url = BASE_URL + ROUTE_POIS + tour.getId();
        return this;
    }

    public String build() {
        return url;
    }
}
