package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.OverRay;
import org.msk86.ygoroid.op.Operation;

public class FlipAction extends BaseAction {
    public FlipAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = null;
        if (item instanceof Card) {
            card = (Card) item;
        } else if (item instanceof OverRay) {
            card = ((OverRay) item).topCard();
        }
        if (card != null) {
            card.flip();
            duel.select(item, container);
        }
    }
}
