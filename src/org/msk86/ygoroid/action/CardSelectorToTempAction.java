package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.CardList;
import org.msk86.ygoroid.core.CardSelector;
import org.msk86.ygoroid.op.Operation;

public class CardSelectorToTempAction extends BaseAction {
    public CardSelectorToTempAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        CardSelector cardSelector = duel.getCardSelector();
        if(cardSelector != null) {
            CardList cardList = cardSelector.getCardList();
            cardList.remove(card);
            CardList tempList = (CardList) duel.getDuelFields().getTempField().getItem();
            tempList.push(card);
            if(cardList.size() == 0) {
                duel.setCardSelector(null);
                duel.select(cardSelector.getSourceItem(), container);
            }
        }
    }
}
