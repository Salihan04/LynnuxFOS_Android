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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lynnux.lynnuxfos.utility.Utility;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;


public class requestResources extends Activity {
    private static final String COUNT = "count";
    int count,TAKE_PHOTO_CODE = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEdit;
    ImageView requestResourcesBtn;
    EditText quantityText;

    private int quantity;
    private String region;
    private Bitmap photo;

    private Spinner resource_spinner;
    private Spinner incident_spinner;
    private Spinner region_spinner;

    private ArrayList<String> resourceList;
    private ArrayList<String> incidentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_resources);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        spEdit = sharedPreferences.edit();
        count = sharedPreferences.getInt(COUNT,0);

        quantityText = (EditText) findViewById(R.id.quantity);

        resource_spinner = (Spinner) findViewById(R.id.resource_spinner);
        resourceList = new ArrayList<String>();
        populateResourceSpinner(resourceList, resource_spinner);

        incident_spinner = (Spinner) findViewById(R.id.incident_spinner);
        incidentList = new ArrayList<String>();
        populateIncidentSpinner(incidentList, incident_spinner);

        region_spinner = (Spinner) findViewById(R.id.region_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.region, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(adapter);

        ImageButton submitRequestBtn = (ImageButton) findViewById(R.id.submitRequestBtn);
        submitRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject requestResource = new ParseObject("RequestResource");
                quantity = Integer.valueOf((quantityText.getText()).toString());
                region = region_spinner.getSelectedItem().toString();

                putResourceToRequest(requestResource);
                putIncidentToRequest(requestResource);

                Utility.requestResource(requestResource, quantity, region, getApplicationContext());

                requestResources.this.finish();
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

    private void populateResourceSpinner(final ArrayList<String> resourceList, final Spinner resource_spinner) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Resource");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : parseObjects) {
                        resourceList.add(object.getString("name"));
                    }
                    Collections.sort(resourceList);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, resourceList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    resource_spinner.setAdapter(adapter);
                }
                else {
                    Timber.e("Cannot retrieve Resources");
                }
            }
        });
    }

    private void populateIncidentSpinner(final ArrayList<String> incidentList, final Spinner incident_spinner) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Incident");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : parseObjects) {
                        incidentList.add(object.getString("name"));
                        Timber.d("Incident name: " + object.getString("name"));
                    }
                    Collections.sort(incidentList);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, incidentList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    incident_spinner.setAdapter(adapter);
                }
                else {
                    Timber.e("Cannot retrieve Incidents");
                }
            }
        });
    }

    public void putResourceToRequest(final ParseObject requestResource) {
        ParseQuery<ParseObject> resourceQuery = ParseQuery.getQuery("Resource");
        resourceQuery.whereMatches("name", resource_spinner.getSelectedItem().toString());
        resourceQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {
                    if(parseObjects.size() == 0) {
                        Toast.makeText(getApplication(), "No such resource", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    requestResource.put("resource", ParseObject.createWithoutData("Resource",parseObjects.get(0).getObjectId()));
//                    Toast.makeText(getApplication(), parseObjects.get(0).getObjectId(), Toast.LENGTH_SHORT).show();
                    requestResource.saveInBackground();
                }
                else {
                    Timber.e(e.getMessage());
                }
            }
        });
    }

    public void putIncidentToRequest(final ParseObject requestResource) {
        ParseQuery<ParseObject> incidentQuery = ParseQuery.getQuery("Incident");
        incidentQuery.whereMatches("name", incident_spinner.getSelectedItem().toString());
        incidentQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {
                    if(parseObjects.size() == 0) {
                        Toast.makeText(getApplication(), "No such incident", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    requestResource.put("incident", ParseObject.createWithoutData("Incident",parseObjects.get(0).getObjectId()));
//                    Toast.makeText(getApplication(), parseObjects.get(0).getObjectId(), Toast.LENGTH_SHORT).show();
                    requestResource.saveInBackground();
                }
                else {
                    Timber.e(e.getMessage());
                }
            }
        });
    }
}
