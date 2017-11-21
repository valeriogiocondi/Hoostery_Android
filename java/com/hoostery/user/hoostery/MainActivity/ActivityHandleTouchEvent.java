package com.hoostery.user.hoostery.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.hoostery.user.hoostery.R;

public class ActivityHandleTouchEvent extends ViewGroup {

    Context parentContext;
    Activity parentActivity;
    ConstraintLayout mainLayout;

    public ActivityHandleTouchEvent(Context context) {
        super(context);

        System.out.println("ActivityHandleTouchEvent create()");

        parentContext = context;
        parentActivity = (Activity) context;
        mainLayout = (ConstraintLayout) parentActivity.findViewById(R.id.main_layout);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if ((event.getAction() == MotionEvent.ACTION_MOVE)) {

            System.out.println("ACTION_MOVE "+event.getRawX());
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
