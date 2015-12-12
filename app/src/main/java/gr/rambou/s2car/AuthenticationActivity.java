package gr.rambou.s2car;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationActivity extends AppCompatActivity {

    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};
    private EditText _emailText;
    private EditText _passwordText;
    private Button _loginButton;
    private Button _registerButton;
    private FloatingActionButton _fbLogin;
    private FloatingActionButton _gpLogin;
    private TextView _signupLink;
    private ImageView _avatar;
    private EditText _regName;
    private EditText _regSurname;
    private EditText _regMail;
    private EditText _regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParallaxImageView backgr = (ParallaxImageView) findViewById(R.id.backgr);
        backgr.setMargins(0, 400);
        backgr.setMultipliers(0f, 2.5f);

        // Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        _loginButton = (Button) findViewById(R.id.btn_login);
        _fbLogin = (FloatingActionButton) findViewById(R.id.fblogin);
        _gpLogin = (FloatingActionButton) findViewById(R.id.gplogin);
        _registerButton = (Button) findViewById(R.id.btn_signup);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _avatar = (ImageView) findViewById(R.id.profile_image);

        _regName = (EditText) findViewById(R.id.register_name);
        _regSurname = (EditText) findViewById(R.id.register_surname);
        _regMail = (EditText) findViewById(R.id.register_email);
        _regPassword = (EditText) findViewById(R.id.register_password);

        _avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1888);
            }
        });
        _fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ParseFacebookUtils.logInWithReadPermissionsInBackground(AuthenticationActivity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            //getUserDetailsFromFB();
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            //getUserDetailsFromParse();
                        }
                    }
                });*/
            }
        });
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadRegister();
            }
        });
        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            _avatar.setImageBitmap(photo);
        }
    }

    public void login() {
        Log.d("LOGIN", "Login");

        if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.AuthenticationLayout), "No internet Connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            login();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.YELLOW);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();

            return;
        }

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        /*final ProgressDialog progressDialog = new ProgressDialog(AuthenticationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();*/

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // Send data to Parse.com for verification
        ParseUser.logInInBackground(email, password,
                new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated
                            onLoginSuccess();
                            finish();
                        } else {
                            //progressDialog.dismiss();
                            onLoginFailed();
                        }
                    }
                });

    }

    public void LoadRegister() {
        SlidingUpPanelLayout sPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        sPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }


    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onLoginFailed() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.AuthenticationLayout), "Login failed", Snackbar.LENGTH_LONG);

        snackbar.show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void register() {
        // Retrieve the text entered from the EditText
        final String name = _regName.getText().toString();
        final String surname = _regSurname.getText().toString();
        final String email = _regMail.getText().toString();
        final String password = _regPassword.getText().toString();
        // Locate the image in res > drawable-hdpi
        Bitmap bitmap = ((BitmapDrawable) _avatar.getDrawable()).getBitmap();
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        final ParseFile file = new ParseFile("avatar.png", image);


        // Force user to fill up the form
        if (email.equals("") && password.equals("")) {

        } else {
            // Upload the image into Parse Cloud
            file.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // Save new user data into Parse.com Data Storage
                        ParseUser user = new ParseUser();
                        user.setUsername(email);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.put("Name", name);
                        user.put("Surname", surname);
                        user.put("Avatar", file);
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.AuthenticationLayout), "Successfully Signed up, please log in.", Snackbar.LENGTH_LONG);

                                    snackbar.show();
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.AuthenticationLayout), "Registration failed", Snackbar.LENGTH_LONG);

                                    snackbar.show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.AuthenticationLayout), "Registration failed", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    private void parseReg() {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}