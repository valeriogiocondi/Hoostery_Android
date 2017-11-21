package com.hoostery.user.hoostery.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hoostery.user.hoostery.MainActivity.LeftMenu.ActivityBookingsHistory;
import com.hoostery.user.hoostery.MainActivity.LeftMenu.ActivityFavorites;
import com.hoostery.user.hoostery.MainActivity.LeftMenu.Login.ActivityLogin;
import com.hoostery.user.hoostery.MainActivity.LeftMenu.ActivityMyProfile;
import com.hoostery.user.hoostery.MainActivity.LeftMenu.LeftMenuAdapter;
import com.hoostery.user.hoostery.MainActivity.LeftMenu.Registration.ActivityRegistration;
import com.hoostery.user.hoostery.R;
import com.hoostery.user.hoostery.Search.ActivitySearch;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout leftMenu;
    private ConstraintLayout mainLayout;
    private ConstraintLayout.LayoutParams mainLayoutParams;
    ListView menuListView;
    _SwipeListenerLeftMenu swipeListenerLeftMenu;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String languageToLoad  = "it"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);


        leftMenu = (ConstraintLayout) findViewById(R.id.left_menu_layout);
        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        menuListView = (ListView) findViewById(R.id.menu_listview);

        //  ICON MENU

        ImageView iconMenu = (ImageView) findViewById(R.id.icon_menu);
        iconMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {

                manageLeftMenu();

            }
        });

        // ICON SEARCH

        ImageView iconSearch = (ImageView) findViewById(R.id.icon_search);
        iconSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {

                Intent intent = new Intent(MainActivity.this, ActivitySearch.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // LOGIN

        isLogged();

        // LEFT MENU

        mDetector = new GestureDetectorCompat(this, new _SwipeListenerLeftMenu(MainActivity.this));
        swipeListenerLeftMenu = new _SwipeListenerLeftMenu(MainActivity.this);
//        leftMenu.setOnTouchListener(swipeListenerLeftMenu);

       /* mainLayout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                if ((event.getAction() == MotionEvent.ACTION_DOWN)) {

                    System.out.println("ACTION_DOWN "+event.getRawX());

                } else if ((event.getAction() == MotionEvent.ACTION_MOVE)) {

                    System.out.println("ACTION_MOVE "+event.getRawX());
                    return false;
                }

                return true;
            }
        });*/

        ActivityHandleTouchEvent prova = new ActivityHandleTouchEvent(MainActivity.this);

    }

/*

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//    public boolean onTouchEvent(MotionEvent event) {

//        super.dispatchTouchEvent(event);

        System.out.println("----------------------------------------------------");

        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            System.out.println("ACTION_MOVE "+event.getRawX());

            mainLayout.setX(event.getRawX());

            if (event.getRawX() < mainLayout.getWidth()*30/100)
                mainLayout.animate().x(0).setDuration(250).start();
            else
                mainLayout.animate().x(mainLayout.getWidth()-mainLayout.getWidth()*20/100).setDuration(250).start();

        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

            System.out.println("action down");
        }


        mDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
//        return true;
//        return false;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void manageLeftMenu() {

        int i=0, n=mainLayout.getWidth()-200;

        if (mainLayout.getX() > 0) {

            for (; i<=n; i++) {

                mainLayout.animate().x(n-i).setDuration(250).start();
            }
        } else {

            for (; i<=n; i++) {

                mainLayout.animate().x(i).setDuration(250).start();
            }
        }
    }

    public void isLogged() {

//        SHARED PREFERENCES
        SharedPreferences prefs = getSharedPreferences("login", MainActivity.this.MODE_PRIVATE);
        String textData = prefs.getString("logged", "");

        System.out.println(textData);

        if (textData.trim().equals("") == false) {

            String[] list = new String[]{"HOME", "IL MIO PROFILO", "RACCOLTA PUNTI", "PRENOTAZIONI", "I MIEI PREFERITI", "LE MIE RECENSIONI", "IMPOSTAZIONI", "LOGOUT"};

            LeftMenuAdapter menuListAdapter = new LeftMenuAdapter(this, list);
            menuListView.setAdapter(menuListAdapter);

            menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction frTransaction = fragmentManager.beginTransaction();

                    switch (position) {

                        case 0: {

//                        FragmentHome fragmentHome = new FragmentHome();
//                        frTransaction.replace(R.id.main_layout, fragmentHome);
//                        frTransaction.addToBackStack(null);
//                        frTransaction.commit();
                            break;
                        }
                        case 1: {

                            Intent intent = new Intent(MainActivity.this, ActivityMyProfile.class);
                            MainActivity.this.startActivity(intent);

                            break;
                        }
                        case 3: {

                            Intent intent = new Intent(MainActivity.this, ActivityBookingsHistory.class);
                            MainActivity.this.startActivity(intent);

                            break;
                        }
                        case 4: {

                            Intent intent = new Intent(MainActivity.this, ActivityFavorites.class);
                            MainActivity.this.startActivity(intent);

                            break;
                        }
                        case 7: {

//                            SHARED PREFERENCES
                            SharedPreferences prefs = getSharedPreferences("login", MainActivity.this.MODE_PRIVATE);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.commit();

                            isLogged();

//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            MainActivity.this.startActivity(intent);

                            break;
                        }

                    }

                    manageLeftMenu();
                    menuListView.setItemChecked(position, true);

                }
            });

        } else {

            String[] list = new String[]{"HOME", "LOGIN", "REGISTRATI"};

            LeftMenuAdapter menuListAdapter = new LeftMenuAdapter(this, list);
            menuListView.setAdapter(menuListAdapter);

            menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction frTransaction = fragmentManager.beginTransaction();

                    switch (position) {

                        case 0: {

//                        FragmentHome fragmentHome = new FragmentHome();
//                        frTransaction.replace(R.id.main_layout, fragmentHome);
//                        frTransaction.addToBackStack(null);
//                        frTransaction.commit();
                            break;
                        }
                        case 1: {

                            Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                            intent.putExtra("from", "MainActivity");
                            MainActivity.this.startActivity(intent);

                            break;
                        }
                        case 2: {

                            Intent intent = new Intent(MainActivity.this, ActivityRegistration.class);
                            MainActivity.this.startActivity(intent);

                            break;
                        }

                    }

                    manageLeftMenu();
                    menuListView.setItemChecked(position, true);

                }
            });
        }

    }


}
