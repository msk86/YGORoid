package org.msk86.ygoroid.action;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.core.*;
import org.msk86.ygoroid.op.Operation;
import org.msk86.ygoroid.utils.Utils;

public class NewTokenAction extends BaseAction {
    public NewTokenAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        SelectableItem selectItem = duel.getCurrentSelectItem();
        Card tokenParent = null;
        if (selectItem instanceof Card) {
            tokenParent = (Card) selectItem;
        }
        if (selectItem instanceof OverRay) {
            tokenParent = ((OverRay) selectItem).topCard();
        }
        if (selectItem instanceof CardList) {
            CardList cardList = (CardList) selectItem;
            if (!cardList.getName().equals(Utils.s(R.string.DECK)) && !cardList.getName().equals(Utils.s(R.string.EX))) {
                tokenParent = cardList.topCard();
            }
        }

        Card token = null;
        if (tokenParent != null && tokenParent.isTokenable()) {
            String tokenId = tokenParent.nextTokenId();
            token = Utils.getDbHelper().loadByIdWithNull(Integer.parseInt(tokenId));
            if (token == null) {
                tokenParent.resetTokenId();
                tokenId = tokenParent.nextTokenId();
                token = Utils.getDbHelper().loadByIdWithNull(Integer.parseInt(tokenId));
            }
        }

        if (token == null) {
            String tokenId = "0";
            String tokenName = "TOKEN";
            token = new Card(tokenId, tokenName, "TOKEN", Const.TYPE_TOKEN + Const.TYPE_MONSTER, Const.NULL, Const.NULL, 0, 0, 0);
        }

        token.negative();
        Field field = (Field) container;
        field.setItem(token);
    }
}
