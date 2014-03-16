package org.msk86.ygoroid.views.newdeckbuilder;

import android.view.KeyEvent;
import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl.ReturnClickDispatcher;
import org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl.VolClickDispatcher;
import org.msk86.ygoroid.newop.impl.ReturnClick;
import org.msk86.ygoroid.newop.impl.VolClick;
import org.msk86.ygoroid.views.OnKeyProcessor;

import java.util.ArrayList;
import java.util.List;

public class DeckOnKeyProcessor implements OnKeyProcessor {
    DeckBuilderView view;

    public DeckOnKeyProcessor(DeckBuilderView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        List<Action> actionChain = new ArrayList<Action>();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                ReturnClick returnClick = new ReturnClick(view.getDeckBuilder());
                actionChain = new ReturnClickDispatcher().dispatch(returnClick);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                VolClick vuClick = new VolClick(view.getDeckBuilder(), VolClick.VOL_UP);
                actionChain = new VolClickDispatcher().dispatch(vuClick);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                VolClick vdClick = new VolClick(view.getDeckBuilder(), VolClick.VOL_DOWN);
                actionChain = new VolClickDispatcher().dispatch(vdClick);
                break;
        }

        for (Action action : actionChain) {
            action.execute();
        }

        view.updateActionTime();
        return true;
    }
}
