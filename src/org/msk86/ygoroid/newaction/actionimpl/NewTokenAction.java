package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.constant.Const;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;

public class NewTokenAction extends BaseAction {

    public NewTokenAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Selectable selectedItem = duel.getCurrentSelectItem();
        Card tokenParent = null, token = null;
        if (selectedItem instanceof Card) {
            tokenParent = (Card) selectedItem;
        }
        if (selectedItem instanceof OverRay) {
            tokenParent = ((OverRay) selectedItem).getOverRayCards().topCard();
        }
        if (selectedItem instanceof Deck) {
            Deck deck = (Deck) selectedItem;
            if (deck.getCardList().isOpen()) {
                tokenParent = deck.getCardList().topCard();
            }
        }

        if (tokenParent != null && isTokenable(tokenParent)) {
        }
    }

    public boolean isTokenable(Card card) {
        return (card.getCategory() & Const.CATEGORY_TOKEN) == Const.CATEGORY_TOKEN ||
                ((card.getDesc().contains("衍生物」") || card.getDesc().toLowerCase().contains("token\"") ||
                        card.getDesc().toLowerCase().contains("tokens\"") || card.getDesc().contains("代幣」") ||
                        card.getDesc().contains("トークン」")));
    }
}
