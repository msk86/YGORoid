package android.ygo.views;

import android.view.KeyEvent;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.action.EmptyAction;
import android.ygo.op.ReturnClick;
import android.ygo.op.VolClick;
import android.ygo.utils.Utils;
import android.ygo.views.deckbuilder.DeckBuilderView;
import android.ygo.views.dueldisk.DuelDiskView;

public class DeckOnKeyProcessor {
    DeckBuilderView view;

    public DeckOnKeyProcessor(DeckBuilderView view) {
        this.view = view;
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.getContext().showDuelWithDeck(view.getCurrentDeckName());
        }
        return true;
    }
}
