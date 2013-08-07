package android.ygo.views.deckbuilder;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.ygo.core.Card;
import android.ygo.layout.Layout;

public class DeckGestureListener extends GestureDetector.SimpleOnGestureListener {
    DeckBuilderView view;

    public DeckGestureListener(DeckBuilderView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(view.isInMain(x, y) || view.isInEx(x, y)) {
            view.setIsMain(true);
        } else if(view.isInSide(x, y)) {
            view.setIsMain(false);
        }
        Card card = view.cardAt(x, y);

        view.select(card);

        view.updateActionTime();
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Card card = view.cardAt(x, y);
        Layout layout = view.layoutAt(x, y);
        if(layout != null && card != null) {
            layout.cards().remove(card);
        }
        view.updateActionTime();
        return super.onDoubleTap(event);
    }
}
