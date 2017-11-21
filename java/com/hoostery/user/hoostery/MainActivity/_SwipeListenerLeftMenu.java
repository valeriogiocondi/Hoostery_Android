package com.hoostery.user.hoostery.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hoostery.user.hoostery.R;

/**
 * Created by valerio on 05/06/17.
 */

public class _SwipeListenerLeftMenu extends GestureDetector.SimpleOnGestureListener {

    Context parentContext;
    Activity parentActivity;
    ConstraintLayout mainLayout;

    public _SwipeListenerLeftMenu(Context ctx) {
        parentContext = ctx;
        parentActivity = (Activity) ctx;
        mainLayout = (ConstraintLayout) parentActivity.findViewById(R.id.main_layout);
    }

    /*@Override
    public boolean onDown(MotionEvent e) {

        System.out.println("onDown");

        return true;
    }*/

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

System.out.println("ciao");
        int i=0, n=mainLayout.getWidth()-mainLayout.getWidth()*20/100;
        float diffX = e1.getX() - e2.getX();

        if (diffX > 0) {

//            System.out.println("Chiudi menu "+diffX);

            for (; i<=n; i++) {

                mainLayout.animate().x(n-i).setDuration(250).start();
            }
        }
        else {
//            System.out.println("Apri menu "+diffX);

            for (; i<=n; i++) {

                mainLayout.animate().x(i).setDuration(250).start();
            }
        }

        return true;
    }

}
