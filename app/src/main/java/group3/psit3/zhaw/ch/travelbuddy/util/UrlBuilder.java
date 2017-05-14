package group3.psit3.zhaw.ch.travelbuddy.util;

import com.google.android.gms.maps.model.LatLng;
import group3.psit3.zhaw.ch.travelbuddy.model.Tour;

public class UrlBuilder {

    public static final String BASE_URL = "http://travelbuddy5.azurewebsites.net/api";
    private static final String CURRENT_POI = "/POI/GetRouteToNextPOI";
    private static final String TOURS = "/tours/gettours";
    private static final String START_TOUR = "/usertour/startusertour";
    private static final String ROUTE_POIS = "/poi/GetPOIsByTour?tourID=";
    private static final String NEXT_POI = "/POI/GetNextPOI";
    private static final String NEXT_POI_IN_RANGE = "/POI/IsNextPOIInRange";
    private static final String DISTANCE_TO_NEXT_POI = "/POI/GetDistanceToNextPOI";
    private String url;

    public static UrlBuilder anUrl() {
        return new UrlBuilder();
    }

    public UrlBuilder currentRoute(LatLng location) {
        this.url = String.format(BASE_URL + CURRENT_POI + "?userID=%s&currentLatitude=%s&currentLongitude=%s",
                5, location.latitude, location.longitude);
        return this;
    }

    public UrlBuilder allTours() {
        this.url = BASE_URL + TOURS;
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

    public UrlBuilder nextPOI(){
        this.url = String.format(BASE_URL + NEXT_POI + "?userID=%s",5);
        return this;
    }

    public UrlBuilder poiInReach(LatLng location) {
        this.url = String.format(BASE_URL + NEXT_POI_IN_RANGE + "?userID=%s&latitude=%s&longitude=%s&allowedDistance=50",
                5, location.latitude, location.longitude);
        return this;
    }

    public String build() {
        return url;
    }

    public UrlBuilder distanceToNextPoi(LatLng location) {
        this.url = String.format(BASE_URL + DISTANCE_TO_NEXT_POI + "?userID=%s&latitude=%s&longitude=%s",
                5, location.latitude, location.longitude);
        return this;
    }
}
