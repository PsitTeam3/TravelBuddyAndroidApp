package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TourOverview {
    private GoogleMap mMap;
    private List<Poi> pois;

    public TourOverview() {
        this.pois = new ArrayList<>();
    }

    /**
     * This draws the preview of a tour with markers representing the POIs.
     * Is zooms the camera so that all POIs are visible with a certain padding
     * to the borders.
     * @return Builder pattern
     */
    public TourOverview draw() {
        List<MarkerOptions> markerOptions = pois.stream()
                .map(cur -> new MarkerOptions().position(cur.getLatLng()).title(cur.getDescription()))
                .collect(Collectors.toList());
        if (mMap != null) {
            markerOptions.forEach(mMap::addMarker);
        }
        if (!pois.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            pois.forEach(cur -> builder.include(cur.getLatLng()));
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 180));
        }
        return this;
    }

    public TourOverview initMap(GoogleMap googleMap) {
        this.mMap = googleMap;
        return this;
    }

    public TourOverview setPois(List<Poi> pois) {
        this.pois = pois;
        return this;
    }
}
