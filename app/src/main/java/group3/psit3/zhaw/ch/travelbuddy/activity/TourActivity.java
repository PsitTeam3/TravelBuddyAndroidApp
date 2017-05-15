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
import android.widget.ProgressBar;
import android.widget.TextView;
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

    private static final String TAG = TourActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TIME_SPENT_ON_ROUTE = "Time spent on route: ";
    private static final String DISTANCE_TO_NEXT_POINT = "Distance to next point: ";
    private static final int LOCATION_REQUEST_INTERVAL = 5000;
    private static final int MAX_LOCATION_REQUEST_INTERVAL = 500;

    private GoogleApiClient mGoogleApiClient;
    private Map mMap;
    private Poi mPoi;
    private Progress mProgress;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mProgress = new Progress();
        setContentView(R.layout.activity_tour);


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
        mProgress.setTour(tour);
        RequestQueuer.aRequest().queueStartTour(TAG, tour, mLocation, this::onReceiveCurrentPoi);
        RequestQueuer.aRequest().queueDistanceToNextPoi(TAG, mLocation, this::onDistanceToNextPoi);
    }

    private void onDistanceToNextPoi(Double distance) {
        mProgress.updateDistance(distance);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProgress.add(imageBitmap);
            mProgress.setStartDistance(null);
            if (mProgress.getBitmaps().size() >= 2) {
                viewSummary();
            }
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
        this.mLocation = location;
        RequestQueuer.aRequest().queueCurrentRoute(TAG, location, this::onReceiveCurrentRoute);
        RequestQueuer.aRequest().queueGetNextPoi(TAG, this::onReceiveCurrentPoi);
        RequestQueuer.aRequest().queueIsPoiInReach(TAG, mLocation, this::onIsPoiInReach);
        RequestQueuer.aRequest().queueDistanceToNextPoi(TAG, mLocation, this::onDistanceToNextPoi);
        updateProgressView(mProgress);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = new Map(googleMap).drawRoute(mPoi);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void onReceiveCurrentPoi(Poi poi) {
        this.mPoi = poi;
        mMap.drawRoute(poi);
    }

    private void onIsPoiInReach(Boolean isInReach) {
        if (isInReach) {
            if (mProgress.canTakePhoto()) {
                dispatchTakePictureIntent();
            }
        }
    }

    private void onReceiveCurrentRoute(RouteResponse routeResponse) {
        if (routeResponse.isFinished()) {
            RequestQueuer.aRequest().queueEndTour(TAG);
            viewSummary();
        }
        mMap.drawRoute(routeResponse.getRoute());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // falls through
    }

    @Override
    public void onConnectionSuspended(int i) {
        // falls through
    }

    private void updateProgressView(Progress progress) {
        TextView timeSpent = (TextView) findViewById(R.id.timeSpent);
        TextView distance = (TextView) findViewById(R.id.currentDistance);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        timeSpent.setText(TIME_SPENT_ON_ROUTE + progress.getTimeSpent());
        distance.setText(DISTANCE_TO_NEXT_POINT + progress.getCurrentDistance() + "m");
        progressBar.setProgress(progress.getProgress(), true);
    }

    private void startLocationUpdates(LocationRequest locationRequest) {
        //noinspection MissingPermission
        FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, locationRequest, this);
    }

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setFastestInterval(MAX_LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private boolean hasLocationPermission() {
        return (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void dispatchTakePictureIntent() {
        RequestQueuer.aRequest().queueSetCurrentPoiVisited(TAG);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void viewSummary() {
        Intent summaryIntent = new Intent(this, SummaryActivity.class);
        summaryIntent.putExtra("group3.psit3.zhaw.ch.travelbuddy.model.Progress", mProgress);
        startActivity(summaryIntent);
    }

}
