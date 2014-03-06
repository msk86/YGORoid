package org.msk86.ygoroid.newaction.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.actionimpl.*;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.newcore.impl.lifepoint.Button;
import org.msk86.ygoroid.newcore.impl.lifepoint.LpDisplay;
import org.msk86.ygoroid.newcore.impl.lifepoint.Numbers;
import org.msk86.ygoroid.newcore.impl.lifepoint.Operator;
import org.msk86.ygoroid.newop.impl.ClickConfirmed;

import java.util.ArrayList;
import java.util.List;

public class ClickConfirmedDispatcher implements Dispatcher<ClickConfirmed> {
    @Override
    public List<Action> dispatch(ClickConfirmed op) {
        List<Action> actionChain = new ArrayList<Action>();

        Item item = op.getItem();

        if(item instanceof InfoBar) {
            actionChain.add(new ShowCardEffectWindow(op));
        }

        return actionChain;
    }
}
