package org.msk86.ygoroid.views.sidechanger;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SideGestureDetector extends GestureDetector {

    SideGestureListener listener;

    public SideGestureDetector(SideGestureListener listener) {
        super(listener);
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            listener.onUp(event);
        }
        return super.onTouchEvent(event);
    }
}
