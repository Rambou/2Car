package gr.rambou.s2car;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import gr.rambou.s2car.utils.mapUtils;

public class AdvertDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    Location location = new Location("IpRovide");
    private Intent i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.CA_map);
        mapFragment.getMapAsync(this);

        i = getIntent();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(i.getStringExtra("VhlAdDescription"));

        loadBackdrop();

        TextView VhlType = (TextView) findViewById(R.id.VhlType);
        TextView Brand = (TextView) findViewById(R.id.Brand);
        TextView VhlModel = (TextView) findViewById(R.id.VhlModel);
        TextView VhlYear = (TextView) findViewById(R.id.VhlYear);
        TextView Vhlkm = (TextView) findViewById(R.id.Vhlkm);
        TextView Vhlcc = (TextView) findViewById(R.id.Vhlcc);
        TextView Vhlbhp = (TextView) findViewById(R.id.Vhlbhp);
        TextView SpnFuel = (TextView) findViewById(R.id.SpnFuel);
        TextView SpnAdType = (TextView) findViewById(R.id.SpnAdType);
        TextView VhlPrice = (TextView) findViewById(R.id.VhlPrice);
        TextView VhlAdDescription = (TextView) findViewById(R.id.VhlAdDescription);
        TextView contact = (TextView) findViewById(R.id.contact);
        TextView mail = (TextView) findViewById(R.id.mail);
        TextView phone = (TextView) findViewById(R.id.phone);

        VhlType.setText(i.getStringExtra("VhlType"));
        Brand.setText(i.getStringExtra("Brand"));
        VhlModel.setText(i.getStringExtra("VhlModel"));
        VhlYear.setText(i.getStringExtra("VhlYear"));
        Vhlkm.setText(i.getStringExtra("Vhlkm"));
        Vhlcc.setText(i.getStringExtra("Vhlcc"));
        Vhlbhp.setText(i.getStringExtra("Vhlbhp"));
        SpnFuel.setText(i.getStringExtra("SpnFuel"));
        SpnAdType.setText(i.getStringExtra("SpnAdType"));
        VhlPrice.setText(i.getStringExtra("VhlPrice"));
        VhlAdDescription.setText(i.getStringExtra("VhlAdDescription"));
        contact.setText(i.getStringExtra("contact"));
        phone.setText(i.getStringExtra("phone"));
        mail.setText(i.getStringExtra("email"));

        location.setLatitude(Double.parseDouble(i.getStringExtra("Latitude")));
        location.setLongitude(Double.parseDouble(i.getStringExtra("Longitude")));
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(i.getByteArrayExtra("Photo")).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        mapUtils.refreshLocation(location, googleMap, AdvertDetailActivity.this.getApplicationContext());
    }
}
