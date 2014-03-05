package org.msk86.ygoroid.views.newdueldisk;

import android.view.GestureDetector;
import android.view.MotionEvent;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.dispatcherimpl.ClickDispatcher;
import org.msk86.ygoroid.newop.impl.Click;

import java.util.List;

public class PlayGestureListener extends GestureDetector.SimpleOnGestureListener {
    DuelDiskView view;

    public PlayGestureListener(DuelDiskView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Click click = new Click(view.getDuel(), event.getX(), event.getY());
        List<Action> actions = new ClickDispatcher().dispatch(click);
        for(Action action : actions) {
            action.execute();
        }
        view.updateActionTime();
        return true;
    }
}
