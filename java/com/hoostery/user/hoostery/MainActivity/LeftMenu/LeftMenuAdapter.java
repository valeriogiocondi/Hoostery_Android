package com.hoostery.user.hoostery.MainActivity.LeftMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoostery.user.hoostery.R;

import java.util.ArrayList;

/**
 * Created by valerio on 31/05/17.
 */

public class LeftMenuAdapter extends ArrayAdapter<String> {

    Context parentContext = null;

    public LeftMenuAdapter(Context context, String[] List)  {

        super(context, R.layout.activity_main_left_menu_list_adapter, List);
        parentContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layout = LayoutInflater.from(getContext());
        View customView = layout.inflate(R.layout.activity_main_left_menu_list_adapter, parent, false);

        TextView voiceTextView = (TextView) customView.findViewById(R.id.voice);

        voiceTextView.setText(getItem(position));

        return customView;
    }

}
