package org.msk86.ygoroid.action;

import org.msk86.ygoroid.core.Card;
import org.msk86.ygoroid.core.Field;
import org.msk86.ygoroid.core.FieldType;
import org.msk86.ygoroid.core.OverRay;
import org.msk86.ygoroid.op.Operation;

public class MoveAction extends BaseAction {

    public MoveAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Field field = (Field) container;
        Card card = null;
        if (item instanceof Card) {
            card = (Card) item;
        } else if (item instanceof OverRay) {
            card = ((OverRay) item).topCard();
        }

        if (card != null) {
            if (field.getType().equals(FieldType.MAGIC_ZONE)
                    || field.getType().equals(FieldType.FIELD_MAGIC_ZONE)) {
                card.positive();
            } else if (field.getType().equals(FieldType.MONSTER_ZONE) && !card.isOpen()) {
                card.negative();
            }
        }

        field.setItem(item);
        duel.select(item, container);
    }
}
