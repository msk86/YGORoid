package android.ygo.views.deckbuilder;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DeckGestureListener extends GestureDetector.SimpleOnGestureListener {
    DeckBuilderView view;

    public DeckGestureListener(DeckBuilderView view) {
        this.view = view;
    }

    public void onUp(MotionEvent event) {

        view.updateActionTime();
    }

    @Override
    public boolean onDown(MotionEvent event) {
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

        view.updateActionTime();
        return super.onDoubleTap(event);
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float dx, float dy) {

        view.updateActionTime();
        return true;
    }
}
