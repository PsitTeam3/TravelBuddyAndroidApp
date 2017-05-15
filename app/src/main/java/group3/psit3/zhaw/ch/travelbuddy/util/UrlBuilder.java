package group3.psit3.zhaw.ch.travelbuddy.util;

import com.google.android.gms.maps.model.LatLng;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;

/**
 * UrlBuilder holds all URL fragments and constants and provides a
 * simple and clean API to build URLs dynamically;
 */
public class UrlBuilder {

    static final String BASE_URL = "http://travelbuddy5.azurewebsites.net/api";
    private static final String CURRENT_POI = "/POI/GetRouteToNextPOI";
    private static final String TOURS = "/tours/gettours";
    private static final String START_TOUR = "/usertour/startusertour";
    private static final String END_TOUR = "/usertour/endusertour";
    private static final String ROUTE_POIS = "/poi/GetPOIsByTour?tourID=";
    private static final String NEXT_POI = "/POI/GetNextPOI";
    private static final String NEXT_POI_IN_RANGE = "/POI/IsNextPOIInRange";
    private static final String SET_POI_VISITED = "/POI/SetNextPOIAsVisited";
    private static final String DISTANCE_TO_NEXT_POI = "/POI/GetDistanceToNextPOI";
    private String url;

    static UrlBuilder anUrl() {
        return new UrlBuilder();
    }

    UrlBuilder currentRoute(LatLng location) {
        this.url = String.format(BASE_URL + CURRENT_POI + "?userID=%s&currentLatitude=%s&currentLongitude=%s",
                5, location.latitude, location.longitude);
        return this;
    }

    UrlBuilder allTours() {
        this.url = BASE_URL + TOURS;
        return this;
    }

    UrlBuilder startTour(LatLng location, Tour tour) {
        this.url = String.format(BASE_URL + START_TOUR + "?userID=%s&tourID=%s&currentLatitude=%s&currentLongitude=%s",
                5, tour.getId(), location.latitude, location.longitude);
        return this;
    }

    UrlBuilder poisOfTour(Tour tour) {
        this.url = BASE_URL + ROUTE_POIS + tour.getId();
        return this;
    }

    UrlBuilder nextPOI() {
        this.url = String.format(BASE_URL + NEXT_POI + "?userID=%s", 5);
        return this;
    }

    UrlBuilder poiInReach(LatLng location) {
        this.url = String.format(BASE_URL + NEXT_POI_IN_RANGE + "?userID=%s&latitude=%s&longitude=%s&allowedDistance=50",
                5, location.latitude, location.longitude);
        return this;
    }

    UrlBuilder distanceToNextPoi(LatLng location) {
        this.url = String.format(BASE_URL + DISTANCE_TO_NEXT_POI + "?userID=%s&latitude=%s&longitude=%s",
                5, location.latitude, location.longitude);
        return this;
    }

    UrlBuilder setCurrentPoiVisited() {
        this.url = String.format(BASE_URL + SET_POI_VISITED + "?userID=%s", 5);
        return this;
    }

    UrlBuilder endTour() {
        this.url = String.format(BASE_URL + END_TOUR + "?userID=%s", 5);
        return this;
    }

    public String build() {
        return url;
    }
}
