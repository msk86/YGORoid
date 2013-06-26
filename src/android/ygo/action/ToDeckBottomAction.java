package android.ygo.action;

import android.ygo.core.*;
import android.ygo.op.Operation;

public class ToDeckBottomAction extends BaseAction {
    public ToDeckBottomAction(Operation operation) {
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

        if(card.isEx()){
            CardList ex = (CardList) duel.getDuelFields().getExDeckField().getItem();
            ex.unShift(card);
        }
        else {
            Deck deck = (Deck) duel.getDuelFields().getDeckField().getItem();
            deck.unShift(card);
        }

        duel.unSelect();
    }
}
