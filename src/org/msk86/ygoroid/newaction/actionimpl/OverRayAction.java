package org.msk86.ygoroid.newaction.actionimpl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Selectable;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;

public class OverRayAction extends BaseAction {
    public OverRayAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        Card card = (Card) item;
        Field field = (Field) container;
        Item targetItem = field.getItem();

        if(targetItem instanceof Card) {
            OverRay overRay = new OverRay();
            overRay.overRay((Card) targetItem);
            overRay.overRay(card);
            field.setItem(overRay);
        }
        if(targetItem instanceof OverRay) {
            ((OverRay) targetItem).overRay(card);
        }
        if(field.getItem() instanceof Selectable) {
            duel.select((Selectable) field.getItem());
        }
    }
}
