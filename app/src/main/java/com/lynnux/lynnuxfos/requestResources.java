package com.lynnux.lynnuxfos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class requestResources extends Activity {
    private static final String COUNT = "count";
    int count,TAKE_PHOTO_CODE = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEdit;
    ImageView requestResourcesBtn;
    EditText incidentNameText,descriptionText,priorityText,messageText;
    String incidentName,description,priority,message;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_resources);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        spEdit = sharedPreferences.edit();
        count = sharedPreferences.getInt(COUNT,0);

        incidentNameText = (EditText) findViewById(R.id.incidentNameText_RR);
        descriptionText = (EditText) findViewById(R.id.descriptionText_RR);
        priorityText = (EditText) findViewById(R.id.priorityText_RR);
        messageText = (EditText) findViewById(R.id.messageText_RR);
        ImageButton submitRequestBtn = (ImageButton) findViewById(R.id.submitRequestBtn);
        submitRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incidentName = (incidentNameText.getText()).toString();
                description = (descriptionText.getText()).toString();
                priority = (priorityText.getText()).toString();
                message = (messageText.getText()).toString();
                requestResources.this.finish();
            }
        });


        requestResourcesBtn = (ImageView) findViewById(R.id.requestResourcesBtn_RR);
        requestResourcesBtn.setOnClickListener(new View.OnClickListener() {
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
            requestResourcesBtn.setImageBitmap(photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.request_resources, menu);
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
