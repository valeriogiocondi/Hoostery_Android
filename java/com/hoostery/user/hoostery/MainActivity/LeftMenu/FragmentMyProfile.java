package com.hoostery.user.hoostery.MainActivity.LeftMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoostery.user.hoostery.R;

/**
 * Created by valerio on 31/05/17.
 */

public class FragmentMyProfile extends Fragment {
    View mainLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainLayout = inflater.inflate(R.layout.activity_main_left_menu_voice_profile, container, false);

        return mainLayout;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
