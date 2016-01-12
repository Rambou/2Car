package gr.rambou.s2car;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.channguyen.rsv.RangeSliderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

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
                search(request.getText().toString(), range.getRangeCount());
            }
        });
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Advert");
        query.whereContains("VehicleDescription", name);
        //query.whereEqualTo("playerName", "");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> AdvertList, ParseException e) {
                if (e == null) {
                    Log.d("Adverts", "Retrieved " + AdvertList.size() + " scores" + AdvertList.get(0));
                } else {
                    Log.d("Adverts", "Error: " + e.getMessage());
                }
            }
        });
    }
}
