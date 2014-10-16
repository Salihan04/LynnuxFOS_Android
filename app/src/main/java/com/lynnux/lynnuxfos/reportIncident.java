package com.lynnux.lynnuxfos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;


public class reportIncident extends Activity {
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
        ImageButton submitBtn = (ImageButton) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incidentName = (incidentNameText.getText()).toString();
                description = (descriptionText.getText()).toString();
                priority = (priorityText.getText()).toString();
                reportIncident.this.finish();
            }
        });


//        newDir.mkdirs();
        incidentImage = (ImageView) findViewById(R.id.incidentImageBtn);
        incidentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
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
}
