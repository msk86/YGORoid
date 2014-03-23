package org.msk86.ygoroid.newaction.dueldisk.actionimpl;

import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.Field;
import org.msk86.ygoroid.newcore.impl.OverRay;
import org.msk86.ygoroid.newop.Operation;
import org.msk86.ygoroid.newop.impl.StartDrag;

public class DragOverRayAction extends BaseAction {
    public DragOverRayAction(Operation operation) {
        super(operation);
    }

    @Override
    public void execute() {
        OverRay overRay = (OverRay) item;
        Field field = (Field) container;

        if(overRay.isSelect() || overRay.getOverRayCards().size() == 1) {
            Card topCard = overRay.getOverRayCards().topCard();
            ((StartDrag) operation).setDragItem(topCard);
            overRay.getOverRayCards().remove(topCard);
            duel.select(topCard);
            if(overRay.getOverRayCards().size() == 0) {
                field.removeItem();
            }
        } else {
            field.removeItem();
            ((StartDrag) operation).setDragItem(overRay);
            duel.select(overRay);
        }
    }
}
