package com.lynnux.lynnuxfos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class home extends Activity {
    Intent reportIncidentIntent,viewEventIntent,viewIncidentsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        reportIncidentIntent = new Intent(this,reportIncident.class);
        viewEventIntent = new Intent(this,viewEvent.class);
        viewIncidentsIntent = new Intent(this,linkedIncidents.class);
        ImageButton reportIncidentBtn = (ImageButton) findViewById(R.id.reportIncidentBtn);
        ImageButton viewEventBtn = (ImageButton) findViewById(R.id.viewEventBtn);
        ImageButton viewIncidentBtn = (ImageButton) findViewById(R.id.viewIncidentBtn);
        reportIncidentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(reportIncidentIntent);
            }
        });
        viewEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewEventIntent);
            }
        });
        viewIncidentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewIncidentsIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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