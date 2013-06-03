package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.FieldType;
import android.ygo.op.Operation;

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
