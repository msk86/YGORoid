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
    private static boolean CARD_PARENT_HAS_CREATED_TOKEN = false;

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
        token = createToken(tokenParent);
        ((Field) container).setItem(token);
    }

    private Card createToken(Card tokenParent) {
        Card token = null;
        if(tokenParent != null && tokenParent.isToken()) {
            tokenParent = PREVIOUS_CARD_PARENT;
        }
        if (tokenParent != null) {
            token = createDynamicToken(tokenParent);
        }
        if (token == null) {
            token = createNormalToken();
        }
        token.negative();
        return token;
    }

    private Card createNormalToken(){
        PREVIOUS_CARD_PARENT = null;
        return new Card("0", "TOKEN", "TOKEN", Const.TYPE_TOKEN + Const.TYPE_MONSTER, Const.NULL, Const.NULL, 0, 0, 0);
    }

    private Card createDynamicToken(Card tokenParent) {
        int tokenSerial = getTokenSerial(tokenParent);
        int tokenId = Integer.parseInt(tokenParent.getId()) + tokenSerial;
        Card token = Utils.getDbHelper().loadByIdWithNull(tokenId);
        if(token != null && token.isToken()) {
            CARD_PARENT_HAS_CREATED_TOKEN = true;
        } else {
            if (CARD_PARENT_HAS_CREATED_TOKEN) {
                resetTokenSerial();
                token = createDynamicToken(tokenParent);
            }
        }
        if(token != null && !token.isToken()) {
            token = null;
        }
        return token;
    }

    private void resetTokenSerial() {
        TOKEN_SERIAL = 0;
    }

    private int getTokenSerial(Card tokenParent) {
        if (tokenParent == PREVIOUS_CARD_PARENT) {
            TOKEN_SERIAL++;
        } else {
            CARD_PARENT_HAS_CREATED_TOKEN = false;
            TOKEN_SERIAL = 1;
        }
        if(!tokenParent.isToken()) {
            PREVIOUS_CARD_PARENT = tokenParent;
        }
        return TOKEN_SERIAL;
    }
}
