package org.msk86.ygoroid.newaction.sidechanger.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.SelectAction;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.ShowCardEffectWindowAction;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.ShowNextPageDescAction;
import org.msk86.ygoroid.newaction.sidechanger.actionimpl.UnSelectAction;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardEffectWindow;
import org.msk86.ygoroid.newcore.impl.InfoBar;
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
        if(item instanceof Card) {
            actionChain.add(new SelectAction(op));
        }
        if(item instanceof InfoBar) {
            actionChain.add(new ShowCardEffectWindowAction(op));
        }
        if(item instanceof CardEffectWindow) {
            actionChain.add(new ShowNextPageDescAction(op));
        }

        return actionChain;
    }
}
