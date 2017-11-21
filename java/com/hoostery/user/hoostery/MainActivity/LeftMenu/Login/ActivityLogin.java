package com.hoostery.user.hoostery.MainActivity.LeftMenu.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.RestaurantProfile.RestaurantProfile;

public class ActivityLogin extends AppCompatActivity {

    private String previousActivityName;
    Bundle previousActivityData;
    String username;
    String password;
    TextView errorMessageTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        previousActivityData = getIntent().getExtras();
        if (previousActivityData == null)
            return;

        previousActivityName = previousActivityData.getString("from");

        // Go back to main activity
        ImageView iconButton = (ImageView) findViewById(R.id.back_to_main_activity);

        iconButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
            }
        });

        // Go back to main activity
        Button loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                username = ((TextView) findViewById(R.id.username)).getText().toString();
                password = ((TextView) findViewById(R.id.password)).getText().toString();
                errorMessageTextview = (TextView) findViewById(R.id.error_message);

                if (validate(username) && validate(password)) {

                    // AsyncTask
                    ThreadGetLogin getLogin = new ThreadGetLogin(ActivityLogin.this, previousActivityName, errorMessageTextview);
                    getLogin.execute();

                } else {

                }
            }
        });

        // Go to registration activity
        TextView registrationButton = (TextView) findViewById(R.id.go_to_registration);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ActivityLogin.this, RestaurantProfile.class);
                ActivityLogin.this.startActivity(intent);
            }
        });
    }

    public boolean validate(String str) {

        return true;
    }
}
