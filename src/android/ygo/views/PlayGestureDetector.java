package android.ygo.views;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class PlayGestureDetector extends GestureDetector {

    PlayGestureListener listener;

    public PlayGestureDetector(PlayGestureListener listener) {
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
