package com.lynnux.lynnuxfos.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lynnux.lynnuxfos.FOSDialog;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import timber.log.Timber;

/**
 * Created by User on 20-Oct-14.
 */
public class Utility {

    public static void reportIncident(String description, ParseGeoPoint location, String name,
                                      int priority, String status, ParseFile incidentImage, final Context context) {
        ParseObject incident = ParseObject.create("Incident");

        incident.put("description", description);
        incident.put("location", location);     //can create location by using new Parse.GeoPoint(latitude, longitude)
        incident.put("name", name);
        incident.put("priority", priority);
        incident.put("reporter", ParseUser.getCurrentUser());
        incident.put("status", status);
        incident.put("incidentImage", incidentImage);
        incident.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(context, "Incident reported", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Report incident failed", Toast.LENGTH_SHORT).show();
                    Timber.e(e.getMessage());
                }
            }
        });
    }

    public static void requestResource(String resourceId, int quantityRequested, String incidentId) {
        ParseObject requestResource = new ParseObject("RequestResource");

        requestResource.put("resource", resourceId);
        requestResource.put("quantityRequested", quantityRequested);
        requestResource.put("incident", incidentId);
        requestResource.saveInBackground();
    }
}
