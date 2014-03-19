package org.msk86.ygoroid.views.newdeckbuilder;

import android.view.GestureDetector;
import android.view.MotionEvent;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl.ClickConfirmedDispatcher;
import org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl.DoubleClickDispatcher;
import org.msk86.ygoroid.newop.impl.ClickConfirmed;
import org.msk86.ygoroid.newop.impl.DoubleClick;

import java.util.List;

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
        ClickConfirmed clickConfirmed = new ClickConfirmed(view.getDeckBuilder(), event.getX(), event.getY());
        List<Action> actions = new ClickConfirmedDispatcher().dispatch(clickConfirmed);
        for(Action action : actions) {
            action.execute();
        }
        view.updateActionTime();
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        DoubleClick dblClick = new DoubleClick(view.getDeckBuilder(), event.getX(), event.getY());
        List<Action> actionChain = new DoubleClickDispatcher().dispatch(dblClick);
        for(Action action : actionChain) {
            action.execute();
        }
        view.updateActionTime();
        return super.onDoubleTap(event);
    }
}
