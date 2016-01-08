package gr.rambou.s2car;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;

import java.util.List;
import java.util.Locale;

public class CreateAdActivity extends AppCompatActivity implements OnMapReadyCallback {


    private final String TAG = "LocationCheck";
    private ParseUser currentUser;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.CA_map);
        mapFragment.getMapAsync(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.Import));

        currentUser = ParseUser.getCurrentUser();

        Spinner spinnerAdType = (Spinner) findViewById(R.id.SpnAdType);
        ArrayAdapter<CharSequence> adapterAdType = ArrayAdapter.createFromResource(this,
                R.array.AdType_array, R.layout.create_ad_spinner);
        adapterAdType.setDropDownViewResource(R.layout.spinner_dropdown_item_ca);
        spinnerAdType.setAdapter(adapterAdType);

        Spinner spinnerFuel = (Spinner) findViewById(R.id.SpnFuel);
        ArrayAdapter<CharSequence> adapterFuel = ArrayAdapter.createFromResource(this,
                R.array.Fuel_array, R.layout.create_ad_spinner);
        adapterFuel.setDropDownViewResource(R.layout.spinner_dropdown_item_ca);
        spinnerFuel.setAdapter(adapterFuel);

        RadioButton rbtnCar = (RadioButton) findViewById(R.id.rbtnCar);
        rbtnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner spinnerBrand = (Spinner) findViewById(R.id.SpnBrand);
                ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getApplication(),
                        R.array.Car_brand_array, R.layout.create_ad_spinner);
                adapterBrand.setDropDownViewResource(R.layout.spinner_dropdown_item_ca);
                spinnerBrand.setAdapter(adapterBrand);
            }
        });

        RadioButton rbtnBike = (RadioButton) findViewById(R.id.rbtnBike);
        rbtnBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner spinnerBrand = (Spinner) findViewById(R.id.SpnBrand);
                ArrayAdapter<CharSequence> adapterBrand = ArrayAdapter.createFromResource(getApplication(),
                        R.array.Bike_brand_array, R.layout.create_ad_spinner);
                adapterBrand.setDropDownViewResource(R.layout.spinner_dropdown_item_ca);
                spinnerBrand.setAdapter(adapterBrand);
            }
        });

        if (BuildConfig.DEBUG) {
            NestedScrollView sv = (NestedScrollView) findViewById(R.id.AC_scrollview);
            sv.fullScroll(View.FOCUS_DOWN);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshLocation(Location location) {
        Toast.makeText(getBaseContext(), "Updated",
                Toast.LENGTH_SHORT).show();

        mMap.clear();
        // Add a marker in yourlocation and move the camera
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());

//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

        //region Get address
        try {
            Geocoder geo = new Geocoder(CreateAdActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                mMap.addMarker(new MarkerOptions().position(myLocation).title("You are here"));
            } else {
                if (addresses.size() > 0) {
                    mMap.addMarker(new MarkerOptions().position(myLocation)
                            .title((addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName()))
                            .snippet(String.valueOf(location.getSpeed())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
        //endregion
//        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager;

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //makeUseOfNewLocation(location);
                Log.v(TAG, "Location updated to " + location.toString());
                refreshLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(getBaseContext(), "Miracle",
                        Toast.LENGTH_SHORT).show();
            }

            public void onProviderEnabled(String provider) {
                Toast.makeText(getBaseContext(), "Don't cry",
                        Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                // TODO: 8/1/2016 remove toast messages
                Toast.makeText(getBaseContext(), "Cry",
                        Toast.LENGTH_SHORT).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.v(TAG, "Permission problem");
            return;
        } else {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                refreshLocation(loc);
                Log.v(TAG, "Got location");
            }
        }

        try {
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0f, locationListener);
        } catch (SecurityException ex) {
            Toast.makeText(getBaseContext(), "Permission Problem",
                    Toast.LENGTH_SHORT).show();
            Log.v(TAG, "Permission problem");
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0f, locationListener);

        } catch (SecurityException ex) {
            Toast.makeText(getBaseContext(), "Permission Problem",
                    Toast.LENGTH_SHORT).show();
            Log.v(TAG, "Permission problem");
        }
    }
}
