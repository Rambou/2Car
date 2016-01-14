package gr.rambou.s2car;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Αναζήτηση");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView request = (TextView) findViewById(R.id.search_text);
                RangeSliderView range = (RangeSliderView) findViewById(R.id.rsv_small);

                search(request.getText().toString(), (int) (range.getSliderRadiusPercent() * 10));
            }
        });

        initlocation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void search(String name, int radius) {
        Log.d("SEARCH", "Searching for: " + name + " and Radius: " + radius);

        ParseGeoPoint userLocation = getGeoPointFromLocation();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Advert");
        query.whereContains("VehicleDescription", name);
        query.whereWithinKilometers("coords", userLocation, radius);
        Log.d("RAIDUS GPS", userLocation.toString() + radius);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> AdvertList, ParseException e) {
                if (e == null) {
                    if (!AdvertList.isEmpty())
                        Log.d("Adverts", "Retrieved " + AdvertList.size() + " scores" + AdvertList.get(0));
                } else {
                    Log.d("Adverts", "Error: " + e.getMessage());
                }
            }
        });
    }

    private ParseGeoPoint getGeoPointFromLocation() {
        ParseGeoPoint pgp = new ParseGeoPoint();
        if (location != null) {
            // Δημιουργία του GeoPoint στο parse
            pgp.setLatitude(location.getLatitude());
            pgp.setLongitude(location.getLongitude());

            Log.d("lol", String.valueOf(pgp.getLatitude()) + String.valueOf(pgp.getLongitude()));
        }

        return pgp;
    }

    private void initlocation() {
        LocationManager locationManager;
        String TAG = "LOCATION";
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //makeUseOfNewLocation(location);
                Log.v("LOCATION", "Location updated to " + location.toString());
                SearchActivity.this.location = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission problem");
            return;
        } else {
            // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                SearchActivity.this.location = loc;
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
