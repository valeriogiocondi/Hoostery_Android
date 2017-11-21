package com.hoostery.user.hoostery.MainActivity.LeftMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hoostery.user.hoostery.MainActivity.ThreadGetRestaurantsList;
import com.hoostery.user.hoostery.R;

/**
 * Created by valerio on 31/05/17.
 */

public class FragmentHome extends Fragment {

    View mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainLayout = inflater.inflate(R.layout.activity_main_left_menu_voice_home, container, false);

        // AsyncTask
        ThreadGetRestaurantsList restaurantsList = new ThreadGetRestaurantsList(getContext(), (ListView) mainLayout.findViewById(R.id.restaurants_list));
        restaurantsList.execute();

        return mainLayout;
//   w     return super.onCreateView(inflater, container, savedInstanceState);
    }

}
