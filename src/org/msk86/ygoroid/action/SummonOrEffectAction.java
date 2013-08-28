package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.op.Operation;

public class SummonOrEffectAction extends BaseAction {
    public SummonOrEffectAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        card.open();
        card.positive();
        ((Field) container).setItem(card);
        duel.select(card, container);
    }
}
