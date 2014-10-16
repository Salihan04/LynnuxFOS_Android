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


public class updateStatus extends Activity {
    private static final String COUNT = "count";
    int count,TAKE_PHOTO_CODE = 0;
    Intent goBackMainIntent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEdit;
    ImageView updateImageBtn;
    EditText incidentNameText,descriptionText,priorityText,updateText;
    String incidentName,description,priority,update;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        spEdit = sharedPreferences.edit();
        count = sharedPreferences.getInt(COUNT,0);

        incidentNameText = (EditText) findViewById(R.id.incidentNameText_US);
        descriptionText = (EditText) findViewById(R.id.descriptionText_US);
        priorityText = (EditText) findViewById(R.id.priorityText_US);
        updateText = (EditText) findViewById(R.id.updateText_US);
        ImageButton submitUpdateBtn = (ImageButton) findViewById(R.id.submitUpdateBtn);
        submitUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incidentName = (incidentNameText.getText()).toString();
                description = (descriptionText.getText()).toString();
                priority = (priorityText.getText()).toString();
                update = (updateText.getText()).toString();
                updateStatus.this.finish();
            }
        });


        updateImageBtn = (ImageView) findViewById(R.id.updateImageBtn);
        updateImageBtn.setOnClickListener(new View.OnClickListener() {
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
            updateImageBtn.setImageBitmap(photo);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_status, menu);
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
