package gr.rambou.s2car;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AdvertDetailActivity extends AppCompatActivity {

    private Intent i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
}
