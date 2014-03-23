package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Listable;
import org.msk86.ygoroid.newcore.impl.CardSelector;
import org.msk86.ygoroid.newcore.impl.InfoBar;
import org.msk86.ygoroid.newop.Operation;

public class OpenCardSelectorAction extends BaseAction {
    public OpenCardSelectorAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Listable listable = null;
        if(item instanceof InfoBar && ((InfoBar) item).getInfoItem() instanceof Listable) {
            listable = (Listable) ((InfoBar) item).getInfoItem();
        }
        if(item instanceof Listable) {
            listable = (Listable) item;
        }
        if(listable != null) {
            CardSelector selector = new CardSelector(listable, listable.listCards());
            if(selector.getLayout().items().size() > 0) {
                duel.setCardSelector(selector);
            }
        }
    }
}
