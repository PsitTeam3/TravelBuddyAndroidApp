package group3.psit3.zhaw.ch.travelbuddy.model;

import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

import java.util.List;

public class Map {
    private static final String TAG = Map.class.getSimpleName();

    private final GoogleMap mMap;
    private Marker mMarker;
    private Polyline mPolyline;

    public Map(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updatePosition(Location location) {
        RequestQueuer.aRequest().queueCurrentRoute(TAG, location, this::drawRoute, null);
        updateMarker(location);
    }

    private void updateMarker(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMarker != null) {
            mMarker.remove();
        }
        this.mMarker = mMap.addMarker(new MarkerOptions().position(position).title("Your position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
    }

    public Map drawRoute(List<LatLng> route) {
        if (route != null) {
            if (mPolyline != null) {
                mPolyline.remove();
            }
            mPolyline = mMap.addPolyline(new PolylineOptions().addAll(route));
        }
        return this;
    }

    public Map drawRoute(Poi poi) {
        if (poi != null) {
            if (mPolyline != null) {
                mPolyline.remove();
            }
            mMap.addPolyline(new PolylineOptions().addAll(poi.getRoute()));
        }
        return this;
    }
}
