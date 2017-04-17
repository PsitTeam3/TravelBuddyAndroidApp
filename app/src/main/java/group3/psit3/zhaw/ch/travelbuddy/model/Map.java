package group3.psit3.zhaw.ch.travelbuddy.model;

import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

public class Map {
    private static final String TAG = Map.class.getSimpleName();

    private final GoogleMap mMap;

    public Map(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updatePosition(Location location) {
        RequestQueuer.aRequest().queueCurrentRoute(TAG, location, this::drawRoute);
        updateMarker(location);
    }

    private void updateMarker(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(position).title("Your position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
    }

    public Map drawRoute(Route route) {
        if (route != null) {
            mMap.addPolyline(new PolylineOptions().addAll(route.getPoints()));
        }
        return this;
    }
}
