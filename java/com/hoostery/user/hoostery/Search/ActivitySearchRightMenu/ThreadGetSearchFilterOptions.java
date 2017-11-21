package com.hoostery.user.hoostery.Search.ActivitySearchRightMenu;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.Search.ActivitySearch;
import com.hoostery.user.hoostery.Search.ActivitySearchRightMenu.FilterTabHost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by valerio on 19/05/17.
 */

//public class ThreadGetSearchFilterOptions extends AsyncTask<Void, Void, String>

/**
 * Created by valerio on 18/04/17.
 */

public class ThreadGetSearchFilterOptions extends AsyncTask<Void, Void, String> implements TabLayout.OnTabSelectedListener {

    Context parentContext;
    ActivitySearch parentView;
    FragmentManager parentFragmentManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ThreadGetSearchFilterOptions(Context context){

        parentContext = context;
        parentView = (ActivitySearch) context;
        parentFragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
    }

    @Override
    protected String doInBackground(Void... urls) {

        try {

            URL url = new URL("http://88.99.185.13/get_filter_search_options.php");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            InputStreamReader streamConnection = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamConnection);

            StringBuffer json = new StringBuffer(1024);
            String tmp;

            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            return json.toString();

        } catch (MalformedURLException e) {

            System.out.println(e.getMessage());

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

        return null;
    }

    protected void onPostExecute(String result) {

        //Initializing the tablayout
        tabLayout = (TabLayout) parentView.findViewById(R.id.filter_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) parentView.findViewById(R.id.filter_view_pager);
        FilterTabHost adapter = new FilterTabHost(parentFragmentManager, tabLayout.getTabCount(), result);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}