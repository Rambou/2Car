package gr.rambou.s2car.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * Created by konstantinos on 11/1/2016.
 */
public class mapUtils {

    static public void refreshLocation(Location location, GoogleMap googleMap, Context context) {
        googleMap.clear();
        // Add a marker in yourlocation and move the camera
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("You are here"));

        try {
            Geocoder geo = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(myLocation)
                        .title((addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName()))
                        .snippet(String.valueOf(location.getSpeed())))
                        .showInfoWindow();
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }
}
