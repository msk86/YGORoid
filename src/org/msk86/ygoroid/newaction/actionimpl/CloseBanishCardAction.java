package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.*;
import org.msk86.ygoroid.newop.Operation;

public class CloseBanishCardAction extends BaseAction {
    public CloseBanishCardAction(Operation operation) {
        super(operation);
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
                Card topCard = overRay.getOverRayCards().topCard();
                overRay.getOverRayCards().remove(topCard);
                if(overRay.getOverRayCards().size() == 0) {
                    field.removeItem();
                }
            }
        } else if (container instanceof HandCards) {
            card = (Card) item;
            ((HandCards) container).getCardList().remove(card);
        }
        if(card != null) {
            card.set();
            Deck banished = (Deck) duel.getDuelFields().getField(FieldType.BANISHED).getItem();
            banished.getCardList().push(card, true);
            duel.unSelect();
        }
    }
}
