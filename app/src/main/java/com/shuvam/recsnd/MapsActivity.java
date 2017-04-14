package com.shuvam.recsnd;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shuvam Ghosh on 4/14/2017.
 */

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener,GoogleApiClient.OnConnectionFailedListener {

    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    int PLACE_PICKER_REQUEST;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        init();
        setinit();
    }

    private void setinit() {

        mapFragment.getMapAsync(this);

        PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<QueryResponse> call = apiService.getHospitals("12.9165,79.1325","500","hospital","AIzaSyA0XTLUSzTedlIPcGiwarorve4YM7P5GLk");
                call.enqueue(new Callback<QueryResponse>() {
                    @Override
                    public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {


                     //   Log.d("First Response Lat",""+response.body().getRes().get(0).getGeo().getLoc().getLatitude());


                        /*for (int i=0;i<5;i++)
                        {

                            LatLng latLng=new LatLng(response.body().getRes().get(i).getGeo().getLoc().getLatitude(),response.body().getRes().get(i).getGeo().getLoc().getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,19);
                            googleMap.animateCamera(cameraUpdate);
                            //options.title(busStops.getValue().get(i).getDescription());
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.placeholdernew);
                            options.icon(icon);
                            googleMap.addMarker(options);

                        }*/




                    }

                    @Override
                    public void onFailure(Call<QueryResponse> call, Throwable t) {

                    }
                });


            }
        });

       /* LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();

        private final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        };*/


    }

    private void init() {



        fab = (FloatingActionButton)findViewById(R.id.fabGetHospitals);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);


       //LatLng latLng=new LatLng(13.082680,80.270718);
       // MarkerOptions options = new MarkerOptions().position(latLng);
      //  CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,19);
        //googleMap.animateCamera(cameraUpdate);
      //  options.title("Title");
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.placeholdernew);
      //  options.icon(icon);
      //  googleMap.addMarker(options);


        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Setting latitude and longitude in the TextView tv_location
       // tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
