package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.OverRay;
import android.ygo.op.Operation;

public class MonsterPositionAction extends BaseAction {

    public MonsterPositionAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Field field = (Field) container;
        Card card = field.getTopCard();
        if (card != null) {
            card.changePosition();
            duel.select(card, container);
        }
    }
}
