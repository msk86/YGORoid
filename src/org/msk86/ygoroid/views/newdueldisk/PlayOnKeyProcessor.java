package org.msk86.ygoroid.views.newdueldisk;

import android.view.KeyEvent;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl.ReturnClickDispatcher;
import org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl.VolClickDispatcher;
import org.msk86.ygoroid.newop.impl.ReturnClick;
import org.msk86.ygoroid.newop.impl.VolClick;
import org.msk86.ygoroid.views.OnKeyProcessor;

import java.util.ArrayList;
import java.util.List;

public class PlayOnKeyProcessor implements OnKeyProcessor {
    DuelDiskView view;

    public PlayOnKeyProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        List<Action> actionChain = new ArrayList<Action>();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ReturnClick returnClick = new ReturnClick(view.getDuel());
                actionChain = new ReturnClickDispatcher().dispatch(returnClick);
                break;
            case KeyEvent.KEYCODE_MENU:
                return false;
            case KeyEvent.KEYCODE_VOLUME_UP:
                VolClick vuClick = new VolClick(view.getDuel(), VolClick.VOL_UP);
                actionChain = new VolClickDispatcher().dispatch(vuClick);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                VolClick vdClick = new VolClick(view.getDuel(), VolClick.VOL_DOWN);
                actionChain = new VolClickDispatcher().dispatch(vdClick);
                break;
        }
        for(Action action : actionChain) {
            action.execute();
        }
        view.updateActionTime();

        return true;
    }
}
