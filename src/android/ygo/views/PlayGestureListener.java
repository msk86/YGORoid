package android.ygo.views;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.touch.Click;
import android.ygo.touch.DoubleClick;
import android.ygo.touch.Drag;

public class PlayGestureListener extends GestureDetector.SimpleOnGestureListener {
    DuelDiskView view;

    public PlayGestureListener(DuelDiskView view) {
        this.view = view;
    }

    public void onUp(MotionEvent event) {
        Drag drag = view.getDuel().getDrag();
        if(drag != null) {
            drag.dropTo(event.getX(), event.getY());
            view.getDuel().setDrag(null);
        }
        view.invalidate();
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Click click = new Click(view.getDuel(), event.getX(), event.getY());
        Action action = ActionDispatcher.dispatch(click);
        action.execute();
        view.invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.e("YGO", "Longggggggggg~");
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        DoubleClick dblClick = new DoubleClick(view.getDuel(), event.getX(), event.getY());
        Action action = ActionDispatcher.dispatch(dblClick);
        action.execute();
        view.invalidate();
        return super.onDoubleTap(event);
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float dx, float dy) {
        Drag drag = view.getDuel().getDrag();
        if(drag == null) {
            drag = new Drag(view.getDuel(), event1.getX(), event1.getY());
            view.getDuel().setDrag(drag);
        }
        drag.move(event2.getX(), event2.getY());
        view.invalidate();
        return true;
    }
}
