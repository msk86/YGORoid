package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;

public class EffectAction extends BaseAction {
    public EffectAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card magicTragCard = (Card) item;
        magicTragCard.open();
        magicTragCard.positive();
        ((Field) container).setItem(magicTragCard);
    }
}
