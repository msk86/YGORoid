package org.msk86.ygoroid.views.sidechanger;

import android.view.KeyEvent;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl.ReturnClickDispatcher;
import org.msk86.ygoroid.newop.impl.ReturnClick;
import org.msk86.ygoroid.views.OnKeyProcessor;

import java.util.ArrayList;
import java.util.List;

public class SideOnKeyProcessor implements OnKeyProcessor {
    SideChangerView view;

    public SideOnKeyProcessor(SideChangerView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        List<Action> actionChain = new ArrayList<Action>();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ReturnClick returnClick = new ReturnClick(view.getSideChanger());
                actionChain = new ReturnClickDispatcher().dispatch(returnClick);
                break;
            case KeyEvent.KEYCODE_MENU:
                return false;
            case KeyEvent.KEYCODE_VOLUME_UP:
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                break;
        }
        for(Action action : actionChain) {
            action.execute();
        }
        view.updateActionTime();

        return true;
    }
}
