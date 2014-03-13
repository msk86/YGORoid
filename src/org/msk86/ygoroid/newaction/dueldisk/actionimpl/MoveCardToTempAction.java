package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardSelector;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newop.Operation;

public class MoveCardToTempAction extends BaseAction {
    public MoveCardToTempAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        if(item instanceof Card && container instanceof CardSelector) {
            Card card = (Card) item;
            Deck deck = (Deck)((CardSelector) container).getSource();
            Deck temp = (Deck)duel.getDuelFields().getField(FieldType.TEMP).getItem();
            if(temp != null) {
                deck.getCardList().remove(card);
                temp.getCardList().push(card);
            }
            if(deck.getCardList().size() == 0) {
                duel.setCardSelector(null);
                duel.select(deck);
            }
        }
    }
}
