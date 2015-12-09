package gr.rambou.s2car;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro2 {

    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {
        // Instead of fragments, you can also use our default slide
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle1), getString(R.string.IntroText1), R.drawable.ic_logo, Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle2), "We need to use the camera.\n", R.drawable.ic_logo, Color.parseColor("#673AB7")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle3), "We need to save stuff on your device. \n", R.drawable.ic_logo, Color.parseColor("#9C27B0")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.IntroTitle4), "Enjoy our app! \n", R.drawable.ic_logo, Color.parseColor("#E91E63")));
        addSlide(AppIntroFragment.newInstance("Location", "One more permission! We need to locate your device. \n", R.drawable.ic_logo, Color.parseColor("#FF9800")));
        addSlide(AppIntroFragment.newInstance("All set!", "All done! \n", R.drawable.ic_logo, Color.parseColor("#4CAF50")));

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
