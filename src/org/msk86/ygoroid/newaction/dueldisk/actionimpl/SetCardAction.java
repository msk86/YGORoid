package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;

public class SetCardAction extends BaseAction {
    public SetCardAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card magicTrapCard = (Card) item;
        magicTrapCard.set();
        magicTrapCard.positive();
        ((Field) container).setItem(magicTrapCard);
    }
}
