package org.msk86.ygoroid.views.logo;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class LogoGestureListener extends GestureDetector.SimpleOnGestureListener {
    LogoView view;

    public LogoGestureListener(LogoView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        view.nextLogo();
        return true;
    }
}
