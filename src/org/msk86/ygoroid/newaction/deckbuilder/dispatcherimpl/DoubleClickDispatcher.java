package org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.RemoveDeckCardAction;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newop.impl.DoubleClick;

import java.util.ArrayList;
import java.util.List;

public class DoubleClickDispatcher implements Dispatcher<DoubleClick> {
    @Override
    public List<Action> dispatch(DoubleClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        Item item = op.getItem();
        if(item instanceof Card) {
            actionChain.add(new RemoveDeckCardAction(op));
        }

        return actionChain;
    }
}
