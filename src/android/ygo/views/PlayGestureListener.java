package android.ygo.views;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.op.*;
import android.ygo.utils.Utils;

public class PlayGestureListener extends GestureDetector.SimpleOnGestureListener {
    DuelDiskView view;

    public PlayGestureListener(DuelDiskView view) {
        this.view = view;
    }

    public void onUp(MotionEvent event) {
        Drag drag = view.getDuel().getDrag();
        if (drag != null) {
            drag.dropTo(Utils.mirrorX(event.getX()) , Utils.mirrorY(event.getY()));
            view.getDuel().setDrag(null);
            Action action = ActionDispatcher.dispatch(drag);
            action.execute();
        }
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Click click = new Click(view.getDuel(), Utils.mirrorX(event.getX()), Utils.mirrorY(event.getY()));
        Action action = ActionDispatcher.dispatch(click);
        action.execute();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Press press = new Press(view.getDuel(), Utils.mirrorX(event.getX()), Utils.mirrorY(event.getY()));
        Action action = ActionDispatcher.dispatch(press);
        action.execute();
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        DoubleClick dblClick = new DoubleClick(view.getDuel(), Utils.mirrorX(event.getX()), Utils.mirrorY(event.getY()));
        Action action = ActionDispatcher.dispatch(dblClick);
        action.execute();
        return super.onDoubleTap(event);
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float dx, float dy) {
        Drag drag = view.getDuel().getDrag();
        if (drag == null) {
            StartDrag startDrag = new StartDrag(view.getDuel(), Utils.mirrorX(event1.getX()), Utils.mirrorY(event1.getY()));
            Action action = ActionDispatcher.dispatch(startDrag);
            action.execute();

            drag = new Drag(startDrag, view.getDuel(), Utils.mirrorX(event1.getX()), Utils.mirrorY(event1.getY()));
            view.getDuel().setDrag(drag);
        }
        drag.move(Utils.mirrorX(event2.getX()), Utils.mirrorY(event2.getY()));
        return true;
    }
}
