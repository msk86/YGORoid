package android.ygo.views;

import android.view.KeyEvent;
import android.ygo.action.ActionDispatcher;
import android.ygo.op.ReturnClick;

public class PlayOnKeyProcessor {
    DuelDiskView view;

    public PlayOnKeyProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK :
                ReturnClick click = new ReturnClick(view.getDuel());
                ActionDispatcher.dispatch(click);
                view.invalidate();
                return true;
            case KeyEvent.KEYCODE_MENU :
                return true;
        }
        return true;
    }
}
