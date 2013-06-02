package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.OverRay;
import android.ygo.op.Operation;

public class MonsterPositionAction extends BaseAction {

    public MonsterPositionAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card;
        if (item instanceof OverRay) {
            card = ((OverRay) item).topCard();
        } else {
            card = (Card) item;
        }
        if (card != null) {
            card.changePosition();
            duel.select(card, container);
        }
    }
}
