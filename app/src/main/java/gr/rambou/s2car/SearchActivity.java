package gr.rambou.s2car;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Location location;
    private AppCompatActivity me = this;
    private Adapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Αναζήτηση");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        final SeekBar range = (SeekBar) findViewById(R.id.rsv_small);
        final TextView kmtxt = (TextView) findViewById(R.id.km);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView request = (TextView) findViewById(R.id.search_text);

                search(request.getText().toString(), range.getProgress());
            }
        });

        range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                kmtxt.setText(seekBar.getProgress() + " km");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                kmtxt.setText(progress + " km");
            }
        });
        // Setup viepager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Location Listeners
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
        ParseQuery<Advert> query = ParseQuery.getQuery("Advert");
        query.whereContains("VehicleDescription", name);
        query.whereWithinKilometers("coords", userLocation, radius);
        Log.d("RAIDUS GPS", userLocation.toString() + radius);
        query.findInBackground(new FindCallback<Advert>() {
            public void done(List<Advert> AdvertList, ParseException e) {
                if (e == null) {
                    if (!AdvertList.isEmpty()) {
                        Log.d("Adverts", "Retrieved " + AdvertList.size() + " | " + AdvertList.get(0));

                        adapter = new Adapter(me.getSupportFragmentManager());
                        adapter.addFragment(new AdvertListFragment(AdvertList), "Αποτελέσματα");
                        viewPager.setAdapter(adapter);
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);
                    } else {
                        Snackbar.make(me.getCurrentFocus(), "Δεν βρέθηκαν αποτελέσματα", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("Adverts", "Error: " + e.getMessage());
                    Snackbar.make(me.getCurrentFocus(), "Υπήρξε πρόβλημα ξαναπροσπαθήστε", Snackbar.LENGTH_LONG).show();
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

    private void setupViewPager(final ViewPager viewPager) {

        ParseQuery<Advert> query = new ParseQuery<Advert>("Advert");
        query.findInBackground(new FindCallback<Advert>() {
            public void done(List<Advert> allAds, ParseException e) {
                if (e == null) {
                    List<Advert> listCars = Lists.newArrayList(Collections2.filter(
                            allAds, new Predicate<Advert>() {
                                @Override
                                public boolean apply(Advert input) {
                                    return input.getVehicleType().equals("Car") ? true : false;
                                }
                            }));
                    List<Advert> listBikes = Lists.newArrayList(Collections2.filter(
                            allAds, new Predicate<Advert>() {
                                @Override
                                public boolean apply(Advert input) {
                                    return input.getVehicleType().equals("Bike") ? true : false;
                                }
                            }));

                    List<Advert> listFavorites = null;
                    try {
                        ParseQuery<Advert> qryFavorites = new ParseQuery<Advert>("Advert");
                        qryFavorites.fromLocalDatastore();
                        listFavorites = qryFavorites.find();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<AdvertListFragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(AdvertListFragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public AdvertListFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
