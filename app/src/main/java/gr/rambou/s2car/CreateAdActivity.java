package gr.rambou.s2car;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.parse.ParseUser;

public class CreateAdActivity extends AppCompatActivity {

    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

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
}
