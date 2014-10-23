package com.lynnux.lynnuxfos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class login extends Activity {
    private Intent intent;
    private String username,password;
    private EditText usernameText,passwordText;
    private FOSDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //finding all the views
        ImageButton loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        //Submit button
        intent = new Intent(this,home.class);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                if(username.isEmpty()||password.isEmpty()){

                }else{
                    //try login
                    dialog = FOSDialog.showProgress(login.this, "Logging in");
                    ParseUser.logInInBackground(username, password, new LogInCallback() {

                        public void done(ParseUser user, ParseException e) {
                            dialog.dismiss();
                            if (user != null) {
                                startActivity(intent);
                                finish();
                            } else {
                                // Signup failed. Look at the ParseException to see what happened.
                            }
                        }
                    });
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
