package com.lynnux.lynnuxfos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class linkedIncidents extends Activity {
    Intent viewIncidentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_incidents);

        viewIncidentIntent = new Intent(this,viewIncident.class);
        ImageButton viewIncidentBtn = (ImageButton) findViewById(R.id.viewIncidentBtn_li);
        ImageButton followEventBtn = (ImageButton) findViewById(R.id.followEventBtn_li);
        TextView eventTitle = (TextView) findViewById(R.id.incidentTitle);
        TextView amountOfIncidents = (TextView) findViewById(R.id.amountOfIncidents);
        viewIncidentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewIncidentIntent);
            }
        });
        followEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.linked_incidents, menu);
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
