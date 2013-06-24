package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.OverRay;
import android.ygo.core.SelectableItem;
import android.ygo.op.Operation;

public class OverRayAction extends BaseAction {

    public OverRayAction(Operation operation) {
        super(operation.getDuel(), operation.getContainer(), operation.getItem());
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        Field field = (Field) container;
        SelectableItem targetItem = field.getItem();
        if (targetItem instanceof OverRay) {
            ((OverRay) targetItem).overRay(card);
        } else if (targetItem instanceof Card) {
            Card targetCard = (Card) targetItem;
            field.removeItem();
            OverRay overRay = new OverRay(targetCard);
            overRay.overRay(card);
            field.setItem(overRay);
        }
        OverRay overRay = (OverRay) field.getItem();
        overRay.adjust(field);
        duel.select(field.getItem(), container);
    }
}
