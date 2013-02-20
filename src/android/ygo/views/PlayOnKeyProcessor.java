package android.ygo.views;

import android.view.KeyEvent;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.action.EmptyAction;
import android.ygo.op.MenuClick;
import android.ygo.op.ReturnClick;

public class PlayOnKeyProcessor {
    DuelDiskView view;

    public PlayOnKeyProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        Action action = new EmptyAction();
        boolean returnValue = true;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ReturnClick returnClick = new ReturnClick(view.getDuel());
                action = ActionDispatcher.dispatch(returnClick);
                break;
            case KeyEvent.KEYCODE_MENU:
                MenuClick menuClick = new MenuClick(view.getDuel());
                action = ActionDispatcher.dispatch(menuClick);
                returnValue = false;
                break;
        }
        action.execute();
        view.invalidate();

        return returnValue;
    }
}
