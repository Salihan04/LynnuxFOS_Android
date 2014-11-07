package com.lynnux.lynnuxfos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.lynnux.lynnuxfos.utility.Utility;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import timber.log.Timber;


public class reportIncident extends Activity{
    private static final String COUNT = "count";
//    private final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/LynnuxFOS";
//    File newDir = new File(dir);
    int count,TAKE_PHOTO_CODE = 0;
    ImageView incidentImage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEdit;
    EditText incidentNameText,descriptionText,priorityText;
    String incidentName,description,priority;
    Bitmap photo;

    FOSDialog dialog;

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;  //in meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;  //in milliseconds

    protected LocationManager locationManager;
    private ParseGeoPoint location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        spEdit = sharedPreferences.edit();
        count = sharedPreferences.getInt(COUNT,0);

        incidentNameText = (EditText) findViewById(R.id.incidentNameText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);
        priorityText = (EditText) findViewById(R.id.priorityText);

        //get location service
        locationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());

        //        newDir.mkdirs();
        incidentImage = (ImageView) findViewById(R.id.incidentImageBtn);
        incidentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });

        //Preparing incident image for uploading to DB
        BitmapDrawable bitmapDrawable = (BitmapDrawable) incidentImage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
//        final Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        final ParseFile pFile = new ParseFile("data.jpg", imageInByte);

        ImageButton submitBtn = (ImageButton) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    incidentName = (incidentNameText.getText()).toString();
                    description = (descriptionText.getText()).toString();
                    priority = (priorityText.getText()).toString();

                    location = getCurrentLocation();
                    if (location == null) {
                        Timber.d("location is null");
                        //location hardcoded to NTU by default for the demo
                        location = new ParseGeoPoint(1.34831, 103.683135);
                    }

                    Timber.d("Latitude: " + location.getLatitude(), ", Longitude: " + location.getLongitude());
                    Utility.reportIncident(description, location, incidentName, Integer.parseInt(priority),
                            "new", pFile, getApplicationContext());

                    reportIncident.this.finish();
                } catch (Exception e) {
                    Timber.e("Report incident failed");
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            photo =(Bitmap) data.getExtras().get("data");
            incidentImage.setImageBitmap(photo);
//            spEdit.putInt(COUNT,count);
//            spEdit.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_incident, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLocation(double lat, double lng) {
        this.location = new ParseGeoPoint(lat, lng);
    }

    protected ParseGeoPoint getCurrentLocation() {
        Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        if(loc == null) {
            Toast.makeText(getApplicationContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();

            return null;
        }

        Timber.d("Latitude: " + loc.getLatitude());
        Timber.d("Longitude: " + loc.getLongitude());

        return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Timber.d("Latitude: " + location.getLatitude());
            Timber.d("Longitude: " + location.getLongitude());
            setLocation(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "Provider status changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Provider enabled by user. GPS turned on", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Provider disabled by user. GPS turned off", Toast.LENGTH_SHORT).show();
        }
    }
}
