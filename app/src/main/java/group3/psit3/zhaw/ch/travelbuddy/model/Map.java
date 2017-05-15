package group3.psit3.zhaw.ch.travelbuddy.model;

import android.location.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

import java.util.List;

public class Map {
    private static final String TAG = Map.class.getSimpleName();
    private static final float CAMERA_ZOOM = 17.0f;
    private static final String MARKER_TITLE = "Your position";

    private final GoogleMap mMap;
    private Marker mMarker;
    private Polyline mPolyline;

    public Map(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    /**
     * Dispatches a request to get current route, updates marker und re-paints the map.
     * @param location Current location of the user.
     */
    public void updatePosition(Location location) {
        RequestQueuer.aRequest().queueCurrentRoute(TAG, location, cur -> drawRoute(cur.getRoute()));
        updateMarker(location);
    }

    /**
     * Draws a list of coordinates to the map.
     *
     * @param route A list of coordinates.
     * @return Builder pattern.
     */
    public Map drawRoute(List<LatLng> route) {
        if (route != null) {
            if (mPolyline != null) {
                mPolyline.remove();
            }
            mPolyline = mMap.addPolyline(new PolylineOptions().addAll(route));
        }
        return this;
    }

    /**
     * Draws a list of coordinates to the map.
     *
     * @param poi Route of POI to draw.
     * @return Builder pattern.
     */
    public Map drawRoute(Poi poi) {
        if (poi != null) {
            if (mPolyline != null) {
                mPolyline.remove();
            }
            mMap.addPolyline(new PolylineOptions().addAll(poi.getRoute()));
        }
        return this;
    }

    private void updateMarker(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMarker != null) {
            mMarker.remove();
        }
        this.mMarker = mMap.addMarker(new MarkerOptions().position(position).title(MARKER_TITLE));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, CAMERA_ZOOM));
    }
}
