package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newop.Operation;

public class SummonAction extends BaseAction {
    public SummonAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card monsterCard = (Card) item;
        monsterCard.open();
        monsterCard.positive();
        ((Field) container).setItem(monsterCard);
    }
}
