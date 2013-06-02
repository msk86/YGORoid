package android.ygo.action;

import android.ygo.core.*;
import android.ygo.op.Operation;

public class CloseRemoveTopAction extends BaseAction {
    public CloseRemoveTopAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card;
        Deck deck = (Deck) item;
        card = deck.pop();
        CardList removed = (CardList) duel.getDuelFields().getRemovedField().getItem();
        removed.push(card, true);
    }
}
