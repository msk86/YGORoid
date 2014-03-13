package org.msk86.ygoroid.newaction.dueldisk.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.ChangeLpAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.CloseLpCalculatorAction;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.ShowCardEffectWindow;
import org.msk86.ygoroid.newaction.dueldisk.actionimpl.UnSelectAction;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.InfoBar;
import org.msk86.ygoroid.newcore.impl.lifepoint.Button;
import org.msk86.ygoroid.newop.impl.ClickConfirmed;

import java.util.ArrayList;
import java.util.List;

public class ClickConfirmedDispatcher implements Dispatcher<ClickConfirmed> {
    @Override
    public List<Action> dispatch(ClickConfirmed op) {
        List<Action> actionChain = new ArrayList<Action>();

        Item item = op.getItem();

        if(item == null) {
            actionChain.add(new UnSelectAction(op));
        }
        if(item instanceof InfoBar) {
            actionChain.add(new ShowCardEffectWindow(op));
        }
        if(item instanceof Button) {
            Button button = (Button) item;
            switch (button) {
                case OK:
                    actionChain.add(new ChangeLpAction(op));
                case CANCEL:
                    actionChain.add(new CloseLpCalculatorAction(op));
                    break;
            }
        }

        return actionChain;
    }
}
