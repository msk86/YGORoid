package android.ygo.views;

import android.view.KeyEvent;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.action.EmptyAction;
import android.ygo.op.ReturnClick;
import android.ygo.op.VolClick;
import android.ygo.views.dueldisk.DuelDiskView;

public class PlayOnKeyProcessor {
    DuelDiskView view;

    public PlayOnKeyProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        Action action = new EmptyAction();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ReturnClick returnClick = new ReturnClick(view.getDuel());
                action = ActionDispatcher.dispatch(returnClick);
                break;
            case KeyEvent.KEYCODE_MENU:
                return false;
            case KeyEvent.KEYCODE_VOLUME_UP :
                VolClick vuClick = new VolClick(view.getDuel(), VolClick.VOL_UP);
                action = ActionDispatcher.dispatch(vuClick);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN :
                VolClick vdClick = new VolClick(view.getDuel(), VolClick.VOL_DOWN);
                action = ActionDispatcher.dispatch(vdClick);
                break;
        }
        action.execute();
        view.updateActionTime();

        return true;
    }
}
