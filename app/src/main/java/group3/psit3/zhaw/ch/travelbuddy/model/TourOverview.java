package group3.psit3.zhaw.ch.travelbuddy.model;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

    public TourOverview draw() {
        List<MarkerOptions> markerOptions = pois.stream()
                .map(cur -> new MarkerOptions().position(cur.getLatLng()).title(cur.getDescription()))
                .collect(Collectors.toList());
        if (mMap != null) {
            markerOptions.forEach(mMap::addMarker);
        }
        if (pois.size() > 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pois.get(0).getLatLng(), 10.0f));
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
