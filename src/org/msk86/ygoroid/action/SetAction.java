package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.core.FieldType;
import org.msk86.ygoroid.op.Operation;

public class SetAction extends BaseAction {

    public SetAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Field field = (Field) container;
        Card card = (Card) item;
        if (field.getType() == FieldType.MONSTER_ZONE) {
            card.negative();
        } else if (field.getType() == FieldType.MAGIC_ZONE) {
            card.positive();
        }
        card.set();
        field.setItem(item);
        duel.select(card, container);
    }
}
