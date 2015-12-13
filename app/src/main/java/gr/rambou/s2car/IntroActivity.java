package gr.rambou.s2car;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro2 {

    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {
        // Instead of fragments, you can also use our default slide
        askForPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE}, 2);
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle1), getString(R.string.IntroText1), R.drawable.ic_vintage_car, ContextCompat.getColor(getApplicationContext(), R.color.intro1)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle2), getString(R.string.IntroText2), R.drawable.ic_car_extra, ContextCompat.getColor(getApplicationContext(), R.color.intro2)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle3), getString(R.string.IntroText3), R.drawable.ic_bike, ContextCompat.getColor(getApplicationContext(), R.color.intro3)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle4), getString(R.string.IntroText4), R.drawable.ic_city, ContextCompat.getColor(getApplicationContext(), R.color.intro4)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle5), getString(R.string.IntroText5), R.drawable.ic_village, ContextCompat.getColor(getApplicationContext(), R.color.intro5)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle6), getString(R.string.IntroText6), R.drawable.ic_shopping, ContextCompat.getColor(getApplicationContext(), R.color.intro6)));

        // Hide ActionBar and Statusbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        showStatusBar(false);

        // Turn vibration on and set intensity
        setVibrate(true);
        setVibrateIntensity(30);
    }


    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        // Set a Key-Value to indicate that intro has been seen
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.PrefName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.IntroSeen), true);
        editor.commit();

        LoadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public void LoadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
