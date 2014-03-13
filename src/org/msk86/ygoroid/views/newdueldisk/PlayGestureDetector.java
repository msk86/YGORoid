package org.msk86.ygoroid.views.newdueldisk;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class PlayGestureDetector extends GestureDetector {

    PlayGestureListener listener;

    public PlayGestureDetector(Context context, PlayGestureListener listener) {
        super(context, listener);
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
