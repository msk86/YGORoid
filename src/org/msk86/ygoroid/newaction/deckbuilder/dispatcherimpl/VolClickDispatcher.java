package org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newop.impl.VolClick;

import java.util.ArrayList;
import java.util.List;

public class VolClickDispatcher implements Dispatcher<VolClick> {
    @Override
    public List<Action> dispatch(VolClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        return actionChain;
    }
}
