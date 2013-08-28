package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.*;
import org.msk86.ygoroid.op.Operation;

public class CloseRemoveCardAction extends BaseAction {
    public CloseRemoveCardAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = null;
        if (container instanceof Field) {
            Field field = (Field) container;
            if (item instanceof Card) {
                card = (Card) item;
                field.removeItem();
            } else if (item instanceof OverRay) {
                OverRay overRay = (OverRay) item;
                card = overRay.removeTopCard();
                overRay.adjust(field);
            }

        } else if (container instanceof HandCards) {
            card = (Card) item;
            ((HandCards) container).remove(card);
        }

        CardList removed = (CardList) duel.getDuelFields().getRemovedField().getItem();
        removed.push(card, true);

        duel.unSelect();
    }
}
