package org.msk86.ygoroid.newaction.actionimpl;

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

        if(overRay.isDeepSelect()) {
            Card topCard = overRay.getOverRayCards().topCard();
            overRay.getOverRayCards().remove(topCard);
            ((StartDrag) operation).setItem(topCard);
            duel.select(topCard);
            if(overRay.getOverRayCards().size() == 1) {
                field.setItem(overRay.getOverRayCards().topCard());
            }
        } else {
            field.removeItem();
        }
    }
}
