package gr.rambou.s2car;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.soundcloud.android.crop.Crop;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    private ImageView avatar;
    private EditText edit_name;
    private EditText edit_surname;
    private EditText edit_mail;

    private TextView name;
    private TextView username;
    private TextView surname;
    private TextView email;
    private TextView phone;

    private FABRevealLayout fabRevealLayout;
    private Button btn_save;
    private FragmentTransaction fragmentTransaction;
    private LoadingFragment f;
    private FragmentManager fm;
    private ParseUser currentUser;
    private Switch showPhone;
    private String Phone = null;

    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = ParseUser.getCurrentUser();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(currentUser.getUsername());

        avatar = (ImageView) findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });
        ParseFile imageFile = (ParseFile) currentUser.get("Avatar");
        imageFile.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // data has the bytes for the image
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Glide.with(ProfileActivity.this).load(data).centerCrop().into(avatar);
                } else {
                    // something went wrong
                    e.printStackTrace();
                }
            }
        });

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_surname = (EditText) findViewById(R.id.edit_surname);
        edit_mail = (EditText) findViewById(R.id.edit_email);
        name = (TextView) findViewById(R.id.name);
        surname = (TextView) findViewById(R.id.surname);
        email = (TextView) findViewById(R.id.email);
        username = (TextView) findViewById(R.id.username);
        phone = (TextView) findViewById(R.id.phone);
        showPhone = (Switch) findViewById(R.id.showPhone);

        setTextFields();

        fabRevealLayout = (FABRevealLayout) findViewById(R.id.fab_reveal_layout);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_btnSave();
            }
        });
        configureFABReveal(fabRevealLayout);

        showPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    Phone = tm.getLine1Number();
                } else {
                    showPhone.setChecked(false);
                    phone = null;
                }
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

    private void onClick_btnSave() {
        // Retrieve the text entered from the EditText
        final String name = edit_name.getText().toString();
        final String surname = edit_surname.getText().toString();
        final String email = edit_mail.getText().toString();

        // Locate the image in res > drawable-hdpi
        Bitmap bitmap;
        try {
            bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        } catch (Exception e) {
            bitmap = ((GlideBitmapDrawable) avatar.getDrawable()).getBitmap();
        }

        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        final ParseFile file = new ParseFile("avatar.png", image);


        // Force user to fill up the form
        if (email.equals("") || name.equals("")) {

        } else {
            f = new LoadingFragment();
            Bundle b = new Bundle();
            b.putString("message", getString(R.string.updating));
            f.setArguments(b);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.main_content, f);
            fragmentTransaction.commit();

            // Upload the image into Parse Cloud
            file.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    fm.beginTransaction().remove(f).commit();
                    if (e == null) {
                        // edit user data into Parse.com Data Storage
                        currentUser.setUsername(email);
                        currentUser.setEmail(email);
                        currentUser.put("Name", name);
                        currentUser.put("Surname", surname);
                        currentUser.put("Phone", Phone);
                        currentUser.put("Avatar", file);
                        currentUser.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // on successfull update
                                    setTextFields();
                                    fabRevealLayout.revealMainView();

                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.main_content), "Successfully updated.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(R.id.main_content), "Update failed", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.main_content), "Update failed", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    private void configureFABReveal(FABRevealLayout fabRevealLayout) {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                edit = false;
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                NestedScrollView nsv = (NestedScrollView) findViewById(R.id.secondary_view);
                nsv.setBackgroundColor(Color.WHITE);
                edit = true;
            }
        });
    }

    private void setTextFields() {
        edit_name.setText((String) currentUser.get("Name"));
        edit_surname.setText((String) currentUser.get("Surname"));
        edit_mail.setText(currentUser.getEmail());
        name.setText((String) currentUser.get("Name"));
        surname.setText((String) currentUser.get("Surname"));
        email.setHint(currentUser.getEmail());
        username.setText(currentUser.getUsername());

        // Έλεγχος για το αν ο χρήστης ενεργοποίησε την εμφάνιση του κινητού του
        if (currentUser.get("Phone") != null) {
            if (currentUser.get("Phone").equals(null)) {
                showPhone.setChecked(false);
                phone.setVisibility(View.INVISIBLE);
            } else {
                showPhone.setChecked(true);
                phone.setVisibility(View.VISIBLE);
                phone.setText((String) currentUser.get("Phone"));
            }
        } else {
            phone.setVisibility(View.INVISIBLE);
            showPhone.setChecked(false);
        }
    }

    private void setImage() {
        if (edit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Επέλεξε φωτογραφία από")
                    .setPositiveButton("Κάμερα", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, 1888);
                        }
                    })
                    .setNegativeButton("Βιβλιοθήκη", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Crop.pickImage(ProfileActivity.this);
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        }

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
        Log.d("[IMAGE]", "new image loaded" + source);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            // Πρέπει να γίνει force για redraw διότι έχει το ίδιο path και δεν γίνεται από μόνο του
            avatar.setImageDrawable(null);
            avatar.setImageURI(Crop.getOutput(result));
            Log.d("[IMAGE]", "new image loaded" + Crop.getOutput(result).getEncodedPath());
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
