package org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.ChangeSideCardAction;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.builder.SideChanger;
import org.msk86.ygoroid.newop.impl.DoubleClick;

import java.util.ArrayList;
import java.util.List;

public class DoubleClickDispatcher implements Dispatcher<DoubleClick> {
    @Override
    public List<Action> dispatch(DoubleClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        SideChanger sideChanger = (SideChanger) op.getBaseContainer();
        if(sideChanger.getCurrentSelectItem() != null && op.getItem() instanceof Card) {
            actionChain.add(new ChangeSideCardAction(op));
        }

        return actionChain;
    }
}
