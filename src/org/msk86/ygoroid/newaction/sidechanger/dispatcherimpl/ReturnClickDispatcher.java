package org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.ChangeSideFinishAction;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.CloseCardEffectWindowAction;
import org.msk86.ygoroid.newcore.impl.side.SideChanger;
import org.msk86.ygoroid.newop.impl.ReturnClick;

import java.util.ArrayList;
import java.util.List;

public class ReturnClickDispatcher implements Dispatcher<ReturnClick> {
    @Override
    public List<Action> dispatch(ReturnClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        SideChanger sideChanger = (SideChanger) op.getBaseContainer();

        if(sideChanger.getCardEffectWindow() != null) {
            actionChain.add(new CloseCardEffectWindowAction(op));
        } else {
            actionChain.add(new ChangeSideFinishAction(op));
        }

        return actionChain;
    }
}
