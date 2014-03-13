package org.msk86.ygoroid.views.sidechanger;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SideGestureListener extends GestureDetector.SimpleOnGestureListener {
    SideChangerView view;

    public SideGestureListener(SideChangerView view) {
        this.view = view;
    }

    public void onUp(MotionEvent event) {
        view.updateActionTime();
    }

    @Override
    public boolean onDown(MotionEvent event) {
        view.updateActionTime();
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        view.updateActionTime();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        view.updateActionTime();
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        return super.onDoubleTap(event);
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float dx, float dy) {
        view.updateActionTime();
        return true;
    }
}
