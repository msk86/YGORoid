package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;

public class ToDeckAction extends BaseAction {
    public ToDeckAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Field field = (Field) container;
        Deck deck = (Deck) field.getItem();
        if(item instanceof Card) {
            deck.getCardList().push((Card) item);
        }
        if(item instanceof OverRay) {
            deck.getCardList().push(((OverRay) item).getOverRayCards());
        }
        duel.select(deck);
    }
}
