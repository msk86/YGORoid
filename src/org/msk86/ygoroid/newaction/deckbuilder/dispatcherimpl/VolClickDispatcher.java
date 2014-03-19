package org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.ShuffleDeckAction;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.SortDeckAction;
import org.msk86.ygoroid.newop.impl.VolClick;

import java.util.ArrayList;
import java.util.List;

public class VolClickDispatcher implements Dispatcher<VolClick> {
    @Override
    public List<Action> dispatch(VolClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        if(op.getVol() == VolClick.VOL_DOWN) {
            actionChain.add(new ShuffleDeckAction(op));
        }
        if(op.getVol() == VolClick.VOL_UP) {
            actionChain.add(new SortDeckAction(op));
        }

        return actionChain;
    }
}
