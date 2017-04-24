package group3.psit3.zhaw.ch.travelbuddy.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import group3.psit3.zhaw.ch.travelbuddy.R;
import group3.psit3.zhaw.ch.travelbuddy.model.*;
import group3.psit3.zhaw.ch.travelbuddy.util.RequestQueuer;

import java.util.Arrays;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class TourActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnMapReadyCallback {
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = TourActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private Map mMap;
    private Route mRoute;
    private Progress mProgress;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mProgress = new Progress();
        setContentView(R.layout.route);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        //noinspection MissingPermission
        mLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
        startLocationUpdates(createLocationRequest());

        Tour tour = (Tour) getIntent().getSerializableExtra("group3.psit3.zhaw.ch.travelbuddy.model.Tour");
        RequestQueuer.aRequest().queueStartTour(TAG, tour, mLocation, this::onReceiveCurrentRoute);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProgress.add(imageBitmap);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION && Arrays.equals(grantResults, new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED})) {
            //noinspection MissingPermission
            FusedLocationApi.getLastLocation(mGoogleApiClient);
            startLocationUpdates(createLocationRequest());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.updatePosition(location);
        RequestQueuer.aRequest().queueCurrentRoute(TAG, location, this::onReceiveCurrentRoute);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = new Map(googleMap).drawRoute(mRoute);
    }

    protected void startLocationUpdates(LocationRequest locationRequest) {
        //noinspection MissingPermission
        FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, locationRequest, this);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private void onReceiveCurrentRoute(Route route) {
        this.mRoute = route;
        mMap.drawRoute(route);

        if (mRoute.isInPoiReach()) {
            dispatchTakePictureIntent();
            RequestQueuer.aRequest().queueIsPhotoValid(TAG, mLocation, this::onReceivePhotoValidation);
        }
    }

    private void onReceivePhotoValidation(PhotoStatus status) {
        if (status.equals(PhotoStatus.OK)) {
            // TODO show some dialog to confirm photo
        } else {
            dispatchTakePictureIntent();
        }
    }

    private boolean hasLocationPermission() {
        return (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // TODO
    }

    @Override
    public void onConnectionSuspended(int i) {
        // TODO
    }
}
