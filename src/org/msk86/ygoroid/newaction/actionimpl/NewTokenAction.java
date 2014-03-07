package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.constant.Const;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Deck;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newutils.Utils;

public class NewTokenAction extends BaseAction {
    private static Card PREVIOUS_CARD_PARENT = null;
    private static int TOKEN_SERIAL = 1;

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

        if (tokenParent != null) {
            int tokenSerial = getTokenSerial(tokenParent);
            int tokenId = Integer.parseInt(tokenParent.getId()) + tokenSerial;
            token = Utils.getDbHelper().loadByIdWithNull(tokenId);
            if (!token.isToken()) {
                token = null;
            }
        }

        if (token == null) {
            token = new Card("0", "TOKEN", "TOKEN", Const.TYPE_TOKEN + Const.TYPE_MONSTER, Const.NULL, Const.NULL, 0, 0, 0);
        }

        token.negative();
        ((Field) container).setItem(token);
    }

    private int getTokenSerial(Card tokenParent) {
        if (tokenParent == PREVIOUS_CARD_PARENT) {
            TOKEN_SERIAL++;
        } else {
            TOKEN_SERIAL = 1;
        }
        PREVIOUS_CARD_PARENT = tokenParent;
        return TOKEN_SERIAL;
    }
}
