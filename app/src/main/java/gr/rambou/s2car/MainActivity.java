package gr.rambou.s2car;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPref;
    AppCompatActivity mainAct = this;
    Adapter adapter;
    ViewPager viewPager;
    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if intro has been opened.
        sharedPref = this.getSharedPreferences(getString(R.string.PrefName), Context.MODE_PRIVATE);
        Boolean IntroSeen = sharedPref.getBoolean(getString(R.string.IntroSeen), false);
        if (!IntroSeen) {
            Intent i = new Intent(this, IntroActivity.class);
            finish();
            startActivity(i);
            return;
        }

        //Check if user is logged in
        currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(this, AuthenticationActivity.class);
            this.finish();
            startActivity(i);
            return;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateAdActivity.class);
                startActivity(i);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View drawerHeader = navigationView.getHeaderView(0);
        TextView name = (TextView) drawerHeader.findViewById(R.id.userFullName);
        TextView email = (TextView) drawerHeader.findViewById(R.id.userEmail);
        final ImageView avatar = (ImageView) drawerHeader.findViewById(R.id.avatar);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    List<Advert> listFavorites = null;
                    try {
                        ParseQuery<Advert> qryFavorites = new ParseQuery<Advert>("Advert");
                        qryFavorites.fromLocalDatastore();
                        listFavorites = qryFavorites.find();

                        AdvertListFragment mSomeFragment = (AdvertListFragment) adapter.getItem(2);
                        mSomeFragment.refreshRecyclerView(listFavorites);
                        Log.v("refreshgui", "refreshed");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        name.setText(currentUser.get("Name") + " " + currentUser.get("Surname"));
        email.setText(currentUser.getEmail());

        ParseFile imageFile = (ParseFile) currentUser.get("Avatar");
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // data has the bytes for the image
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    avatar.setImageBitmap(bmp);
                } else {
                    // something went wrong
                    e.printStackTrace();
                }
            }
        });

        if (BuildConfig.DEBUG_CREATE_AD) {
            fab.performClick();
        }
        if (BuildConfig.DEBUG) {
            ParseQuery<Advert> query = new ParseQuery<Advert>("Advert");
            query.findInBackground(new FindCallback<Advert>() {
                public void done(List<Advert> allAds, ParseException e) {
                    for (int i = 0; i < allAds.size(); i++) {
                        allAds.get(i).unpinInBackground();
                    }
                }
            });
        }
    }

    private void setupViewPager(ViewPager viewPager) {

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

                    ParseQuery<Advert> query = new ParseQuery<Advert>("Advert");
                    List<Advert> listFavorites = null;
                    try {
                        ParseQuery<Advert> qryFavorites = new ParseQuery<Advert>("Advert");
                        qryFavorites.fromLocalDatastore();
                        listFavorites = qryFavorites.find();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                    adapter = new Adapter(mainAct.getSupportFragmentManager());
                    adapter.addFragment(new AdvertListFragment(listCars), "Αυτοκίνητα");
                    adapter.addFragment(new AdvertListFragment(listBikes), "Μηχανές");
                    adapter.addFragment(new AdvertListFragment(listFavorites), "Αγαπημένα");
                    viewPager.setAdapter(adapter);
                } else {

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create) {
            Intent i = new Intent(this, CreateAdActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_auto) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_motocycle) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_Favorites) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.nav_profil) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            ParseUser.getCurrentUser().logOut();
            Intent i = new Intent(this, AuthenticationActivity.class);
            startActivity(i);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
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
