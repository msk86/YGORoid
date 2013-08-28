package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.op.Operation;

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
            duel.select(item, container);
        }
    }
}
