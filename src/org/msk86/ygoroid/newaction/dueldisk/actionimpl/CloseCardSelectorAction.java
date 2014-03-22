package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Listable;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newop.Operation;

public class CloseCardSelectorAction extends BaseAction {
    public CloseCardSelectorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Listable source = duel.getCardSelector().getSource();
        duel.setCardSelector(null);
        if(source instanceof Selectable) {
            duel.select((Selectable) source);
        }
    }
}
