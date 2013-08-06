package android.ygo.views.deckbuilder;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DeckGestureDetector extends GestureDetector {

    DeckGestureListener listener;

    public DeckGestureDetector(DeckGestureListener listener) {
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
