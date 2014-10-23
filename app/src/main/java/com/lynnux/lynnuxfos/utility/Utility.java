package com.lynnux.lynnuxfos.utility;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by User on 20-Oct-14.
 */
public class Utility {

    public static void reportIncident(String description, ParseGeoPoint location, String name, int priority, String status) {
        ParseObject incident = new ParseObject("Incident");

        incident.put("description", description);
        incident.put("location", location);     //can create location by using new Parse.GeoPoint(latitude, longitude)
        incident.put("name", name);
        incident.put("priority", priority);
        incident.put("reporter", ParseUser.getCurrentUser());
        incident.put("status", status);
        incident.saveInBackground();
    }

    public static void requestResource() {

    }
}
