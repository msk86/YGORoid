package org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.CloseCardEffectWindowAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.CloseCardSelectorAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.CloseLpCalculatorAction;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newop.impl.ReturnClick;

import java.util.ArrayList;
import java.util.List;

public class ReturnClickDispatcher implements Dispatcher<ReturnClick> {
    @Override
    public List<Action> dispatch(ReturnClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        Duel duel = (Duel) op.getBaseContainer();

        if(duel.getCardEffectWindow() != null) {
            actionChain.add(new CloseCardEffectWindowAction(op));
        } else if(duel.getCardSelector() != null) {
            actionChain.add(new CloseCardSelectorAction(op));
        } else if(duel.getLifePointCalculator() != null) {
            actionChain.add(new CloseLpCalculatorAction(op));
        }

        return actionChain;
    }
}
