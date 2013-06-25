package android.ygo.action;

import android.ygo.core.*;
import android.ygo.op.Operation;

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
            if(!cardList.getName().equals(CardList.DECK) && !cardList.getName().equals(CardList.EX)) {
                tokenParent = cardList.topCard();
            }
        }

        String tokenId = "0";
        String tokenName = "TOKEN";
        if(tokenParent != null && tokenParent.isTokenable()) {
            tokenId = tokenParent.nextTokenId();
            tokenName = tokenParent.getName() + tokenName;
        }

        Card token = new Card(tokenId, tokenName, "TOKEN", Const.TYPE_TOKEN + Const.TYPE_MONSTER, Const.NULL, Const.NULL, 0, 0, 0);
        token.negative();
        Field field = (Field) container;
        field.setItem(token);
    }
}
