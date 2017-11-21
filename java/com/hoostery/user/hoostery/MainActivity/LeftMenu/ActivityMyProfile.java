package com.hoostery.user.hoostery.MainActivity.LeftMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hoostery.user.hoostery.R;

public class ActivityMyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_left_menu_voice_profile);

        // Go back to main activity
        ImageView iconButton = (ImageView) findViewById(R.id.back_to_main_activity);

        iconButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}
