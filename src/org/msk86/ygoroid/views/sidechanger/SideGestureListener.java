package org.msk86.ygoroid.views.sidechanger;

import android.view.GestureDetector;
import android.view.MotionEvent;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl.ClickConfirmedDispatcher;
import org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl.DoubleClickDispatcher;
import org.msk86.ygoroid.newop.impl.ClickConfirmed;
import org.msk86.ygoroid.newop.impl.DoubleClick;

import java.util.List;

public class SideGestureListener extends GestureDetector.SimpleOnGestureListener {
    SideChangerView view;

    public SideGestureListener(SideChangerView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        ClickConfirmed clickConfirmed = new ClickConfirmed(view.getSideChanger(), event.getX(), event.getY());
        List<Action> actions = new ClickConfirmedDispatcher().dispatch(clickConfirmed);
        for(Action action : actions) {
            action.execute();
        }
        view.updateActionTime();
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        DoubleClick dblClick = new DoubleClick(view.getSideChanger(), event.getX(), event.getY());
        List<Action> actionChain = new DoubleClickDispatcher().dispatch(dblClick);
        for(Action action : actionChain) {
            action.execute();
        }
        view.updateActionTime();
        return super.onDoubleTap(event);
    }

}
